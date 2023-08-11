package com.sylym.animefan.core.designsystem.component

import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * 带有图标和选中图标内容槽的Anime Fan切换按钮。 Wraps Material 3
 * [IconButton].
 *
 * @param checked 当前是否选中切换按钮
 * @param onCheckedChange 当用户单击切换按钮并切换选中时调用
 * @param modifier 要应用于切换按钮的modifier
 * @param enabled 控制切换按钮的启用状态。 当为“false”时，此切换按钮将不可点击，并且对辅助功能服务不可用。
 * @param icon 取消选中时要显示的图标内容
 * @param checkedIcon 选中时要显示的图标内容。
 */
@Composable
fun AnimeFanIconToggleButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: @Composable () -> Unit,
    checkedIcon: @Composable () -> Unit = icon,
) {
    // TODO: 文件bug
    // 无法使用常规图标切换按钮，因为它不包括形状（显示为方形）
    FilledIconToggleButton(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        enabled = enabled,
        colors = IconButtonDefaults.iconToggleButtonColors(
            checkedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            checkedContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            disabledContainerColor = if (checked) {
                MaterialTheme.colorScheme.onBackground.copy(
                    alpha = AnimeFanIconButtonDefaults.DisabledIconButtonContainerAlpha,
                )
            } else {
                Color.Transparent
            },
        ),
    ) {
        if (checked) checkedIcon() else icon()
    }
}

/**
 * Anime Fan图标按钮默认值
 */
object AnimeFanIconButtonDefaults {
    // TODO: 文件bug
    // IconToggleButton禁用容器 alpha 未由IconButtonDefaults公开
    const val DisabledIconButtonContainerAlpha = 0.12f
}
