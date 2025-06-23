plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services") version "4.4.2" apply false
}

android {
    namespace = "com.datj.mobile"
    compileSdk = 35
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
    defaultConfig {
        applicationId = "com.datj.mobile"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

}

dependencies {
    implementation ("com.android.volley:volley:1.2.1")
    implementation ("com.google.maps.android:android-maps-utils:2.3.0")
    implementation("com.google.android.gms:play-services-maps:18.2.0") // Maps SDK
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("com.google.firebase:firebase-analytics")
    implementation ("androidx.core:core:1.12.0")// thông báo
    implementation ("me.leolin:ShortcutBadger:1.1.22@aar")// thông báo
    implementation(platform("com.google.firebase:firebase-bom:33.11.0"))
    implementation("com.google.firebase:firebase-firestore:24.10.1") // Firestore Database
    implementation("com.google.firebase:firebase-messaging:23.2.1") // Cloud Messaging
    implementation("com.google.firebase:firebase-storage:20.3.0")
    implementation ("com.google.firebase:firebase-auth:22.1.2")
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    implementation ("com.google.gms:google-services:4.4.2")
    implementation ("com.google.android.gms:play-services-auth:20.7.0")
    implementation ("com.squareup.okhttp3:okhttp:4.12.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation ("androidx.recyclerview:recyclerview:1.3.2")
    implementation ("androidx.recyclerview:recyclerview-selection:1.1.0")
    implementation ("androidx.appcompat:appcompat:1.4.1")
    implementation ("androidx.core:core-ktx:1.8.0")

    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation ("com.google.android.material:material:1.6.0")

    //jwt
    implementation ("com.auth0.android:jwtdecode:2.0.2")

    //glide for accessory
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation(libs.firebase.database)
    annotationProcessor ("com.github.bumptech.glide:compiler:4.16.0")

    //avt css
    implementation ("com.google.android.material:material:1.9.0")

    //constraint layout
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")

    //Gson to create Json on device
    implementation("com.google.code.gson:gson:2.10")

    //Paypal
    implementation("com.paypal.checkout:android-sdk:1.3.2")
    implementation("com.paypal.android:paypal-web-payments:2.0.0")

    //browser
    implementation ("androidx.browser:browser:1.7.0")


    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}