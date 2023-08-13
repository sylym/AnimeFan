package com.sylym.animefan.core.model.data

/**
 * 汇总用户兴趣数据的类
 */
data class UserData(
    val followedAnime: Set<String>, // String: animeId
    val bookmarkedEpisodeResources: Set<String>, // String: episodeId
    val watchedEpisodeResources: Map<String, WatchedEpisodesResource>, // String: episodeId
    val darkThemeConfig: DarkThemeConfig,
)
