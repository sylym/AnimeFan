plugins {
    id("animefan.android.application")
    id("animefan.android.application.compose")
    id("animefan.android.application.flavors")
    id("animefan.android.hilt")
}

android {
    defaultConfig {
        applicationId = "com.sylym.animefan"
        versionCode = 1
        versionName = "0.1.0" // X.Y.Z；其中X代表主版本号，Y代表次版本号，Z代表补丁级别
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
        }
        getting {
            isMinifyEnabled = true
            applicationIdSuffix = null
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

            // 在Play商店上发布应用程序需要使用私有签名密钥，但如果要允许任何克隆代码的人签名和运行发布变体，则可以使用调试签名密钥
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
    namespace = "com.sylym.animefan"
}

dependencies {
    implementation(project(":core:designsystem"))

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.compose.runtime.tracing)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.window.manager)
    implementation(libs.androidx.profileinstaller)
    implementation(libs.kotlinx.coroutines.guava)
    implementation(libs.coil.kt)
}