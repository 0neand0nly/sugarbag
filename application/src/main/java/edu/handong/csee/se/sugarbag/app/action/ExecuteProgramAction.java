package edu.handong.csee.se.sugarbag.app.action;

import java.io.IOException;

import edu.handong.csee.se.sugarbag.app.SugarBag;
import edu.handong.csee.se.sugarbag.app.view.PluginListView;

public class ExecuteProgramAction extends Action {
    
    public ExecuteProgramAction(SugarBag sugarBag) {
        super(sugarBag);
    }

    @Override
    public boolean act() throws IOException, InterruptedException {
        sugarBag.execute();
        sugarBag.setView(new PluginListView());
        sugarBag.show(sugarBag.loadPlugins(), sugarBag.loadSelected());

        return true;
    }
}
