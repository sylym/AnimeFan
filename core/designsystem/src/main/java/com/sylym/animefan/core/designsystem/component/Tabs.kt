package com.sylym.animefan.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * Anime Fan选项卡。 Wraps Material 3 [Tab] 并下移文本标签
 *
 * @param selected 选项卡是否被选中
 * @param onClick 当选中此选项卡时要调用的回调
 * @param modifier 要应用于选项卡的modifier
 * @param enabled 控制选项卡的启用状态。 当为`false`时，此选项卡将不可点击，并且对辅助功能服务不可用
 * @param text 文本标签内容
 */
@Composable
fun AnimeFanTab(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: @Composable () -> Unit,
) {
    Tab(
        selected = selected,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        text = {
            val style = MaterialTheme.typography.labelLarge.copy(textAlign = TextAlign.Center)
            ProvideTextStyle(
                value = style,
                content = {
                    Box(modifier = Modifier.padding(top = AnimeFanTabDefaults.TabTopPadding)) {
                        text()
                    }
                },
            )
        },
    )
}

/**
 * Anime Fan选项卡行。 Wraps Material 3 [TabRow].
 *
 * @param selectedTabIndex 当前所选选项卡的索引
 * @param modifier 要应用于选项卡行的modifier
 * @param tabs 此选项卡行中的选项卡。通常情况下，这将是多个[AnimeFanTab]。
 * 这个lambda中的每个元素都将被测量并均匀地放置在行中，每个元素都占据相等的空间
 */
@Composable
fun AnimeFanTabRow(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    tabs: @Composable () -> Unit,
) {
    TabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier,
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onSurface,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                height = 2.dp,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        tabs = tabs,
    )
}

object AnimeFanTabDefaults {
    val TabTopPadding = 7.dp
}
