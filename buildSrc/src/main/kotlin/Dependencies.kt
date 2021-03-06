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
        const val testRunnerLib = "androidx.test:runner:1.1.0"

        object Lifecycle {
            private const val version = "2.2.0"

            const val extensionsLib = "androidx.lifecycle:lifecycle-extensions:$version"
            const val liveDataLib = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
            const val viewModelLib = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
            const val testLib = "androidx.arch.core:core-testing:2.1.0"
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

        object Paging {
            private const val version = "3.0.0-alpha02"

            const val runtimeLib = "androidx.paging:paging-runtime:$version"
            const val commonLib = "androidx.paging:paging-common:$version"
        }

        object Room {
            private const val version = "2.2.5"

            const val runtimeLib = "androidx.room:room-runtime:$version"
            const val compilerLib = "androidx.room:room-compiler:$version"
            const val ktxLib = "androidx.room:room-ktx:$version"
            const val testLib = "androidx.room:room-testing:$version"
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
        private const val version = "2.1.6"

        const val coreLib = "org.koin:koin-core-ext:$version"
        const val androidLib = "org.koin:koin-androidx-ext:$version"
        const val testLib = "org.koin:koin-test:$version"
    }

    object Google {
        const val gradlePluginLib = "com.google.gms:google-services:4.3.3"
        const val playServiceCoroutinesLib =
            "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.3.2"
        const val materialLib = "com.google.android.material:material:1.1.0"
    }

    object Firebase {
        const val analyticsLib = "com.google.firebase:firebase-analytics-ktx:17.4.4"
        const val distributionLib = "com.google.firebase:firebase-appdistribution-gradle:2.0.0"
        const val authLib = "com.google.firebase:firebase-auth-ktx:19.3.2"
        const val firestoreLib = "com.google.firebase:firebase-firestore-ktx:21.5.0"
    }

    object OkHttp {
        private const val version = "4.8.0"

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
