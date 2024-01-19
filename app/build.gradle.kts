import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.cryotoapp"
    compileSdk = 34
    testOptions {
        unitTests {
            isIncludeAndroidResources =true
        }
        packagingOptions {
            jniLibs {
                useLegacyPackaging = true
            }
        }
    }
    defaultConfig {
        applicationId = "com.example.cryotoapp"
        minSdk = 24
        targetSdk = 34
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
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {

        resources.excludes.add("META-INF/*")
    }



}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.test:core-ktx:1.5.0")
    testImplementation("junit:junit:4.13.2")
    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")


    //Dagger hilt
    implementation ("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-compiler:2.44")

    // corutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")

    //navigation
    implementation("androidx.navigation:navigation-compose:2.7.6")

    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    // retrofit
    implementation( "com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.13.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.8.1")
    //coil
    implementation("io.coil-kt:coil-compose:2.2.0")
    // swipe up refresh
    implementation("com.google.accompanist:accompanist-swiperefresh:0.24.13-rc")
    // date time library
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")
    //
    testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")

    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.2")

    //For using viewModelScope
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.0")
//For runBlockingTest, CoroutineDispatcher etc.
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.2")
//For InstantTaskExecutorRule
   testImplementation  ("androidx.arch.core:core-testing:2.2.0")
    testImplementation ("io.mockk:mockk:1.13.4")
//    testImplementation ("org.robolectric:robolectric:4.2.1")
    testImplementation ("junit:junit:4.13.2")
    testImplementation ("org.robolectric:robolectric:4.11.1")

    androidTestImplementation( "androidx.test:core-ktx:1.5.0")

    androidTestImplementation ("androidx.test.ext:junit-ktx:1.1.5")

    testImplementation ("org.hamcrest:hamcrest:2.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test:runner:1.5.2")
    androidTestImplementation ("androidx.test:core:1.5.0")
    androidTestImplementation ("androidx.test:rules:1.5.0")

    androidTestImplementation ("androidx.arch.core:core-testing:2.2.0")

    testImplementation ("androidx.arch.core:core-testing:2.2.0")

    androidTestImplementation("androidx.test.ext:junit:1.1.3")

    androidTestImplementation ("io.mockk:mockk:1.13.9")
    // Optional -- MockK Android
    androidTestImplementation ("io.mockk:mockk-android:1.13.9")

    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.test:core:1.4.0")

}
kapt {
    correctErrorTypes = true
}