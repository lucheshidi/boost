package com.boost;

import java.util.ArrayList;
import java.util.List;

public class BuildContext {

    // 存储插件信息
    private final List<Plugin> plugins = new ArrayList<>();
    private final List<Task> tasks = new ArrayList<>();
    private final List<Dependency> dependencies = new ArrayList<>();
    private final List<Dependency> testDependencies = new ArrayList<>();

    // 添加插件
    public void pl(String group, String id, String version) {
        plugins.add(new Plugin(group, id, version));
    }

    // 解析依赖
    public void dependency(String group, String id, String version) {
        dependencies.add(new Dependency(group, id, version));
    }

    public void testDependency(String group, String id, String version) {
        testDependencies.add(new Dependency(group, id, version));
    }

    // 添加任务
    public void task(String name, Runnable action) {
        tasks.add(new Task(name, action));
    }

    // 测试函数
    public void useJUnitPlatform() {
        System.out.println("Using JUnit Platform for testing");
    }

    // Getter
    public List<Plugin> getPlugins() {
        return plugins;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public List<Dependency> getDependencies() {
        return dependencies;
    }

    public List<Dependency> getTestDependencies() {
        return testDependencies;
    }
}
