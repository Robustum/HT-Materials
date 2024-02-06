pluginManagement {
    repositories {
        maven(url = "https://maven.fabricmc.net/") {
            name = "fabric"
        }
        maven(url = "https://maven.architectury.dev/") {
            name = "architectury"
        }
        maven(url = "https://maven.minecraftforge.net/") {
            name = "forge"
        }
        gradlePluginPortal()
    }
}

buildscript {
    dependencies {
        classpath("com.google.code.gson:gson:2.10.1")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "HT-Materials"
include("common", "fabric", "forge")