val archivesBaseName: String by rootProject

base.archivesName = "$archivesBaseName-api"

val fabricLoader: String by rootProject

architectury {
    common("fabric", "forge")
}

dependencies {
    modImplementation("net.fabricmc:fabric-loader:$fabricLoader")
}

tasks {
    remapJar {
        archiveClassifier = "raw"
    }
}
