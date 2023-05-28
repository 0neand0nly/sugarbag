import com.sun.source.tree.*;
import com.sun.source.util.*;
import com.sun.tools.javac.api.BasicJavacTask;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.TypeTag;
import com.sun.tools.javac.tree.*;
import com.sun.tools.javac.tree.JCTree.JCExpression;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.ListBuffer;
import com.sun.tools.javac.util.Names;

import javax.tools.JavaCompiler;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A {@link JavaCompiler javac} plugin which inserts {@code >= min && <= max} checks into resulting {@code *.class} files
 * for string method parameters marked by {@link Numeric}
 */
public class NumericJavacPlugin implements Plugin {

    public static final String NAME = "NumericPlugin";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void init(JavacTask task, String... args) {
        Context context = ((BasicJavacTask) task).getContext();
        task.addTaskListener(new TaskListener() {
            @Override
            public void started(TaskEvent e) {
            }

            @Override
            public void finished(TaskEvent e) {
                if (e.getKind() != TaskEvent.Kind.PARSE) {
                    return;
                }
                e.getCompilationUnit()
                    .accept(new TreeScanner<Void, Void>() {
                        @Override
                        public Void visitMethod(MethodTree method, Void v) {
                            List<VariableTree> parametersToInstrument = method.getParameters()
                                .stream()
                                .filter(NumericJavacPlugin.this::shouldInstrument)
                                .collect(Collectors.toList());
                            if (!parametersToInstrument.isEmpty()) {
                                // Process parameters RTL to ensure correct order.
                                Collections.reverse(parametersToInstrument);
                                parametersToInstrument.forEach(p -> addCheck(method, p, context));
                            }
                            return super.visitMethod(method, v);
                        }
                    }, null);
            }
        });
    }

    private boolean shouldInstrument(VariableTree parameter) {
        return "String".equals(parameter.getType().toString())
          && parameter.getModifiers().getAnnotations()
            .stream()
            .anyMatch(a -> Numeric.class.getSimpleName().equals(a.getAnnotationType().toString()));
    }

    private void addCheck(MethodTree method, VariableTree parameter, Context context) {
        JCExpression check = createCheck(parameter, context);
        JCTree.JCBlock body = (JCTree.JCBlock) method.getBody();
        body.stats = body.stats.prepend(check);
    }

    private JCExpression createCheck(VariableTree parameter, Context context) {
        TreeMaker factory = TreeMaker.instance(context);
        Names symbolsTable = Names.instance(context);
        
        Symbol.VarSymbol varSymbol = (Symbol.VarSymbol) TreeInfo.symbol(parameter);
        Numeric numeric = varSymbol.getAnnotation(Numeric.class);

        ListBuffer<JCExpression> checks = new ListBuffer<>();

        String parameterName = parameter.getName().toString();
        Name parameterId = symbolsTable.fromString(parameterName);

        if (numeric.min() != Double.NEGATIVE_INFINITY) {
            checks.add(factory.Binary(JCTree.Tag.GE, 
                factory.Ident(parameterId), 
                factory.Literal(numeric.min())));
        }

        if (numeric.max() != Double.POSITIVE_INFINITY) {
            checks.add(factory.Binary(JCTree.Tag.LE, 
                factory.Ident(parameterId), 
                factory.Literal(numeric.max())));
        }

        JCExpression check = checks.remove();
        while (!checks.isEmpty()) {
            check = factory.Binary(JCTree.Tag.AND, check, checks.remove());
        }
        
        return check;
    }

}
