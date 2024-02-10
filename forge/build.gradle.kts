plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

architectury {
    platformSetupLoomIde()
    forge()
}

loom {
    accessWidenerPath = project(":common").loom.accessWidenerPath

    forge {
        convertAccessWideners = true
    }
}

val archivesBaseName: String by rootProject

base.archivesName = "$archivesBaseName-forge"

val common = configurations.maybeCreate("common")
val shadowCommon = configurations.maybeCreate("shadowCommon")

configurations {
    compileClasspath.get().extendsFrom(common)
    runtimeClasspath.get().extendsFrom(common)
    getByName("developmentForge").extendsFrom(common)
}

val forgeLoader: String by rootProject

dependencies {
    add("forge", "net.minecraftforge:forge:$forgeLoader")
    modImplementation("thedarkcolour:kotlinforforge:1.17.0")
    modImplementation("mezz.jei:jei-1.16.5:7.7.1.153")
    add(
        "common",
        project(path = ":common", configuration = "namedElements").apply { isTransitive = false },
    )
    add(
        "shadowCommon",
        project(path = ":common", configuration = "transformProductionForge").apply { isTransitive = false },
    )
}

java {
    withSourcesJar()
}

tasks {
    processResources {
        inputs.property("version", project.version)
        filesMatching("META-INF/mods.toml") {
            expand("version" to project.version)
        }
    }

    shadowJar {
        configurations = listOf(shadowCommon)
        archiveClassifier.set("dev-shadow")
    }

    remapJar {
        injectAccessWidener = true
        inputFile = shadowJar.get().archiveFile.get()
        dependsOn(shadowJar)
        archiveClassifier = null
    }

    jar {
        archiveClassifier = "dev"
    }

    sourcesJar {
        project(":common").tasks.sourcesJar.get().run {
            dependsOn(this)
            from(archiveFile.map(::zipTree))
        }
    }
}

components.getByName<AdhocComponentWithVariants>("java") {
    withVariantsFromConfiguration(project.configurations.shadowRuntimeElements.get()) {
        skip()
    }
}