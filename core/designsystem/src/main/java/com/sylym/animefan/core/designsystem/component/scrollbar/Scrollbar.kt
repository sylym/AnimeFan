package com.sylym.animefan.core.designsystem.component.scrollbar

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.util.packFloats
import androidx.compose.ui.util.unpackFloat1
import androidx.compose.ui.util.unpackFloat2
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeout
import kotlin.math.max
import kotlin.math.min

/**
 * 当用户长按滚动条track以启动滚动而不是拖动滚动条thumb时滚动之间的延迟。
 */
private const val SCROLLBAR_PRESS_DELAY_MS = 10L

/**
 * 长按滚动条track时滚动条的百分比位移。
 */
private const val SCROLLBAR_PRESS_DELTA_PCT = 0.02f

/**
 * 滚动条核心属性的类定义
 */
@Immutable
@JvmInline
value class ScrollbarState internal constructor(
    internal val packedValue: Long,
) {
    companion object {
        val FULL = ScrollbarState(
            thumbSizePercent = 1f,
            thumbMovedPercent = 0f,
        )
    }
}

/**
 * 滚动条track核心属性的类定义
 */
@Immutable
@JvmInline
private value class ScrollbarTrack(
    val packedValue: Long,
) {
    constructor(
        max: Float,
        min: Float,
    ) : this(packFloats(max, min))
}

/**
 * 使用列出的属性创建 [ScrollbarState]
 * @param thumbSizePercent 滚动条thumb大小占track大小的百分比
 * 指thumb宽度（对于水平滚动条）或高度（对于垂直滚动条）
 * @param thumbMovedPercent thumb行进的距离占track大小的百分比
 */
fun ScrollbarState(
    thumbSizePercent: Float,
    thumbMovedPercent: Float,
) = ScrollbarState(
    packFloats(
        val1 = thumbSizePercent,
        val2 = thumbMovedPercent,
    ),
)

/**
 * 返回滚动条thumb大小（占track大小的百分比）
 */
val ScrollbarState.thumbSizePercent
    get() = unpackFloat1(packedValue)

/**
 * 返回thumb行进的距离占track大小的百分比
 */
val ScrollbarState.thumbMovedPercent
    get() = unpackFloat2(packedValue)

/**
 * 返回滚动条track的大小（以像素为单位）
 */
private val ScrollbarTrack.size
    get() = unpackFloat2(packedValue) - unpackFloat1(packedValue)

/**
 * 以百分比形式返回滚动条thumb在track上的位置
 */
private fun ScrollbarTrack.thumbPosition(
    dimension: Float,
): Float = max(
    a = min(
        a = dimension / size,
        b = 1f,
    ),
    b = 0f,
)

/**
 * 返回沿 [this] 指定的轴的 [offset] 值
 */
internal fun Orientation.valueOf(offset: Offset) = when (this) {
    Orientation.Horizontal -> offset.x
    Orientation.Vertical -> offset.y
}

/**
 * 沿 [this] 指定的轴返回 [intSize] 的值
 */
internal fun Orientation.valueOf(intSize: IntSize) = when (this) {
    Orientation.Horizontal -> intSize.width
    Orientation.Vertical -> intSize.height
}

/**
 * 沿 [this] 指定的轴返回 [intOffset] 的值
 */
internal fun Orientation.valueOf(intOffset: IntOffset) = when (this) {
    Orientation.Horizontal -> intOffset.x
    Orientation.Vertical -> intOffset.y
}

/**
 * 用于绘制滚动条的可组合项
 * @param orientation 滚动条的滚动方向
 * @param state 描述滚动条位置的状态
 * @param minThumbSize 滚动条thumb的最小大小
 * @param interactionSource 允许观察滚动条的状态
 * @param thumb 用于绘制滚动条thumb的可组合项
 * @param onThumbMoved 用于响应由用户在滚动条thumb上的直接交互引起的滚动条位移的函数，例如实现快速滚动
 */
@Composable
fun Scrollbar(
    modifier: Modifier = Modifier,
    orientation: Orientation,
    state: ScrollbarState,
    minThumbSize: Dp = 40.dp,
    interactionSource: MutableInteractionSource? = null,
    thumb: @Composable () -> Unit,
    onThumbMoved: ((Float) -> Unit)? = null,
) {
    val localDensity = LocalDensity.current

    // 使用Offset. Unspecified和Float. NaN而不是null来防止不必要的基元装箱
    var pressedOffset by remember { mutableStateOf(Offset.Unspecified) }
    var draggedOffset by remember { mutableStateOf(Offset.Unspecified) }

    // 用于跟踪滚动条的交互状态
    var interactionThumbTravelPercent by remember { mutableStateOf(Float.NaN) }

    var track by remember { mutableStateOf(ScrollbarTrack(packedValue = 0)) }

    val thumbTravelPercent = when {
        interactionThumbTravelPercent.isNaN() -> state.thumbMovedPercent
        else -> interactionThumbTravelPercent
    }
    val thumbSizePx = max(
        a = state.thumbSizePercent * track.size,
        b = with(localDensity) { minThumbSize.toPx() },
    )
    val thumbSizeDp by animateDpAsState(
        targetValue = with(localDensity) { thumbSizePx.toDp() },
        label = "scrollbar thumb size",
    )
    val thumbMovedPx = min(
        a = track.size * thumbTravelPercent,
        b = track.size - thumbSizePx,
    )

    // 滚动条track的可组合项
    Box(
        modifier = modifier
            .run {
                val withHover = interactionSource?.let(::hoverable) ?: this
                when (orientation) {
                    Orientation.Vertical -> withHover.fillMaxHeight()
                    Orientation.Horizontal -> withHover.fillMaxWidth()
                }
            }
            .onGloballyPositioned { coordinates ->
                val scrollbarStartCoordinate = orientation.valueOf(coordinates.positionInRoot())
                track = ScrollbarTrack(
                    max = scrollbarStartCoordinate,
                    min = scrollbarStartCoordinate + orientation.valueOf(coordinates.size),
                )
            }
            // 处理滚动条按压
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = { offset ->
                        try {
                            // 等待长按后滚动
                            withTimeout(viewConfiguration.longPressTimeoutMillis) {
                                tryAwaitRelease()
                            }
                        } catch (e: TimeoutCancellationException) {
                            // 启动按压触发的滚动
                            val initialPress = PressInteraction.Press(offset)
                            interactionSource?.tryEmit(initialPress)

                            pressedOffset = offset
                            interactionSource?.tryEmit(
                                when {
                                    tryAwaitRelease() -> PressInteraction.Release(initialPress)
                                    else -> PressInteraction.Cancel(initialPress)
                                },
                            )

                            // 按压触发的滚动结束
                            pressedOffset = Offset.Unspecified
                        }
                    },
                )
            }
            // 处理滚动条拖动
            .pointerInput(Unit) {
                var dragInteraction: DragInteraction.Start? = null
                val onDragStart: (Offset) -> Unit = { offset ->
                    val start = DragInteraction.Start()
                    dragInteraction = start
                    interactionSource?.tryEmit(start)
                    draggedOffset = offset
                }
                val onDragEnd: () -> Unit = {
                    dragInteraction?.let { interactionSource?.tryEmit(DragInteraction.Stop(it)) }
                    draggedOffset = Offset.Unspecified
                }
                val onDragCancel: () -> Unit = {
                    dragInteraction?.let { interactionSource?.tryEmit(DragInteraction.Cancel(it)) }
                    draggedOffset = Offset.Unspecified
                }
                val onDrag: (change: PointerInputChange, dragAmount: Float) -> Unit =
                    onDrag@{ _, delta ->
                        if (draggedOffset == Offset.Unspecified) return@onDrag
                        draggedOffset = when (orientation) {
                            Orientation.Vertical -> draggedOffset.copy(
                                y = draggedOffset.y + delta,
                            )

                            Orientation.Horizontal -> draggedOffset.copy(
                                x = draggedOffset.x + delta,
                            )
                        }
                    }

                when (orientation) {
                    Orientation.Horizontal -> detectHorizontalDragGestures(
                        onDragStart = onDragStart,
                        onDragEnd = onDragEnd,
                        onDragCancel = onDragCancel,
                        onHorizontalDrag = onDrag,
                    )

                    Orientation.Vertical -> detectVerticalDragGestures(
                        onDragStart = onDragStart,
                        onDragEnd = onDragEnd,
                        onDragCancel = onDragCancel,
                        onVerticalDrag = onDrag,
                    )
                }
            },
    ) {
        val scrollbarThumbMovedDp = max(
            a = with(localDensity) { thumbMovedPx.toDp() },
            b = 0.dp,
        )
        // 滚动条thumb的可组合项
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .run {
                    when (orientation) {
                        Orientation.Horizontal -> width(thumbSizeDp)
                        Orientation.Vertical -> height(thumbSizeDp)
                    }
                }
                .offset(
                    y = when (orientation) {
                        Orientation.Horizontal -> 0.dp
                        Orientation.Vertical -> scrollbarThumbMovedDp
                    },
                    x = when (orientation) {
                        Orientation.Horizontal -> scrollbarThumbMovedDp
                        Orientation.Vertical -> 0.dp
                    },
                ),
        ) {
            thumb()
        }
    }

    if (onThumbMoved == null) return

    // 将在随后的效果中读取的状态，但不会导致重新触发它们
    val updatedState by rememberUpdatedState(state)

    // 处理按压
    LaunchedEffect(pressedOffset) {
        // 按压结束，重置interactionThumbTravelPercent
        if (pressedOffset == Offset.Unspecified) {
            interactionThumbTravelPercent = Float.NaN
            return@LaunchedEffect
        }

        var currentThumbMovedPercent = updatedState.thumbMovedPercent
        val destinationThumbMovedPercent = track.thumbPosition(
            dimension = orientation.valueOf(pressedOffset),
        )
        val isPositive = currentThumbMovedPercent < destinationThumbMovedPercent
        val delta = SCROLLBAR_PRESS_DELTA_PCT * if (isPositive) 1f else -1f

        while (currentThumbMovedPercent != destinationThumbMovedPercent) {
            currentThumbMovedPercent = when {
                isPositive -> min(
                    a = currentThumbMovedPercent + delta,
                    b = destinationThumbMovedPercent,
                )

                else -> max(
                    a = currentThumbMovedPercent + delta,
                    b = destinationThumbMovedPercent,
                )
            }
            onThumbMoved(currentThumbMovedPercent)
            interactionThumbTravelPercent = currentThumbMovedPercent
            delay(SCROLLBAR_PRESS_DELAY_MS)
        }
    }

    // 处理拖动
    LaunchedEffect(draggedOffset) {
        if (draggedOffset == Offset.Unspecified) {
            interactionThumbTravelPercent = Float.NaN
            return@LaunchedEffect
        }
        val currentTravel = track.thumbPosition(
            dimension = orientation.valueOf(draggedOffset),
        )
        onThumbMoved(currentTravel)
        interactionThumbTravelPercent = currentTravel
    }
}
