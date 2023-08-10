package com.sylym.animefan

import com.android.build.api.variant.LibraryAndroidComponentsExtension
import org.gradle.api.Project

/**
 * 如果没有androidTest文件夹，则禁用[project]中不必要的Android仪器化测试。
 * 否则，这些项目将被编译、打包、安装和运行，最终显示以下消息：
 *
 * > 在AVD上开始0个测试
 *
 * 注意：可以通过检查基于buildTypes和flavors的其他潜在sourceSets来改进此过程。
 */
internal fun LibraryAndroidComponentsExtension.disableUnnecessaryAndroidTests(
    project: Project,
) = beforeVariants {
    it.enableAndroidTest = it.enableAndroidTest
        && project.projectDir.resolve("src/androidTest").exists()
}
