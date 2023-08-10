package com.sylym.animefan.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.TextButton
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Anime Fan标签。 Wraps Material 3
 *
 * @param modifier 要应用于标签的modifier
 * @param followed 标签是否已关注，关注的标签将使用主题的主要颜色，未关注的标签将使用主题的表面颜色
 * @param onClick 当标签被点击时要调用的回调
 * @param enabled 控制标签的启用状态。 当`false`时，标签将不可点击，并且对辅助功能服务不可用
 * @param text 标签文本内容
 */
@Composable
fun AnimeFanTopicTag(
    modifier: Modifier = Modifier,
    followed: Boolean,
    onClick: () -> Unit,
    enabled: Boolean = true,
    text: @Composable () -> Unit,
) {
    Box(modifier = modifier) {
        val containerColor = if (followed) {
            MaterialTheme.colorScheme.primaryContainer
        } else {
            MaterialTheme.colorScheme.surfaceVariant.copy(
                alpha = AnimeFanTagDefaults.UnfollowedTopicTagContainerAlpha,
            )
        }
        TextButton(
            onClick = onClick,
            enabled = enabled,
            colors = ButtonDefaults.textButtonColors(
                containerColor = containerColor,
                contentColor = contentColorFor(backgroundColor = containerColor),
                disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(
                    alpha = AnimeFanTagDefaults.DisabledTopicTagContainerAlpha,
                ),
            ),
        ) {
            ProvideTextStyle(value = MaterialTheme.typography.labelSmall) {
                text()
            }
        }
    }
}

/**
 * Anime Fan标签默认值
 */
object AnimeFanTagDefaults {
    const val UnfollowedTopicTagContainerAlpha = 0.5f

    // TODO: 文件bug
    // ButtonDefaults未公开按钮禁用容器的alpha值
    const val DisabledTopicTagContainerAlpha = 0.12f
}
