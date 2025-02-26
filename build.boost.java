root {
    projectName = "example";
    groupId = "com.example";
    version = "1.0-SNAPSHOT";
}

ext {
    // put variables here
    // can be used for custom tasks
}

plugins {
    pl("com.example", "example", "1.0-SNAPSHOT");
    plugin("com.example", "example", "1.0-SNAPSHOT");
    pl("com.example:example:1.0-SNAPSHOT");
    plugin("com.example:example:1.0-SNAPSHOT");
}

tasks {
    tasks.register("example", () -> {
        System.out.println("This is a example task");
    });

    task("example2", () -> {
        System.out.println("This is another example task");
    });
}

dependencies {
    dep("com.example:example:1.0-SNAPSHOT");
    testDep("com.example:example:1.0-SNAPSHOT");
    dep("com.example", "example", "1.0-SNAPSHOT");
    testDep("com.example", "example", "1.0-SNAPSHOT");
}