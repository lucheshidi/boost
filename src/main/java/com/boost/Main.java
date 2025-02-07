package com.boost;

import com.boost.annotations.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputFilter;
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
        Task help = new Help();
        boolean allTasksSuccessful = true;

        // Process `args` and execute tasks accordingly
        if (args.length > 0) {
            for (String arg : args) {
                try {
                    // Search for a class inheriting task and create an instance
                    Class<?> clazz = Class.forName("com.boost." + arg.substring(0, 1).toUpperCase() + arg.substring(1).toLowerCase());
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
        } 
        else {
            help.run();
        }

        //Test: 打印build.java信息
        BuildDSL.Build build = new BuildDSL.Build();

        // 设置构建系统的根信息
        build.root("com.example", "1.0-SNAPSHOT");

        // 配置插件
        BuildDSL.Plugins plugins = build.plugins();
        plugins.pl("com.example.plugin", "1.0.0");
        plugins.pl("com.android.tools.build:gradle", "7.0.4");

        // 配置任务
        BuildDSL.Tasks tasks = build.tasks();
        tasks.task("preBuild", "com.example.preBuildTask");
        tasks.task("clean", "com.example.cleanTask");

        // 配置依赖项
        BuildDSL.Dependencies dependencies = build.dependencies();
        dependencies.implementation("com.google.guava", "guava", "31.0.1-jre");
        dependencies.implementation("org.apache.logging.log4j", "log4j-core", "2.14.1");
        dependencies.testImplementation("junit", "junit", "4.13.2");

        // 打印配置详情
        build.printConfig();

        // Print final build status
        if (allTasksSuccessful) {
            System.out.println(Green + "\nBUILD SUCCESSFUL!" + Reset);
        } else {
            System.out.println(Red + "\nBUILD FAILED!" + Reset);
        }
        System.out.println("Finished at: " + dateFormat.format(date) + "\n");
    }
}