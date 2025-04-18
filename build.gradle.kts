plugins {
    java
}

tasks.named<Jar>("jar") {
    manifest {
        attributes["Main-Class"] = "Main"
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.json:json:20250107")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(23))
    }
    sourceSets {
        main {
            java.srcDirs("src/main/java")
        }
    }
}