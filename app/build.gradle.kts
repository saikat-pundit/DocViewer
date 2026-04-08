plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.fileviewer"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.fileviewer"
        minSdk = 26 
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    // STRATEGY 1: ABI Splitting
    // This tells Gradle to build separate APKs for ARM devices only, 
    // stripping out x86/x86_64 emulator code from the PDF viewer.
    splits {
        abi {
            isEnable = true
            reset()
            include("armeabi-v7a", "arm64-v8a") // Only support standard Android ARM architectures
            isUniversalApk = false // Do not build the massive 31MB universal APK
        }
    }

    buildTypes {
        release {
            // STRATEGY 2: Aggressive Code and Resource Shrinking
            isMinifyEnabled = true
            isShrinkResources = true // <-- ADDED: Strips unused XML and drawables
            proguardFiles(
                // Using the 'optimize' version of ProGuard rules for maximum reduction
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
        viewBinding = true
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    
    // PDF Viewer
    implementation("com.github.DImuthuUpe:AndroidPdfViewer:2.8.1")
    
    // STRATEGY 3: Apache POI Diet
    // Exclude massive cryptography, signature validation, and 2D graphics rendering libraries.
    // We only need the core text/table extractors.
    implementation("org.apache.poi:poi:5.2.5")
    implementation("org.apache.poi:poi-ooxml:5.2.5") {
        exclude(group = "org.apache.santuario", module = "xmlsec") // Digital signatures
        exclude(group = "org.bouncycastle", module = "bcpkix-jdk15on") // Heavy Crypto
        exclude(group = "org.bouncycastle", module = "bcprov-jdk15on") // Heavy Crypto
        exclude(group = "org.apache.xmlgraphics", module = "batik-all") // Advanced graphics rendering
        exclude(group = "com.github.virtuald", module = "curvesapi") // Complex math curves
    }
    
    // CSV
    implementation("com.opencsv:opencsv:5.9")
    
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
