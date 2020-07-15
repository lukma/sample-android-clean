plugins {
    `java-library`
    kotlin
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // Kotlin
    implementation(kotlin("stdlib"))
    implementation(Dependencies.Kotlin.coroutinesCoreLib)

    // Testing
    testImplementation(Dependencies.JUnit.jupiterLib)
    testImplementation(Dependencies.MockK.coreLib)

    // Koin
    implementation(Dependencies.Koin.coreLib)
}
