package edu.handong.csee.se.sugarbag.app.view;

public class ReportView extends View{
    
    @Override
    public void print() {

        System.out.println("> Admin info");
        System.out.println("\t* 21800637@handong.ac.kr");
        System.out.println("\t* 21700383@handong.ac.kr");
        System.out.println("\t* naver@handong.ac.kr");
        System.out.println("\t* 21800353@handong.ac.kr");
        System.out.println("\t* inwoo405@handong.ac.kr");
        System.out.println("\t* 22100113@handong.ac.kr");

    }
    
    public View previousView(int index) {

        return children.get(index);

    }

    public ActionFactory getActionFactory() {

        return actionFactory;

    }

}
