package edu.handong.csee.se.sugarbag.app.view;

import java.util.Set;

public class ExecutionView extends LeafView {

    public ExecutionView() {
        kind = ViewKind.EXECUTION;
    }
    
    @Override
    public void print(String[] plugins, Set<String> selected) {
        System.out.println();
        System.out.println("back: Go back to previous page, exit: Exit the program");
        System.out.print("Insert execute to run the program >>> ");
    }

    @Override
    public View previousView() {
        return new InputFileView();
    }
}
