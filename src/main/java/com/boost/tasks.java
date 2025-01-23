package com.boost;

import com.boost.annotations.Task;

class failed extends Task {
    @Override
    public void run() {

    }

    @Override
    public String getName() {
        return "failed";
    }

    @Override
    public boolean isSuccess() {
        return false;
    }
}

class help extends Task {
    @Override
    public void run() {
        System.out.println("""
Boost help:

Tasks:
build - build jar file
clean - clean build directory and jar file
help  - show this help
jar   - build jar file

Example:
boost clean build
boost clean jar
""");
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public boolean isSuccess() {
        return true;
    }
}