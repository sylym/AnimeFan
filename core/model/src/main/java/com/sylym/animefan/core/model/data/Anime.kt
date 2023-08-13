package com.sylym.animefan.core.model.data

import kotlinx.datetime.Instant

/**
 * 动画介绍的外部数据层表示
 */
data class Anime(
    val id: String,
    val name: String,
    val alias: List<String>,
    val date: Instant,
    val score: Float,
    val scoredNum: Int,
    val rank: Int,
    val imageUrl: String,
    val summary: String,
    val tags: List<String>,
)
