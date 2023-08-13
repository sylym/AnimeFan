package com.sylym.animefan.core.model.data

enum class EpisodeType(
    val serializedName: String,
    val displayText: String,
    // TODO: 描述可能应该是字符串资源
    val description: String,
) {
    Feature(
        serializedName = "正片",
        displayText = "正片",
        description = "动画正片",
    ),
    Special(
        serializedName = "SP",
        displayText = "SP",
        description = "动画特别篇",
    ),
    Unknown(
        serializedName = "未知",
        displayText = "未知",
        description = "未知",
    ),
}

fun String?.asEpisodeType() = when (this) {
    null -> EpisodeType.Unknown
    else -> EpisodeType.values()
        .firstOrNull { type -> type.serializedName == this }
        ?: EpisodeType.Unknown
}