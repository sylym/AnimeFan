package com.sylym.animefan.core.designsystem.component

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * 带有图标和标签内容槽的Anime Fan导航栏项目。 Wraps Material 3
 * [NavigationBarItem].
 *
 * @param selected 项目是否已选择
 * @param onClick 当选择项目时要调用的回调
 * @param icon 项目图标内容
 * @param modifier 要应用于项目的modifier
 * @param selectedIcon 当选中时的项目图标内容
 * @param enabled 控制项目的启用状态。 当`false`时，项目将不可点击，并且对辅助功能服务不可用。
 * @param label 项目文本标签内容
 * @param alwaysShowLabel 是否始终显示此项目的标签。如果为false，则只有选择此项目时才会显示标签
 */
@Composable
fun RowScope.AnimeFanNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    selectedIcon: @Composable () -> Unit = icon,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true,
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = AnimeFanNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = AnimeFanNavigationDefaults.navigationContentColor(),
            selectedTextColor = AnimeFanNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = AnimeFanNavigationDefaults.navigationContentColor(),
            indicatorColor = AnimeFanNavigationDefaults.navigationIndicatorColor(),
        ),
    )
}

/**
 * 带有内容槽的Anime Fan导航栏。 Wraps Material 3 [NavigationBar]
 *
 * @param modifier 要应用于导航栏的modifier
 * @param content 要在导航栏中显示的内容。这应该是一系列[NavigationBarItem]
 */
@Composable
fun AnimeFanNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    NavigationBar(
        modifier = modifier,
        contentColor = AnimeFanNavigationDefaults.navigationContentColor(),
        tonalElevation = 0.dp,
        content = content,
    )
}

/**
 * 带有图标和标签内容槽的Anime Fan竖版导航栏项目。 Wraps Material 3
 * [NavigationRailItem].
 *
 * @param selected 项目是否已选择
 * @param onClick 当选择项目时要调用的回调
 * @param icon 项目图标内容
 * @param modifier 要应用于项目的modifier
 * @param selectedIcon 当选中时的项目图标内容
 * @param enabled 控制项目的启用状态。 当`false`时，项目将不可点击，并且对辅助功能服务不可用。
 * @param label 项目文本标签内容
 * @param alwaysShowLabel 是否始终显示此项目的标签。如果为false，则只有选择此项目时才会显示标签
 */
@Composable
fun AnimeFanNavigationRailItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    selectedIcon: @Composable () -> Unit = icon,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true,
) {
    NavigationRailItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationRailItemDefaults.colors(
            selectedIconColor = AnimeFanNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = AnimeFanNavigationDefaults.navigationContentColor(),
            selectedTextColor = AnimeFanNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = AnimeFanNavigationDefaults.navigationContentColor(),
            indicatorColor = AnimeFanNavigationDefaults.navigationIndicatorColor(),
        ),
    )
}

/**
 * 带有标题和内容槽的Anime Fan竖版导航栏。 Wraps Material 3 [NavigationRail].
 *
 * @param modifier 要应用于竖版导航栏的modifier
 * @param header 可选页眉，可容纳浮动操作按钮或徽标
 * @param content 要在竖版导航栏中显示的内容。这应该是一系列[NavigationRailItem]
 */
@Composable
fun AnimeFanNavigationRail(
    modifier: Modifier = Modifier,
    header: @Composable (ColumnScope.() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    NavigationRail(
        modifier = modifier,
        containerColor = Color.Transparent,
        contentColor = AnimeFanNavigationDefaults.navigationContentColor(),
        header = header,
        content = content,
    )
}

/**
 * Anime Fan导航栏默认值
 */
object AnimeFanNavigationDefaults {
    @Composable
    fun navigationContentColor() = MaterialTheme.colorScheme.onSurfaceVariant

    @Composable
    fun navigationSelectedItemColor() = MaterialTheme.colorScheme.onPrimaryContainer

    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.primaryContainer
}
