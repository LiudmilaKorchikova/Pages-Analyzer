plugins {
    id("java")
    id("application")
    id("checkstyle")
    id("jacoco")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.freefair.lombok") version "8.3"
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("hexlet.code.App")
}

repositories {
    mavenCentral()
}

checkstyle {
    toolVersion = "10.12.4"
    configFile = file("config/checkstyle/checkstyle.xml")
}

jacoco {
    toolVersion = "0.8.12"
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("com.fasterxml.jackson.core:jackson-core:2.15.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.0")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.14.0")

    implementation("org.slf4j:slf4j-simple:2.0.16")
    implementation("io.javalin:javalin:6.3.0")

    implementation("io.javalin:javalin:6.1.3")
    implementation("gg.jte:jte:3.1.9")
    implementation("io.javalin:javalin-rendering:6.1.3")
    implementation("io.javalin:javalin-bundle:6.1.3")

    implementation("com.h2database:h2:2.2.220")
    runtimeOnly("com.h2database:h2:2.2.224")
    runtimeOnly("org.postgresql:postgresql:42.7.2")
    implementation("com.zaxxer:HikariCP:5.0.1")


    implementation("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.30")
    testImplementation("org.projectlombok:lombok:1.18.30")
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        html.required.set(false)
    }
}

tasks.withType<JavaCompile> {
    options.compilerArgs.addAll(
        listOf(
            "-Amapstruct.suppressGeneratorTimestamp=true",
            "-s",
            file("build/generated/sources/annotationProcessor/java/main").absolutePath
        )
    )
}

java {
    sourceSets["main"].java {
        srcDir("build/generated/sources/annotationProcessor/java/main")
    }
}
