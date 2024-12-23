plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    id("kotlin-kapt")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.plugin.compose") version ("2.0.0")
}

android {
    namespace = "com.droidcon.alldone"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.droidcon.alldone"
        minSdk = 23
        targetSdk = 35
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
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    val coreKtx = "1.12.0"
    val activityCompose = "1.7.2"
    val composeVersion = "1.5.1"
    val composeBom = "2023.09.00"
    val lifecycleVersion = "2.6.2"
    val arrowVersion = "1.2.1"
    val moshiVersion = "1.15.0"
    val hiltCompiler = "1.0.0"
    val hiltAndroid = "2.51.1"
    val blueprint = "1.0.0-alpha05"

    implementation("androidx.core:core-ktx:$coreKtx")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:$lifecycleVersion")

    // compose
    implementation("androidx.activity:activity-compose:$activityCompose")
    implementation(platform("androidx.compose:compose-bom:$composeBom"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    // arrow
    implementation("io.arrow-kt:arrow-core:$arrowVersion")
    implementation("io.arrow-kt:arrow-fx-coroutines:$arrowVersion")

    // moshi
    implementation("com.squareup.moshi:moshi:$moshiVersion")
    implementation("com.squareup.moshi:moshi-kotlin:$moshiVersion")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:$moshiVersion")


    // hilt
    implementation("com.google.dagger:hilt-android:$hiltAndroid")
    kapt("com.google.dagger:hilt-android-compiler:$hiltAndroid")
    kapt("androidx.hilt:hilt-compiler:$hiltCompiler")

    // blueprint (compose)
    debugImplementation("com.github.popovanton0.blueprint:blueprint:$blueprint")
    releaseImplementation("com.github.popovanton0.blueprint:blueprint-no-op:$blueprint")

    // testing
    val jUnit = "4.13.2"
    val jUnitExt = "1.1.5"
    val espressoCore = "3.5.1"
    val googleTruth = "1.1.4"

    testImplementation("junit:junit:$jUnit")
    testImplementation("com.google.truth:truth:$googleTruth")

    androidTestImplementation("androidx.test.ext:junit:$jUnitExt")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espressoCore")
    androidTestImplementation(platform("androidx.compose:compose-bom:$composeBom"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}