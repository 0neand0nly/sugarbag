package edu.handong.csee.se.sugarbag.app.view;

import java.util.Set;

public class ReportView extends LeafView {
    
    public ReportView() {
        kind = ViewKind.REPORT;
    }

    @Override
    public void print(String[] plugins, Set<String> selected) {
        
        System.out.println();
        System.out.println("> Admin info");
        System.out.println("\t* 21800637@handong.ac.kr");
        System.out.println("\t* 21700383@handong.ac.kr");
        System.out.println("\t* naver@handong.ac.kr");
        System.out.println("\t* 21800353@handong.ac.kr");
        System.out.println("\t* inwoo405@handong.ac.kr");
        System.out.println("\t* 22100113@handong.ac.kr");
        System.out.println("Please contact us to address some issues" 
                           + " or suggest more functionalities.");
        System.out.println("back: Go back to previous page, exit: Exit the program");
        System.out.print(">>> ");
    }
    
    @Override
    public View previousView() {

        return new RootView();

    }
}
