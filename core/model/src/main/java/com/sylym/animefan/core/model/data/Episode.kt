package com.sylym.animefan.core.model.data

import kotlinx.datetime.Instant

/**
 * 单集介绍的外部数据层表示形式
 */
data class Episode(
    val id: String,
    val name: String,
    val nameCN: String,
    val type: EpisodeType,
    val num: Int,
    val airDate: Instant,
    val anime: Anime,
    val sourceUrl: Map<String, String>,
)
