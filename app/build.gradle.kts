plugins {
    alias(libs.plugins.android.application)
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
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
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
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

    testCompileOnly(libs.lombok)
    testAnnotationProcessor(libs.lombok)

    implementation(libs.viewPager2)

    implementation(libs.richEditor)

    implementation(libs.signalR)
}
