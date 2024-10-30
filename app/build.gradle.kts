plugins {
    alias(libs.plugins.android.application)
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.SE1730.Group3.JobLink"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.SE1730.Group3.JobLink"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    viewBinding {
        enable = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    //networking
    implementation(libs.retrofit)

    implementation(libs.moshi)
    implementation(libs.moshi.adapter)
    implementation(libs.converter.moshi)

    implementation(libs.lottie)
    implementation(libs.constraintlayout)
    implementation(libs.recyclerview)
    implementation(libs.shimmer)
    implementation(libs.picasso)

    implementation(libs.hilt)

    implementation(libs.mapstruct)
    implementation(libs.mapstruct.processor)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)

    //lombok
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    //room
    implementation(libs.room)
    annotationProcessor(libs.room.compiler)

    // Hilt Dependency Injection
    implementation(libs.hilt)
    annotationProcessor(libs.hilt.android.compiler)

    // MapStruct
    implementation(libs.mapstruct)
    annotationProcessor(libs.mapstruct.processor)

    //Three ten ABP
    implementation(libs.threetenABP)

    //rxjava
    implementation(libs.rxjava)
    implementation(libs.rxandroid)
    implementation(libs.adapter.rxjava)
    implementation(libs.room.rxjava)

    //play services
    implementation(libs.play.services.maps)
    implementation(libs.play.services.location)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    testCompileOnly(libs.lombok)
    testAnnotationProcessor(libs.lombok)

    implementation(libs.viewPager2)

    implementation(libs.richEditor)

    implementation(libs.signalR)
}
