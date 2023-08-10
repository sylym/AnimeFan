buildscript {
    repositories {
        google()
        mavenCentral()

        // Android 构建服务器
        maven { url = uri("../animefan-prebuilts/m2repository") }
    }
}

// 列出项目中使用的所有插件，但不应用它们
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.secrets) apply false
}