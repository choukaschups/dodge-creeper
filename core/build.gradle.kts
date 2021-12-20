plugins {
    id("dodgecreeper.java-conventions")
    id("fr.il_totore.manadrop") version "0.4.1-SNAPSHOT"
    id("com.github.johnrengelman.shadow") version "7.1.0"
}

repositories {
    mavenCentral()

    maven {
        name = "CodeMC repository"
        url = uri("https://repo.codemc.org/repository/maven-public/")
    }

    maven {
        name = "Jitpack repository"
        url = uri("https://jitpack.io")
    }

    mavenLocal()
}

dependencies {
    // API
    implementation(project(":api"))

    // NMS
    compileOnly(group = "org.spigotmc", name = "spigot", version = "1.8.8-R0.1-SNAPSHOT")

    // SmartInvs
    implementation(group = "fr.minuskube.inv", name = "smart-invs", version = "1.2.7")

    // BungeeChannelAPI
    implementation(group = "io.github.leonardosnt", name = "bungeechannelapi", version = "1.0.0-SNAPSHOT")

    // NBTEditor
    implementation(group = "io.github.bananapuncher714", name = "nbteditor", version = "7.18.0")

    // FastBoard
    implementation(group = "fr.mrmicky", name = "fastboard", version = "1.2.1")

    // Guice
    implementation(group = "com.google.inject", name = "guice", version = "4.1.0")
    implementation(group = "com.google.inject.extensions", name = "guice-assistedinject", version = "4.1.0")
    implementation(group = "com.google.inject.extensions", name = "guice-throwingproviders", version = "4.1.0")
    implementation(group = "com.google.inject.extensions", name = "guice-multibindings", version = "4.1.0")

    // Jupiter
    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter-api", version = "5.8.2")
    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter-engine", version = "5.8.2")

    // MockBukkit
    testImplementation(group = "com.github.seeseemelk", name = "MockBukkit", version = "v1.8-spigot-SNAPSHOT")
}

spigot {
    desc {
        named(project.properties["plugin-name"].toString())
        authors(project.properties["plugin-author"].toString())
        main(project.properties["plugin-main-class"].toString())
    }
}

tasks.buildTools {
    mavenPath = "/usr/local/bin/mvn"
    versions("1.8")
    workDir = file("/Users/Juan/dodge-creeper/")
}

tasks.processResources {
    finalizedBy("spigotPlugin")
}

tasks.shadowJar {
    archiveFileName.set("${project.properties["plugin-name"].toString()}.jar")
    destinationDirectory.set(file(System.getenv("SERVER_PLUGINS_FOLDER")))
}

tasks.test {
    useJUnitPlatform()
}
