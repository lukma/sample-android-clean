object Dependencies {

    object Kotlin {
        private const val version = "1.3.7"

        const val coroutinesCoreLib = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val coroutinesAndroidLib = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val coroutinesTestLib = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    }

    object Jetpack {
        const val coreKtxLib = "androidx.core:core-ktx:1.3.0"
        const val constraintLayoutLib = "androidx.constraintlayout:constraintlayout:1.1.3"
        const val legacySupportV4Lib = "androidx.legacy:legacy-support-v4:1.0.0"
        const val fragmentTestLib = "androidx.fragment:fragment-testing:1.2.5"

        object Lifecycle {
            private const val version = "2.2.0"

            const val extensionsLib = "androidx.lifecycle:lifecycle-extensions:$version"
            const val liveDataLib = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
            const val viewModelLib = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        }

        object Navigation {
            private const val version = "2.3.0"

            const val safeArgsLib =
                "androidx.navigation:navigation-safe-args-gradle-plugin:$version"
            const val fragmentKtxLib = "androidx.navigation:navigation-fragment-ktx:$version"
            const val uiKtxLib = "androidx.navigation:navigation-ui-ktx:$version"
            const val dynamicFeatureLib =
                "androidx.navigation:navigation-dynamic-features-fragment:$version"
            const val testLib = "androidx.navigation:navigation-testing:$version"
        }
    }

    object JUnit {
        const val jupiterLib = "org.junit.jupiter:junit-jupiter:5.6.2"
    }

    object JUnit5Android {
        private const val version = "1.2.0"

        const val gradlePluginLib = "de.mannodermaus.gradle.plugins:android-junit5:1.6.2.0"
        const val coreLib = "de.mannodermaus.junit5:android-test-core:$version"
        const val runnerLib = "de.mannodermaus.junit5:android-test-runner:$version"
    }

    object MockK {
        private const val version = "1.10.0"

        const val coreLib = "io.mockk:mockk:$version"
        const val androidLib = "io.mockk:mockk-android:$version"
    }

    object Espresso {
        const val coreLib = "androidx.test.espresso:espresso-core:3.2.0"
    }

    object Koin {
        private const val version = "2.1.5"

        const val coreLib = "org.koin:koin-core-ext:$version"
        const val androidCoreLib = "org.koin:koin-android:$version"
        const val testLib = "org.koin:koin-test:$version"
    }

    object Google {
        const val gradlePluginLib = "com.google.gms:google-services:4.3.3"
        const val playServiceCoroutinesLib =
            "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.3.2"
        const val materialLib = "com.google.android.material:material:1.1.0"
    }

    object Firebase {
        const val analyticsLib = "com.google.firebase:firebase-analytics-ktx:17.4.3"
        const val authLib = "com.google.firebase:firebase-auth-ktx:19.3.1"
        const val firestoreLib = "com.google.firebase:firebase-firestore-ktx:21.4.3"
    }

    object OkHttp {
        private const val version = "4.7.2"

        const val coreLib = "com.squareup.okhttp3:okhttp:$version"
        const val mockLib = "com.squareup.okhttp3:mockwebserver:$version"
    }

    object Retrofit {
        private const val version = "2.9.0"

        const val coreLib = "com.squareup.retrofit2:retrofit:$version"
        const val converterLib = "com.squareup.retrofit2:converter-moshi:$version"
    }

    object AirBnb {
        const val lottieLib = "com.airbnb.android:lottie:3.4.1"
    }
}
