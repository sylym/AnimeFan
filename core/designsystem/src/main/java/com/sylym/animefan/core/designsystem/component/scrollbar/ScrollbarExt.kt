package com.sylym.animefan.core.designsystem.component.scrollbar

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridItemInfo
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable

/**
 * 计算由 [LazyListState] 中的更改驱动的 [ScrollbarState]
 *
 * @param itemsAvailable 可在惰性列表中滚动的项目总数
 * @param itemIndex 查找列表中相对于 [itemsAvailable] 的项目索引的查找函数
 */
@Composable
fun LazyListState.scrollbarState(
    itemsAvailable: Int,
    itemIndex: (LazyListItemInfo) -> Int = LazyListItemInfo::index,
): ScrollbarState =
    scrollbarState(
        itemsAvailable = itemsAvailable,
        visibleItems = { layoutInfo.visibleItemsInfo },
        firstVisibleItemIndex = { visibleItems ->
            interpolateFirstItemIndex(
                visibleItems = visibleItems,
                itemSize = { it.size },
                offset = { it.offset },
                nextItemOnMainAxis = { first -> visibleItems.find { it != first } },
                itemIndex = itemIndex,
            )
        },
        itemPercentVisible = itemPercentVisible@{ itemInfo ->
            itemVisibilityPercentage(
                itemSize = itemInfo.size,
                itemStartOffset = itemInfo.offset,
                viewportStartOffset = layoutInfo.viewportStartOffset,
                viewportEndOffset = layoutInfo.viewportEndOffset,
            )
        },
        reverseLayout = { layoutInfo.reverseLayout },
    )

/**
 * 计算由 [LazyGridState] 中的更改驱动的 [ScrollbarState]
 *
 * @param itemsAvailable 可在网格中滚动的项目总数
 * @param itemIndex 网格中相对于 [itemsAvailable] 的项索引的查找函数
 */
@Composable
fun LazyGridState.scrollbarState(
    itemsAvailable: Int,
    itemIndex: (LazyGridItemInfo) -> Int = LazyGridItemInfo::index,
): ScrollbarState =
    scrollbarState(
        itemsAvailable = itemsAvailable,
        visibleItems = { layoutInfo.visibleItemsInfo },
        firstVisibleItemIndex = { visibleItems ->
            interpolateFirstItemIndex(
                visibleItems = visibleItems,
                itemSize = {
                    layoutInfo.orientation.valueOf(it.size)
                },
                offset = { layoutInfo.orientation.valueOf(it.offset) },
                nextItemOnMainAxis = { first ->
                    when (layoutInfo.orientation) {
                        Orientation.Vertical -> visibleItems.find {
                            it != first && it.row != first.row
                        }

                        Orientation.Horizontal -> visibleItems.find {
                            it != first && it.column != first.column
                        }
                    }
                },
                itemIndex = itemIndex,
            )
        },
        itemPercentVisible = itemPercentVisible@{ itemInfo ->
            itemVisibilityPercentage(
                itemSize = layoutInfo.orientation.valueOf(itemInfo.size),
                itemStartOffset = layoutInfo.orientation.valueOf(itemInfo.offset),
                viewportStartOffset = layoutInfo.viewportStartOffset,
                viewportEndOffset = layoutInfo.viewportEndOffset,
            )
        },
        reverseLayout = { layoutInfo.reverseLayout },
    )
