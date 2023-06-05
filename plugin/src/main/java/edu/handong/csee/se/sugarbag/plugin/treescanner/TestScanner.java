package edu.handong.csee.se.sugarbag.plugin.treescanner;

import java.util.List;

import com.sun.source.tree.BlockTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.UnaryTree;
import com.sun.source.tree.VariableTree;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.Context;

public class TestScanner extends ASTModificationScanner {
    
    public TestScanner(Context context, String annotationName) {
        super(context, annotationName);
    }

    @Override
    protected void modifyBlock(BlockTree node, List<Tree> targets) {
        System.out.println(((VariableTree) node.getStatements().get(0)).getName());
    }

}
