plugins {
    id("java")
    id("application")
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("org.beryx.jlink") version "2.26.0"
}

group = "org.example"
version = "25.08.17"

repositories {
    mavenCentral()
}

dependencies {
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

// jlink 설정
jlink {
    options.set(listOf("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages"))
    launcher {
        name = "길이 분할 계산기"
        mainClass.set("org.example.NumberCalculatorApp")
    }
    jpackage {
        installerType = "exe"
        installerOptions = listOf(
            "--win-per-user-install",
            "--win-dir-chooser",
            "--win-menu",
            "--win-shortcut"
        )
        appVersion = project.version.toString()
        appName = "길이 분할 계산기"
        vendor = "Jinwoo"
        icon = "src/main/resources/icon.ico"
    }
}