package com.sylym.animefan.core.model.data

/**
 * 带有是否追番的附加信息的[anime]。
 */
data class FollowableAnime ( // TODO 考虑更改为用户主题并扁平化
    val anime: Anime,
    val isFollowed: Boolean,
)