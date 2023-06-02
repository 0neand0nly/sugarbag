package edu.handong.csee.se.sugarbag.app.action;

import java.io.IOException;

import edu.handong.csee.se.sugarbag.app.SugarBag;

/**
 * Abstract class that represents actions of each view.
 */
public abstract class Action {
    protected SugarBag sugarBag;
    protected Object arg;

    public Action() {
    }

    public Action(SugarBag sugarBag) {
        this.sugarBag = sugarBag;
    }

    public Action(SugarBag sugarBag, Object arg) {
        this.sugarBag = sugarBag;
        this.arg = arg;
    }

    /**
     * Take action that this instance represents.
     * @throws IOException
     * @throws InterruptedException
     * @return <code>true</code> if this action continues the program, 
     *         <code>false</code> otherwise
     */
    public abstract boolean act() throws IOException, InterruptedException; 
}
