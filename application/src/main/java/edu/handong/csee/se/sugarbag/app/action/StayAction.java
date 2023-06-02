package edu.handong.csee.se.sugarbag.app.action;

import edu.handong.csee.se.sugarbag.app.SugarBag;

public class StayAction extends Action {

    public StayAction(SugarBag sugarBag, Object arg) {
        super(sugarBag, arg);
    }

    @Override
    public boolean act() {
        System.out.println("invalid command: " + arg);
        sugarBag.show(sugarBag.loadPlugins(), sugarBag.loadSelected());
        
        return true;
    }
}
