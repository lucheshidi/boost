package com.boost;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BuildFileRunner {

    public static void main(String[] args) throws IOException {
        // 读取 build.java 文件内容
        String filePath = "build.java"; // 替换为实际路径
        String content = new String(Files.readAllBytes(Paths.get(filePath)));

        BuildDSL.Build build = new BuildDSL.Build();

        // 解析 root {}
        parseRoot(content, build);

        // 提取 build 块
        String buildBlock = DSLParserUtils.extractBlock(content, "build");
        if (buildBlock != null) {
            // 修正后的 parseBuild 调用
            parseBuild(buildBlock, build);
        } else {
            System.out.println("Build block not found.");
        }

        // 解析 dependencies {}
        String dependenciesBlock = DSLParserUtils.extractBlock(content, "dependencies");
        if (dependenciesBlock != null) {
            parseDependencies(dependenciesBlock, build.dependencies());
        } else {
            System.out.println("Dependencies block not found.");
        }

        // 打印最终配置
        build.printConfig();
    }

    private static void parseRoot(String content, BuildDSL.Build build) {
        String rootBlock = DSLParserUtils.extractBlock(content, "root");
        if (rootBlock != null) {
            System.out.println("Root Block: " + rootBlock); // 调试输出
            String group = extractAttribute(rootBlock, "group");
            String version = extractAttribute(rootBlock, "version");
            if (group != null && version != null) {
                build.root(group, version);
            }
        } else {
            System.out.println("Root block not found.");
        }
    }

    private static void parseBuild(String buildBlock, BuildDSL.Build build) {
        System.out.println("Received Build Block: " + buildBlock); // 调试输出
        System.out.println("Build Instance: " + build); // 检查 build 是否为 null 或类型问题

        // 确保 plugins 和 tasks 的提取与调用匹配
        String pluginsBlock = DSLParserUtils.extractBlock(buildBlock, "plugins");
        if (pluginsBlock != null) {
            System.out.println("Plugins Block: " + pluginsBlock); // 调试输出
            parsePlugins(pluginsBlock, build.plugins());
        } else {
            System.out.println("Plugins block not found.");
        }

        String tasksBlock = DSLParserUtils.extractBlock(buildBlock, "tasks");
        if (tasksBlock != null) {
            System.out.println("Tasks Block: " + tasksBlock); // 调试输出
            parseTasks(tasksBlock, build.tasks());
        } else {
            System.out.println("Tasks block not found.");
        }
    }

    private static void parseDependencies(String dependenciesBlock, BuildDSL.Dependencies dependencies) {
        System.out.println("Parsing Dependencies Block: \n" + dependenciesBlock); // 调试输出

        // 匹配多种形式的 dependencies 方法，如 implementation/testImplementation
        Pattern depPattern = Pattern.compile(
                "(\\w+Implementation)\\s*\\(\\s*\"([^\"]+)\"\\s*\\)|" + // 单参数实现
                        "(\\w+Implementation)\\s*\\(\\s*\"([^\"]+)\"\\s*,\\s*\"([^\"]+)\"\\s*,\\s*\"([^\"]+)\"\\s*\\)" // 多参数实现
        );

        Matcher matcher = depPattern.matcher(dependenciesBlock);

        while (matcher.find()) {
            if (matcher.group(1) != null) { // 是否为 implementation/testImplementation
                String type = matcher.group(1); // 获取类型（implementation 或 testImplementation）

                // 处理单参数依赖
                if (matcher.group(2) != null) {
                    String dependency = matcher.group(2);
                    System.out.println("Single Dependency Type: " + type + ", Dependency: " + dependency);
                    if ("testImplementation".equals(type)) {
                        dependencies.testImplementation(dependency);
                    } else {
                        dependencies.implementation(dependency);
                    }
                }

                // 处理多个参数的依赖
                if (matcher.group(3) != null && matcher.group(4) != null && matcher.group(5) != null) {
                    String groupArtifactVersion = matcher.group(3) + ":" + matcher.group(4) + ":" + matcher.group(5);
                    System.out.println("Complex Dependency Type: " + type + ", Dependency: " + groupArtifactVersion);
                    if ("testImplementation".equals(type)) {
                        dependencies.testImplementation(groupArtifactVersion);
                    } else {
                        dependencies.implementation(groupArtifactVersion);
                    }
                }
            }
        }
    }

    private static void parsePlugins(String pluginsBlock, BuildDSL.Plugins plugins) {
        String[] lines = pluginsBlock.split("\n");
        for (String line : lines) {
            line = line.trim();
            if (line.startsWith("pl")) {
                String[] parts = line.replace("pl(", "")
                        .replace(")", "")
                        .replace("\"", "").split(",");
                if (parts.length == 2) {
                    plugins.pl(parts[0].trim(), parts[1].trim());
                }
            }
        }
    }

    private static void parseTasks(String tasksBlock, BuildDSL.Tasks tasks) {
        String[] lines = tasksBlock.split("\n");
        for (String line : lines) {
            line = line.trim();
            if (line.startsWith("task")) {
                String[] parts = line.replace("task(", "")
                        .replace(")", "")
                        .replace("\"", "").split(",");
                if (parts.length == 2) {
                    tasks.task(parts[0].trim(), parts[1].trim());
                }
            }
        }
    }

    private static String extractAttribute(String block, String key) {
        String regex = key + "\\s*=\\s*\"([^\"]+)\"";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(block);
        return matcher.find() ? matcher.group(1) : null;
    }
}