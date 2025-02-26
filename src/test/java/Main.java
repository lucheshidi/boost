package com.boost;

import com.boost.annotations.task;
import com.boost.TaskActions.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.NotNull;

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
        task help = new Help();

        // 创建 Gson 实例
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // Process `args` and execute tasks accordingly
        if (args.length > 0) {
            for (String arg : args) {
                try {
                    // Search for a class inheriting task and create an instance
                    Class<?> clazz = Class.forName("com.boost." + arg);
                    if (task.class.isAssignableFrom(clazz)) {
                        task taskInstance = (task) clazz.getDeclaredConstructor().newInstance();
                        taskInstance.run();
                        boolean success = taskInstance.isSuccess();
                        if (success) {
                            successful.run();
                        } else if (!success) {
                            failed.run();
                        } else {
                            System.out.println("Unknown Exception occured.");
                            failed.run();
                        }
                    } else {
                        failed.run();
                        return;
                    }
                } catch (ReflectiveOperationException e) {
                    failed.run();
                    return;
                }
            }
        } else {
            help.run();
        }
    }

    public static void isSuccessTask(@NotNull task task, String reason) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        boolean isSuccess = task.isSuccess();
        if (isSuccess) {
            System.out.println(Green + "BUILD SUCCESSFUL!" + Reset);
            System.out.println("Finished at: " + dateFormat.format(date) + "\n");
        } else {
            System.out.println(Red + "BUILD FAILED!" + Reset);
            if (reason != null) {
                System.out.println(reason);
            }
            System.out.println("Finished at: " + dateFormat.format(date) + "\n");
        }
    }
}