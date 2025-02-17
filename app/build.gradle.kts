plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.voiceproject"
    compileSdk = 35



    defaultConfig {
        applicationId = "com.example.voiceproject"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
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
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation("com.google.mlkit:translate:17.0.3")

//    implementation("com.google.cloud:google-cloud-translate:2.54.0")
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")
//    implementation ("com.google.cloud:google-cloud-speech:1.29.1")
//    implementation ("com.google.auth:google-auth-library-oauth2-http:0.26.0")
//    implementation ("io.grpc:grpc-okhttp:1.38.1")
//    implementation ("io.grpc:grpc-stub:1.38.1")
//    implementation ("com.google.api:gax:1.58.0")
}