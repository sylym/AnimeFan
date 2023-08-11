package com.sylym.animefan.core.designsystem.component.scrollbar

import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlin.math.abs
import kotlin.math.min

/**
 * 计算惰性布局的 [ScrollbarState]
 * @param itemsAvailable 可在布局中滚动的项目总数
 * @param visibleItems 布局中当前可见的项目列表
 * @param firstVisibleItemIndex 函数用于在滚动过程中插入延迟布局中的第一个可见索引，以实现平滑和线性的滚动条thumb进度
 * [itemsAvailable].
 * @param reverseLayout 如果后备惰性布局中的项目以相反的顺序布局
 * */
@Composable
internal inline fun <LazyState : ScrollableState, LazyStateItem> LazyState.scrollbarState(
    itemsAvailable: Int,
    crossinline visibleItems: LazyState.() -> List<LazyStateItem>,
    crossinline firstVisibleItemIndex: LazyState.(List<LazyStateItem>) -> Float,
    crossinline itemPercentVisible: LazyState.(LazyStateItem) -> Float,
    crossinline reverseLayout: LazyState.() -> Boolean,
): ScrollbarState {
    var state by remember { mutableStateOf(ScrollbarState.FULL) }

    LaunchedEffect(
        key1 = this,
        key2 = itemsAvailable,
    ) {
        snapshotFlow {
            if (itemsAvailable == 0) return@snapshotFlow null

            val visibleItemsInfo = visibleItems(this@scrollbarState)
            if (visibleItemsInfo.isEmpty()) return@snapshotFlow null

            val firstIndex = min(
                a = firstVisibleItemIndex(visibleItemsInfo),
                b = itemsAvailable.toFloat(),
            )
            if (firstIndex.isNaN()) return@snapshotFlow null

            val itemsVisible = visibleItemsInfo.sumOf {
                itemPercentVisible(it).toDouble()
            }.toFloat()

            val thumbTravelPercent = min(
                a = firstIndex / itemsAvailable,
                b = 1f,
            )
            val thumbSizePercent = min(
                a = itemsVisible / itemsAvailable,
                b = 1f,
            )
            ScrollbarState(
                thumbSizePercent = thumbSizePercent,
                thumbMovedPercent = when {
                    reverseLayout() -> 1f - thumbTravelPercent
                    else -> thumbTravelPercent
                },
            )
        }
            .filterNotNull()
            .distinctUntilChanged()
            .collect { state = it }
    }
    return state
}

/**
 * 线性插值 [visibleItems] 中第一项的索引，以实现平滑的滚动条进度
 * @param visibleItems 布局中当前可见的项目列表
 * @param itemSize 布局中项目大小的查找函数
 * @param offset 一个查找函数，用于查找项目相对于视口起点的偏移量
 * @param nextItemOnMainAxis 在滚动方向上主轴上查找下一个项目的查找函数
 * @param itemIndex 布局中项目相对于可用项目总数的索引的查找函数
 *
 * @return [Float] 在 firstItemPosition.nextItemPosition 其中 nextItemPosition 是沿主轴的连续项目的索引
 * */
internal inline fun <LazyState : ScrollableState, LazyStateItem> LazyState.interpolateFirstItemIndex(
    visibleItems: List<LazyStateItem>,
    crossinline itemSize: LazyState.(LazyStateItem) -> Int,
    crossinline offset: LazyState.(LazyStateItem) -> Int,
    crossinline nextItemOnMainAxis: LazyState.(LazyStateItem) -> LazyStateItem?,
    crossinline itemIndex: (LazyStateItem) -> Int,
): Float {
    if (visibleItems.isEmpty()) return 0f

    val firstItem = visibleItems.first()
    val firstItemIndex = itemIndex(firstItem)

    if (firstItemIndex < 0) return Float.NaN

    val firstItemSize = itemSize(firstItem)
    if (firstItemSize == 0) return Float.NaN

    val itemOffset = offset(firstItem).toFloat()
    val offsetPercentage = abs(itemOffset) / firstItemSize

    val nextItem = nextItemOnMainAxis(firstItem) ?: return firstItemIndex + offsetPercentage

    val nextItemIndex = itemIndex(nextItem)

    return firstItemIndex + ((nextItemIndex - firstItemIndex) * offsetPercentage)
}

/**
 * 返回当前在视口中可见的项的百分比
 * @param itemSize 项目的大小
 * @param itemStartOffset 项目相对于视口开始的起始偏移量
 * @param viewportStartOffset 视口的起始偏移量
 * @param viewportEndOffset 视口的结束偏移量
 */
internal fun itemVisibilityPercentage(
    itemSize: Int,
    itemStartOffset: Int,
    viewportStartOffset: Int,
    viewportEndOffset: Int,
): Float {
    if (itemSize == 0) return 0f
    val itemEnd = itemStartOffset + itemSize
    val startOffset = when {
        itemStartOffset > viewportStartOffset -> 0
        else -> abs(abs(viewportStartOffset) - abs(itemStartOffset))
    }
    val endOffset = when {
        itemEnd < viewportEndOffset -> 0
        else -> abs(abs(itemEnd) - abs(viewportEndOffset))
    }
    val size = itemSize.toFloat()
    return (size - startOffset - endOffset) / size
}
