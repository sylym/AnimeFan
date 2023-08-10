package com.sylym.animefan.core.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * 带有通用内容槽的Anime Fan填充按钮。 Wraps Material 3 [Button].
 *
 * @param onClick 将在用户单击按钮时调用
 * @param modifier 要应用于按钮的Modifier
 * @param enabled 控制按钮的启用状态。当“false”时，此按钮将无法单击，并且对可访问性能服务显示为禁用。
 * @param contentPadding 按钮内容的内边距
 * @param content 按钮内容
 */
@Composable
fun AnimeFanButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.onBackground,
        ),
        contentPadding = contentPadding,
        content = content,
    )
}

/**
 * 带有文本和图标内容槽的Anime Fan填充按钮
 *
 * @param onClick 将在用户单击按钮时调用
 * @param modifier 要应用于按钮的Modifier
 * @param enabled 控制按钮的启用状态。当“false”时，此按钮将无法单击，并且对可访问性能服务显示为禁用。
 * @param text 按钮文本内容
 * @param leadingIcon 按钮引导图标内容。如果没有引导图标，请在此处传递“null”
 */
@Composable
fun AnimeFanButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    AnimeFanButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        contentPadding = if (leadingIcon != null) {
            ButtonDefaults.ButtonWithIconContentPadding
        } else {
            ButtonDefaults.ContentPadding
        },
    ) {
        AnimeFanButtonContent(
            text = text,
            leadingIcon = leadingIcon,
        )
    }
}

/**
 * 带有通用内容槽的Anime Fan轮廓按钮。 Wraps Material 3 [OutlinedButton].
 *
 * @param onClick 将在用户单击按钮时调用
 * @param modifier 要应用于按钮的Modifier
 * @param enabled 控制按钮的启用状态。当“false”时，此按钮将无法单击，并且对可访问性能服务显示为禁用。
 * @param contentPadding 按钮内容的内边距
 * @param content 按钮内容
 */
@Composable
fun AnimeFanOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground,
        ),
        border = BorderStroke(
            width = AnimeFanButtonDefaults.OutlinedButtonBorderWidth,
            color = if (enabled) {
                MaterialTheme.colorScheme.outline
            } else {
                MaterialTheme.colorScheme.onSurface.copy(
                    alpha = AnimeFanButtonDefaults.DisabledOutlinedButtonBorderAlpha,
                )
            },
        ),
        contentPadding = contentPadding,
        content = content,
    )
}

/**
 * 带有文本和图标内容槽的Anime Fan轮廓按钮
 *
 * @param onClick 将在用户单击按钮时调用
 * @param modifier 要应用于按钮的Modifier
 * @param enabled 控制按钮的启用状态。当“false”时，此按钮将无法单击，并且对可访问性能服务显示为禁用。
 * @param text 按钮文本内容
 * @param leadingIcon 按钮引导图标内容。如果没有引导图标，请在此处传递“null”
 */
@Composable
fun AnimeFanOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    AnimeFanOutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        contentPadding = if (leadingIcon != null) {
            ButtonDefaults.ButtonWithIconContentPadding
        } else {
            ButtonDefaults.ContentPadding
        },
    ) {
        AnimeFanButtonContent(
            text = text,
            leadingIcon = leadingIcon,
        )
    }
}

/**
 * 带有通用内容槽的Anime Fan文字按钮。 Wraps Material 3 [TextButton].
 *
 * @param onClick 将在用户单击按钮时调用
 * @param modifier 要应用于按钮的Modifier
 * @param enabled 控制按钮的启用状态。当“false”时，此按钮将无法单击，并且对可访问性能服务显示为禁用。
 * @param content 按钮内容
 */
@Composable
fun AnimeFanTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground,
        ),
        content = content,
    )
}

/**
 * 带有文本和图标内容槽的Anime Fan文字按钮
 *
 * @param onClick 将在用户单击按钮时调用
 * @param modifier 要应用于按钮的Modifier
 * @param enabled 控制按钮的启用状态。当“false”时，此按钮将无法单击，并且对可访问性能服务显示为禁用。
 * @param text 按钮文本内容
 * @param leadingIcon 按钮引导图标内容。如果没有引导图标，请在此处传递“null”
 */
@Composable
fun AnimeFanTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    AnimeFanTextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
    ) {
        AnimeFanButtonContent(
            text = text,
            leadingIcon = leadingIcon,
        )
    }
}

/**
 * 用于排列文本标签和引导图标的Anime Fan按钮内容布局
 *
 * @param text 按钮文本内容
 * @param leadingIcon 按钮引导图标内容。如果没有引导图标，请在此处传递“null”
 */
@Composable
private fun AnimeFanButtonContent(
    text: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    if (leadingIcon != null) {
        Box(Modifier.sizeIn(maxHeight = ButtonDefaults.IconSize)) {
            leadingIcon()
        }
    }
    Box(
        Modifier
            .padding(
                start = if (leadingIcon != null) {
                    ButtonDefaults.IconSpacing
                } else {
                    0.dp
                },
            ),
    ) {
        text()
    }
}

/**
 * Anime Fan按钮默认值
 */
object AnimeFanButtonDefaults {
    // TODO: 文件bug
    // 轮廓按钮边框颜色默认情况下不考虑禁用状态
    const val DisabledOutlinedButtonBorderAlpha = 0.12f

    // TODO: 文件bug
    // 轮廓按钮默认边框宽度未通过ButtonDefaults公开
    val OutlinedButtonBorderWidth = 1.dp
}
