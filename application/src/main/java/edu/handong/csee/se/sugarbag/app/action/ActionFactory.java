package edu.handong.csee.se.sugarbag.app.action;

import edu.handong.csee.se.sugarbag.app.SugarBag;

/**
 * Class that creates actions based on the current view.
 * This class is implementation of the factory pattern.
 */
public class ActionFactory {
    
    /**
     * Creates <code>Action</code> with the given view kind and argument.
     * @param kind the view kind
     * @param arg the argument
     * @return the <code>Action</code>
     */
    public Action create(SugarBag sugarbag, ActionKind kind, String arg) {
      
    }
}
