package com.boost;

import com.boost.annotations.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static final String Green = "\033[32m";
    public static final String Yellow = "\033[33m";
    public static final String Red = "\033[31m";
    public static final String Blue = "\033[34m";
    public static final String Magenta = "\033[35m";
    public static final String Bold = "\033[1m";
    public static final String Underline = "\033[4m";
    public static final String Reset = "\033[0m";

    public static void main(String[] args) {
        // INIT
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        Task help = new help();
        boolean allTasksSuccessful = true;

        // Process `args` and execute tasks accordingly
        if (args.length > 0) {
            for (String arg : args) {
                try {
                    // Search for a class inheriting task and create an instance
                    Class<?> clazz = Class.forName("com.boost." + arg);
                    if (Task.class.isAssignableFrom(clazz)) {
                        Task taskInstance = (Task) clazz.getDeclaredConstructor().newInstance();
                        System.out.println("> Task: " + arg);
                        taskInstance.run();
                        boolean success = taskInstance.isSuccess();
                        if (success) {
                            System.out.println("> Task: " + arg + " SUCCESSFUL");
                        } else {
                            System.out.println("> Task: " + arg + " FAILED");
                            allTasksSuccessful = false;
                        }
                    } else {
                        System.out.println("> Task: " + arg + " FAILED");
                        allTasksSuccessful = false;
                    }
                } catch (ReflectiveOperationException e) {
                    System.out.println("> Task: " + arg + " FAILED");
                    allTasksSuccessful = false;
                }
            }
        } else {
            help.run();
        }

        try (FileReader reader = new FileReader("build.json")) {
            // 将 JSON 文件解析为 Config 对象
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Config config = gson.fromJson(reader, Config.class);

            // 获取各个字段值
            // System.out.println("Plugins (pl): " + config.getBuild().getPlugins().getPl());
            // System.out.println("Repository: " + config.getRepository());
            // System.out.println("Dependencies (implementation): " + config.getDependencies().getImplementation());
            // System.out.println("Tasks: " + config.getBuild().getTasks()); // 动态 Map
            // System.out.println("Example 'print()' task value: " + config.getBuild().getTasks().get("example").get("print()"));
        } catch (IOException e) {
            e.fillInStackTrace();
        }

        // Print final build status
        if (allTasksSuccessful) {
            System.out.println(Green + "\nBUILD SUCCESSFUL!" + Reset);
        } else {
            System.out.println(Red + "\nBUILD FAILED!" + Reset);
        }
        System.out.println("Finished at: " + dateFormat.format(date) + "\n");
    }
}