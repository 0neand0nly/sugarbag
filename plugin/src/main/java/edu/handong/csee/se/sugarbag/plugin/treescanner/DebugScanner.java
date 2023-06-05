package edu.handong.csee.se.sugarbag.plugin.treescanner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sun.source.tree.BlockTree;
import com.sun.source.tree.EnhancedForLoopTree;
import com.sun.source.tree.ExpressionStatementTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.ForLoopTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.StatementTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TreeVisitor;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.TreeScanner;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.ListBuffer;

import edu.handong.csee.se.sugarbag.plugin.utils.AnnotationTargetFinder;

public class DebugScanner extends ASTModificationScanner {
	private final int NUM_DEBUG_STATEMENT = 3;

	private IdentifierValueCheckScanner scanner = 
			new IdentifierValueCheckScanner();

    public DebugScanner(Context context, String annotationName, 
                        String identifierType) {
        super(context, annotationName, identifierType);
    }

    @Override
    protected List<Tree> findFromMethod(MethodTree node) {
        return AnnotationTargetFinder.findTargetVariables(
                node.getParameters(), annotationName, identifierType);
    }
    
    @Override 
    protected List<Tree> findFromBlock(BlockTree node) { 
        return AnnotationTargetFinder.findTargetVariables(
                node.getStatements(), annotationName, identifierType);
    }

    @Override 
    protected List<Tree> findFromForLoop(ForLoopTree node) {
        return AnnotationTargetFinder.findTargetVariables(
                node.getInitializer(), annotationName, identifierType);
    }

    @Override 
    protected List<Tree> findFromEnhancedForLoop(EnhancedForLoopTree node) {
        return AnnotationTargetFinder.findTargetVariables(
                Arrays.asList(node.getVariable()), annotationName, 
                identifierType);
    }

	@Override 
	protected void modifyMethod(MethodTree node, List<Tree> targets) {
		JCTree.JCBlock body;
		List<? extends VariableTree> parameters = node.getParameters();
		ListBuffer<JCTree.JCStatement> addedStatementBuffer = 
				new ListBuffer<>();
		
		for (int i = 0; i < parameters.size(); i++) {
			VariableTree parameter = parameters.get(i);
			
			if (targets.contains(parameter)) {
				addedStatementBuffer.appendList(makeDebugStatements(
						parameter, parameter));
			}
		}

		body = (JCTree.JCBlock) node.getBody();
		body.stats = body.getStatements()
						 .prependList(addedStatementBuffer.toList());  
	}

    @Override
    protected void modifyBlock(BlockTree node, List<Tree> targets) {
		ArrayList<StatementTree> statements = 
				new ArrayList<>(node.getStatements());
		int n = statements.size();
		
		for (int i = 1, j = 0, k = 0; i <= n; i++, j = k) {
			StatementTree statement = statements.get(j);
			Tree.Kind kind = statement.getKind();
			
			k++;

			if (kind == Tree.Kind.ASSERT 
					|| kind == Tree.Kind.EXPRESSION_STATEMENT) {
				for (Tree target : targets) {
					if (statement.accept(scanner, 
					    				 ((VariableTree) target).getName()
												   				.toString())) {
						statements.addAll(
								k, makeDebugStatements(statement, 
													   (VariableTree) target));
						k += NUM_DEBUG_STATEMENT;
					}
				}
			}

			if (kind == Tree.Kind.VARIABLE 
					&& targets.contains(statement) 
					&& ((VariableTree) statement).getInitializer() != null) {
				statements.addAll(k, makeDebugStatements(
						statement, (VariableTree) statement));
				
				k += NUM_DEBUG_STATEMENT;
			}	
		}
		
		((JCTree.JCBlock) node).stats = com.sun.tools.javac.util.List.from(
				statements.toArray(new JCTree.JCStatement[statements.size()]));
    }

	@Override 
	protected void modifyForLoop(ForLoopTree node, List<Tree> targets) {
		ListBuffer<JCTree.JCStatement> addedStatementBuffer = 
				new ListBuffer<>();

		for (ExpressionStatementTree updateExpression : node.getUpdate()) {
			for (Tree target : targets) {
				if (updateExpression.accept(
						scanner, ((VariableTree) target).getName()
														.toString())) {
					addedStatementBuffer.appendList(makeDebugStatements(
							updateExpression, (VariableTree) target));
				}
			}
		}

		if (!addedStatementBuffer.isEmpty()) {
			StatementTree statement = node.getStatement();
			
			if (statement.getKind() == Tree.Kind.BLOCK) {
				((JCTree.JCBlock) statement).stats = 
						((JCTree.JCBlock) statement).getStatements()
								.prependList(addedStatementBuffer.toList());
			} else {
				((JCTree.JCForLoop) node).body = 
						treeMaker.Block(0, addedStatementBuffer.toList()
								.append((JCTree.JCStatement) statement));
			}
		}
	}

    /**
     * Makes debug statements with the given statement and variable.
     * @param statement the statement tree that changed the value 
     *                  of the variable
     * @param variable the debug annotated variable tree that got changed
     *                 by the statement
     * @return the list of debug statement
     */
    private com.sun.tools.javac.util.List<JCTree.JCStatement> makeDebugStatements(
            StatementTree statement, VariableTree variable) {
        treeMaker.at(((JCTree) statement).pos);
        
        return com.sun.tools.javac.util.List.of(
                makePrintStatement(treeMaker.Literal(
						"becuase of the statement " + statement.toString())),
                makePrintStatement(treeMaker.Binary(
                        JCTree.Tag.PLUS, 
                        treeMaker.Literal("value of " 
                                          + variable.getName() 
										  + " changed to: "),
                        treeMaker.Ident(symbolTable.fromString(
                                variable.getName().toString())))),
				makePrintStatement(null));
    }

    /**
     * Makes print statement with the given argument.
     * @param arg the argument
     * @return the print statement
     */
    private JCTree.JCStatement makePrintStatement(JCTree.JCExpression arg) {
        return treeMaker.Exec(treeMaker.Apply(
                null,
                treeMaker.Select(
                        treeMaker.Select(
                                treeMaker.Ident(
                                        symbolTable.fromString("System")), 
                                symbolTable.fromString("out")), 
                        symbolTable.fromString("println")),
                arg == null ? com.sun.tools.javac.util.List.nil() 
							: com.sun.tools.javac.util.List.of(arg)));        
    }
}
