import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    kotlin("jvm") version "1.9.21"
    id("architectury-plugin") version "3.4-SNAPSHOT"
    id("dev.architectury.loom") version "1.5-SNAPSHOT" apply(false)
    id("org.jlleitschuh.gradle.ktlint") version "12.1.0"
}

val archivesBaseName: String by rootProject
val modVersion: String by rootProject
val mavenGroup: String by rootProject

architectury {
    minecraft = "1.16.5"
}

subprojects {
    apply(plugin = "dev.architectury.loom")

    withGroovyBuilder {
        "loom" {
            "silentMojangMappingsLicense"()
        }
    }

    dependencies {
        add("minecraft", "com.mojang:minecraft:1.16.5")
        add("mappings", "net.fabricmc:yarn:1.16.5+build.10:v2")
        testImplementation("org.jetbrains.kotlin:kotlin-test")
    }
}

allprojects {
    apply(plugin = "kotlin")
    apply(plugin = "architectury-plugin")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    base.archivesName = archivesBaseName
    version = modVersion
    group = mavenGroup

    repositories {
        mavenCentral()
        maven(url = "https://cursemaven.com") {
            content { includeGroup("curse.maven") }
        }
        maven(url = "https://api.modrinth.com/maven") {
            content { includeGroup("maven.modrinth") }
        }
        maven(url = "https://maven.architectury.dev/")
        maven(url = "https://maven.blamejared.com") {
            content { includeGroup("vazkii.patchouli") }
        }
        maven(url = "https://maven.shedaniel.me/") //REI
        maven(url = "https://maven.terraformersmc.com/releases/")
        maven(url = "https://thedarkcolour.github.io/KotlinForForge/") //KfF
        maven(url = "https://dvs1.progwml6.com/files/maven/") //JEI
    }

    kotlin {
        jvmToolchain(8)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
            freeCompilerArgs.add("-Xjvm-default=all")
        }
    }

    java {
        withSourcesJar()
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    ktlint {
        reporters {
            reporter(ReporterType.HTML)
            reporter(ReporterType.SARIF)
        }
        filter {
            exclude("**/generated/**")
            include("**/kotlin/**")
        }
    }

    tasks {
        test {
            useJUnitPlatform()
        }
        jar {
            from("LICENSE") {
                rename { "${it}_${archivesBaseName}" }
            }
        }
    }
}