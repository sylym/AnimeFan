package com.sylym.animefan.core.model.data

import kotlinx.datetime.Instant

data class WatchedEpisodesResource(
    val animeSource: String,
    val episodeUrl: String,
    val watchedTime: Long,
    val totalTime: Long,
    val watchedDate: Instant
)
