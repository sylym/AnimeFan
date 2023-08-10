package com.sylym.animefan.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

/**
 * 为Anime Fan建模背景颜色和色调高程值的类
 */
@Immutable
data class BackgroundTheme(
    val color: Color = Color.Unspecified,
    val tonalElevation: Dp = Dp.Unspecified,
)

/**
 * [BackgroundTheme]的CompositionLocal
 */
val LocalBackgroundTheme = staticCompositionLocalOf { BackgroundTheme() }
