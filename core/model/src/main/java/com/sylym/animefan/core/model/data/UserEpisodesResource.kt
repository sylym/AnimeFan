package com.sylym.animefan.core.model.data

import kotlinx.datetime.Instant

/**
 * 包含其他用户信息的 [EpisodesResource]，
 * 例如用户是否正在关注单集资源的动画以及他们是否已保存（添加书签）单集资源
 */
data class UserEpisodesResource internal constructor(
    val id: String,
    val name: String,
    val nameCN: String,
    val type: EpisodesResourceType,
    val num: Int,
    val airDate: Instant,
    val followableAnime: FollowableAnime,
    val isSaved: Boolean,
    val watchedEpisodesResource: WatchedEpisodesResource? = null,

){
    constructor(episodesResource: EpisodesResource, userData: UserData) : this(
        id = episodesResource.id,
        name = episodesResource.name,
        nameCN = episodesResource.nameCN,
        type = episodesResource.type,
        num = episodesResource.num,
        airDate = episodesResource.airDate,
        followableAnime = FollowableAnime(
                anime = episodesResource.anime,
                isFollowed = userData.followedAnime.contains(episodesResource.anime.id),
            ),
        isSaved = userData.bookmarkedEpisodeResources.contains(episodesResource.id),
        watchedEpisodesResource = if (userData.watchedEpisodeResources.containsKey(episodesResource.id)) {
            userData.watchedEpisodeResources[episodesResource.id]
        } else {
            null
        },
    )
}

fun List<EpisodesResource>.mapToUserNewsResources(userData: UserData): List<UserEpisodesResource> {
    return map { UserEpisodesResource(it, userData) }
}