root {
    group = "com.example.project"
    version = "1.0.0"
}

build {
    plugins {
        pl("com.example.plugin.one", 2.3.4)
        pl("com.example.plugin.two", 1.0.0)
    }

    tasks {
        task("clean", "com.example.task.CleanTask")
        task("compile", "com.example.task.CompileTask")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter:3.1.2")
    implementation("org.apache.commons", "commons-lang3", "3.12.0")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito", "mockito-core", "4.5.1")
}