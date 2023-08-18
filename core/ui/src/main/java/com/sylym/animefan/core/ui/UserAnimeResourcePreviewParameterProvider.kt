package com.sylym.animefan.core.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.sylym.animefan.core.model.data.Anime
import com.sylym.animefan.core.model.data.DarkThemeConfig
import com.sylym.animefan.core.model.data.Episode
import com.sylym.animefan.core.model.data.EpisodeType
import com.sylym.animefan.core.model.data.UserData
import com.sylym.animefan.core.model.data.UserEpisode
import com.sylym.animefan.core.model.data.WatchedEpisode
import com.sylym.animefan.core.ui.PreviewParameterData.episodes
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

/* ktlint-disable max-line-length */
/**
 * [PreviewParameterProvider](https://developer.android.com/reference/kotlin/androidx/compose/ui/tooling/preview/PreviewParameterProvider)
 * 提供可组合预览的 [UserEpisode] 列表。
 */
class UserEpisodePreviewParameterProvider : PreviewParameterProvider<List<UserEpisode>> {

    override val values: Sequence<List<UserEpisode>> = sequenceOf(episodes)
}

object PreviewParameterData {

    private val userData: UserData = UserData(
        followedAnime = setOf("328609"),
        bookmarkedEpisodes = setOf("1128641"),
        watchedEpisodes = mapOf(
            "1128641" to WatchedEpisode(
                animeSource = "www.yhdmzz.com-1",
                episodeUrl = "https://www.yhdmzz.com/vp/22203-1-0.html",
                watchedTime = 600000,
                totalTime = 1440000,
                watchedDate = LocalDateTime(2021, 8, 1, 0, 0, 0).toInstant(TimeZone.UTC),
            ),
        ),
        darkThemeConfig = DarkThemeConfig.DARK,
    )

    val anime = listOf(
        Anime(
            id = "328609",
            name = "ぼっち・ざ・ろっく！",
            nameCN = "孤独摇滚！",
            alias = listOf("Bocchi the Rock!", "Bocchi the &quot;Guitar Hero&quot; Rock Story"),
            date = Instant.parse("2022-10-08T00:00:00Z"),
            score = 8.4f,
            scoredNum = 16942,
            rank = 68,
            imageUrl = "https://lain.bgm.tv/r/400/pic/cover/l/e2/e7/328609_2EHLJ.jpg",
            summary = "作为网络吉他手“吉他英雄”而广受好评的后藤一里，在现实中却是个什么都不会的沟通障碍者。一里有着组建乐队的梦想，但因为不敢向人主动搭话而一直没有成功，直到一天在公园中被伊地知虹夏发现并邀请进入缺少吉他手的“结束乐队”。可是，完全没有和他人合作经历的一里，在人前完全发挥不出原本的实力。为了努力克服沟通障碍，一里与“结束乐队”的成员们一同开始努力……",
            tags = listOf("芳文社", "CloverWorks", "音乐", "轻百合", "日常", "2022年10月", "漫画改", "乐队题材", "TV", "搞笑", "百合", "2022", "漫改", "斎藤圭一郎", "梅原翔太", "校园", "青春"),
        ),
        Anime(
            id = "1424",
            name = "けいおん！",
            nameCN = "轻音少女",
            alias = listOf("轻音！", "K-ON!"),
            date = Instant.parse("2009-04-02T00:00:00Z"),
            score = 8.1f,
            scoredNum = 16673,
            rank = 170,
            imageUrl = "https://lain.bgm.tv/r/400/pic/cover/l/48/9d/1424_q8FMQ.jpg",
            summary = "春天，在新生决定社团的时候，田井中律硬拉着青梅竹马的秋山澪参观轻音部让其入部，在得知前辈们毕业后由于人数不足将面临闭部结局，秋山澪与琴吹紬成为了轻音部成员，但离4人指标还差1位名额。这时，一名弄错了部名的少女平泽唯误打误撞之下填补了最后一位空位，但这位少女却是一个连乐谱也看不懂的新人，学习成绩又差。而就是这样的4名少女，却展开了奏响青春的音乐之旅。",
            tags = listOf("京阿尼", "K-ON!", "轻音", "校园", "萌", "治愈", "2009年4月", "TV", "废萌", "卖萌", "2009", "漫画改", "日常", "京都", "京都动画", "音乐", "山田尚子"),
        ),
        Anime(
            id = "977",
            name = "あずまんが大王 THE ANIMATION",
            nameCN = "阿滋漫画大王",
            alias = listOf("笑园漫画大王", "阿兹漫画大王"),
            date = Instant.parse("2002-04-08T00:00:00Z"),
            score = 7.8f,
            scoredNum = 1508,
            rank = 443,
            imageUrl = "https://lain.bgm.tv/r/400/pic/cover/l/cc/1a/977_0152I.jpg",
            summary = "某天，班上转来了两个学生，一个是跳级生美滨千代，她拥有着和可爱外表完全不匹配的天才级智商，以及同智商完全不匹配的情商。另一个叫春日步，她倒是表里如一的迟钝和呆萌。很快，她们便和同学们打成了一片。泷野智人称阿智，争强好斗的同时，却有着意外的单纯和天真。外表冷静成熟的榊对世上一切可爱的东西抵抗力为零。水原历是阿智的童年玩伴，成绩优秀的她毫无疑问的成为了阿智的挑战对象。再加上一个活宝班主任谷崎由佳里，这群人聚到一起，想必这三年的高中生活一定会多姿多彩的。",
            tags = listOf("搞笑", "阿兹漫画大王!", "J.C.STAFF", "大阪", "治愈", "TV", "2002", "校园", "四格", "漫画改", "千代爸爸", "日常", "电波", "2002年4月", "治愈系", "阿兹", "東清彥"),
        ),
    )

    val episodes = listOf(
        UserEpisode(
            episode = Episode(
                id = "1128641",
                name = "転がるぼっち",
                nameCN = "翻转孤独",
                type = EpisodeType.Feature,
                num = 1,
                airDate = Instant.parse("2022-10-08T00:00:00Z"),
                anime = anime[0],
                sourceUrl = mapOf(
                    "www.yhdmzz.com-1" to "https://www.yhdmzz.com/vp/22203-1-0.html",
                    "www.yhdmzz.com-2" to "https://www.yhdmzz.com/vp/22203-2-0.html",
                ),
            ),
            userData = userData,
        ),
        UserEpisode(
            episode = Episode(
                id = "1128642",
                name = "また明日",
                nameCN = "明天见",
                type = EpisodeType.Feature,
                num = 2,
                airDate = Instant.parse("2022-10-15T00:00:00Z"),
                anime = anime[0],
                sourceUrl = mapOf(
                    "www.yhdmzz.com-1" to "https://www.yhdmzz.com/vp/22203-1-1.html",
                    "www.yhdmzz.com-2" to "https://www.yhdmzz.com/vp/22203-2-1.html",
                ),
            ),
            userData = userData,
        ),
        UserEpisode(
            episode = Episode(
                id = "1128643",
                name = "馳せサンズ",
                nameCN = "火速增员",
                type = EpisodeType.Feature,
                num = 3,
                airDate = Instant.parse("2022-10-22T00:00:00Z"),
                anime = anime[0],
                sourceUrl = mapOf(
                    "www.yhdmzz.com-1" to "https://www.yhdmzz.com/vp/22203-1-2.html",
                    "www.yhdmzz.com-2" to "https://www.yhdmzz.com/vp/22203-2-2.html",
                ),
            ),
            userData = userData,
        ),
    )
}
