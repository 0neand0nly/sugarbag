package edu.handong.csee.se.sugarbag.app.view;

public class RootView extends View {

    @Override
    public void print() {

        System.out.println("MENU");
        System.out.println("1. Show Plugin List");
        System.out.println("2. Show System Manual");
        System.out.println("3. Report Bugs");
        System.out.println("0. Quit Program");
        System.out.print(">>>");

    }
    
    public View nextView(int index) {

        return children.get(index);

    }

    public ActionFactory getActionFactory() {

        return actionFactory;

    }
    
}
