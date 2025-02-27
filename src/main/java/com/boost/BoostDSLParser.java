package com.boost;

import com.boost.annotations.Logging.Logger;
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
     * 日志输出
     * @implNote 通过 Logger 进行输出
     */
    private static final Logger log = new Logger();

    /**
     * 核心解析方法
     *
     * @param filePath 构建文件路径
     */
    public static void parse(String filePath) {
        try {
            List<String> lines = Files.readAllLines(Path.of(filePath));
            parseDSL(lines);
        }
        catch (IOException e) {
            log.error("Failed to read DSL file at %s. Detail: %s", filePath, e.getMessage());
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
                }
                else {
                    currentBlock = line.substring(0, line.indexOf("{")).trim();
                }
            }
            else if (line.equals("}")) {
                if (!blockContent.isEmpty()) {
                    handleBlock(currentBlock, blockContent);
                    blockContent.clear();
                }
                currentBlock = null;
            }
            else if (currentBlock != null) {
                blockContent.add(line);
            }
            else {
                log.error("Unknown line <%s>", line);
            }
        }
    }

    /**
     * 处理不同块内容
     */
    private static void handleBlock(String blockName, List<String> content) {
        log.info("Processing block: %s", blockName);
        switch (blockName) {
            case "root" -> handleRoot(content);
            case "ext" -> handleExt(content);
            case "plugins" -> handlePlugins(content);
            case "tasks" -> handleTasks(content);
            case "dependencies" -> handleDependencies(content);
            default -> log.warn("Unknown block: %s", blockName);
        }
    }

    private static void handleRoot(List<String> content) {
        for (String line : content) {
            if (line.contains("=")) {
                String[] parts = line.split("=");
                String key = parts[0].trim();
                String value = parts[1].trim().replace("\"", "");
                log.info("Property set: %s = %s", key, value);
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
                String taskName = extractArgumentsFromTaskRegister(line);
                if (taskName != null) {
                    Tasks.registerDynamicTask(taskName, () ->
                            log.info("Running dynamically registered task: %s", taskName)
                    );
                }
            }
        }
    }

    private static void handleDependencies(@NotNull List<String> content) {
        for (String line : content) {
            if (line.contains("dep") || line.contains("testDep")) {
                String depInfo = extractArguments(line);
                log.info("Dependency added: %s", depInfo);
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
        String pattern = "tasks\\.register\\(\"(.*?)\"\\)";
        java.util.regex.Pattern regex = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher matcher = regex.matcher(line);

        if (matcher.find()) {
            return matcher.group(1);
        }

        return null;
    }
}