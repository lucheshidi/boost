package com.boost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildDSL {

    public static class Plugins {
        private final List<String> plugins = new ArrayList<>();

        public void pl(String pluginName, String version) {
            plugins.add(pluginName + ":" + version);
        }

        public List<String> getPlugins() {
            return plugins;
        }
    }

    public static class Tasks {
        private final Map<String, String> tasks = new HashMap<>();

        public void task(String name, String className) {
            tasks.put(name, className);
        }

        public Map<String, String> getTasks() {
            return tasks;
        }
    }

    public static class Dependencies {
        private final Map<String, List<String>> dependencies = new HashMap<>();

        public void implementation(String group, String artifact, String version) {
            addDependency("implementation", group + ":" + artifact + ":" + version);
        }

        public void implementation(String groupArtifactVersion) {
            addDependency("implementation", groupArtifactVersion);
        }

        public void testImplementation(String group, String artifact, String version) {
            addDependency("testImplementation", group + ":" + artifact + ":" + version);
        }

        public void testImplementation(String groupArtifactVersion) {
            addDependency("testImplementation", groupArtifactVersion);
        }

        public void addDependency(String type, String dependency) {
            dependencies.computeIfAbsent(type, k -> new ArrayList<>()).add(dependency);
        }

        public Map<String, List<String>> getDependencies() {
            return dependencies;
        }
    }

    public static class Build {
        private String group;
        private String version;
        private final Plugins plugins = new Plugins();
        private final Tasks tasks = new Tasks();
        private final Dependencies dependencies = new Dependencies();

        public void root(String group, String version) {
            this.group = group;
            this.version = version;
        }

        public Plugins plugins() {
            return plugins;
        }

        public Tasks tasks() {
            return tasks;
        }

        public Dependencies dependencies() {
            return dependencies;
        }

        public void printConfig() {
            System.out.println("Build Configuration:");
            System.out.println("Group: " + group);
            System.out.println("Version: " + version);

            System.out.println("\nPlugins:");
            plugins.getPlugins().forEach(System.out::println);

            System.out.println("\nTasks:");
            tasks.getTasks().forEach((task, className) -> System.out.println(task + " -> " + className));

            System.out.println("\nDependencies:");
            dependencies.getDependencies().forEach((type, deps) -> {
                System.out.println(type + ":");
                deps.forEach(dep -> System.out.println("  " + dep));
            });
        }
    }
}