plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.poc_sdk_ui_pdf_2"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.poc_sdk_ui_pdf_2"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        dataBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    /**
     * Step-1 : add spandan-sdk and spandan-sdk-ui dependency in build.gradle.kts file
     * **/
    implementation("in.sunfox.healthcare.commons.android.spandan-sdk:spandan-sdk:2.0.2-beta1")
    implementation("in.sunfox.healthcare.commons.android.spandan-sdk:spandan-sdk-ui:1.0.1-beta1")

}