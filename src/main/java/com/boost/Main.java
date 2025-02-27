package com.boost;

import com.boost.annotations.Logging.Logger;
import java.io.File;

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

    static Logger log = new Logger();

    // 存储 DSL 配置的全局变量（例如属性：rootProjectName, group, version）
    public static void main(String[] args) {
        log.info("Starting Boost Build ...\n");
        // 模拟解析 DSL 文件（注册动态任务）
        File dslFileKotlin = new File("build.boost.kts");
        File dslFileJava = new File("build.boost.java");
        switch ((dslFileKotlin.exists() ? "kotlin" : "") + (dslFileJava.exists() ? "java" : "")) {
            case "kotlinjava":
                // System.out.println(Bold + Yellow + "WARN: Kotlin DSL file and Java DSL file all exist, using build.boost.java!" + Reset);
                log.warn("Kotlin DSL file and Java DSL file all exist, using build.boost.java!");
                BoostDSLParser.parse("build.boost.java");
                break;
            case "java":
                BoostDSLParser.parse("build.boost.java");
                break;
            case "kotlin":
                BoostDSLParser.parse("build.boost.kts");
                break;
            case "":
                // System.out.println(Bold + Red + "ERROR: No build DSL file found!" + Reset);
                log.error("No build DSL file found!");
                break;
            default:
                log.fatal("FATAL: Unknown error occurred.");
                break;
        }

        // 检查输入参数
        if (args.length == 0) {
            // System.out.println("No tasks specified. Showing help:\n");
            Tasks.execute("help");
        } else {
            // System.out.println("Tasks will run: " + String.join(" ", args) + "\n");
            log.info("Tasks will run: " + String.join(" ", args) + "\n");
            for (String task : args) {
                Tasks.execute(task);
            }
        }
    }
}