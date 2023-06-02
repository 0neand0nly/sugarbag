package edu.handong.csee.se.sugarbag.app.action;

/**
 * Enumeration of <code>View</code>s
 */
public enum ActionKind {
    GO_BACK, GO_TO_PLUGIN_LIST, GO_TO_MANUAL, GO_TO_REPORT, 
    SELECT_PLUGIN, EXECUTE_PROGRAM;

    /**
     * Gets <code>ActionKind</code> based on the given view and arguemnt.
     * @param view the view
     * @param arg the argument
     * @return the <code>ActionKind</code>
     */
    public static ActionKind getActionKind(View view, Object arg) {

    }
} 
