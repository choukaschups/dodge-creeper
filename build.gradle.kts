import fr.il_totore.manadrop.MinecraftRepositoryHelper.paperPublic

plugins {
    java
    id("fr.il_totore.manadrop") version "0.4.1-SNAPSHOT"
    id("com.github.johnrengelman.shadow") version "7.1.0"
}

group = "me.choukas"
version = "1.0-SNAPSHOT"

repositories {
    paperPublic()
}

dependencies {
    // Paper API
    compileOnly("org.github.paperspigot:paperspigot-api:1.8.8-R0.1-SNAPSHOT")
}

spigot {
    desc {
        named(project.properties["plugin-name"].toString())
        authors("Choukas")
        main("me.choukas.dodgecreeper.DodgeCreeperPlugin")
    }
}

tasks.processResources {
    finalizedBy("spigotPlugin")
}

tasks.shadowJar {
    archiveFileName.set("${project.properties["plugin-name"].toString()}.jar")
    destinationDirectory.set(file(System.getenv("SERVER_PLUGINS_FOLDER")))
}
