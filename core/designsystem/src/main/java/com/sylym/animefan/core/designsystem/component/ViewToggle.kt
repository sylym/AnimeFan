package com.sylym.animefan.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sylym.animefan.core.designsystem.icon.AnimeFanIcons

/**
 * 带有引导图标以及展开和收起的文本标签内容槽的Anime Fan视图切换按钮
 *
 * @param expanded 切换按钮是否处于展开模式
 * @param onExpandedChange 当用户切换按钮时要调用的回调
 * @param modifier 要应用于按钮的modifier
 * @param enabled 控制按钮的启用状态。 当`false`时，按钮将不可点击，并且对辅助功能服务不可用
 * @param compactText 在收起模式下，要显示的文本标签内容
 * @param expandedText 在展开模式下，要显示的文本标签内容
 */
@Composable
fun AnimeFanViewToggleButton(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    compactText: @Composable () -> Unit,
    expandedText: @Composable () -> Unit,
) {
    TextButton(
        onClick = { onExpandedChange(!expanded) },
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground,
        ),
        contentPadding = AnimeFanViewToggleDefaults.ViewToggleButtonContentPadding,
    ) {
        AnimeFanViewToggleButtonContent(
            text = if (expanded) expandedText else compactText,
            trailingIcon = {
                Icon(
                    imageVector = if (expanded) AnimeFanIcons.ViewDay else AnimeFanIcons.ShortText,
                    contentDescription = null,
                )
            },
        )
    }
}

/**
 * Anime Fan视图切换按钮内容布局，用于排列文本标签和引导图标
 *
 * @param text 按钮文本标签内容
 * @param trailingIcon 按钮引导图标内容。默认值为“null”表示无引导图标
 */
@Composable
private fun AnimeFanViewToggleButtonContent(
    text: @Composable () -> Unit,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    Box(
        Modifier
            .padding(
                end = if (trailingIcon != null) {
                    ButtonDefaults.IconSpacing
                } else {
                    0.dp
                },
            ),
    ) {
        ProvideTextStyle(value = MaterialTheme.typography.labelSmall) {
            text()
        }
    }
    if (trailingIcon != null) {
        Box(Modifier.sizeIn(maxHeight = ButtonDefaults.IconSize)) {
            trailingIcon()
        }
    }
}

/**
 * Anime Fan视图切换按钮默认值
 */
object AnimeFanViewToggleDefaults {
    // TODO: 文件bug
    // 各种默认按钮填充值不会通过ButtonDefaults公开
    val ViewToggleButtonContentPadding =
        PaddingValues(
            start = 16.dp,
            top = 8.dp,
            end = 12.dp,
            bottom = 8.dp,
        )
}
