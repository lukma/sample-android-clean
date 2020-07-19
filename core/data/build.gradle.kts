plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
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
        consumerProguardFile("consumer-rules.pro")

        javaCompileOptions {
            annotationProcessorOptions {
                arguments(
                    mapOf(
                        "room.schemaLocation" to "$projectDir/schemas",
                        "room.incremental" to "true",
                        "room.expandProjection" to "true"
                    )
                )
            }
        }
    }

    packagingOptions {
        exclude("META-INF/LICENSE*")
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    flavorDimensions("environment")
    productFlavors {
        create("production") {
            dimension = "environment"
            buildConfigField(
                "String",
                "ROOT_API_URL",
                "\"https://private-30c186-lukmaice.apiary-mock.com/\""
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // Kotlin
    implementation(kotlin("stdlib"))
    implementation(Dependencies.Kotlin.coroutinesCoreLib)
    testImplementation(Dependencies.Kotlin.coroutinesTestLib)

    implementation(project(":core:domain"))
    androidTestImplementation(project(":core:test"))

    // Common
    implementation(Dependencies.Jetpack.coreKtxLib)
    implementation(Dependencies.Google.playServiceCoroutinesLib)

    // Testing
    testImplementation(Dependencies.JUnit.jupiterLib)
    testImplementation(Dependencies.MockK.coreLib)
    androidTestImplementation(Dependencies.Jetpack.testRunnerLib)
    androidTestImplementation(Dependencies.JUnit.jupiterLib)
    androidTestImplementation(Dependencies.JUnit5Android.coreLib)
    androidTestRuntimeOnly(Dependencies.JUnit5Android.runnerLib)

    // Room
    implementation(Dependencies.Jetpack.Room.runtimeLib)
    kapt(Dependencies.Jetpack.Room.compilerLib)
    implementation(Dependencies.Jetpack.Room.ktxLib)
    androidTestImplementation(Dependencies.Jetpack.Room.testLib)

    // Koin
    implementation(Dependencies.Koin.androidLib)

    // Firebase
    implementation(Dependencies.Firebase.analyticsLib)
    implementation(Dependencies.Firebase.authLib)
    implementation(Dependencies.Firebase.firestoreLib)

    // OkHttp
    implementation(Dependencies.OkHttp.coreLib)
    testImplementation(Dependencies.OkHttp.mockLib)

    // Retrofit
    implementation(Dependencies.Retrofit.coreLib)
    implementation(Dependencies.Retrofit.converterLib)
}
