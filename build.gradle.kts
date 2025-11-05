plugins {
    id("java")
    id("application")
}

group = "org.rzsp"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.apache.logging.log4j:log4j-api:2.25.2")
    implementation("org.apache.logging.log4j:log4j-core:2.25.2")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("org.rzsp.filemanager.Main")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "org.rzsp.filemanager.Main"
    }
    from({
        configurations.runtimeClasspath.get().map {
            if (it.isDirectory) it else zipTree(it)
        }
    })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}