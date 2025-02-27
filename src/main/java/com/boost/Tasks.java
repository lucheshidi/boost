package com.boost;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boost.TaskActions.failed;
import com.boost.TaskActions.successful;
import com.boost.annotations.task;

import static com.boost.Main.*;

class Help extends task {
    @Override
    public void run() {
        System.out.println("""
Boost help:

Tasks:
build - build project
clean - clean build directory and jar file
help  - show this help
jar   - build jar file

Example:
boost clean build
boost clean jar
""");
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public boolean isSuccess() {
        return true;
    }

    @Override
    public List<String> getChildTasks() {
        return super.getChildTasks();
    }
}

class Clean extends task {
    boolean success = false;

    @Override
    public void run() {
        String ProgramPath = System.getProperty("user.dir");

        File buildDir = new File(ProgramPath + "/build");
        if (buildDir.exists()) {
            try {
                buildDir.delete();
                if (!buildDir.delete()) {
                    success = false;
                    return;
                }
                else if (buildDir.delete()) {
                    success = true;
                }
                else{
                    System.out.println(Red + "fatal: Unknown error occured." + Reset);
                    success = false;
                    return;
                }
            } catch (SecurityException e) {
                System.out.println(Red + "fatal: Permission denied." + e.getMessage() + Reset);
                success = false;
                return;
            }
            buildDir.mkdir();
        }
    }

    @Override
    public String getName() {
        return "clean";
    }

    @Override
    public boolean isSuccess() {
        return true;
    }

    @Override
    public List<String> getChildTasks() {
        return super.getChildTasks();
    }

}

class Build extends task {
    @Override
    public void run() {

    }

    @Override
    public String getName() {
        return "build";

    }

    @Override
    public boolean isSuccess() {
        return false;
    }

    @Override
    public List<String> getChildTasks() {
        return super.getChildTasks();
    }
}

class CompileClasses extends task {
    @Override
    public void run() {
        List<String> commands = new ArrayList<>();
        commands.add("javac");
        commands.add("-d");
        commands.add("build/classes");

        // 添加Java文件
        commands.add("src/main/java/**/*.java");
        // 添加Kotlin文件
        commands.add("src/main/kotlin/**/*.kt");
        commands.add("src/main/kotlin/**/*.kts");
        // 添加Groovy文件
        commands.add("src/main/groovy/**/*.groovy");

        // 忽略依赖报错
        commands.add("-Xlint:none");

        ProcessBuilder processBuilder = new ProcessBuilder(commands);
        processBuilder.directory(new File(System.getProperty("user.dir")));
        processBuilder.redirectErrorStream(true);

        try {
            Process process = processBuilder.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return "compileClasses";
    }

    @Override
    public boolean isSuccess() {
        File buildDir = new File("build/classes");
        return buildDir.exists() && buildDir.isDirectory();
    }

    @Override
    public List<String> getChildTasks() {
        return super.getChildTasks();
    }
}

class Jar extends task {
    @Override
    public void run() {
        List<String> commands = new ArrayList<>();
        commands.add("jar");
        commands.add("-cf");
        commands.add("build/boost.jar");
        commands.add("-C");
        commands.add("build/classes");
        commands.add(".");

        ProcessBuilder processBuilder = new ProcessBuilder(commands);
        processBuilder.directory(new File(System.getProperty("user.dir")));
       processBuilder.redirectErrorStream(true);
    }

    @Override
    public String getName() {
        return "jar";
    }

    @Override
    public boolean isSuccess() {
        return false;
    }

    @Override
    public List<String> getChildTasks() {
        return super.getChildTasks();
    }
}

class dependencies extends task {

    @Override
    public void run() {

    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public boolean isSuccess() {
        return false;
    }

    @Override
    public List<String> getChildTasks() {
        return super.getChildTasks();
    }
}

public class Tasks {
    // 任务注册表：包含所有静态任务和动态任务
    private static final Map<String, task> TASK_REGISTRY = new HashMap<>();

    static {
        // 本地静态任务初始化
        registerStaticTasks();
    }

    /**
     * 静态任务注册
     */
    private static void registerStaticTasks() {
        register(new Help());
        register(new Clean());
        register(new Build());
        register(new CompileClasses());
        register(new Jar());
        register(new dependencies());
        // 注册更多内置任务...
    }

    /**
     * 注册任务（静态或动态任务）
     *
     * @param newTask 需要注册的任务
     */
    public static void register(task newTask) {
        if (newTask != null && !TASK_REGISTRY.containsKey(newTask.getName())) {
            TASK_REGISTRY.put(newTask.getName(), newTask);
            System.out.println("Task registered: " + newTask.getName());
        }
    }

    /**
     * 动态任务注册（通过 DSL 解析器解析得出的新任务）
     */
    public static void registerDynamicTask(String taskName, Runnable action) {
        register(new task() {
            @Override
            public void run() {
                action.run(); // 执行自定义动态任务
            }

            @Override
            public String getName() {
                return taskName;
            }

            @Override
            public boolean isSuccess() {
                return true; // 动态任务默认成功
            }

            @Override
            public String getDescription() {
                return "Dynamic task registered via DSL.";
            }
        });
    }

    /**
     * 执行任务（包含依赖）
     */
    public static void execute(String taskName) {
        task currentTask = TASK_REGISTRY.get(taskName);

        if (currentTask == null) {
            // 如果任务未找到，调用失败方法
            failed.run("Task not found.");
            return;
        }

        // 执行其依赖的任务
        List<String> dependencies = currentTask.getChildTasks();
        if (dependencies != null && !dependencies.isEmpty()) {
            for (String dependency : dependencies) {
                execute(dependency); // 递归调用依赖任务
            }
        }

        // 执行当前任务
        System.out.printf(Bold + "Task> " + Reset + Underline + "%s%n", taskName);
        currentTask.run();

        // 检查任务是否成功并记录状态
        if (currentTask.isSuccess()) {
            // successful.run();
        } else {
            failed.run("Task execution failed internally.");
            System.exit(1);
        }
    }
}