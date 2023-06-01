package edu.handong.csee.se.sugarbag.app;

import java.util.List;
import java.util.Scanner;

import edu.handong.csee.se.sugarbag.app.action.ActionFactory;

public class SugarBag {
    private PluginData data;
    private View view;
    private ActionFactory factory;

    /**
     * Initializes the <code>data</code> and <code>view</code> of this instance 
     * with the given path name.
     * @param pathname the pathname 
     */
    public SugarBag(String pathname) {
    
    }

    /**
     * Processes this instance with the given input.
     * @param input the input
     */
    public void process(String input) {
        
        factory.create(this, view.getKind(), input)
               .act();
    }

    /**
     * Executes the given argument with new process.
     * @param arg the argument
     */
    public void execute(String arg) {

    }

    /**
     * Shows the current <code>view</code>.
     * This method is used in <code>DataView</code>. 
     */
    public void show(String[] plugins, String[] selected) {
        
    }

    /**
     * Shows the current <code>view</code>.
     * This method is used in <code>SimpleView</code>
     */
    public void show() {

    }

    /**
     * Moves to the next view.
     * @param index index 
     */
    public void next(int index) {

    }

    /**
     * Moves to the previous view.
     */
    public void previous() {

    }

    /**
     * Selects plugins based on the given plugin numbers.
     * @param pluginNumbers the plugin numbers
     */
    public void select(int[] pluginNumbers) {

    }

    /**
     * Extracts plugin data from <code>data</code>.
     * @return the plugin data
     */
    public String[] extractPluginData() {

    }
}
