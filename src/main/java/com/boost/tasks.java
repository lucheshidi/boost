package com.boost;

import com.boost.annotations.task;

class help extends task {
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
./boostw clean build
./boostw clean jar
""");
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public String isSuccess() {
        return "true";
    }
}