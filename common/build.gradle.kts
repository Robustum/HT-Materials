val archivesBaseName: String by rootProject

base.archivesName = "$archivesBaseName-api"

val fabricLoader: String by rootProject

dependencies {
    modImplementation("net.fabricmc:fabric-loader:$fabricLoader")
}

architectury {
    common("fabric", "forge")
}

tasks {
    remapJar {
        archiveClassifier = "raw"
    }
}
