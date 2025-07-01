plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.ptpws.GartekGo"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.ptpws.GartekGo"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    kotlin{
        sourceSets {
            all {
                languageSettings.optIn("kotlinx.serialization.ExperimentalSerializationApi")
            }
        }
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("androidx.compose.material:material-icons-extended:1.6.0")
    implementation(libs.androidx.constraintlayout)
    implementation ("androidx.constraintlayout:constraintlayout-compose:1.0.1")
    // Accompanist permissions

    implementation (libs.accompanist.permissions)

    // Activity Compose
    implementation("androidx.activity:activity-compose:1.7.2")
    // Coil untuk load gambar dari URI
    implementation("io.coil-kt:coil-compose:2.2.2")
    // Navigasi
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation(libs.androidx.navigation.compose.android)

    //system ui controller
    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.34.0")

    //pager
    implementation("androidx.compose.foundation:foundation:1.6.0")

    //swipe refresh
    implementation (libs.accompanist.swiperefresh)

    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.androidx.navigation.runtime.android)
    implementation(libs.firebase.storage)

    //serialization json
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.firebase.dataconnect)

    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    //yt
    implementation("androidx.media3:media3-exoplayer:1.3.1")
    implementation("androidx.media3:media3-ui:1.3.1")
    implementation("com.google.firebase:firebase-storage-ktx:20.3.0")


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}