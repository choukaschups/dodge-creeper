plugins {
    id("dodgecreeper.java-conventions")
    id("fr.il_totore.manadrop") version "0.4.1-SNAPSHOT"
    id("com.github.johnrengelman.shadow") version "7.1.0"
}

dependencies {
    implementation(project(":api"))
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
