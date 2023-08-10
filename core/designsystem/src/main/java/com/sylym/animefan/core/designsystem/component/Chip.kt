package com.sylym.animefan.core.designsystem.component

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sylym.animefan.core.designsystem.icon.AnimeFanIcons

/**
 * Anime Fan过滤标签，包括引导复选图标以及文本内容槽
 *
 * @param selected 标签是否被选中
 * @param onSelectedChange 当用户单击标签并改变选中状态时的回调
 * @param modifier 要应用于标签的Modifier
 * @param enabled 控制标签的启用状态。当为`false`时，此标签将不可点击，并且对辅助功能服务将显示为禁用状态。
 * @param label 标签文本内容槽
 */
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AnimeFanFilterChip(
    selected: Boolean,
    onSelectedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: @Composable () -> Unit,
) {
    FilterChip(
        selected = selected,
        onClick = { onSelectedChange(!selected) },
        label = {
            ProvideTextStyle(value = MaterialTheme.typography.labelSmall) {
                label()
            }
        },
        modifier = modifier,
        enabled = enabled,
        leadingIcon = if (selected) {
            {
                Icon(
                    imageVector = AnimeFanIcons.Check,
                    contentDescription = null,
                )
            }
        } else {
            null
        },
        shape = CircleShape,
        border = FilterChipDefaults.filterChipBorder(
            borderColor = MaterialTheme.colorScheme.onBackground,
            selectedBorderColor = MaterialTheme.colorScheme.onBackground,
            disabledBorderColor = MaterialTheme.colorScheme.onBackground.copy(
                alpha = AnimeFanChipDefaults.DisabledChipContentAlpha,
            ),
            disabledSelectedBorderColor = MaterialTheme.colorScheme.onBackground.copy(
                alpha = AnimeFanChipDefaults.DisabledChipContentAlpha,
            ),
            selectedBorderWidth = AnimeFanChipDefaults.ChipBorderWidth,
        ),
        colors = FilterChipDefaults.filterChipColors(
            labelColor = MaterialTheme.colorScheme.onBackground,
            iconColor = MaterialTheme.colorScheme.onBackground,
            disabledContainerColor = if (selected) {
                MaterialTheme.colorScheme.onBackground.copy(
                    alpha = AnimeFanChipDefaults.DisabledChipContainerAlpha,
                )
            } else {
                Color.Transparent
            },
            disabledLabelColor = MaterialTheme.colorScheme.onBackground.copy(
                alpha = AnimeFanChipDefaults.DisabledChipContentAlpha,
            ),
            disabledLeadingIconColor = MaterialTheme.colorScheme.onBackground.copy(
                alpha = AnimeFanChipDefaults.DisabledChipContentAlpha,
            ),
            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            selectedLabelColor = MaterialTheme.colorScheme.onBackground,
            selectedLeadingIconColor = MaterialTheme.colorScheme.onBackground,
        ),
    )
}

/**
 * Anime Fan标签默认值
 */
object AnimeFanChipDefaults {
    // TODO: 文件bug
    // 过滤标签默认值不会通过FilterChipDefaults公开
    const val DisabledChipContainerAlpha = 0.12f
    const val DisabledChipContentAlpha = 0.38f
    val ChipBorderWidth = 1.dp
}
