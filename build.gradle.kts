plugins {
    id("java")
    id("application")
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "org.example"
version = "25.11.19"

repositories { 
    mavenCentral() 
}

dependencies {
    // JavaFX 의존성(개발/테스트 용). jlink는 jmods에서 끌어옵니다.
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

// Shadow JAR (모든 의존성 포함 JAR)
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
    // JavaFX는 리플렉션/리소스가 많아서 minimize()는 권장 X
    mergeServiceFiles()
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

// 경로를 프로퍼티로 받아서 OS마다 쉽게 바꿀 수 있게
val fxJmods = (findProperty("javafxJmods") as String?) ?: "javafx-jmods-20"
val jdkJmods = (System.getenv("JAVA_HOME")?.let { "$it/jmods" }) ?: "C:/Program Files/Java/jdk-21/jmods"

// 1) jlink로 JavaFX 포함 커스텀 런타임 생성
tasks.register<Exec>("jlinkImage") {
    group = "build"
    description = "Create custom runtime image with JavaFX"

    // Windows 경로 구분자 ;  (Mac/Linux는 :)
    val modulePath = "$jdkJmods;$fxJmods"

    // 포함할 모듈: 필요시 추가(java.sql 등)
    val modules = "java.base,java.logging,java.prefs,java.desktop,javafx.controls,javafx.fxml"

    doFirst {
        // 기존 런타임 디렉토리 삭제
        delete("build/runtime")
    }

    commandLine(
        "jlink",
        "--module-path", modulePath,
        "--add-modules", modules,
        "--strip-java-debug-attributes",
        "--no-header-files",
        "--no-man-pages",
        "--compress=zip-6",
        "--output", "build/runtime"
    )
}

// 한글 이름 사용
val appKoreanName = "길이분할계산기"

// 2) jpackage로 EXE 생성(런타임 포함)
tasks.register<Exec>("createExe") {
    group = "build"
    description = "Create self-contained Windows EXE (bundled runtime)"
    dependsOn("shadowJar", "jlinkImage")

    doFirst {
        // 이전 빌드 잔여물 제거(깨진 이름 포함 폴더까지 싹 정리)
        // Windows에서 파일이 잠겨있을 수 있으므로 강제 삭제 시도
        try {
            delete("build/distributions")
        } catch (e: Exception) {
            println("경고: build/distributions 삭제 실패. 실행 중인 프로세스가 있는지 확인하세요.")
            println("해결 방법: LengthCalculator.exe가 실행 중이면 종료한 후 다시 시도하세요.")
        }
    }

    commandLine(
        "jpackage",
        "--type", "app-image",
        "--input", "build/libs",
        "--name", "LengthCalculator",
        "--main-jar", "길이분할계산기-${project.version}.jar",
        "--main-class", "org.example.NumberCalculatorApp",
        "--dest", "build/distributions",
        "--icon", "app_icon.ico",
        "--app-version", project.version.toString(),
        "--vendor", "Jinwoo",
        "--runtime-image", "build/runtime"
    )
}