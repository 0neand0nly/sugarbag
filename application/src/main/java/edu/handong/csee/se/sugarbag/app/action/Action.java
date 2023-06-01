package edu.handong.csee.se.sugarbag.app.action;

import edu.handong.csee.se.sugarbag.app.SugarBag;

/**
 * Abstract class that represents actions of each view.
 */
public abstract class Action {
    protected SugarBag sugarbag;
    protected String arg;

    /**
     * Take action that this instance represents.
     */
    public abstract void act(); 
}
