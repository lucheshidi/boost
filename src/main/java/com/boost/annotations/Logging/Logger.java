package com.boost.annotations.Logging;

import com.boost.Main;

import java.util.Arrays;

public class Logger {
    public Logger() {
    }

    // Debug 输出
    public void debug(String format, Object... args) {
        String message = formatMessage(format, args);
        System.out.println(Main.Blue + "[DEBUG]: " + Main.Reset + message);
    }

    // Info 输出
    public void info(String format, Object... args) {
        String message = formatMessage(format, args);
        System.out.println(Main.Green + "[INFO ]: " + Main.Reset + message);
    }

    // Warn 输出
    public void warn(String format, Object... args) {
        String message = formatMessage(format, args);
        System.out.println(Main.Yellow + "[WARN ]: " + Main.Reset + message);
    }

    // Error 输出
    public void error(String format, Object... args) {
        String message = formatMessage(format, args);
        System.out.println(Main.Red + "[ERROR]: " + Main.Reset + message);
    }

    // Fatal 输出
    public void fatal(String format, Object... args) {
        String message = formatMessage(format, args);
        System.out.println(Main.Purple + Main.Red + "[FATAL]: " + Main.Reset + message);
    }

    // Trace 输出
    public void trace(String format, Object... args) {
        String message = formatMessage(format, args);
        System.out.println(Main.Purple + "[TRACE]: " + Main.Reset + message);
    }

    /**
     * 格式化辅助方法：基于格式字符串和参数返回最终消息
     */
    private String formatMessage(String format, Object... args) {
        try {
            return String.format(format, args);
        } catch (Exception e) {
            return "Log formatting error: " + e.getMessage() + " | Message: " + format + " | Args: " + Arrays.toString(args);
        }
    }
}