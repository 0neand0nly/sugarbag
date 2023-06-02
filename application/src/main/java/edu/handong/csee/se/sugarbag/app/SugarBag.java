package edu.handong.csee.se.sugarbag.app;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import edu.handong.csee.se.sugarbag.app.action.ActionFactory;
import edu.handong.csee.se.sugarbag.app.view.RootView;
import edu.handong.csee.se.sugarbag.app.view.View;

public class SugarBag {
    private PluginData data;
    private View view;
    private ActionFactory factory;

    /**
     * Initializes the <code>data</code>, <code>view</code>,
     * and <code>factory</code> of this instance 
     * with the given plugin path name.
     * @param pathname the plugin path name
     */
    public SugarBag(String pathname) {
        data = new PluginData(pathname);
        view = new RootView();
        factory = new ActionFactory();
    }

    /**
     * Processes this instance with the given input.
     * @param input the input
     * @return <code>true</code> if the program continues, 
     *         otherwise <code>false</code>.  
     * @throws IOException
     * @throws InterruptedException
     */
    public boolean process(String input) throws IOException, 
                                                InterruptedException {
        Object convertedInput = convertInput(input);

        return factory.create(view.getKind(), this, convertedInput)
                      .act();
    }

    /**
     * Converts the given input to the appropriate type.
     * @param input the input
     * @return the converted input
     */
    private Object convertInput(String input) {
        if (input.matches("\\d")) {
            return Integer.parseInt(input);
        } else if (input.matches("\\d(\\s\\d)+")) {
            String[] stringNumbers = input.split(" ");
            int[] numbers = new int[stringNumbers.length];

            for (int i = 0; i < numbers.length; i++) {
                numbers[i] = Integer.parseInt(stringNumbers[i]);
            }

            return numbers;
        } else {
            return input;
        }
    }

    /**
     * Executes the program with new process.
     * @throws IOException
     * @throws InterruptedException
     */
    public void execute() throws IOException, InterruptedException {
        Process child;
        InputStream in, err;
        Set<String> selected = data.getSelected();
        List<String> compileCommand = new ArrayList<>();
        List<String> executeCommand = new ArrayList<>();

        compileCommand.add("javac");
        compileCommand.add("-cp");
        compileCommand.add("\"" + data.getPluginClasspath() + File.pathSeparator 
                           + data.getUserClasspath() + "\"");
        
        for (String selection : selected) {
            compileCommand.add("-Xplugin:" + selection);
        }

        compileCommand.add(data.getInputJavaFile()); 
    
        //child = new ProcessBuilder(compileCommand).start();

        child = new ProcessBuilder(compileCommand).start();
        in = child.getInputStream();
        err = child.getErrorStream();

        while (child.isAlive()) {
            if (in.available() > 0) {
                System.out.println(new String(in.readNBytes(in.available())));
            }

            if (err.available() > 0) {
                System.out.println(
                        new String(err.readNBytes(err.available())));
            }
        }
        
        child.waitFor();
        executeCommand.add("java");
        executeCommand.add("-cp");
        executeCommand.add("\"." + File.pathSeparator + data.getUserClasspath() + "\"");
        executeCommand.add(data.getInputClassFile());

        child = new ProcessBuilder(executeCommand).start();
        in = child.getInputStream();
        err = child.getErrorStream();

        while (child.isAlive()) {
            if (in.available() > 0) {
                System.out.println(new String(in.readNBytes(in.available())));
            }

            if (err.available() > 0) {
                System.out.println(
                        new String(err.readNBytes(err.available())));
            }
        }
    }

    /**
     * Shows the current <code>view</code>.
     * This method is for using <code>DataView.print()</code>. 
     */
    public void show(String[] plugins, Set<String> selected) {
        view.print(plugins, selected);
    }

    /**
     * Shows the current <code>view</code>.
     * Works exactly same as <code>SimpleView.print(null, null)</code>.
     */
    public void show() {
        view.print();
    }

    /**
     * Moves to the next view.
     * @param index index 
     */
    public void next(int index) {
        setView(view.nextView(index));
    }

    /**
     * Moves to the previous view.
     */
    public void previous() {
        setView(view.previousView());
    }

    /**
     * Selects plugins based on the given plugin numbers.
     * @param pluginNumbers the plugin numbers
     */
    public void select(int[] pluginNumbers) {
        data.addSelected(pluginNumbers);
    }

    /**
     * Selects plugin based on the given plugin number.
     * @param pluginNumber the plugin number
     */
    public void select(int pluginNumber) {
        data.addSelected(new int[] { pluginNumber });
    }

    /**
     * Loads all of the available plugins from <code>data</code>.
     * @return all of the available plugins
     */
    public String[] loadPlugins() {
        return data.getPlugins();
    }

    /**
     * Loads all of the selected plugins form <code>data</code>.
     * @return the selected plugins
     */
    public Set<String> loadSelected() {
        return data.getSelected();
    }

    /**
     * Saves the given classpath to this instance's <code>data</code>.
     * @param classpath the classpath
     */
    public void saveClasspath(String classpath) {
        data.setUserClasspath(classpath);
    }

    /**
     * Saves the given input file to this instance's <code>data</code>.
     * @param inputFile the input file
     */
    public void saveInputFile(String inputFile) {
        data.setInputJavaFile(inputFile);
    }

    public void setView(View view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }


    public static void main(String[] args) {
        final String PLUGIN_PATHNAME = "../../../lib/edu/handong/csee/se/sugarbag/plugin/plugin.jar";
       
        SugarBag sugarBag = new SugarBag(PLUGIN_PATHNAME);

        sugarBag.show();

        try (Scanner scanner = new Scanner(System.in)) {      
            //System.out.println(System.in.read());      
            do {
                scanner.hasNextLine();
            } while (sugarBag.process(scanner.nextLine()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
