package edu.handong.csee.se.sugarbag.plugin;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// import javax.naming.Context;
import javax.tools.JavaCompiler;

import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TreeVisitor;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.JavacTask;
import com.sun.source.util.Plugin;
import com.sun.source.util.TaskEvent;
import com.sun.source.util.TaskListener;
import com.sun.source.util.TreeScanner;
import com.sun.tools.javac.api.BasicJavacTask;
import com.sun.tools.javac.code.TargetType;
import com.sun.tools.javac.code.TypeTag;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.Name;
import com.sun.tools.javac.util.Names;

public class NumericPlugin extends ASTModificationPlugin {
    public static final String NAME = "NumericPlugin";

    // @Override
    public String getName() {
        return NAME;
    }

    private boolean shouldInstrument(VariableTree parameter) {
        return "String".contains(parameter.getType().toString())
          && parameter.getModifiers().getAnnotations()
            .stream()
            .anyMatch(a -> Numeric.class.getSimpleName().equals(a.getAnnotationType().toString()));
    }

    private void addCheck(MethodTree method, VariableTree parameter, Context context) {
        JCTree.JCIf check = createCheck(parameter, context);
        JCTree.JCBlock body = (JCTree.JCBlock) method.getBody();
        body.stats = body.stats.prepend(check);
    }

    private static JCTree.JCIf createCheck(VariableTree parameter, Context context) {
        TreeMaker factory = TreeMaker.instance(context);
        Names symbolsTable = Names.instance(context);
        
        return factory.at(((JCTree) parameter).pos)
                .If(factory.Parens(createIfCondition(factory, symbolsTable, parameter)),
                createIfBlock(factory, symbolsTable, parameter),
                null);
    }

    private static JCTree.JCExpression createIfCondition(TreeMaker factory, Names symbolsTable, VariableTree parameter) {
        Name parameterId = symbolsTable.fromString(parameter.getName().toString());
        // Extract min and max values from @Numeric annotation
        double min = Double.NEGATIVE_INFINITY;
        double max = Double.POSITIVE_INFINITY;
        String numericType = null;

        for (AnnotationTree annotation : parameter.getModifiers().getAnnotations()) {
            if (annotation.getAnnotationType().toString().equals(Numeric.class.getSimpleName())) {
                for (Tree arg : annotation.getArguments()) {
                    if (arg.toString().startsWith("min=") || arg.toString().startsWith("min =")) {
                        min = extractDouble(arg.toString());
                    } else if (arg.toString().startsWith("max=") || arg.toString().startsWith("max =")) {
                        max = extractDouble(arg.toString());
                    } else if (arg.toString().startsWith("numericType=") || arg.toString().startsWith("numericType =")) {
                        numericType = arg.toString().split("=")[1].trim().replaceAll("\"", "");
                    }
                }
            }
        }

        // Check if parameter's type does not match numericType
        // Create type-checking condition based on numericType
        JCTree.JCUnary typeCheckNot = createTypeCheckCondition(factory, symbolsTable, parameterId, numericType);

        JCTree.JCMethodInvocation parseMethod;
        if(numericType.equals("int") || numericType.equals("Integer")) {
            parseMethod = factory.Apply(com.sun.tools.javac.util.List.nil(),
                    factory.Select(factory.Ident(symbolsTable.fromString("Integer")), symbolsTable.fromString("parseInt")), 
                    com.sun.tools.javac.util.List.of(factory.Ident(parameterId)));
        } else {
            parseMethod = factory.Apply(com.sun.tools.javac.util.List.nil(),
                    factory.Select(factory.Ident(symbolsTable.fromString("Double")), symbolsTable.fromString("parseDouble")), 
                    com.sun.tools.javac.util.List.of(factory.Ident(parameterId)));
        }
        
        // Create binary condition for checking if parameter < min
        JCTree.JCBinary lessThanMin  = factory.Binary(JCTree.Tag.GT, 
                parseMethod, 
                factory.Literal(min));
    
        // Create binary condition for checking if parameter > max
        JCTree.JCBinary greaterThanMax = factory.Binary(JCTree.Tag.LT, 
                parseMethod, 
                factory.Literal(max));
        
        // Connect the three conditions with 'OR' operators
        return factory.Binary(JCTree.Tag.OR,
            factory.Binary(JCTree.Tag.OR, typeCheckNot, lessThanMin),
            greaterThanMax);
    }

    private static JCTree.JCBlock createIfBlock (TreeMaker factory, Names symbolsTable, VariableTree parameter) {
        String parameterName = parameter.getName().toString();
        Name parameterId = symbolsTable.fromString(parameterName);

        String errorMessagePrefix = String.format(
                "Argument '%s' of type %s is marked by @%s but got '",
                parameterName, parameter.getType(), Numeric.class.getSimpleName());
        String errorMessageSuffix = "' for it";

        return factory.Block(0, com.sun.tools.javac.util.List.of(
                factory.Throw(
                        factory.NewClass(null, null,//nil(),
                                factory.Ident(symbolsTable.fromString(
                                        IllegalArgumentException.class.getSimpleName())),
                                com.sun.tools.javac.util.List.of(factory.Binary(JCTree.Tag.PLUS,
                                        factory.Binary(JCTree.Tag.PLUS,
                                                factory.Literal(TypeTag.CLASS, errorMessagePrefix),
                                                factory.Ident(parameterId)),
                                        factory.Literal(TypeTag.CLASS, errorMessageSuffix))), null))));
    }

    private static double extractDouble(String s) {
        String temp = "";
        for(int i = 0; i < s.length(); i++){
            if('0' <= s.charAt(i) && s.charAt(i) <= '9')
                temp += s.charAt(i);
        }
        return Double.parseDouble(temp);
    }

    private static JCTree.JCUnary createTypeCheckCondition(TreeMaker factory, Names symbolsTable, Name parameterId, String numericType) {
        JCTree.JCExpression pattern = null;
    
        switch (numericType) {
            case "int":
                // Check if parameter is not a valid int
                pattern = factory.Literal("^-?\\d+$");
                break;
            case "double":
                // Check if parameter is not a valid double
                pattern = factory.Literal("^-?\\d*\\.?\\d+$");
                break;
            case "float":
                // Check if parameter is not a valid float
                pattern = factory.Literal("^-?\\d*\\.?\\d+f$");
                break;
            default:
                // No specific numericType, so always return false (non-matching)
                pattern = factory.Literal("a^");
                break;
        }
    
        JCTree.JCFieldAccess matchesMethod = factory.Select(factory.Ident(parameterId), symbolsTable.fromString("matches"));
        JCTree.JCMethodInvocation matchesCall = factory.Apply(com.sun.tools.javac.util.List.nil(), matchesMethod, com.sun.tools.javac.util.List.of(pattern));
        
        return factory.Unary(JCTree.Tag.NOT, matchesCall);
    }//!num.matches()
    
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
                        .filter(NumericPlugin.this::shouldInstrument)
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
