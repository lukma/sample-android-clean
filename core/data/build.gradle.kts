plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
}

android {
    compileSdkVersion(Configs.Android.compileSdkVersion)
    buildToolsVersion = Configs.Android.buildToolsVersion

    defaultConfig {
        minSdkVersion(Configs.Android.minSdkVersion)
        targetSdkVersion(Configs.Android.targetSdkVersion)
        versionCode = 1
        versionName = "1.0"
        consumerProguardFile("consumer-rules.pro")
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
            buildConfigField("String", "ROOT_API_URL", "\"https://private-30c186-lukmaice.apiary-mock.com/\"")
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

    implementation(project(":core:domain"))

    // Common
    implementation(Dependencies.Jetpack.coreKtxLib)
    implementation(Dependencies.Google.playServiceCoroutinesLib)

    // Testing
    testImplementation(Dependencies.JUnit.jupiterLib)
    testImplementation(Dependencies.MockK.coreLib)

    // Koin
    implementation(Dependencies.Koin.coreLib)

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
