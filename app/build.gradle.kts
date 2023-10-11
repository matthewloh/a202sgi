plugins {
    id("com.android.application")
    id("androidx.navigation.safeargs")
}

android {
    namespace = "com.example.intisuperapp"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.intisuperapp"
        minSdk = 26
        targetSdk = 33
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
        buildFeatures {
            viewBinding = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    val room_version = "2.5.2"
    val lifecycle_version = "2.6.2"
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("androidx.appcompat:appcompat:1.6.1")

    // Navigation component UI
    implementation("androidx.navigation:navigation-fragment:2.5.3")
    implementation("androidx.navigation:navigation-ui:2.5.3")
//    // Dependencies for working with Architecture components
//    // You'll probably have to update the version numbers in build.gradle (Project)
//
//    // Room components
    implementation("androidx.room:room-runtime:2.5.2")
    annotationProcessor("androidx.room:room-compiler:2.5.2")
    androidTestImplementation("androidx.room:room-testing:2.5.2")
//
//    // Lifecycle components
//    implementation("androidx.lifecycle:lifecycle-viewmodel:2.6.2")
//    implementation("androidx.lifecycle:lifecycle-livedata:2.6.2")
//    implementation("androidx.lifecycle:lifecycle-common-java8:2.6.2")
//
//
//    // Testing
//    androidTestImplementation("androidx.arch.core:core-testing:$rootProject.coreTestingVersion")
//    androidTestImplementation("androidx.test.espresso:espresso-core:$rootProject.espressoVersion", {
//        exclude(group = "androidx.annotation", module = "annotation")
//    })
//
//    androidTestImplementation("androidx.test.ext:junit:$rootProject.androidxJunitVersion")

}