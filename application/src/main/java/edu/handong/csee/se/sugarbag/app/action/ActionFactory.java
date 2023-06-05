package edu.handong.csee.se.sugarbag.app.action;

import edu.handong.csee.se.sugarbag.app.SugarBag;
import edu.handong.csee.se.sugarbag.app.view.ViewKind;

/**
 * Class that creates actions based on the current view.
 * This class is implementation of the factory pattern.
 */
public class ActionFactory {
    
    /**
     * Creates <code>Action</code> depending on the given view kind 
     * and argument. 
     * @param kind the view kind
     * @param sugarbag the sugarbag
     * @param arg the arugment
     * @return the <code>Action</code>
     */
    public Action create(ViewKind kind, SugarBag sugarBag, Object arg) {
        if (arg.toString().equals("back")) {
            return new GoBackAction(sugarBag);
        } else if (arg.toString().equals("exit")) {
            return new ExitAction();
        } else if (kind == ViewKind.PLUGIN_LIST 
                   && (arg instanceof Integer || arg instanceof int[])) {
            return new SelectPluginAction(sugarBag, arg);
        } else if (kind == ViewKind.PLUGIN_LIST 
                   && arg.toString().equals("done")) {
            return new GoToAction(sugarBag, 1);
        } else if (kind != ViewKind.INPUT_FILE && arg instanceof Integer) {
            return new GoToAction(sugarBag, arg);
        } else if (kind == ViewKind.EXECUTION 
                   && arg.toString().equals("execute")) {
            return new ExecuteProgramAction(sugarBag);
        } else if (kind == ViewKind.INPUT_FILE && arg instanceof String) {
            return new SetInputFileAction(sugarBag, arg);
        } else {
            return new StayAction(sugarBag, arg);
        }
    }
}
