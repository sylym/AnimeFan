package com.sylym.animefan.core.model.data

import kotlinx.datetime.Instant

/**
 * 包含其他用户信息的 [Episode]，
 * 例如用户是否正在关注单集资源的动画以及他们是否已保存（添加书签）单集资源
 */
data class UserEpisode internal constructor(
    val id: String,
    val name: String,
    val nameCN: String,
    val type: EpisodeType,
    val num: Int,
    val airDate: Instant,
    val followableAnime: FollowableAnime,
    val isSaved: Boolean,
    val watchedEpisode: WatchedEpisode? = null,

){
    constructor(episode: Episode, userData: UserData) : this(
        id = episode.id,
        name = episode.name,
        nameCN = episode.nameCN,
        type = episode.type,
        num = episode.num,
        airDate = episode.airDate,
        followableAnime = FollowableAnime(
                anime = episode.anime,
                isFollowed = userData.followedAnime.contains(episode.anime.id),
            ),
        isSaved = userData.bookmarkedEpisodes.contains(episode.id),
        watchedEpisode = if (userData.watchedEpisodes.containsKey(episode.id)) {
            userData.watchedEpisodes[episode.id]
        } else {
            null
        },
    )
}

fun List<Episode>.mapToUserEpisodes(userData: UserData): List<UserEpisode> {
    return map { UserEpisode(it, userData) }
}