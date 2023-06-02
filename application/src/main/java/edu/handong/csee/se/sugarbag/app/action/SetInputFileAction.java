package edu.handong.csee.se.sugarbag.app.action;

import java.io.File;

import edu.handong.csee.se.sugarbag.app.SugarBag;

public class SetInputFileAction extends Action {
    
    public SetInputFileAction(SugarBag sugarBag, Object arg) {
        super(sugarBag, arg);
    }

    @Override
    public boolean act() {
        if (new File((String) arg).exists()) {
            sugarBag.saveInputFile((String) arg);
            sugarBag.next(0);
            sugarBag.show();
        } else {
            System.out.println("File not found. " + 
                               "Please Insert the input file again.");
            sugarBag.show();
        }

        return true;
    }
}
