package com.sylym.animefan.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * 为Anime Fan建模色调主题和高程值的类
 */
@Immutable
data class TintTheme(
    val iconTint: Color? = null,
)

/**
 * [TintTheme]的CompositionLocal
 */
val LocalTintTheme = staticCompositionLocalOf { TintTheme() }
