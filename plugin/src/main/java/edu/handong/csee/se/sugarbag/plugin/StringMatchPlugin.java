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
import com.sun.source.tree.VariableTree;
import com.sun.source.util.JavacTask;
import com.sun.source.util.Plugin;
import com.sun.source.util.TaskEvent;
import com.sun.source.util.TaskListener;
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

public class StringMatchPlugin implements Plugin{
    public static final String NAME = "StringMatchPlugin";

    private static Set<String> TARGET_TYPES = new HashSet<>(Arrays.asList(
        // Use only primitive types for simplicity
        byte.class.getName(), short.class.getName(), char.class.getName(),
        int.class.getName(), long.class.getName(), float.class.getName(), double.class.getName()));

    @Override
    public String getName() {
        return NAME;
    }

    // @Override
    // public void init(JavacTask task, String... args) {
    //     Context context = ((BasicJavacTask) task).getContext();
    //     task.addTaskListener(new TaskListener() {
    //         @Override
    //         public void started(TaskEvent e) {
    //         }

    //         @Override
    //         public void finished(TaskEvent e) {
    //             if (e.getKind() != TaskEvent.Kind.PARSE) {
    //                 return;
    //             }
                
    //             e.getCompilationUnit().accept(new TreeScanner<Void, Void>() {
    //                 @Override
    //                 public Void visitClass(ClassTree node, Void aVoid) {
    //                     return super.visitClass(node, aVoid);
    //                 }

    //                 @Override
    //                 public Void visitMethod(MethodTree method, Void v) {
    //                     List<VariableTree> parametersToInstrument
    //                             = method.getParameters().stream()
    //                             .filter(StringMatchPlugin.this::shouldInstrument)
    //                             .collect(Collectors.toList());
    //                     if(!parametersToInstrument.isEmpty()) {
    //                         Collections.reverse(parametersToInstrument);
    //                         parametersToInstrument.forEach(p -> addCheck(method, p, context));
    //                     }
    //                     return super.visitMethod(method, v);
    //                 }
    //             }, null);
    //         }
    //     });
    
    // }

    private boolean shouldInstrument(VariableTree parameter) {
        return parameter.getModifiers().getAnnotations()
                .stream()
                .anyMatch(a -> StringMatch.class.getSimpleName().equals(a.getAnnotationType().toString()));
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
                .If(createIfCondition(factory, symbolsTable, parameter),
                    createIfBlock(factory, symbolsTable, parameter),
                    null);
    }

    private static JCTree.JCExpression createIfCondition(TreeMaker factory, Names symbolsTable, VariableTree parameter) {
        Name parameterId = symbolsTable.fromString(parameter.getName().toString());
        String format = ""; // extract format from @StringMatch annotation

        for (AnnotationTree annotation : parameter.getModifiers().getAnnotations()) {
            if (annotation.getAnnotationType().toString().equals(StringMatch.class.getSimpleName())) {
                for (Tree arg : annotation.getArguments()) {
                    if (arg.toString().startsWith("format=") || arg.toString().startsWith("format =")) {
                        format = arg.toString().substring(arg.toString().indexOf("\"") + 1, arg.toString().lastIndexOf("\""));
                    }
                }
            }
        }
        if(format.startsWith("\\")){
            format = format.substring(1);
        }
        
        // Create unary condition for checking if parameter does not match format
        JCLiteral formatLiteral = factory.Literal(TypeTag.CLASS, format);

        JCTree.JCMethodInvocation formatCheck = factory.Apply(
            com.sun.tools.javac.util.List.nil(),
            factory.Select(factory.Ident(parameterId), symbolsTable.fromString("matches")),
            com.sun.tools.javac.util.List.of(formatLiteral)
        );
        
        return factory.Unary(JCTree.Tag.NOT, formatCheck);
    }

    private static JCTree.JCBlock createIfBlock (TreeMaker factory, Names symbolsTable, VariableTree parameter) {
        String parameterName = parameter.getName().toString();
        Name parameterId = symbolsTable.fromString(parameterName);
    
        String errorMessagePrefix = String.format(
                "Argument '%s' does not match format '",
                parameterName);
        String errorMessageSuffix = "'";
    
        return factory.Block(0, com.sun.tools.javac.util.List.of(
                factory.Throw(
                        factory.NewClass(null, null,
                                factory.Ident(symbolsTable.fromString(
                                        IllegalArgumentException.class.getSimpleName())),
                                com.sun.tools.javac.util.List.of(factory.Binary(JCTree.Tag.PLUS,
                                        factory.Binary(JCTree.Tag.PLUS,
                                                factory.Literal(errorMessagePrefix),
                                                factory.Ident(parameterId)),
                                        factory.Literal(errorMessageSuffix))), null))));
    }

    @Override
    protected TreeScanner<Void, List<Tree>> createVisitor(Context context) {
        TreeScanner treeScanner = new TreeScanner<Void, Void>() {
            @Override
            public Void visitClass(ClassTree node, Void aVoid) {
                return super.visitClass(node, aVoid);
            }

            @Override
            public Void visitMethod(MethodTree method, Void v) {
                List<VariableTree> parametersToInstrument
                        = method.getParameters().stream()
                        .filter(StringMatchPlugin.this::shouldInstrument)
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
