package edu.handong.csee.se.sugarbag.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONObject;

public class PluginData {
    private final String CONFIG_FILE = "build.json";
    
    private String[] plugins;
    private Set<String> selected;
    private String mainClass;
    private String pluginClasspath;
    private String userClasspath;
    private String inputJavaFile;
    private String classFileDir;
   
    /**
     * Default constructor for PluginData
     * loads all <code>plugins</code> right away
     */
    public PluginData() {
    
        plugins = new String[] { "PositivePlugin", "NegativePlugin", 
                                 "NeutralPlugin", "InRangePlugin", 
                                 "StringFormatPlugin", "EmailPlugin", 
                                 "DebugPlugin" };
        selected = new HashSet<>();

    }

    /**
     * Configures <code>mainClass</code>, <code>pluginClasspath</code>, 
     * and <code>userClasspath</code> of this instance.
     * @throws IOException
     */
    public void configure() throws IOException {
        
        JSONObject jo;
        String dependencyDir;
        String js = "";

        try (BufferedReader reader = 
                new BufferedReader(new FileReader(CONFIG_FILE))) {
            String line;

            while ((line = reader.readLine()) != null) {
                js += line;
            }
        }

        jo = new JSONObject(js);
        mainClass = jo.getString("mainClass");
        pluginClasspath = jo.getString("plugin");
        dependencyDir = System.getProperty("user.home") 
                        + jo.getString("dependencyDir");
        userClasspath = "";
        
        for (Object o : jo.getJSONArray("dependencies")) {
            String dependencyPackageDir = dependencyDir + "/" + (String) o;
            String[] tempDirnames = new File(dependencyPackageDir).list();

            for (String tempDirname : tempDirnames) {
                userClasspath += dependencyPackageDir + "/" + tempDirname 
                                 + "/*" + File.pathSeparator;
            }
        }

        classFileDir = jo.getString("classFileDir");

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

    public String getMainClass() {
        
        return mainClass;

    }

    public String getPluginClasspath() {
        
        return pluginClasspath; 
    }

    public String getUserClasspath() {
        
        return userClasspath;

    }

    public String getInputJavaFile() {
        
        return inputJavaFile;

    }

    public String getClassFileDir() {
        
        return classFileDir;

    }
}
