package edu.handong.csee.se.sugarbag.app.view;

import java.util.Set;

public class PluginListView extends View {
    
    public PluginListView() {
        children.add(new ClasspathView());
       
        kind = ViewKind.PLUGIN_LIST;
    }

    @Override
    public void print(String[] plugins, Set<String> selected) {
        System.out.println();
        System.out.println("Select plugins to applicate.");

        for (int i = 0; i < plugins.length; i++) {
            System.out.printf("%d %s", i + 1, plugins[i]);

            if (selected.contains(plugins[i])) {
                System.out.println("[V]");
            } else {
                System.out.println("[ ]");
            }
        }

        System.out.println("back: Go back to previous page, exit: Exit the program");
        System.out.println("done: Move to next stage");
        System.out.print("Insert plugin numbers to select >>> ");
    }
    
    @Override
    public View previousView() {
        return new RootView();
    }
}
