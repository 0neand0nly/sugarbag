package edu.handong.csee.se.sugarbag.app.view;

import java.util.Set;

public class ClasspathView extends View {

    public ClasspathView() {
        children.add(new InputFileView());

        kind = ViewKind.CLASSPATH;
    }
    
    @Override
    public void print(String[] pulgins, Set<String> selected) {
        System.out.println();
        System.out.println("back: Go back to previous page, exit: Exit the program");
        System.out.print("Insert the classpath >>> ");
    }
    
    @Override
    public View previousView() {
        return new PluginListView();
    }
}
