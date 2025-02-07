package com.boost; // 如果有具体包名，请修改为你的项目路径

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// BuildDSL 定义在其他地方，请确保它可以正确导入
import com.boost.BuildDSL;

import java.util.regex.*;

public class DSLParser {

    // 解析 dependencies 块
    public static void parseDependencies(String dependenciesBlock, BuildDSL.Dependencies dependencies) {
        System.out.println("Parsing Dependencies Block: \n" + dependenciesBlock); // 调试输出

        // 正则表达式，支持解析 implementation 和 testImplementation
        Pattern depPattern = Pattern.compile(
                "(\\w+Implementation)\\s*\\(\\s*\"([^\"]+)\"\\s*\\)|" + // 匹配单参数依赖
                        "(\\w+Implementation)\\s*\\(\\s*\"([^\"]+)\"\\s*,\\s*\"([^\"]+)\"\\s*,\\s*\"([^\"]+)\"\\s*\\)" // 匹配多参数依赖
        );

        Matcher matcher = depPattern.matcher(dependenciesBlock);

        boolean found = false; // 标志是否找到匹配项
        while (matcher.find()) {
            found = true;

            String type = matcher.group(1); // 获取 implementation 或 testImplementation
            if (matcher.group(2) != null) { // 单参数依赖
                String dependency = matcher.group(2);
                System.out.println("Detected Single Dependency: Type = " + type + ", Dependency = " + dependency);
                if ("testImplementation".equals(type)) {
                    dependencies.testImplementation(dependency);
                } else {
                    dependencies.implementation(dependency);
                }
            }

            if (matcher.group(3) != null && matcher.group(4) != null && matcher.group(5) != null) { // 多参数依赖
                String groupArtifactVersion = matcher.group(3) + ":" + matcher.group(4) + ":" + matcher.group(5);
                System.out.println("Detected Complex Dependency: Type = " + type + ", Dependency = " + groupArtifactVersion);
                if ("testImplementation".equals(type)) {
                    dependencies.testImplementation(groupArtifactVersion);
                } else {
                    dependencies.implementation(groupArtifactVersion);
                }
            }
        }

        if (!found) {
            System.out.println("No dependencies matched in the block!");
        }
    }
}