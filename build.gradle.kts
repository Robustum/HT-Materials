import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    kotlin("jvm") version libs.versions.kotlin
    alias(libs.plugins.fabric.loom)
    alias(libs.plugins.ktlint)
}

group = "io.github.hiiragi283.material"
version = "2.0.0+1.16.5"

sourceSets {
    create("api")
    main {
        // compileClasspath.forEach { print("$it\n") }
        compileClasspath += getByName("api").output
        runtimeClasspath += getByName("api").output
    }
}

configurations {
    // names.forEach { print("$it\n") }
    getByName("apiCompileClasspath").extendsFrom(getByName("compileClasspath"))
}

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
    maven(url = "https://maven.shedaniel.me/")
    maven(url = "https://maven.terraformersmc.com/releases/")
}

dependencies {
    minecraft(libs.minecraft)
    mappings("net.fabricmc:yarn:${libs.versions.fabric.yarn.get()}:v2")
    modApi(libs.bundles.mods.fabric) {
        exclude(module = "fabric-api")
        exclude(module = "fabric-loader")
    }
    modCompileOnly(libs.bundles.mods.compile) {
        exclude(module = "fabric-api")
        exclude(module = "fabric-loader")
    }
    modLocalRuntime(libs.bundles.mods.debug) {
        exclude(module = "fabric-api")
        exclude(module = "fabric-loader")
    }
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

loom {
    accessWidenerPath = file("src/main/resources/ht_materials.accesswidener")
    runs {
        getByName("client") {
            programArg("--username=Developer")
            vmArg("-Dmixin.debug.export=true")
        }
        getByName("server") {
            runDir = "server"
        }
    }
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
    processResources {
        inputs.property("version", project.version)
        filesMatching("fabric.mod.json") {
            expand("version" to project.version)
        }
    }
    jar {
        from("LICENSE") {
            rename { "${it}_${project.base.archivesName.get()}" }
        }
    }
    compileJava {
        source(sourceSets.getByName("api").allSource)
    }
}
