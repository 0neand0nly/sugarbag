package edu.handong.csee.se.sugarbag.app.view;

import edu.handong.csee.se.sugarbag.app.View;

public class DataView extends View{
    
    public void print(String[] plugins, String[] selected) {

        

    }
    
    public View previousView(int index) {

        return children.get(index);

    }

    public ActionFactory getActionFactory() {

        return actionFactory;

    }

}
