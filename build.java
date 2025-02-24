class build implements BuildFile {
    rootProjectName = "example";
    group = "com.example";
    version = "1.0-SNAPSHOT";

    build {
        plugins {
            pl("com.example", "example", "1.0.0");
            pl("com.example:example:1.0.0");
        }

        tasks {
            task("example") {
                System.out.println("Hello World");
                // do something
            }
        }
    }

    dependencies {
        dependency("com.example", "example", "1.0.0");
        dependency("com.example:example:1.0.0");
        testDependency("com.example", "testExample", "1.0.0");
        testDependency("com.example:testExample:1.0.0")
    }

    test {
        // JUnit();
        // TestNG();
        // ...
    }
}