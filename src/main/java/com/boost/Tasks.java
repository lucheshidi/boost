package com.boost;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.boost.annotations.Task;

import static com.boost.Main.*;

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
        return "help";
    }

    @Override
    public boolean isSuccess() {
        return true;
    }
}

class clean extends Task {
    boolean success = false;

    @Override
    public void run() {
        String ProgramPath = System.getProperty("user.dir");

        File buildDir = new File(ProgramPath + "/build");
        if (buildDir.exists()) {
            try {
                buildDir.delete();
                if (!buildDir.delete()) {
                    success = false;
                    return;
                } 
                else if (buildDir.delete()) {
                    success = true;
                }
                else{
                    System.out.println(Red + "fatal: Unknown error occured." + Reset);
                    success = false;
                    return;
                }
            } catch (SecurityException e) {
                System.out.println(Red + "fatal: Permission denied." + e.getMessage() + Reset);
                success = false;
                return;
            }
            buildDir.mkdir();
        }
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean isSuccess() {
        return true;
    }
    
}

class build extends Task {
    @Override
    public void run() {
        
    }

    @Override
    public String getName() {
        return "build";
        
    }

    @Override
    public boolean isSuccess() {
        return false;
    }
}

class compileClasses extends Task {
    @Override
    public void run() {
        List<String> commands = new ArrayList<>();
        commands.add("javac");
        commands.add("-d");
        commands.add("build/classes");

        // 添加Java文件
        commands.add("src/main/java/**/*.java");
        // 添加Kotlin文件
        commands.add("src/main/kotlin/**/*.kt");
        commands.add("src/main/kotlin/**/*.kts");
        // 添加Groovy文件
        commands.add("src/main/groovy/**/*.groovy");

        // 忽略依赖报错
        commands.add("-Xlint:none");

        ProcessBuilder processBuilder = new ProcessBuilder(commands);
        processBuilder.directory(new File(System.getProperty("user.dir")));
        processBuilder.redirectErrorStream(true);

        try {
            Process process = processBuilder.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return "compileClasses";
    }

    @Override
    public boolean isSuccess() {
        File buildDir = new File("build/classes");
        return buildDir.exists() && buildDir.isDirectory();
    }
}