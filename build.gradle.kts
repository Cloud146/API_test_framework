plugins {
    id("java")
    id("io.qameta.allure") version "2.12.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

// Библиотеки
val testngVersion = "7.12.0"
val restAssuredVersion = "6.0.0"
val wiremockVersion = "3.13.2"
val allureVersion = "2.27.0"
val jacksonVersion = "2.21.3"
val lombokVersion = "1.18.34"
val aspectjVersion = "1.9.25"

val aspectjWeaver: Configuration by configurations.creating

dependencies {
    add(aspectjWeaver.name, "org.aspectj:aspectjweaver:$aspectjVersion")

    // === 1. ЯДРО ФРЕЙМВОРКА (Доступно в src/main) ===
    // Эти библиотеки используются внутри классов GetRequest, SpecBuilder, ResponseAssert и т.д.
    implementation(platform("io.qameta.allure:allure-bom:$allureVersion"))
    implementation("io.rest-assured:rest-assured:$restAssuredVersion")
    implementation("io.qameta.allure:allure-rest-assured")
    implementation("org.assertj:assertj-core:3.27.7")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("ch.qos.logback:logback-classic:1.4.14")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:${jacksonVersion}")
    implementation("org.testng:testng:${testngVersion}")

    // === 2. ЗАПУСК ТЕСТОВ (Доступно только в src/test) ===
    // Эти инструменты нужны только там, где висят аннотации @Test
    testImplementation("io.qameta.allure:allure-testng")
    testImplementation("org.wiremock:wiremock-standalone:$wiremockVersion")

    // === 3. LOMBOK (Работает везде) ===
    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    testCompileOnly("org.projectlombok:lombok:$lombokVersion")
    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}
tasks.withType<ProcessResources>().configureEach {
    filteringCharset = "UTF-8"
}

tasks.test {
    systemProperty("file.encoding", "UTF-8")
    useTestNG {
        if (project.hasProperty("ci")) {
            suites("src/test/resources/testng.xml")
        }
    }
    testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = true
    }
}

allure {
    version.set(allureVersion)
}