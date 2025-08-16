plugins {
    id("com.android.application") version "8.0.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.21" apply false
    kotlin("android")
    kotlin("kapt") // Bật kapt để dùng annotation processor (Glide compiler)
}

android {
    namespace = "com.example.picture"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.picture"
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
}

dependencies {
    // Glide thư viện load ảnh kèm annotation processor
    implementation("com.github.bumptech.glide:glide:4.16.0")

    // Các dependencies Android phổ biến khác
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.activity:activity-ktx:1.7.2")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
