package com.boost.annotations;

public abstract class Task {
    public static void init() {

    }
    public abstract void run();
    public abstract String getName();
    public String getDescription() { return ""; }
    public abstract boolean isSuccess();
    public String getUsage() { return ""; }
    public boolean isDefault() { return false; };
}
