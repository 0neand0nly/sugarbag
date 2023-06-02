package edu.handong.csee.se.sugarbag.app.action;

import edu.handong.csee.se.sugarbag.app.SugarBag;

public class GoToAction extends Action {
    
    public GoToAction(SugarBag sugarBag, Object arg) {
        super(sugarBag, arg);
    }

    @Override
    public boolean act() {
        if ((int) arg <= sugarBag.getView().numChildren()) {
            sugarBag.next((int) arg - 1);
        }
        
        sugarBag.show(sugarBag.loadPlugins(), sugarBag.loadSelected());

        return true;
    } 
}
