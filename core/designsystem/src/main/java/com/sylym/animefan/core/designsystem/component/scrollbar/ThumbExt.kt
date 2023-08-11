package com.sylym.animefan.core.designsystem.component.scrollbar

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue

/**
 * 记住一个函数以响应 [Scrollbar] thumb位置位移 [LazyListState]
 * @param itemsAvailable 列表中的项数
 */
@Composable
fun LazyListState.rememberDraggableScroller(
    itemsAvailable: Int,
): (Float) -> Unit = rememberDraggableScroller(
    itemsAvailable = itemsAvailable,
    scroll = ::scrollToItem,
)

/**
 * 记住一个函数，以响应 [LazyGridState] 的 [Scrollbar] thumb位置位移
 * @param itemsAvailable the amount of items in the grid.
 */
@Composable
fun LazyGridState.rememberDraggableScroller(
    itemsAvailable: Int,
): (Float) -> Unit = rememberDraggableScroller(
    itemsAvailable = itemsAvailable,
    scroll = ::scrollToItem,
)

/**
 * 在惰性布局中对 [Scrollbar] thumb位移做出反应的通用函数
 * @param itemsAvailable 可在布局中滚动的项目总数
 * @param scroll 在确定要滚动到的索引时要调用的函数
 */
@Composable
private inline fun rememberDraggableScroller(
    itemsAvailable: Int,
    crossinline scroll: suspend (index: Int) -> Unit,
): (Float) -> Unit {
    var percentage by remember { mutableStateOf(Float.NaN) }
    val itemCount by rememberUpdatedState(itemsAvailable)

    LaunchedEffect(percentage) {
        if (percentage.isNaN()) return@LaunchedEffect
        val indexToFind = (itemCount * percentage).toInt()
        scroll(indexToFind)
    }
    return remember {
        { newPercentage -> percentage = newPercentage }
    }
}
