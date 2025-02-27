package com.boost;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * DSL 文件解析器
 */
public class BoostDSLParser {

    /**
     * 核心解析方法
     *
     * @param filePath 构建文件路径
     */
    public static void parse(String filePath) {
        try {
            List<String> lines = Files.readAllLines(Path.of(filePath));
            parseDSL(lines);
        } catch (IOException e) {
            System.out.println("Error: Failed to read DSL file at " + filePath);
            e.printStackTrace();
        }
    }

    private static void parseDSL(List<String> lines) {
        String currentBlock = null;
        List<String> blockContent = new ArrayList<>();

        for (String line : lines) {
            line = line.trim();

            if (line.isEmpty() || line.startsWith("//")) {
                continue; // 忽略空行和注释
            }

            if (line.endsWith("{")) {
                if (currentBlock != null) {
                    blockContent.add(line);
                } else {
                    currentBlock = line.substring(0, line.indexOf("{")).trim();
                }
            } else if (line.equals("}")) {
                if (!blockContent.isEmpty()) {
                    handleBlock(currentBlock, blockContent);
                    blockContent.clear();
                }
                currentBlock = null;
            } else if (currentBlock != null) {
                blockContent.add(line);
            } else {
                System.out.println("Unknown command: " + line);
            }
        }
    }

    /**
     * 处理不同块内容
     */
    private static void handleBlock(String blockName, List<String> content) {
        System.out.println("\nProcessing block: " + blockName);
        switch (blockName) {
            case "root" -> handleRoot(content);
            case "ext" -> handleExt(content);
            case "plugins" -> handlePlugins(content);
            case "tasks" -> handleTasks(content);
            case "dependencies" -> handleDependencies(content);
            default -> System.out.println("Unknown block: " + blockName);
        }
    }

    private static void handleRoot(List<String> content) {
        for (String line : content) {
            if (line.contains("=")) {
                String[] parts = line.split("=");
                String key = parts[0].trim();
                String value = parts[1].trim().replace("\"", "");
                System.out.printf("Property set: %s = %s%n", key, value);
            }
        }
    }

    private static void handleExt(List<String> content) {
        // 自定义扩展内容
    }

    private static void handlePlugins(List<String> content) {
        // 插件定义逻辑
    }

    private static void handleTasks(List<String> content) {
        for (String line : content) {
            if (line.startsWith("tasks.register")) {
                // 提取任务名称
                String taskName = extractArgumentsFromTaskRegister(line);

                // 如果任务名称存在，则注册动态任务
                if (taskName != null) {
                    Tasks.registerDynamicTask(taskName, () -> {
                        System.out.printf("Running dynamically registered task: %s%n", taskName);
                    });
                }
            }
        }
    }

    private static void handleDependencies(@NotNull List<String> content) {
        for (String line : content) {
            if (line.contains("dep") || line.contains("testDep")) {
                // 解析依赖逻辑
                String depInfo = extractArguments(line);
                System.out.println("Dependency added: " + depInfo);
            }
        }
    }

    private static @NotNull String extractArguments(@NotNull String line) {
        int start = line.indexOf("(");
        int end = line.indexOf(")");
        if (start != -1 && end != -1) {
            return line.substring(start + 1, end).trim();
        }
        return "No arguments";
    }

    private static @Nullable String extractArgumentsFromTaskRegister(String line) {
        // 定义匹配规则：提取 tasks.register("taskName") 中的 taskName
        String pattern = "tasks\\.register\\(\"(.*?)\"\\)";
        java.util.regex.Pattern regex = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher matcher = regex.matcher(line);

        // 判断是否符合规则
        if (matcher.find()) {
            return matcher.group(1); // 返回第一个捕获组，即任务名称
        }

        return null; // 如果未匹配到，返回 null
    }
}