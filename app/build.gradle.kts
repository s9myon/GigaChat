@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.application")
    id("kotlin-parcelize")
    kotlin("android")
    kotlin("kapt")
    kotlin("plugin.serialization") version "1.8.0"
}

android {
    namespace = "com.shudss00.gigachat"
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        applicationId = "com.shudss00.gigachat"
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        buildConfigField("String", "API_URL", "\"https://gigachads519.zulipchat.com/api/v1/\"")
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        getByName("debug") {
            buildConfigField("String", "USER_EMAIL", "\"shudss00@gmail.com\"")
            buildConfigField("String", "API_KEY", "\"WQRafvOHUEI6DkbjmOoDOh6sY545nFTv\"")
        }
        getByName("release") {
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
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}

dependencies {
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.appCompat)
    implementation(Dependencies.material)
    implementation(Dependencies.constraintLayout)
    // swipe refresh layout
    implementation(Dependencies.swipeRefreshLayout)
    // rxjava
    implementation(Dependencies.rxjava)
    implementation(Dependencies.rxkotlin)
    implementation(Dependencies.rxandroid)
    // retrofit
    implementation(Dependencies.retrofit)
    implementation(Dependencies.retrofitRxJavaAdapter)
    // okhttp3
    implementation(Dependencies.okhttp)
    implementation(Dependencies.okhttpLogInterceptor)
    // kotlin serialization
    implementation(Dependencies.kotlinSerializationJSON)
    implementation(Dependencies.kotlinSerializationRetrofit)
    // dagger
    implementation(Dependencies.dagger)
    kapt(Dependencies.daggerKapt)
    // coil
    implementation(Dependencies.coil)
    // timber
    implementation(Dependencies.timber)
    // viewBinding property delegate
    implementation(Dependencies.viewBindingPropertyDelegate)
    // viewBinding property delegate isn't building without these dependencies
    implementation(Dependencies.lifecycleViewModel)
    implementation(Dependencies.lifecycleViewModelKtx)
}