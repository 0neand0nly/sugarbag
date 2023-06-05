package edu.handong.csee.se.sugarbag.app.view;

import java.util.Set;

public class InputFileView extends View {

    public InputFileView() {
        children.add(new ExecutionView());
        
        kind = ViewKind.INPUT_FILE;
    }
    
    @Override
    public void print(String[] plugins, Set<String> selected) {
        System.out.println();
        System.out.println("current directory: " + System.getProperty("user.dir"));
        System.out.println("back: Go back to previous page, exit: Exit the program");
        System.out.print("Insert the input file >>> ");
    }

    @Override
    public View previousView() {
        return new PluginListView();
    }
}
