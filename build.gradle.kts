import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
}

group = "jp.kaleidot725.eyegen"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    google()
    maven("https://packages.jetbrains.team/maven/p/kpm/public/")
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
}

dependencies {
    implementation(compose.components.resources)
    implementation(compose.desktop.currentOs) {
        exclude(group = "org.jetbrains.compose.material")
    }
    implementation("org.jetbrains.jewel:jewel-int-ui-standalone-241:0.18.1")
    implementation("org.jetbrains.jewel:jewel-int-ui-decorated-window-241:0.18.1")
    implementation("com.github.skydoves:colorpicker-compose:1.1.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.8.0")
    implementation("app.cash.molecule:molecule-runtime:2.0.0")
    implementation("com.russhwolf:multiplatform-settings:1.1.1")
}

kotlin {
    jvmToolchain {
        vendor = JvmVendorSpec.JETBRAINS
        languageVersion = JavaLanguageVersion.of(17)
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg)
            packageName = "EyeGen"
            packageVersion = "1.0.0"

            macOS {
                iconFile.set(project.file("icon.icns"))
            }
        }
    }
}
