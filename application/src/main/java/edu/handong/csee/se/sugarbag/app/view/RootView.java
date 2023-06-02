package edu.handong.csee.se.sugarbag.app.view;

import java.util.Set;

public class RootView extends View {

    public RootView() {
        children.add(new PluginListView());
        children.add(new ManualView());
        children.add(new ReportView());
        
        kind = ViewKind.ROOT;
    }

    @Override
    public void print(String[] plugins, Set<String> selected) {
        System.out.println();
        System.out.println("MENU");
        System.out.println("1. Show Plugin List");
        System.out.println("2. Show System Manual");
        System.out.println("3. Report Bugs");
        System.out.println("back: Go back to previous page, " 
                           + "exit: Exit the program");
        System.out.print(">>> ");
    }

    @Override
    public View previousView() {
        return this;
    }
    
}
