package com.boost;

import java.util.List;
import java.util.Map;

class Config {
    private Build build;
    private List<String> repository; // repository 是一个字符串数组
    private Dependencies dependencies;

    public Build getBuild() {
        return build;
    }

    public List<String> getRepository() {
        return repository;
    }

    public Dependencies getDependencies() {
        return dependencies;
    }
}

class Build {
    private Plugins plugins;
    private Map<String, Map<String, String>> tasks; // 任务部分是动态的键值对

    public Plugins getPlugins() {
        return plugins;
    }

    public Map<String, Map<String, String>> getTasks() {
        return tasks;
    }
}

class Plugins {
    private List<String> pl; // plugins.pl 是一个数组

    public List<String> getPl() {
        return pl;
    }
}

class Dependencies {
    private List<String> implementation; // dependencies.implementation 是一个数组

    public List<String> getImplementation() {
        return implementation;
    }
}