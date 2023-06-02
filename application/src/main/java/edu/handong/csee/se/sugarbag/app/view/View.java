package edu.handong.csee.se.sugarbag.app.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class View {
    
    protected List<View> children = new ArrayList<>();
    protected ViewKind kind;

    /**
     * Parent <code>print()</code> method for child <code>View</code>s
     * Mainly used to print the menu for the corresponding class
     */
    public void print() {

        print(null, null);

    }

    /**
     * Parent <code>print()</code> method for child <code>View</code>s
     * Mainly used to print the menu for the corresponding class
     * 
     * @param plugins  
     * @param selected
     */
    public abstract void print(String[] plugins, Set<String> selected);

    /**
     * Shifts the current <code>View</code> to another <code>View</code>
     * Sepcified by given index
     * 
     * @param index index of desired <code>View</code>
     * @return Returns specified <code>View</code> via index
     */
    public View nextView(int index) {
        return children.get(index);
    };

    /**
     * Shifts the current <code>View</code> to the <code>RootView</code>
     * Sepcified by given index
     * 
     * @return Returns specified <code>View</code> via index
     */
    public abstract View previousView();

    /**
     * Gets the number of children of this instance.
     * @reutrn the number of children
     */
    public int numChildren() {
        return children.size();
    }

    public ViewKind getKind() {
        return kind;
    }
}
