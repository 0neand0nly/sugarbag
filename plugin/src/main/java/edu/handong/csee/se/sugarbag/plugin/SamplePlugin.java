package edu.handong.csee.se.sugarbag.plugin;

import com.sun.source.util.JavacTask;
import com.sun.source.util.Plugin;
import com.sun.source.util.TaskEvent;
import com.sun.source.util.TaskListener;
import com.sun.source.util.TaskEvent.Kind;

public class SamplePlugin implements Plugin {
    
    @Override
    public void init(JavacTask task, String... args) {
        task.addTaskListener(new TaskListener() {
            
            @Override
            public void finished(TaskEvent e) {
                if (e.getKind() != Kind.PARSE) {
                    return;
                }

                System.out.println("hello plugin");
            }
        });
    }

    @Override
    public String getName() {
        return "SamplePlugin";
    }
}
