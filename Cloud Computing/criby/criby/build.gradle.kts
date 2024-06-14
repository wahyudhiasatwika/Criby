plugins {
    kotlin("jvm") version "1.6.0"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-netty:1.6.5")
    implementation("io.ktor:ktor-server-core:1.6.5")
    implementation("io.ktor:ktor-auth:1.6.5")
    implementation("io.ktor:ktor-auth-jwt:1.6.5")
    implementation("io.ktor:ktor-serialization:1.6.5")
    implementation("ch.qos.logback:logback-classic:1.2.6")
    implementation("io.ktor:ktor-client-jetty:1.6.5")
    implementation("io.ktor:ktor-client-serialization-jvm:1.6.5")
    implementation("io.ktor:ktor-client-core-jvm:1.6.5")
    implementation("io.ktor:ktor-client-json-jvm:1.6.5")
    implementation("io.ktor:ktor-client-logging-jvm:1.6.5")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")

    implementation("org.jetbrains.exposed:exposed-core:0.38.3")
    implementation("org.jetbrains.exposed:exposed-dao:0.38.3")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.38.3")
    implementation("org.mariadb.jdbc:mariadb-java-client:2.7.4")
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://repo.kotlinlang.org/artifactory/kotlin")
    }
}


application {
    // Define the main class manually if necessary:
    // mainClassName = "com.criby.ApplicationKt"

    // Or let Gradle find the main class for you (recommended):
    application.mainClass.set("Criby")
}
