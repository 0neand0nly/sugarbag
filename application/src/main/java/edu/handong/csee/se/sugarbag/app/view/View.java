package edu.handong.csee.se.sugarbag.app.view;

import java.util.ArrayList;
import java.util.List;

public class View {
    
    protected List<View> children = new ArrayList<>();
    private ActionFactory actionFactory;
    private Kind kind;

    /**
     * Parent <code>print()</code> method for child <code>View</code>s
     * Mainly used to print the menu for the corresponding class
     */
    public void print() {

        System.out.println("******SugarBag******");

    }

    /**
     * Shifts the current <code>View</code> to another <code>View</code>
     * Sepcified by given index
     * 
     * @param index index of desired <code>View</code>
     * @return Returns specified <code>View</code> via index
     */
    public View nextView(int index) {

    }

    /**
     * Shifts the current <code>View</code> to the <code>RootView</code>
     * Sepcified by given index
     * 
     * @return Returns specified <code>View</code> via index
     */
    public View previousView(int index) {

    }

    /**
     * Get the <code>actionFactory</code> of the current <code>View</code>
     * The actions available change depending on the <code>View</code>
     * 
     * @return An <code>ActionFactory</code> instance
     */
    public ActionFactory getActionFactory() {

        return actionFactory;

    }

}
