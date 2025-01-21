package com.boost.annotations;

public abstract class task {
    public abstract void run();
    public abstract String getName();
    public abstract String getDescription();
    public abstract String isSuccess();
    public String getUsage() { return ""; }
    public boolean isDefault() { return false; };
}
