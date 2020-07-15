buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.0.1")
        classpath(kotlin("gradle-plugin", version = "1.3.72"))
        classpath(Dependencies.Google.gradlePluginLib)
        classpath(Dependencies.Jetpack.Navigation.safeArgsLib)
        classpath(Dependencies.JUnit5Android.gradlePluginLib)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
