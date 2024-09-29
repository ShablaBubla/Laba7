plugins {
    id("java")
    application
}

repositories {
    mavenCentral()
}

project(":client"){
    apply(plugin = "application")
    application{
        mainClass.set("com.bubla.Main")
    }
    tasks.getByName<Test>("test") {
        useJUnitPlatform()
    }

    tasks.withType<JavaCompile> {
        options.compilerArgs.addAll(listOf("-Xlint:unchecked"))
    }

    tasks.named<JavaExec>("run") {
        standardInput = System.`in`
    }

// Добавление задачи для сборки JAR с зависимостями
    tasks.jar {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE

        manifest {
            attributes["Main-Class"] = "com.bubla.Main" // Указываем главный класс для JAR
        }

        // Включаем все зависимости в JAR (Fat JAR)
        from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })

        // Имя JAR-файла
        archiveFileName.set("client.jar")
    }

    dependencies {
        implementation(project(":common"))
        compileOnly("org.projectlombok:lombok:1.18.32")
        annotationProcessor("org.projectlombok:lombok:1.18.32")

        testCompileOnly("org.projectlombok:lombok:1.18.32")
        testAnnotationProcessor("org.projectlombok:lombok:1.18.32")

        implementation("org.apache.commons:commons-lang3:3.12.0")

        implementation ("com.google.code.gson:gson:2.10.1")
        implementation ("com.google.guava:guava:31.1-jre")


        testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    }
}
project(":server"){
    apply(plugin = "application")
    application{
        mainClass.set("com.bubla.Main")
    }
    tasks.getByName<Test>("test") {
        useJUnitPlatform()
    }

    tasks.withType<JavaCompile> {
        options.compilerArgs.addAll(listOf("-Xlint:unchecked"))
    }

    tasks.named<JavaExec>("run") {
        standardInput = System.`in`
    }

// Добавление задачи для сборки JAR с зависимостями
    tasks.jar {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE

        manifest {
            attributes["Main-Class"] = "com.bubla.Main" // Указываем главный класс для JAR
        }

        // Включаем все зависимости в JAR (Fat JAR)
        from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })

        // Имя JAR-файла
        archiveFileName.set("server.jar")
    }

    dependencies {
        implementation(project(":common"))
        compileOnly("org.projectlombok:lombok:1.18.32")
        annotationProcessor("org.projectlombok:lombok:1.18.32")
        implementation ("com.jcraft:jsch:0.1.55")
        implementation ("org.postgresql:postgresql:42.6.0")

        testCompileOnly("org.projectlombok:lombok:1.18.32")
        testAnnotationProcessor("org.projectlombok:lombok:1.18.32")

        implementation("org.apache.commons:commons-lang3:3.12.0")

        implementation ("com.google.code.gson:gson:2.10.1")
        implementation ("com.google.guava:guava:31.1-jre")

        implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.17.0")
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.17.0")

        testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    }
}
project(":common"){
    apply(plugin = "application")
    application{
        mainClass.set("com.bubla.Main")
    }
    tasks.getByName<Test>("test") {
        useJUnitPlatform()
    }

    tasks.withType<JavaCompile> {
        options.compilerArgs.addAll(listOf("-Xlint:unchecked"))
    }

    tasks.named<JavaExec>("run") {
        standardInput = System.`in`
    }

// Добавление задачи для сборки JAR с зависимостями
    tasks.jar {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE

        manifest {
            attributes["Main-Class"] = "com.bubla.Main" // Указываем главный класс для JAR
        }

        // Включаем все зависимости в JAR (Fat JAR)
        from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })

        // Имя JAR-файла
        archiveFileName.set("common.jar")
    }

    dependencies {
        compileOnly("org.projectlombok:lombok:1.18.32")
        annotationProcessor("org.projectlombok:lombok:1.18.32")

        testCompileOnly("org.projectlombok:lombok:1.18.32")
        testAnnotationProcessor("org.projectlombok:lombok:1.18.32")

        implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.17.0")
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.17.0")

        testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    }
}
project(":client2"){
    apply(plugin = "application")
    application{
        mainClass.set("com.bubla.Main")
    }
    tasks.getByName<Test>("test") {
        useJUnitPlatform()
    }

    tasks.withType<JavaCompile> {
        options.compilerArgs.addAll(listOf("-Xlint:unchecked"))
    }

    tasks.named<JavaExec>("run") {
        standardInput = System.`in`
    }

// Добавление задачи для сборки JAR с зависимостями
    tasks.jar {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE

        manifest {
            attributes["Main-Class"] = "com.bubla.Main" // Указываем главный класс для JAR
        }

        // Включаем все зависимости в JAR (Fat JAR)
        from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })

        // Имя JAR-файла
        archiveFileName.set("client.jar")
    }

    dependencies {
        implementation(project(":common"))
        compileOnly("org.projectlombok:lombok:1.18.32")
        annotationProcessor("org.projectlombok:lombok:1.18.32")

        testCompileOnly("org.projectlombok:lombok:1.18.32")
        testAnnotationProcessor("org.projectlombok:lombok:1.18.32")

        implementation("org.apache.commons:commons-lang3:3.12.0")

        implementation ("com.google.code.gson:gson:2.10.1")
        implementation ("com.google.guava:guava:31.1-jre")


        testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    }
}