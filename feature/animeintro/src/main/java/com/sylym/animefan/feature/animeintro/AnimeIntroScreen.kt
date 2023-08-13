package com.sylym.animefan.feature.animeintro

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.samples.apps.nowinandroid.core.designsystem.component.DynamicAsyncImage
import com.google.samples.apps.nowinandroid.core.designsystem.component.NiaBackground
import com.google.samples.apps.nowinandroid.core.designsystem.component.NiaFilterChip
import com.google.samples.apps.nowinandroid.core.designsystem.component.NiaLoadingWheel
import com.google.samples.apps.nowinandroid.core.designsystem.component.scrollbar.DraggableScrollbar
import com.google.samples.apps.nowinandroid.core.designsystem.component.scrollbar.rememberDraggableScroller
import com.google.samples.apps.nowinandroid.core.designsystem.component.scrollbar.scrollbarState
import com.google.samples.apps.nowinandroid.core.designsystem.icon.NiaIcons
import com.google.samples.apps.nowinandroid.core.designsystem.theme.NiaTheme
import com.google.samples.apps.nowinandroid.core.model.data.FollowableTopic
import com.google.samples.apps.nowinandroid.core.model.data.UserNewsResource
import com.sylym.animefan.core.ui.DevicePreviews
import com.sylym.animefan.core.ui.UserNewsResourcePreviewParameterProvider
import com.google.samples.apps.nowinandroid.core.ui.userNewsResourceCardItems
import com.google.samples.apps.nowinandroid.feature.topic.NewsUiState
import com.google.samples.apps.nowinandroid.feature.topic.NewsUiState.Loading
import com.google.samples.apps.nowinandroid.feature.topic.R.string
import com.google.samples.apps.nowinandroid.feature.topic.TopicUiState
import com.google.samples.apps.nowinandroid.feature.topic.TopicUiState.Error
import com.google.samples.apps.nowinandroid.feature.topic.TopicUiState.Success
import com.google.samples.apps.nowinandroid.feature.topic.TopicViewModel

@Composable
internal fun TopicRoute(
    onBackClick: () -> Unit,
    onTopicClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TopicViewModel = hiltViewModel(),
) {
    val topicUiState: TopicUiState by viewModel.topicUiState.collectAsStateWithLifecycle()
    val newsUiState: NewsUiState by viewModel.newUiState.collectAsStateWithLifecycle()

    TopicScreen(
        topicUiState = topicUiState,
        newsUiState = newsUiState,
        modifier = modifier,
        onBackClick = onBackClick,
        onFollowClick = viewModel::followTopicToggle,
        onBookmarkChanged = viewModel::bookmarkNews,
        onNewsResourceViewed = { viewModel.setNewsResourceViewed(it, true) },
        onTopicClick = onTopicClick,
    )
}

@VisibleForTesting
@Composable
internal fun TopicScreen(
    topicUiState: TopicUiState,
    newsUiState: NewsUiState,
    onBackClick: () -> Unit,
    onFollowClick: (Boolean) -> Unit,
    onTopicClick: (String) -> Unit,
    onBookmarkChanged: (String, Boolean) -> Unit,
    onNewsResourceViewed: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = rememberLazyListState()
    Box(
        modifier = modifier,
    ) {
        LazyColumn(
            state = state,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Spacer(Modifier.windowInsetsTopHeight(WindowInsets.safeDrawing))
            }
            when (topicUiState) {
                TopicUiState.Loading -> item {
                    NiaLoadingWheel(
                        modifier = modifier,
                        contentDesc = stringResource(id = string.topic_loading),
                    )
                }

                Error -> TODO()
                is Success -> {
                    item {
                        TopicToolbar(
                            onBackClick = onBackClick,
                            onFollowClick = onFollowClick,
                            uiState = topicUiState.followableTopic,
                        )
                    }
                    TopicBody(
                        name = topicUiState.followableTopic.topic.name,
                        description = topicUiState.followableTopic.topic.longDescription,
                        news = newsUiState,
                        imageUrl = topicUiState.followableTopic.topic.imageUrl,
                        onBookmarkChanged = onBookmarkChanged,
                        onNewsResourceViewed = onNewsResourceViewed,
                        onTopicClick = onTopicClick,
                    )
                }
            }
            item {
                Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
            }
        }
        val itemsAvailable = topicItemsSize(topicUiState, newsUiState)
        val scrollbarState = state.scrollbarState(
            itemsAvailable = itemsAvailable,
        )
        state.DraggableScrollbar(
            modifier = Modifier
                .fillMaxHeight()
                .windowInsetsPadding(WindowInsets.systemBars)
                .padding(horizontal = 2.dp)
                .align(Alignment.CenterEnd),
            state = scrollbarState,
            orientation = Orientation.Vertical,
            onThumbMoved = state.rememberDraggableScroller(
                itemsAvailable = itemsAvailable,
            ),
        )
    }
}

private fun topicItemsSize(
    topicUiState: TopicUiState,
    newsUiState: NewsUiState,
) = when (topicUiState) {
    Error -> 0 // Nothing
    TopicUiState.Loading -> 1 // Loading bar
    is Success -> when (newsUiState) {
        NewsUiState.Error -> 0 // Nothing
        Loading -> 1 // Loading bar
        is NewsUiState.Success -> 2 + newsUiState.news.size // Toolbar, header
    }
}

private fun LazyListScope.TopicBody(
    name: String,
    description: String,
    news: NewsUiState,
    imageUrl: String,
    onBookmarkChanged: (String, Boolean) -> Unit,
    onNewsResourceViewed: (String) -> Unit,
    onTopicClick: (String) -> Unit,
) {
    // TODO: Show icon if available
    item {
        TopicHeader(name, description, imageUrl)
    }

    userNewsResourceCards(news, onBookmarkChanged, onNewsResourceViewed, onTopicClick)
}

@Composable
private fun TopicHeader(name: String, description: String, imageUrl: String) {
    Column(
        modifier = Modifier.padding(horizontal = 24.dp),
    ) {
        DynamicAsyncImage(
            imageUrl = imageUrl,
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(216.dp)
                .padding(bottom = 12.dp),
        )
        Text(name, style = MaterialTheme.typography.displayMedium)
        if (description.isNotEmpty()) {
            Text(
                description,
                modifier = Modifier.padding(top = 24.dp),
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

// TODO: Could/should this be replaced with [LazyGridScope.newsFeed]?
private fun LazyListScope.userNewsResourceCards(
    news: NewsUiState,
    onBookmarkChanged: (String, Boolean) -> Unit,
    onNewsResourceViewed: (String) -> Unit,
    onTopicClick: (String) -> Unit,
) {
    when (news) {
        is NewsUiState.Success -> {
            userNewsResourceCardItems(
                items = news.news,
                onToggleBookmark = { onBookmarkChanged(it.id, !it.isSaved) },
                onNewsResourceViewed = onNewsResourceViewed,
                onTopicClick = onTopicClick,
                itemModifier = Modifier.padding(24.dp),
            )
        }

        is Loading -> item {
            NiaLoadingWheel(contentDesc = "Loading news") // TODO
        }

        else -> item {
            Text("Error") // TODO
        }
    }
}

@Preview
@Composable
private fun TopicBodyPreview() {
    NiaTheme {
        LazyColumn {
            TopicBody(
                name = "Jetpack Compose",
                description = "Lorem ipsum maximum",
                news = NewsUiState.Success(emptyList()),
                imageUrl = "",
                onBookmarkChanged = { _, _ -> },
                onNewsResourceViewed = {},
                onTopicClick = {},
            )
        }
    }
}

@Composable
private fun TopicToolbar(
    uiState: FollowableTopic,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onFollowClick: (Boolean) -> Unit = {},
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp),
    ) {
        IconButton(onClick = { onBackClick() }) {
            Icon(
                imageVector = NiaIcons.ArrowBack,
                contentDescription = stringResource(
                    id = com.google.samples.apps.nowinandroid.core.ui.R.string.back,
                ),
            )
        }
        val selected = uiState.isFollowed
        NiaFilterChip(
            selected = selected,
            onSelectedChange = onFollowClick,
            modifier = Modifier.padding(end = 24.dp),
        ) {
            if (selected) {
                Text("FOLLOWING")
            } else {
                Text("NOT FOLLOWING")
            }
        }
    }
}

@DevicePreviews
@Composable
fun TopicScreenPopulated(
    @PreviewParameter(UserNewsResourcePreviewParameterProvider::class)
    userNewsResources: List<UserNewsResource>,
) {
    NiaTheme {
        NiaBackground {
            TopicScreen(
                topicUiState = Success(userNewsResources[0].followableTopics[0]),
                newsUiState = NewsUiState.Success(userNewsResources),
                onBackClick = {},
                onFollowClick = {},
                onBookmarkChanged = { _, _ -> },
                onNewsResourceViewed = {},
                onTopicClick = {},
            )
        }
    }
}

@DevicePreviews
@Composable
fun TopicScreenLoading() {
    NiaTheme {
        NiaBackground {
            TopicScreen(
                topicUiState = TopicUiState.Loading,
                newsUiState = Loading,
                onBackClick = {},
                onFollowClick = {},
                onBookmarkChanged = { _, _ -> },
                onNewsResourceViewed = {},
                onTopicClick = {},
            )
        }
    }
}
