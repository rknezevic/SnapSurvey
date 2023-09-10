plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    compileSdkVersion(31)
    defaultConfig {
        applicationId = ("com.robertknezevic.qme")
        minSdkVersion(16)
        targetSdkVersion(31)
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.21")
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    implementation("com.github.aurae.retrofit2:converter-scalars:2.9.0") // Axios zavisnost
    implementation("io.ktor:ktor-client-core:2.3.3")
    implementation("io.ktor:ktor-client-json:2.3.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:2.3.3")


    // Dodajte ostale zavisnosti prema potrebi
}
