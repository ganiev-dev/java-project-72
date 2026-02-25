plugins {
    id("com.github.ben-manes.versions") version "0.52.0"
    id("org.sonarqube") version "6.2.0.5505"
    id("jacoco")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    checkstyle
    application
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

application {
    mainClass = "hexlet.code.App"
}

repositories {
    mavenCentral()
}

sonar {
    properties {
        property("sonar.projectKey", "ganiev-dev_java-project-72")
        property("sonar.organization", "ganiev-dev")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.junit.reportPaths", "build/test-results/test")
        property("sonar.coverage.jacoco.xmlReportPaths", "build/reports/jacoco/test/jacocoTestReport.xml")
    }
}

dependencies {
    dependencies {

        implementation("io.javalin:javalin:6.1.3")
        implementation("io.javalin:javalin-rendering:6.1.3")
        implementation("org.slf4j:slf4j-simple:2.0.16")
        implementation("com.zaxxer:HikariCP:5.0.1")
        implementation("org.postgresql:postgresql:42.7.2")
        testImplementation("com.h2database:h2:2.2.224")
        testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
        testImplementation("io.javalin:javalin-testtools:6.1.3")
        testImplementation("org.assertj:assertj-core:3.25.3")

        implementation("gg.jte:jte:3.1.9")
        compileOnly("org.projectlombok:lombok:1.18.30")
        annotationProcessor("org.projectlombok:lombok:1.18.30")
    }

}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

jacoco {
    toolVersion = "0.8.13"
    reportsDirectory = layout.buildDirectory.dir("customJacocoReportDir")
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        xml.outputLocation.set(layout.buildDirectory.file("reports/jacoco/test/jacocoTestReport.xml"))
        html.required.set(true)
    }
}


tasks.shadowJar {
    archiveBaseName.set("app")
    archiveVersion.set("1.0-SNAPSHOT")
    archiveClassifier.set("all") // => app-1.0-SNAPSHOT-all.jar

    // объединение service files (важно для JDBC драйверов)
    mergeServiceFiles()

    // манифест с указанием главного класса
    manifest {
        attributes("Main-Class" to application.mainClass.get())
    }
}
