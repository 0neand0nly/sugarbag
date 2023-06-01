package edu.handong.csee.se.sugarbag.plugin;

import java.util.List;

import com.sun.source.tree.Tree;
import com.sun.source.tree.TreeVisitor;
import com.sun.source.util.TreeScanner;
import com.sun.tools.javac.util.Context;

import edu.handong.csee.se.sugarbag.plugin.treescanner.DebugScanner;

public class DebugPlugin extends ASTModificationPlugin {
    private final String NAME = "DebugPlugin";

    @Override
    protected TreeScanner<Void, List<Tree>> createVisitor(Context context) {
        return new DebugScanner(context, "Debug", null);
    }

    @Override
    public String getName() {
        return NAME;
    }
}
