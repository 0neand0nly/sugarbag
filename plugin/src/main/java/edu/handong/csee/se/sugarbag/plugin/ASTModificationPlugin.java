package edu.handong.csee.se.sugarbag.plugin;

import java.util.ArrayList;
import java.util.List;

import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TreeVisitor;
import com.sun.source.util.JavacTask;
import com.sun.source.util.Plugin;
import com.sun.source.util.TaskEvent;
import com.sun.source.util.TaskListener;
import com.sun.source.util.TaskEvent.Kind;
import com.sun.tools.javac.api.BasicJavacTask;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.Names;

public abstract class ASTModificationPlugin implements Plugin {

    @Override 
    public void init(JavacTask task, String... args) {
        Context context = ((BasicJavacTask) task).getContext();
        
        task.addTaskListener(new TaskListener() {
            
            @Override
            public void finished(TaskEvent e) {
                if (e.getKind() != Kind.PARSE) {
                    return;
                }
                
                e.getCompilationUnit()
                 .accept(createVisitor(context), new ArrayList<Tree>());
            }
        }); 
    }

    /**
     * Creates the <code>TreeVisitor</code> that visits AST nodes 
     * with the given context.
     * @param context the context of <code>JavacTask</code>
     * @return the <code>TreeVisitor</code>
     */
    protected abstract TreeVisitor<Void, List<Tree>> createVisitor(
            int context);
}
