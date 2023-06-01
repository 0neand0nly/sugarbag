package edu.handong.csee.se.sugarbag.app;

import java.io.File;
import java.util.ArrayList;

public class PluginData {

    private final String PACKAGE_NAME = "java/edu/handong/scee/se/sugarbg/oplug-ins";
 
    private String[] plugins;
    private String[] selected;

    /**
     * Default constructor for PluginData
     * loads all <code>plugins</code> right away
     * 
     * @param pathname path to plugin/
     */
    public PluginData(String pathname) {

        loadPlugins(pathname);
    
    }

    /**
     * traverses directory of plugins to obtain plugin names
     * 
     * @param pathname path to plugin/
     */
    public void loadPlugins(String pathname) {

        File directory = new File(pathname);

        // Check if the specified directory exists
        if (directory.exists() && directory.isDirectory()) {
            // Get all file names in the directory
            File[] files = directory.listFiles();
            
            if (files != null) {
                ArrayList<String> pluginList = new ArrayList<>();
                
                for (File file : files) {
                    if (file.isFile()) {
                        String fileName = file.getName();

                        // don't add test files
                        if (!fileName.contains("Test")) {
                            pluginList.add(fileName);
                        }
                    }
                }

                plugins = pluginList.toArray(new String[pluginList.size()]);

            }
        } else {
            System.out.println("Invalid directory path");
        }

    }

    /**
     * Update the list of selected plugins, <code>listOfSelectedPlugins</code>
     * 
     * @param listOfSelectedPlugins list of user-selected plug-ins
     */
    public void addSelected(int[] listOfSelectedPlugins) {

        ArrayList<String> selectedList = new ArrayList<>();

        for (int i = 0; i < listOfSelectedPlugins.length; i++) {
            selectedList.add(plugins[listOfSelectedPlugins[i]]);
        }

        selected = selectedList.toArray(new String[selectedList.size()]);

    }

    public String[] getPlugins() {

        return plugins;
        
    }

    public String[] getSelected() {
     
        return selected;

    }

}
