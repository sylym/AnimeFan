package com.sylym.animefan.core.designsystem.component.scrollbar

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.Orientation.Horizontal
import androidx.compose.foundation.gestures.Orientation.Vertical
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sylym.animefan.core.designsystem.component.scrollbar.ThumbState.Active
import com.sylym.animefan.core.designsystem.component.scrollbar.ThumbState.Dormant
import com.sylym.animefan.core.designsystem.component.scrollbar.ThumbState.Inactive
import kotlinx.coroutines.delay

/**
 * 在与滚动条thumb交互后，在其逐渐消失之前显示滚动条thumb的时间段
 */
private const val SCROLLBAR_INACTIVE_TO_DORMANT_TIME_IN_MS = 2_000L

/**
 * 允许通过拖动thumb快速滚动内容的 [Scrollbar]
 * 当滚动容器处于休眠状态时，thumb消失
 * @param modifier 要应用于[Scrollbar]的[Modifier]
 * @param state [Scrollbar]的驱动状态
 * @param orientation 滚动条的方向
 * @param onThumbMoved 快速滚动实现
 */
@Composable
fun ScrollableState.DraggableScrollbar(
    modifier: Modifier = Modifier,
    state: ScrollbarState,
    orientation: Orientation,
    onThumbMoved: (Float) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Scrollbar(
        modifier = modifier,
        orientation = orientation,
        interactionSource = interactionSource,
        state = state,
        thumb = {
            DraggableScrollbarThumb(
                interactionSource = interactionSource,
                orientation = orientation,
            )
        },
        onThumbMoved = onThumbMoved,
    )
}

/**
 * 一个简单的[Scrollbar]，不能拖动
 * 当滚动容器处于休眠状态时，thumb消失
 * @param modifier 要应用于[Scrollbar]的[Modifier]
 * @param state [Scrollbar]的驱动状态
 * @param orientation 滚动条的方向
 */
@Composable
fun ScrollableState.DecorativeScrollbar(
    modifier: Modifier = Modifier,
    state: ScrollbarState,
    orientation: Orientation,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Scrollbar(
        modifier = modifier,
        orientation = orientation,
        interactionSource = interactionSource,
        state = state,
        thumb = {
            DecorativeScrollbarThumb(
                interactionSource = interactionSource,
                orientation = orientation,
            )
        },
    )
}

/**
 * 一种滚动条thumb，也可以作为快速滚动的触摸目标
 */
@Composable
private fun ScrollableState.DraggableScrollbarThumb(
    interactionSource: InteractionSource,
    orientation: Orientation,
) {
    Box(
        modifier = Modifier
            .run {
                when (orientation) {
                    Vertical -> width(12.dp).fillMaxHeight()
                    Horizontal -> height(12.dp).fillMaxWidth()
                }
            }
            .background(
                color = scrollbarThumbColor(
                    interactionSource = interactionSource,
                ),
                shape = RoundedCornerShape(16.dp),
            ),
    )
}

/**
 * 装饰性滚动条thumb，仅用于传达用户在列表中的位置
 */
@Composable
private fun ScrollableState.DecorativeScrollbarThumb(
    interactionSource: InteractionSource,
    orientation: Orientation,
) {
    Box(
        modifier = Modifier
            .run {
                when (orientation) {
                    Vertical -> width(2.dp).fillMaxHeight()
                    Horizontal -> height(2.dp).fillMaxWidth()
                }
            }
            .background(
                color = scrollbarThumbColor(
                    interactionSource = interactionSource,
                ),
                shape = RoundedCornerShape(16.dp),
            ),
    )
}

/**
 * 滚动条缩略图的颜色作为其交互状态的函数
 * @param interactionSource 滚动容器中的交互源
 */
@Composable
private fun ScrollableState.scrollbarThumbColor(
    interactionSource: InteractionSource,
): Color {
    var state by remember { mutableStateOf(Dormant) }
    val pressed by interactionSource.collectIsPressedAsState()
    val hovered by interactionSource.collectIsHoveredAsState()
    val dragged by interactionSource.collectIsDraggedAsState()
    val active = (canScrollForward || canScrollForward) &&
        (pressed || hovered || dragged || isScrollInProgress)

    val color by animateColorAsState(
        targetValue = when (state) {
            Active -> MaterialTheme.colorScheme.onSurface.copy(0.5f)
            Inactive -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
            Dormant -> Color.Transparent
        },
        animationSpec = SpringSpec(
            stiffness = Spring.StiffnessLow,
        ),
        label = "Scrollbar thumb color",
    )
    LaunchedEffect(active) {
        when (active) {
            true -> state = Active
            false -> if (state == Active) {
                state = Inactive
                delay(SCROLLBAR_INACTIVE_TO_DORMANT_TIME_IN_MS)
                state = Dormant
            }
        }
    }

    return color
}

private enum class ThumbState {
    Active, Inactive, Dormant
}
