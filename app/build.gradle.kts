plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    id("com.google.firebase.appdistribution")
    id("com.google.gms.google-services")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdkVersion(Configs.Android.compileSdkVersion)
    buildToolsVersion = Configs.Android.buildToolsVersion

    defaultConfig {
        applicationId = "com.lukma.android"
        minSdkVersion(Configs.Android.minSdkVersion)
        targetSdkVersion(Configs.Android.targetSdkVersion)
        versionCode = Configs.Android.versionCode
        versionName = Configs.Android.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArgument("runnerBuilder", "de.mannodermaus.junit5.AndroidJUnit5Builder")
    }

    testOptions {
        animationsDisabled = true
    }

    packagingOptions {
        exclude("META-INF/LICENSE*")
    }

    buildTypes {
        getByName("debug") {
            firebaseAppDistribution {
                serviceCredentialsFile="./credentials/google-services-account.json"
                groups="internal-tester"
            }
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    dynamicFeatures = mutableSetOf(":features:auth", ":features:chat")

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // Kotlin
    implementation(kotlin("stdlib"))
    testImplementation(Dependencies.Kotlin.coroutinesTestLib)

    implementation(project(":core:domain"))
    implementation(project(":core:data"))
    implementation(project(":core:uikit"))
    testImplementation(project(":core:test"))

    // Common
    implementation(Dependencies.Jetpack.coreKtxLib)
    implementation(Dependencies.Jetpack.constraintLayoutLib)
    implementation(Dependencies.Jetpack.legacySupportV4Lib)
    implementation(Dependencies.Google.materialLib)

    // Testing
    testImplementation(Dependencies.JUnit.jupiterLib)
    androidTestImplementation(Dependencies.JUnit.jupiterLib)
    androidTestImplementation(Dependencies.JUnit5Android.coreLib)
    androidTestRuntimeOnly(Dependencies.JUnit5Android.runnerLib)
    androidTestImplementation(Dependencies.Espresso.coreLib)
    debugImplementation(Dependencies.Jetpack.fragmentTestLib)
    testImplementation(Dependencies.MockK.coreLib)
    androidTestImplementation(Dependencies.MockK.androidLib)

    // Lifecycle
    implementation(Dependencies.Jetpack.Lifecycle.extensionsLib)
    implementation(Dependencies.Jetpack.Lifecycle.liveDataLib)
    implementation(Dependencies.Jetpack.Lifecycle.viewModelLib)

    // Navigation
    implementation(Dependencies.Jetpack.Navigation.fragmentKtxLib)
    implementation(Dependencies.Jetpack.Navigation.uiKtxLib)
    implementation(Dependencies.Jetpack.Navigation.dynamicFeatureLib)
    androidTestImplementation(Dependencies.Jetpack.Navigation.testLib)

    // Koin
    implementation(Dependencies.Koin.androidLib)
    debugImplementation(Dependencies.Koin.testLib)
}
