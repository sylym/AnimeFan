package com.sylym.animefan.core.model.data

/**
 * 汇总用户兴趣数据的类
 */
data class UserData(
    val followedAnime: Set<String>, // String: animeId
    val bookmarkedEpisodes: Set<String>, // String: episodeId
    val watchedEpisodes: Map<String, WatchedEpisode>, // String: episodeId
    val darkThemeConfig: DarkThemeConfig,
)
