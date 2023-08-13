package com.sylym.animefan.core.ui

import androidx.compose.ui.tooling.preview.Preview

/**
 * 表示各种设备大小的多重预览注释。将此注释添加到可组合对象以呈现各种设备。
 */
@Preview(name = "phone", device = "spec:shape=Normal,width=360,height=640,unit=dp,dpi=480")
@Preview(name = "landscape", device = "spec:shape=Normal,width=640,height=360,unit=dp,dpi=480")
@Preview(name = "foldable", device = "spec:shape=Normal,width=673,height=841,unit=dp,dpi=480")
@Preview(name = "tablet", device = "spec:shape=Normal,width=1280,height=800,unit=dp,dpi=480")
annotation class DevicePreviews
