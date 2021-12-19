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
    compileOnly(group = "org.github.paperspigot", name = "paperspigot-api", version = "1.8.8-R0.1-SNAPSHOT")
}
