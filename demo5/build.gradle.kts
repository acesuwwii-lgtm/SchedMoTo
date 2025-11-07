plugins {
    java
    application
    id("org.javamodularity.moduleplugin") version "1.8.15"
    id("org.openjfx.javafxplugin") version "0.0.13"
    id("org.beryx.jlink") version "2.25.0"
}

group = "com.OOP.Naingue"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val junitVersion = "5.12.1"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(24)
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

application {
    mainModule.set("com.oop.naingue.demo5")
    mainClass.set("com.oop.naingue.demo5.HelloApplication")
}

javafx {
    version = "21.0.6"
    modules = listOf("javafx.controls", "javafx.fxml")
}

dependencies {
    implementation("org.controlsfx:controlsfx:11.2.1")
    implementation("com.dlsc.formsfx:formsfx-core:11.6.0") {
        exclude(group = "org.openjfx")
    }
    implementation("net.synedra:validatorfx:0.6.1") {
        exclude(group = "org.openjfx")
    }
    implementation("org.kordamp.bootstrapfx:bootstrapfx-core:0.4.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.0.21")

    implementation("org.mongodb:mongodb-driver-sync:4.11.1")
    implementation("org.openjfx:javafx-controls:17.0.2")
    implementation("org.openjfx:javafx-fxml:17.0.2")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

jlink {
    imageZip.set(layout.buildDirectory.file("/distributions/app-${javafx.platform.classifier}.zip"))
    options.set(listOf("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages"))
    launcher {
        name = "app"
    }
}
