package com.boost;

import com.boost.annotations.task;

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
        task help = new help();

        // 创建 Gson 实例
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // 将输入的args通过" "区分为args
        try {
            args = args[0].split("\\s+");
        }
        catch (ArrayIndexOutOfBoundsException e) {
            if (args.length == 0) {
                help.run();
                boolean isSuccess = help.isSuccess().equals("true");
                if (isSuccess) {
                    System.out.println(Green + Bold + "BUILD SUCCESSFUL!" + Reset);
                    System.out.println("Finished at: " + dateFormat.format(date));  //2023-03-25 17:27:20

                }
                else {
                    System.out.println(Red + Bold + "BUILD FAILED!" + Reset);
                    System.out.println("Finished at: " + dateFormat.format(date));  //2023-03-25 17:27:20

                }
            }
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
}
