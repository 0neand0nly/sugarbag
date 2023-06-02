package edu.handong.csee.se.sugarbag.app.action;

import edu.handong.csee.se.sugarbag.app.SugarBag;

public class SetClasspathAction extends Action {
    
    public SetClasspathAction(SugarBag sugarBag, Object arg) {
        super(sugarBag, arg);
    } 

    @Override
    public boolean act() {
        sugarBag.saveClasspath((String) arg);
        sugarBag.next(0);
        sugarBag.show();
        
        return true;
    }
}
