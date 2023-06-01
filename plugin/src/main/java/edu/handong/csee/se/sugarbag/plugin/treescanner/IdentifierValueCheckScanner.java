package edu.handong.csee.se.sugarbag.plugin.treescanner;

import com.sun.source.tree.AssignmentTree;
import com.sun.source.tree.CompoundAssignmentTree;
import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.UnaryTree;
import com.sun.source.util.TreeScanner;
import com.sun.tools.javac.tree.JCTree;

/**
 * Scanner that checks whether the value of the given identifier changes or not.
 */
public class IdentifierValueCheckScanner extends TreeScanner<Boolean, String> {
    
    @Override 
    public Boolean reduce(Boolean r1, Boolean r2) {
        return (r1 != null && r1 == true) || (r2 != null && r2 == true);
    }

    @Override
    public Boolean visitAssignment(AssignmentTree node, String p) {
        return node.getVariable().toString().equals(p) 
                ? true : super.visitAssignment(node, p);      
    }

    @Override
    public Boolean visitCompoundAssignment(CompoundAssignmentTree node, 
                                           String p) {
        return node.getVariable().toString().equals(p) 
                ? true : super.visitCompoundAssignment(node, p);                                        
    }

    @Override 
    public Boolean visitUnary(UnaryTree node, String p) {
        JCTree.Tag tag = ((JCTree.JCUnary) node).getTag();
        
        return super.visitUnary(node, p) 
                && (tag == JCTree.Tag.POSTDEC || tag == JCTree.Tag.POSTINC 
                    || tag == JCTree.Tag.PREDEC || tag == JCTree.Tag.PREINC);
    }

    @Override
    public Boolean visitIdentifier(IdentifierTree node, String p) {
        return node.getName().toString().equals(p);
    }
}
