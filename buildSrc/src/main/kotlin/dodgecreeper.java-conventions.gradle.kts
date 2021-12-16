plugins {
    java
}

group = "me.choukas"
version = project.properties["plugin-version"].toString()

repositories {
    maven {
        name = "Paper API repository"
        url = uri("https://papermc.io/repo/repository/maven-public/")
    }
}

dependencies {
    // Paper API
    compileOnly("org.github.paperspigot:paperspigot-api:1.8.8-R0.1-SNAPSHOT")
}
