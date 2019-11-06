import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.android.extensions")
    id("org.jetbrains.kotlin.kapt")
    id("kotlinx-serialization")
    id("org.jlleitschuh.gradle.ktlint")
    id("de.mannodermaus.android-junit5")
}

android {
    compileSdkVersion(29)
    defaultConfig {
        applicationId = "com.credera.mobile.android"
        minSdkVersion(23)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArgument("runnerBuilder", "de.mannodermaus.junit5.AndroidJUnit5Builder")
    }
    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    packagingOptions {
        exclude("META-INF/LICENSE*")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    lintOptions {
        setLintConfig(file("$rootDir/lint.xml"))
        setAbortOnError(true)
        setWarningsAsErrors(true)
    }
}
androidExtensions {
    isExperimental = true
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

dependencies {
    // ------------------------------------ //
    // region Kotlin                        //
    implementation(Libs.kotlin_stdlib_jdk7)
    implementation(Libs.kotlin_reflect)
    implementation(Libs.kotlinx_coroutines_core)
    implementation(Libs.kotlinx_coroutines_android)
    implementation(Libs.kotlinx_serialization_runtime)
    testImplementation(Libs.kotlinx_coroutines_test)
    // endregion                            //
    // ------------------------------------ //

    // ------------------------------------ //
    // region Android Support Libraries     //
    implementation(Libs.legacy_support_v4)
    implementation(Libs.appcompat)
    implementation(Libs.recyclerview)
    implementation(Libs.annotation)
    implementation(Libs.jsr305)
    implementation(Libs.material)
    implementation(Libs.browser)
    implementation(Libs.constraintlayout)
    implementation(Libs.preference_ktx)
    implementation(Libs.lifecycle_viewmodel_ktx)
    // endregion                            //
    // ------------------------------------ //

    // ------------------------------------ //
    // region Annotation processors         //
    //      Room - Database
    kapt(Libs.room_compiler)
    implementation(Libs.room_runtime)
    implementation(Libs.room_ktx)
    testImplementation(Libs.room_testing)

    //      Retrofit - Networking
    implementation(Libs.logging_interceptor)
    implementation(Libs.retrofit2_kotlinx_serialization_converter)
    implementation(Libs.retrofit)
    // endregion                            //
    // ------------------------------------ //

    // ------------------------------------ //
    // region Other                         //
    //      Koin - DI
    implementation(Libs.javax_inject)
    implementation(Libs.koin_android)
    implementation(Libs.koin_android_scope)
    implementation(Libs.koin_android_viewmodel)
    implementation(Libs.koin_android_ext)
    testImplementation(Libs.koin_test)

    //      Coil - Image Loading
    implementation(Libs.coil)

    //      ThreeTenABP - Backward-compatible Java 8 LocalDate APIs
    implementation(Libs.threetenabp)
    testImplementation(Libs.threetenbp)

    //      Logging, App Configuration, Debug Tools
    implementation(Libs.common)
    implementation(Libs.androidclog)
    debugImplementation(Libs.debug_db)
    // endregion                            //
    // ------------------------------------ //

    // ------------------------------------ //
    // region Unit Testing                  //
    testImplementation(Libs.androidx_test_runner)
    testImplementation(Libs.androidx_test_rules)
    testImplementation(Libs.junit)
    testImplementation(Libs.junit_jupiter_api)
    testImplementation(Libs.junit_jupiter_params)
    testRuntimeOnly(Libs.junit_jupiter_engine)
    testRuntimeOnly(Libs.junit_vintage_engine)
    testImplementation(Libs.mockk)
    testImplementation(Libs.strikt_core)
    testImplementation(Libs.robolectric)
    // endregion                            //
    // ------------------------------------ //

    // ------------------------------------ //
    // region UI Testing                    //
    implementation(Libs.espresso_idling_resource)
    androidTestImplementation(Libs.espresso_core)
    androidTestImplementation(Libs.espresso_contrib)
    androidTestImplementation(Libs.espresso_intents)
    androidTestImplementation(Libs.androidx_test_runner)
    androidTestImplementation(Libs.androidx_test_rules)
    androidTestImplementation(Libs.android_test_core)
    androidTestImplementation(Libs.junit_jupiter_api)
    androidTestImplementation(Libs.junit_jupiter_params)
    androidTestImplementation(Libs.junit)
    androidTestRuntimeOnly(Libs.android_test_runner)
    androidTestRuntimeOnly(Libs.junit_jupiter_engine)
    androidTestImplementation(Libs.barista) {
        exclude(group = "org.jetbrains.kotlin")
    }
    // endregion                            //
    // ------------------------------------ //
}

ktlint {
    debug.set(false)
    verbose.set(true)
    android.set(true)
    outputToConsole.set(true)
    reporters.set(setOf(ReporterType.JSON, ReporterType.CHECKSTYLE))
    ignoreFailures.set(false)
    enableExperimentalRules.set(false)
    additionalEditorconfigFile.set(file("$rootDir/.editorconfig"))
    filter {
        exclude("**/generated/**")
        include("**/kotlin/**")
        include("**/java/**")
    }
}
