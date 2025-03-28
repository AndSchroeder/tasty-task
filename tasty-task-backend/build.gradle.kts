plugins {
    kotlin("jvm") version "2.1.10"
    id("io.quarkus")
}

repositories {
    mavenCentral()
    mavenLocal()
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

val playwrightVersion = "1.51.0"

val jacksonVersion = "2.18.3"
val quarkusRestVersion = "3.21.0"
dependencies {
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))

    implementation("io.quarkus:quarkus-kotlin")
    implementation("com.microsoft.playwright:playwright:$playwrightVersion")

    implementation("io.quarkus:quarkus-rest:$quarkusRestVersion")
    implementation("io.quarkus:quarkus-rest-jackson:$quarkusRestVersion")
    implementation("io.quarkus:quarkus-rest-links:$quarkusRestVersion")

    implementation("io.quarkus:quarkus-mongodb-panache")
    implementation("io.quarkus:quarkus-mongodb-panache-kotlin")


    implementation("io.quarkus:quarkus-config-yaml")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")

    implementation("io.quarkus:quarkus-arc")
    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured")

}

group = "de.reinhardy.tastytask"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.withType<Test> {
    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
    jvmArgs("-Dquarkus.test.continuous-testing.enabled=true")

}
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}
