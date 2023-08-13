plugins {
    id("animefan.android.feature")
    id("animefan.android.library.compose")
}

android {
    namespace = "com.sylym.animefan.feature.topic"
}

dependencies {
    implementation(libs.kotlinx.datetime)
}