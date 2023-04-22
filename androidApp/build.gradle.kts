//import java.util.Properties
//import java.io.FileInputStream

plugins {
    id("com.android.application")
    kotlin("android")
}

//val apikeyPropertiesFile = rootProject.file("apikey.properties")
//val apikeyProperties = Properties()
//apikeyProperties.load(FileInputStream(apikeyPropertiesFile))

android {
    namespace = "com.testapp.imagefinder.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.testapp.imagefinder.android"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "API_KEY", "fsdf")
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    val compose_version = "1.2.0"

    implementation(project(":shared"))
    implementation("androidx.compose.ui:ui:$compose_version")
    implementation("androidx.compose.material:material:$compose_version")
    implementation("androidx.compose.compiler:compiler:$compose_version")
    implementation("androidx.compose.ui:ui-tooling-preview:$compose_version")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$compose_version")
    implementation("androidx.compose.ui:ui-tooling:$compose_version")
    implementation("androidx.activity:activity-compose:1.7.1")
}