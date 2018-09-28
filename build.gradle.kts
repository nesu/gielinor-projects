import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "lt.monad"
version = "1.0"

buildscript {
    repositories { mavenCentral() }
    dependencies {  classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.2.70") }
}

plugins {
    kotlin("jvm") version "1.2.70"
}

subprojects {
    apply(plugin = "kotlin")
    apply(plugin = "java")
    repositories { mavenCentral() }
    dependencies { compile(kotlin("stdlib-jdk8", "1.2.70")) }
    tasks.withType<KotlinCompile> { kotlinOptions.jvmTarget = "1.8" }
    configurations.all { resolutionStrategy { preferProjectModules() } }
    java.sourceSets {
        "main" {
            java.setSrcDirs(listOf("src"))
            resources.srcDir(file("resources"))
        }
    }

    extra.set("export", true)
}

project(":automata-library") {
    description = "RuneMate helper library"
    dependencies { compile(files("C:\\Program Files (x86)\\RuneMate\\RuneMate.jar")) }
}

project(":automata-utility-runecrafting") {
    description = "Library that contains functionality for runecrafting"
    dependencies {
        compile(project(":automata-library"))
        compile(files("C:\\Program Files (x86)\\RuneMate\\RuneMate.jar"))
    }
}

subprojects {
    if (extra.get("export") as Boolean) {
        tasks.getByName("build").dependsOn(task("fatJar", type = Jar::class) {
            baseName = "${project.name}-export"

            val sources = configurations.runtime.filter { !it.name.contains("RuneMate") }
                    .map { if (it.isDirectory) it else zipTree(it) }

            from(sources)
            with(tasks["jar"] as CopySpec)
        })
    }
}