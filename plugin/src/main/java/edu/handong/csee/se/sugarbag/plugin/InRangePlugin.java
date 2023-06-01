package edu.handong.csee.se.sugarbag.plugin;

import com.sun.source.util.TaskEvent.Kind;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TreeVisitor;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.TreeScanner;
import com.sun.tools.javac.tree.JCTree.JCLiteral;
import com.sun.tools.javac.api.BasicJavacTask;
import com.sun.tools.javac.code.TargetType;
import com.sun.tools.javac.code.TypeTag;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.Name;
import com.sun.tools.javac.util.Names;

import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.AssignmentTree;
import com.sun.source.tree.LiteralTree;

import javax.tools.JavaCompiler;
import java.util.*;
import java.util.stream.Collectors;

public class InRangePlugin extends ASTModificationPlugin {
    public static String NAME = "InRangePlugin";

    @Override
    public String getName() {
        return NAME;
    }

    private boolean shouldInstrument(VariableTree parameter) {
        return parameter.getModifiers().getAnnotations()
                .stream()
                .anyMatch(a -> InRange.class.getSimpleName().equals(a.getAnnotationType().toString()));
    }

    private void addCheck(MethodTree method, VariableTree parameter, Context context) {
        JCTree.JCIf check = createCheck(parameter, context);
        JCTree.JCBlock body = (JCTree.JCBlock) method.getBody();
        body.stats = body.stats.prepend(check);
    }

    private static JCTree.JCIf createCheck(VariableTree parameter, Context context) {
        TreeMaker factory = TreeMaker.instance(context);
        Names symbolsTable = Names.instance(context);

        JCTree.JCBinary condition = createIfCondition(factory, symbolsTable, parameter);
        JCTree.JCBlock block = createIfBlock(factory, symbolsTable, parameter);

        return factory.at(((JCTree) parameter).pos)
                .If(factory.Parens(condition), block, null);
    }

    private static JCTree.JCBinary createIfCondition(TreeMaker factory, Names symbolsTable, VariableTree parameter) {
        Name parameterId = symbolsTable.fromString(parameter.getName().toString());

        JCTree.JCExpression minValue = getAnnotationValue(factory, parameter, "min");
        JCTree.JCExpression maxValue = getAnnotationValue(factory, parameter, "max");

        return factory.Binary(
                JCTree.Tag.OR,
                factory.Binary(JCTree.Tag.LE, factory.Ident(parameterId), minValue),
                factory.Binary(JCTree.Tag.GE, factory.Ident(parameterId), maxValue)
        );
    }

    private static JCTree.JCBlock createIfBlock(TreeMaker factory, Names symbolsTable, VariableTree parameter) {
        String parameterName = parameter.getName().toString();
        Name parameterId = symbolsTable.fromString(parameterName);

        JCTree.JCExpression minValue = getAnnotationValue(factory, parameter, "min");
        JCTree.JCExpression maxValue = getAnnotationValue(factory, parameter, "max");

        String errorMessagePrefix = String.format(
                "Argument '%s' of type %s is marked by @%s but got '",
                parameterName, parameter.getType(), InRange.class.getSimpleName());
        String errorMessageSuffix = String.format("' for it When min is '%s' and max is '%s'", minValue, maxValue);

        return factory.Block(0, com.sun.tools.javac.util.List.of(
                factory.Throw(
                        factory.NewClass(null, null,
                                factory.Ident(symbolsTable.fromString(
                                        IllegalArgumentException.class.getSimpleName())),
                                com.sun.tools.javac.util.List.of(factory.Binary(JCTree.Tag.PLUS,
                                        factory.Binary(JCTree.Tag.PLUS,
                                                factory.Literal(TypeTag.CLASS, errorMessagePrefix),
                                                factory.Ident(parameterId)),
                                        factory.Literal(TypeTag.CLASS, errorMessageSuffix))), null))));
    }

    private static JCTree.JCExpression getAnnotationValue(TreeMaker factory, VariableTree parameter, String fieldName) {
        List<? extends AnnotationTree> annotations = parameter.getModifiers().getAnnotations();
        AnnotationTree inRangeAnnotation = annotations.stream()
                .filter(a -> InRange.class.getSimpleName().equals(a.getAnnotationType().toString()))
                .findFirst()
                .orElse(null);

        if (inRangeAnnotation != null) {
            ExpressionTree valueExpression = inRangeAnnotation.getArguments().stream()
                    .filter(arg -> arg instanceof AssignmentTree)
                    .map(arg -> (AssignmentTree) arg)
                    .filter(assignment -> fieldName.equals(assignment.getVariable().toString()))
                    .map(AssignmentTree::getExpression)
                    .findFirst()
                    .orElse(null);

            if (valueExpression != null && valueExpression instanceof LiteralTree) {
                LiteralTree literal = (LiteralTree) valueExpression;
                return factory.Literal(literal.getValue());
            }
        }

        // Default to minimum and maximum value of the parameter type
        return getDefaultMinMaxValue(factory, parameter);
    }

    private static JCTree.JCExpression getDefaultMinMaxValue(TreeMaker factory, VariableTree parameter) {
        String typeName = parameter.getType().toString();
        switch (typeName) {
            case "byte":
                return factory.Literal(Byte.MIN_VALUE);
            case "short":
                return factory.Literal(Short.MIN_VALUE);
            case "char":
                return factory.Literal(Character.MIN_VALUE);
            case "int":
                return factory.Literal(Integer.MIN_VALUE);
            case "long":
                return factory.Literal(Long.MIN_VALUE);
            case "float":
                return factory.Literal(Float.NEGATIVE_INFINITY);
            case "double":
                return factory.Literal(Double.NEGATIVE_INFINITY);
            default:
                return factory.Literal(TypeTag.BOT, null);
        }
    }
    
    @Override
    protected TreeScanner<Void, List<Tree>> createVisitor(Context context) {
        TreeScanner treeScanner = new TreeScanner<Void, List<Tree>>() {
            @Override
            public Void visitClass(ClassTree node, List<Tree> aVoid) {
                return super.visitClass(node, aVoid);
            }

            @Override
            public Void visitMethod(MethodTree method, List<Tree> v) {
                List<VariableTree> parametersToInstrument
                        = method.getParameters().stream()
                        .filter(InRangePlugin.this::shouldInstrument)
                        .collect(Collectors.toList());
                if(!parametersToInstrument.isEmpty()) {
                    Collections.reverse(parametersToInstrument);
                    parametersToInstrument.forEach(p -> addCheck(method, p, context));
                }
                return super.visitMethod(method, v);
            }
        };
        return treeScanner;
    }
}