package com.boost;

import com.boost.annotations.PrivateTask;
import com.boost.annotations.Task;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.NotNull;

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
        PrivateTask failed = new failed();

        // 创建 Gson 实例
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // Process `args` and execute tasks accordingly
        if (args.length > 0) {
            for (String arg : args) {
                try {
                    // Search for a class inheriting task and create an instance
                    Class<?> clazz = Class.forName("com.boost." + arg);
                    if (Task.class.isAssignableFrom(clazz)) {
                        if (PrivateTask.class.isAssignableFrom(clazz)) {
                            continue;
                        }
                        else {
                            Task taskInstance = (Task) clazz.getDeclaredConstructor().newInstance();
                            taskInstance.run();
                        }
                    }
                    else {
                        System.out.println(Red + "BUILD FAILED!" + Reset);
                        System.out.println("Finished at: " + dateFormat.format(date) + "\n");
                        return;
                    }
                }
                catch (ReflectiveOperationException e) {
                    System.out.println(Red + "BUILD FAILED!" + Reset);
                    System.out.println("Finished at: " + dateFormat.format(date) + "\n");
                    return;
                }
            }
        }
        else {
            help.run();
        }
        

        try (FileReader reader = new FileReader("build.json")) {
            // 将 JSON 文件解析为 Config 对象
            Config config = gson.fromJson(reader, Config.class);

            // 获取各个字段值
            // System.out.println("Plugins (pl): " + config.getBuild().getPlugins().getPl());
            // System.out.println("Repository: " + config.getRepository());
            // System.out.println("Dependencies (implementation): " + config.getDependencies().getImplementation());
            // System.out.println("Tasks: " + config.getBuild().getTasks()); // 动态 Map
            // System.out.println("Example 'print()' task value: " + config.getBuild().getTasks().get("example").get("print()"));

        }
        catch (IOException e) {
            e.fillInStackTrace();
        }
    }
    public static void isSuccessTask(@NotNull Task task, String reason) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        boolean isSuccess = task.isSuccess();
        if (isSuccess) {
            System.out.println(Green + "BUILD SUCCESSFUL!" + Reset);
            System.out.println("Finished at: " + dateFormat.format(date) + "\n");
        }
        else {
            System.out.println(Red + "BUILD FAILED!" + Reset);
            if (reason != null) {
                System.out.println(reason);
            }
            System.out.println("Finished at: " + dateFormat.format(date) + "\n");
        }
    }
}
