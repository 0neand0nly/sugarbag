package edu.handong.csee.se.sugarbag.app.action;

import edu.handong.csee.se.sugarbag.app.SugarBag;

public class SelectPluginAction extends Action {
    
    public SelectPluginAction(SugarBag sugarBag, Object arg) {
        super(sugarBag, arg);       
    }

    @Override
    public boolean act() {
        if (arg instanceof Integer) {
            sugarBag.select((int) arg);
        } else {
            sugarBag.select((int[] ) arg);
        }

        sugarBag.show(sugarBag.loadPlugins(), sugarBag.loadSelected());
        
        return true;
    }
}
