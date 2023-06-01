package edu.handong.csee.se.sugarbag.app.view;

public class PluginListView extends DataView{
    
    @Override
    public void print(String[] plugins, String[] selected) {

        for (int i = 0; i < plugins.length; i++) {
            boolean flag = false;

            for (int j = 0; j < selected.length; j++) {
                if (plugins[i] == selected[j]) {
                    System.out.print("[X]");
                    flag = true;
                    break;
                }
            }

            if (!flag) {
                System.out.print("[ ]");
            }

            System.out.println(" " + i + ". " + plugins[i]);
        }

    }
    
    public View previousView(int index) {

        return children.get(index);

    }

    public ActionFactory getActionFactory() {

        return actionFactory;

    }

}
