plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id ("kotlin-parcelize")
    alias(libs.plugins.ksp) apply true
    id ("dagger.hilt.android.plugin")
    //id ("androidx.navigation.safeargs")
    alias(libs.plugins.safeargs)
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.fadybassem.gitexplore"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.fadybassem.gitexplore"
        minSdk = 26
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation( libs.androidx.appcompat)
    implementation( libs.material)

    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.ui.viewbinding)

    implementation (libs.androidx.material)
    implementation (libs.androidx.material.icons.extended)

    implementation( libs.androidx.runtime.livedata)
    implementation (libs.androidx.runtime)

    implementation (libs.androidx.constraintlayout.compose)
    implementation (libs.androidx.constraintlayout)

    implementation (libs.androidx.navigation.compose)
    implementation (libs.androidx.navigation.fragment.ktx)
    implementation (libs.androidx.navigation.ui.ktx)
    implementation (libs.androidx.legacy.support.v4)

    implementation (libs.androidx.lifecycle.livedata.ktx)
    implementation (libs.androidx.lifecycle.viewmodel.ktx)

    implementation (platform(libs.firebase.bom))
    implementation (libs.firebase.auth.ktx)
    implementation(libs.firebase.analytics)
    implementation (libs.play.services.auth)
    implementation (libs.facebook.login)
    implementation(libs.firebase.messaging.ktx)

    implementation (libs.hilt.android)
    ksp (libs.hilt.android.compiler)
    ksp (libs.hilt.compiler)
    annotationProcessor (libs.dagger.android.processor)

    implementation (libs.androidx.hilt.work)
    implementation (libs.androidx.hilt.navigation.fragment)
    implementation (libs.androidx.hilt.navigation.compose)
    ksp (libs.androidx.hilt.compiler)
    implementation (libs.androidx.work.runtime.ktx)

    implementation (libs.retrofit)
    implementation (libs.retrofit2.kotlin.coroutines.adapter)
    implementation (libs.converter.gson)
    implementation (libs.okhttp)
    implementation (libs.logging.interceptor)

    implementation (libs.rxjava)
    implementation (libs.rxandroid)

    implementation (libs.accompanist.insets)
    implementation (libs.accompanist.insets.ui)
    implementation (libs.accompanist.permissions)
    implementation (libs.accompanist.swiperefresh)
    implementation (libs.accompanist.pager)
    implementation (libs.accompanist.pager.indicators)
    implementation (libs.accompanist.systemuicontroller)

    implementation(libs.jackson.databind)
    androidTestImplementation(libs.jackson.databind)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

apply(plugin = "com.google.gms.google-services")
