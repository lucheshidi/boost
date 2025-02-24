package com.boost;

class Plugin {
    private final String group;
    private final String id;
    private final String version;

    public Plugin(String group, String id, String version) {
        this.group = group;
        this.id = id;
        this.version = version;
    }

    @Override
    public String toString() {
        return group + ":" + id + ":" + version;
    }
}

class Dependency {
    private final String group;
    private final String id;
    private final String version;

    public Dependency(String group, String id, String version) {
        this.group = group;
        this.id = id;
        this.version = version;
    }

    @Override
    public String toString() {
        return group + ":" + id + ":" + version;
    }
}

class Task {
    private final String name;
    private final Runnable action;

    public Task(String name, Runnable action) {
        this.name = name;
        this.action = action;
    }

    public void execute() {
        System.out.println("Executing task: " + name);
        action.run();
    }
}