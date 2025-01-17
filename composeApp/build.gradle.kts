import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.buildKonfig)
    id("com.google.gms.google-services")
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.kotlinx.coroutines.android)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            // Firebase Auth and Realtime DB Dependencies
            implementation(libs.gitlive.firebase.auth)
            implementation(libs.firebase.database)

            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.androidx.lifecycle.viewmodel)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

android {
    namespace = "com.dineshworkspace.juicekadai"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.dineshworkspace.juicekadai"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
    dependencies {
        debugImplementation(compose.uiTooling)
    }
}
dependencies {
    implementation(libs.navigation.compose)
}

buildkonfig {
    packageName = "com.dineshworkspace.juicekadai"

    defaultConfigs {
        val projectId: String =
            gradleLocalProperties(rootDir).getProperty("PROJECT_ID")

        val firebaseDatabaseUrl: String =
            gradleLocalProperties(rootDir).getProperty("FIREBASE_DB_URL")

        val appId: String =
            gradleLocalProperties(rootDir).getProperty("APP_ID")

        val apiKey: String =
            gradleLocalProperties(rootDir).getProperty("API_KEY")

        val printHttpLogs: String =
            gradleLocalProperties(rootDir).getProperty("PRINT_HTTP_LOGS")

        val baseLocation: String =
            gradleLocalProperties(rootDir).getProperty("BASE_LOCATION")

        buildConfigField(STRING, "PROJECT_ID", projectId)
        buildConfigField(STRING, "FIREBASE_DB_URL", firebaseDatabaseUrl)
        buildConfigField(STRING, "APP_ID", appId)
        buildConfigField(STRING, "API_KEY", apiKey)
        buildConfigField(STRING, "PRINT_HTTP_LOGS", printHttpLogs)
        buildConfigField(STRING, "BASE_LOCATION", baseLocation)
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.dineshworkspace.juicekadai"
            packageVersion = "1.0.0"
            macOS {
                iconFile.set(project.file("src/commonMain/composeResources/drawable/ic_juice.xml"))
            }
            linux {
                iconFile.set(project.file("src/commonMain/composeResources/drawable/ic_juice.xml"))
            }
        }
    }
}