package com.boost;

import groovy.lang.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.jetbrains.annotations.NotNull;

public class Main {
    // 设置颜色变量，用于程序打印输出颜色及字体样式
    public static final String Red = "\u001B[31m";
    public static final String Green = "\u001B[32m";
    public static final String Yellow = "\u001B[33m";
    public static final String Blue = "\u001B[34m";
    public static final String Purple = "\u001B[35m";
    public static final String Reset = "\u001B[0m";
    public static final String Bold = "\u001B[1m";
    public static final String Italic = "\u001B[3m";
    public static final String Underline = "\u001B[4m";

    // 存储 DSL 配置的全局变量（例如属性：rootProjectName, group, version）
    public static void main(String[] args) {
        System.out.println("Starting Boost Build ...\n");

        // 模拟解析 DSL 文件（注册动态任务）
        BoostDSLParser.parse("build.boost.kts");
        BoostDSLParser.parse("build.boost.java");

        // 检查输入参数
        if (args.length == 0) {
            // System.out.println("No tasks specified. Showing help:\n");
            Tasks.execute("help");
        } else {
            System.out.println("Tasks will run: " + String.join(" ", args) + "\n");
            for (String task : args) {
                Tasks.execute(task);
            }
        }
    }
}