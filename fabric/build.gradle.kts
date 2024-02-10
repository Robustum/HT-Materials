plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

architectury {
    platformSetupLoomIde()
    fabric()
}

loom {
    accessWidenerPath = project(":common").loom.accessWidenerPath

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

val archivesBaseName: String by rootProject

base.archivesName = "$archivesBaseName-fabric"

val common = configurations.maybeCreate("common")
val shadowCommon = configurations.maybeCreate("shadowCommon")

configurations {
    compileClasspath.get().extendsFrom(common)
    runtimeClasspath.get().extendsFrom(common)
    getByName("developmentFabric").extendsFrom(common)
}

val minecraftVersion: String by rootProject
val fabricLoader: String by rootProject
val fabricApi: String by rootProject
val fabricKotlin: String by rootProject
val modMenu: String by rootProject
val rei: String by rootProject
val indRev: String by rootProject

dependencies {
    modImplementation("net.fabricmc:fabric-loader:$fabricLoader") {
        exclude(module = "fabric-api")
        exclude(module = "fabric-loader")
    }
    modImplementation("net.fabricmc.fabric-api:fabric-api:$fabricApi") {
        exclude(module = "fabric-api")
        exclude(module = "fabric-loader")
    }
    modImplementation("net.fabricmc:fabric-language-kotlin:$fabricKotlin") {
        exclude(module = "fabric-api")
        exclude(module = "fabric-loader")
    }
    modImplementation("maven.modrinth:modmenu:$modMenu") {
        exclude(module = "fabric-api")
        exclude(module = "fabric-loader")
    }
    modImplementation("me.shedaniel:RoughlyEnoughItems:$rei") {
        exclude(module = "fabric-api")
        exclude(module = "fabric-loader")
    }
    modImplementation("TechReborn:TechReborn-1.16:+") {
        exclude(module = "fabric-api")
        exclude(module = "fabric-loader")
    }
    modImplementation("curse.maven:industrial-revolution-391708:$indRev") {
        exclude(module = "fabric-api")
        exclude(module = "fabric-loader")
    }
    add(
        "common",
        project(path = ":common", configuration = "namedElements").apply { isTransitive = false },
    )
    add(
        "shadowCommon",
        project(path = ":common", configuration = "transformProductionFabric").apply { isTransitive = false },
    )
}

java {
    withSourcesJar()
}

tasks {
    processResources {
        inputs.property("version", project.version)
        filesMatching("fabric.mod.json") {
            expand("version" to project.version)
        }
    }

    shadowJar {
        configurations = listOf(shadowCommon)
        archiveClassifier = "dev-shadow"
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