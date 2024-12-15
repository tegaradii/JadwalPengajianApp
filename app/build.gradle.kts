plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.kapt)

}

android {
    namespace = "com.example.jadwalpengajian"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.jadwalpengajian"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.databinding.runtime)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    // RecyclerView
    implementation(libs.androidx.recyclerview)

    // Retrofit (Untuk API CRUD)
    implementation(libs.retrofit)
    implementation(libs.retrofit2.converter.gson)

    // OkHttp (Untuk logging network request)
    implementation(libs.squareup.logging.interceptor)
    implementation(libs.logging.interceptor)

    // Room (Untuk database lokal favorit)
    implementation (libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)

    // Coroutine (Untuk asynchronous task seperti API call atau Room)
    implementation (libs.jetbrains.kotlinx.coroutines.android)

    // Lifecycle Components (ViewModel dan LiveData)
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    implementation (libs.androidx.lifecycle.livedata.ktx)

    // Navigation Component
    implementation (libs.androidx.navigation.fragment.ktx)
    implementation (libs.androidx.navigation.ui.ktx)

    // Shared Preferences (Jetpack Datastore jika perlu)
    implementation (libs.androidx.datastore.preferences)

    // Glide (Untuk loading gambar, jika diperlukan)
    implementation (libs.glide)
    kapt (libs.compiler)


    // Testing
    testImplementation (libs.junit)
    androidTestImplementation (libs.androidx.junit.v115)
    androidTestImplementation (libs.androidx.espresso.core.v351)

    implementation(libs.androidx.navigation.fragment.ktx.v253)
    implementation(libs.androidx.navigation.ui.ktx.v253)
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.room.ktx)



}