plugins {
    kotlin("jvm") version "1.8.0"
    kotlin("plugin.serialization") version "1.8.0"
    id ("com.google.devtools.ksp") version "1.8.10-1.0.9"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
    mavenLocal()
}
sourceSets.main{
    java.srcDirs("build/generated/ksp/main/kotlin")
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.google.devtools.ksp:symbol-processing-api:1.8.10-1.0.9")
    implementation("io.insert-koin:koin-core:3.4.0")
    implementation("io.insert-koin:koin-annotations:1.2.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.21")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
    implementation("com.google.code.gson:gson:2.10.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    ksp("io.insert-koin:koin-ksp-compiler:1.2.0")
}

tasks.test {
    useJUnitPlatform()
}



kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("server.Main.kt")
}