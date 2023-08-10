package com.sylym.animefan.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * 为渐变颜色值进行建模的类
 *
 * @param top 要呈现的顶部渐变颜色
 * @param bottom 要呈现的底部渐变颜色
 * @param container 将在其上呈现渐变的容器渐变颜色
 */
@Immutable
data class GradientColors(
    val top: Color = Color.Unspecified,
    val bottom: Color = Color.Unspecified,
    val container: Color = Color.Unspecified,
)

/**
 * [GradientColors]的CompositionLocal
 */
val LocalGradientColors = staticCompositionLocalOf { GradientColors() }
