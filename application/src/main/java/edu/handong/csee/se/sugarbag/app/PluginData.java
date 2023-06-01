package edu.handong.csee.se.sugarbag.app;

import java.util.List;
import java.io.File;
import java.util.ArrayList;

public class PluginData {
 
    private List<String> plugins = new ArrayList<>();
    private List<String> selected = new ArrayList<>();

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
                for (File file : files) {
                    if (file.isFile()) {
                        String fileName = file.getName();

                        // don't add test files
                        if (fileName.contains("Text")) {
                            break;
                        }

                        plugins.add(fileName);
                    }
                }
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
    public void addSelected(List<String> listOfSelectedPlugins) {

        selected.addAll(listOfSelectedPlugins);

    }

    public List<String> getPlugins() {

        return plugins;
        
    }

    public List<String> getSelected() {
     
        return selected;

    }

}
