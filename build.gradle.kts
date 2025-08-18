plugins {
    id("java")
    id("application")
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "org.example"
version = "25.08.17"

repositories {
    mavenCentral()
}

dependencies {
    // JavaFX 의존성을 명시적으로 추가
    implementation("org.openjfx:javafx-controls:20")
    implementation("org.openjfx:javafx-fxml:20")
    implementation("org.openjfx:javafx-base:20")
    implementation("org.openjfx:javafx-graphics:20")
    
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

javafx {
    version = "20"
    modules = listOf("javafx.controls", "javafx.fxml")
}

application {
    mainClass.set("org.example.NumberCalculatorApp")
    applicationName = "길이 분할 계산기"
}

// Shadow JAR 설정 - 완벽한 의존성 포함
tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
    archiveBaseName.set("길이분할계산기")
    archiveClassifier.set("")
    archiveVersion.set(project.version.toString())
    
    manifest {
        attributes(
            "Main-Class" to "org.example.NumberCalculatorApp",
            "Implementation-Title" to "길이 분할 계산기",
            "Implementation-Version" to project.version
        )
    }
    
    mergeServiceFiles()
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

// JPackage 태스크 - 실행 가능한 앱 생성
tasks.register<Exec>("createExe") {
    group = "build"
    description = "Creates Windows app directory"
    
    dependsOn("shadowJar")
    
    commandLine("jpackage", 
        "--input", "build/libs",
        "--name", "LengthCalculator",
        "--main-jar", "길이분할계산기-25.08.17.jar",
        "--main-class", "org.example.NumberCalculatorApp",
        "--type", "app-image",
        "--dest", "build/distributions",
        "--app-version", project.version.toString(),
        "--vendor", "Jinwoo"
    )
    
    doFirst {
        // distributions 디렉토리 생성
        file("build/distributions").mkdirs()
    }
}