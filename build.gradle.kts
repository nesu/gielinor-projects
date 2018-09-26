import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "lt.monad"
version = "1.0"

var DEFAULT_COPY_DIR = "C:\\.dev\\rune.d\\built"

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
    extra.set("copy", false)
}

project(":automata-library") {
    description = "RuneMate helper library"
    dependencies { compile(files("C:\\Program Files (x86)\\RuneMate\\RuneMate.jar")) }
}

project(":gielinor-trees") {
    description = "Does farming tree runs"
    dependencies { compile(project(":automata-library")) }
    extra.set("copy", true)
}

project(":gielinor-magicka") {
    description = "Magic training using various methods"
    dependencies { compile(project(":automata-library")) }
    extra.set("copy", true)
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

    if (extra.get("copy") as Boolean) {
        tasks.getByName("build").finalizedBy(task("copy_jar") {
            doLast {
                println("Copying project ${project.name} to $DEFAULT_COPY_DIR.")
                copy {
                    from("build/libs/${project.name}-export.jar")
                    into(DEFAULT_COPY_DIR)
                }
            }
        })
    }
}