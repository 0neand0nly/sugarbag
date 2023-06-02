package edu.handong.csee.se.sugarbag.app;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PluginData {

    private final String PLUGIN_CLASSPATH;
    private final int EXTENSION_LEN = 5;
    
    private String[] plugins;
    private Set<String> selected;
    private String userClasspath;
    private String inputJavaFile;
   
    /**
     * Default constructor for PluginData
     * loads all <code>plugins</code> right away
     * 
     * @param pathname path to plugin/
     */
    public PluginData(String pathname) {

        //loadPlugins(pathname);
        PLUGIN_CLASSPATH = pathname;
        plugins = new String[] { "PositivePlugin", "NegativePlugin", 
                                 "NeutralPlugin", "InRangePlugin", 
                                 "StringFormatPlugin", "EmailPlugin", 
                                 "DebugPlugin" };
        selected = new HashSet<>();
        
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
     * @param listOfSelectedPlugins list of user-selected plugins
     */
    public void addSelected(int[] listOfSelectedPlugins) {

        for (int i = 0; i < listOfSelectedPlugins.length; i++) {
            selected.add(plugins[listOfSelectedPlugins[i] - 1]);
        }

    }

    public void setUserClasspath(String userClasspath) {

        this.userClasspath = userClasspath;

    }

    public void setInputJavaFile(String inputJavaFile) {
        
        this.inputJavaFile = inputJavaFile;

    }

    public String[] getPlugins() {

        return plugins;
        
    }

    public Set<String> getSelected() {
     
        return selected;

    }

    public String getPluginClasspath() {
        
        return PLUGIN_CLASSPATH; 
    }

    public String getUserClasspath() {
        
        return userClasspath;

    }

    public String getInputJavaFile() {
        
        return inputJavaFile;

    }

    public String getInputClassFile() {
        return inputJavaFile.substring(0, 
                                       inputJavaFile.length() - EXTENSION_LEN);

    }
}
