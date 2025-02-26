package com.boost.annotations;

import java.util.List;

public abstract class task {

    /**
     * 执行任务的主要逻辑
     */
    public abstract void run();

    /**
     * 获取任务名，用于注册和调用
     */
    public abstract String getName();

    /**
     * 判断任务是否成功
     */
    public abstract boolean isSuccess();

    /**
     * 返回依赖的子任务
     */
    public List<String> getChildTasks() {
        return null; // 可以被子类覆盖
    }

    /**
     * 返回任务描述（默认空）
     */
    public String getDescription() {
        return "No description provided";
    }
}