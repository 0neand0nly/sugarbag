package edu.handong.csee.se.sugarbag.plugin.treescanner;

import java.util.List;

import com.sun.source.tree.BlockTree;
import com.sun.source.tree.CaseTree;
import com.sun.source.tree.DoWhileLoopTree;
import com.sun.source.tree.EnhancedForLoopTree;
import com.sun.source.tree.ForLoopTree;
import com.sun.source.tree.IfTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.WhileLoopTree;
import com.sun.source.util.TreeScanner;
import com.sun.tools.javac.util.Context;

/**
 * Scanner that modifies AST based on the annotation.
 */
@SuppressWarnings("unchecked")
public abstract class ASTModificationScanner 
        extends TreeScanner<Void, List<Tree>> {    
    protected Context context;
    protected String annotationName;
    protected String identifierType;

    public ASTModificationScanner(Context context, String annotationName, 
                                  String identifierType) {
        this.context = context;
        this.annotationName = annotationName;
        this.identifierType = identifierType;
    }

    public ASTModificationScanner(Context context, String annotationName) {
        this.context = context;
        this.annotationName = annotationName;
    }

    @Override
    public Void visitBlock(BlockTree node, List<Tree> p) {
        List<Tree> targets = findFromBlock(node);
        
        if (targets != null) {
            p.addAll(targets);
        }
        
        super.visitBlock(node, p);
        modifyBlock(node, p);
        
        if (targets != null) {
            p.removeAll(targets);
        }

        return null;
    }

    @Override
    public Void visitForLoop(ForLoopTree node, List<Tree> p) {
        List<Tree> targets = findFromForLoop(node);
        
        if (targets != null) {
            p.addAll(targets);
        }

        super.visitForLoop(node, p);
        modifyForLoop(node, p);
        
        if (targets != null) {
            p.removeAll(targets);
        }
    
        return null;
    }

    @Override
    public Void visitEnhancedForLoop(EnhancedForLoopTree node, List<Tree> p) {
        List<Tree> targets = findFromEnhancedForLoop(node);
        
        if (targets != null) {
            p.addAll(targets);
        }

        super.visitEnhancedForLoop(node, p);
        modifyEnhancedForLoop(node, p);
        
        if (targets != null) {
            p.removeAll(targets);
        }
    
        return null;
    }

    @Override
    public Void visitWhileLoop(WhileLoopTree node, List<Tree> p) {
        List<Tree> targets = findFromWhileLoop(node);

        if (targets != null) {
            p.addAll(targets);
        }

        super.visitWhileLoop(node, p);
        modifyWhileLoop(node, p);
   
        if (targets != null) {
            p.removeAll(targets);
        }
    
        return null;
    }

    @Override
    public Void visitDoWhileLoop(DoWhileLoopTree node, List<Tree> p) {
        List<Tree> targets = findFromDoWhileLoop(node);
        
        if (targets != null) {
            p.addAll(targets);
        }

        super.visitDoWhileLoop(node, p);
        modifyDoWhileLoop(node, p);
        
        if (targets != null) {
            p.removeAll(targets);
        }

        return null;
    }

    @Override
    public Void visitIf(IfTree node, List<Tree> p) {
        List<Tree> targets = findFromIf(node);
        
        if (targets != null) {
            p.addAll(targets);
        }

        super.visitIf(node, p);
        modifyIf(node, p);

        if (targets != null) {
            p.removeAll(targets);
        }
    
        return null;
    }

    @Override
    public Void visitCase(CaseTree node, List<Tree> p) {
        List<Tree> targets = findFromCase(node);
        
        if (targets != null) {
            p.addAll(targets);
        }

        super.visitCase(node, p);
        modifyCase(node, p);

        if (targets != null) {
            p.removeAll(targets);
        }

        return null;
    }

    protected List<Tree> findFromMethod(MethodTree node) {
        return null;
    }
    
    protected List<Tree> findFromBlock(BlockTree node) { 
        return null; 
    }

    protected List<Tree> findFromForLoop(ForLoopTree node) { 
        return null; 
    }

    protected List<Tree> findFromEnhancedForLoop(EnhancedForLoopTree node) { 
        return null; 
    }

    protected List<Tree> findFromWhileLoop(WhileLoopTree node) { 
        return null; 
    }

    protected List<Tree> findFromDoWhileLoop(DoWhileLoopTree node) { 
        return null; 
    }

    protected List<Tree> findFromIf(IfTree node) { 
        return null; 
    }

    protected List<Tree> findFromCase(CaseTree node) { 
        return null; 
    } 

    protected void modifyMethod(MethodTree node, 
                                List<Tree> targets) {}

    protected void modifyBlock(BlockTree node, 
                               List<Tree> targets) {}

    protected void modifyForLoop(ForLoopTree node, 
                                 List<Tree> targets) {}

    protected void modifyEnhancedForLoop(EnhancedForLoopTree node,
                                         List<Tree> targets) {}

    protected void modifyWhileLoop(WhileLoopTree node, 
                                   List<Tree> targets) {}

    protected void modifyDoWhileLoop(DoWhileLoopTree node, 
                                     List<Tree> targets) {}

    protected void modifyIf(IfTree node, 
                            List<Tree> targets) {}

    protected void modifyCase(CaseTree node, 
                              List<Tree> targets) {}     
}
