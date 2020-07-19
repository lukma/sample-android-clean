plugins {
    id("com.android.dynamic-feature")
    kotlin("android")
    kotlin("android.extensions")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdkVersion(Configs.Android.compileSdkVersion)
    buildToolsVersion = Configs.Android.buildToolsVersion

    defaultConfig {
        minSdkVersion(Configs.Android.minSdkVersion)
        targetSdkVersion(Configs.Android.targetSdkVersion)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArgument("runnerBuilder", "de.mannodermaus.junit5.AndroidJUnit5Builder")
    }

    testOptions {
        animationsDisabled = true
    }

    packagingOptions {
        exclude("META-INF/LICENSE*")
    }

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
    implementation(Dependencies.Kotlin.coroutinesAndroidLib)

    implementation(project(":app"))
    implementation(project(":core:domain"))
    implementation(project(":core:uikit"))
    testImplementation(project(":core:test"))
    androidTestImplementation(project(":core:androidTest"))

    // Common
    implementation(Dependencies.Jetpack.coreKtxLib)
    implementation(Dependencies.Jetpack.constraintLayoutLib)
    implementation(Dependencies.Jetpack.legacySupportV4Lib)

    // Testing
    testImplementation(Dependencies.JUnit.jupiterLib)
    testImplementation(Dependencies.Kotlin.coroutinesTestLib)
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

    // Paging 3
    implementation(Dependencies.Jetpack.Paging.runtimeLib)
    testImplementation(Dependencies.Jetpack.Paging.commonLib)

    // Koin
    implementation(Dependencies.Koin.coreLib)
    debugImplementation(Dependencies.Koin.testLib)
}
