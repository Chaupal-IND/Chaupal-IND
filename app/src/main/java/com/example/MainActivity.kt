package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.*
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.ChaupalTab
import com.example.viewmodel.ChaupalViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                ChaupalApp()
            }
        }
    }
}

@Composable
fun ChaupalApp(
    viewModel: ChaupalViewModel = viewModel()
) {
    val currentLanguage by viewModel.currentLanguage.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val userProfile by viewModel.userProfile.collectAsStateWithLifecycle()
    val friends by viewModel.friends.collectAsStateWithLifecycle()
    val feedPosts by viewModel.feedPosts.collectAsStateWithLifecycle()
    val connectedFriends by viewModel.connectedFriends.collectAsStateWithLifecycle()
    val pendingIncomingRequests by viewModel.pendingIncomingRequests.collectAsStateWithLifecycle()
    val mapSearchResults by viewModel.mapSearchResults.collectAsStateWithLifecycle()
    val commentsMap by viewModel.comments.collectAsStateWithLifecycle()
    val reelCommentsMap by viewModel.reelComments.collectAsStateWithLifecycle()
    val chatMessagesMap by viewModel.chatMessages.collectAsStateWithLifecycle()
    val reels by viewModel.reels.collectAsStateWithLifecycle()
    val filteredEvents by viewModel.filteredEvents.collectAsStateWithLifecycle()

    val quoteThemes = viewModel.quoteThemes
    val regionLocations = viewModel.regionLocations

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            // Show top bar unless in deep conversation view
            if (uiState.currentTab != ChaupalTab.MESSAGES || uiState.activeChatFriendId == null) {
                ChaupalTopBar(
                    currentLanguage = currentLanguage,
                    selectedRegion = uiState.selectedRegion,
                    pendingRequestsCount = pendingIncomingRequests.size,
                    onOpenRegionPicker = { viewModel.openLocalityPicker() },
                    onOpenLanguagePicker = { viewModel.openLanguagePicker() },
                    onOpenPendingRequests = { viewModel.selectTab(ChaupalTab.PROFILE) }
                )
            }
        },
        bottomBar = {
            // Show bottom navigation bar unless inside an active chat thread
            if (uiState.currentTab != ChaupalTab.MESSAGES || uiState.activeChatFriendId == null) {
                ChaupalBottomNav(
                    currentTab = uiState.currentTab,
                    currentLanguage = currentLanguage,
                    onTabSelected = { viewModel.selectTab(it) }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (uiState.currentTab) {
                ChaupalTab.FEED -> {
                    FeedScreen(
                        currentLanguage = currentLanguage,
                        preferredTranslationLanguage = uiState.preferredTranslationLanguage,
                        translatedPostIds = uiState.translatedPostIds,
                        posts = feedPosts,
                        friends = friends,
                        quoteThemes = quoteThemes,
                        onLikePost = { viewModel.togglePostLike(it) },
                        onOpenComments = { viewModel.openPostComments(it) },
                        onCreatePostClick = { viewModel.openCreatePostDialog() },
                        onOpenExploreClick = { viewModel.selectTab(ChaupalTab.MAP_SEARCH) },
                        onOpenEventsClick = { viewModel.selectTab(ChaupalTab.EVENTS) },
                        onTogglePostTranslation = { viewModel.togglePostTranslation(it) },
                        onSelectTranslationLanguage = { viewModel.setPreferredTranslationLanguage(it) }
                    )
                }

                ChaupalTab.EVENTS -> {
                    EventsScreen(
                        currentLanguage = currentLanguage,
                        selectedRegion = uiState.selectedRegion,
                        regionLocations = regionLocations,
                        events = filteredEvents,
                        selectedCategory = uiState.selectedEventCategory,
                        searchQuery = uiState.eventSearchQuery,
                        onSelectRegion = { viewModel.selectRegion(it) },
                        onCategoryChange = { viewModel.setEventCategory(it) },
                        onSearchQueryChange = { viewModel.setEventSearchQuery(it) },
                        onToggleGoing = { viewModel.toggleEventGoing(it) },
                        onToggleInterested = { viewModel.toggleEventInterested(it) },
                        onOpenCreateEventDialog = { viewModel.openCreateEventDialog() }
                    )
                }

                ChaupalTab.MAP_SEARCH -> {
                    MapSearchScreen(
                        currentLanguage = currentLanguage,
                        selectedRegion = uiState.selectedRegion,
                        regionLocations = regionLocations,
                        radiusKm = uiState.searchRadiusKm,
                        selectedInterest = uiState.selectedInterestFilter,
                        searchQuery = uiState.mapSearchQuery,
                        searchResults = mapSearchResults,
                        onSelectRegion = { viewModel.selectRegion(it) },
                        onRadiusChange = { viewModel.setRadiusKm(it) },
                        onInterestChange = { viewModel.setInterestFilter(it) },
                        onSearchQueryChange = { viewModel.setMapSearchQuery(it) },
                        onSendFriendRequest = { viewModel.sendFriendRequest(it) },
                        onAcceptFriendRequest = { viewModel.acceptFriendRequest(it) },
                        onOpenChat = { viewModel.openChat(it) }
                    )
                }

                ChaupalTab.MESSAGES -> {
                    MessagesScreen(
                        currentLanguage = currentLanguage,
                        connectedFriends = connectedFriends,
                        activeChatFriendId = uiState.activeChatFriendId,
                        chatMessagesMap = chatMessagesMap,
                        onOpenChat = { viewModel.openChat(it) },
                        onCloseChat = { viewModel.closeChat() },
                        onSendMessage = { friendId, text -> viewModel.sendMessage(friendId, text) },
                        onOpenExploreClick = { viewModel.selectTab(ChaupalTab.MAP_SEARCH) }
                    )
                }

                ChaupalTab.REELS -> {
                    ReelsScreen(
                        currentLanguage = currentLanguage,
                        reels = reels,
                        onToggleLike = { viewModel.toggleReelLike(it) },
                        onOpenComments = { viewModel.openReelComments(it) }
                    )
                }

                ChaupalTab.PROFILE -> {
                    ProfileScreen(
                        userProfile = userProfile,
                        currentLanguage = currentLanguage,
                        friends = friends,
                        onOpenEditProfile = { viewModel.openEditProfile() },
                        onOpenLanguagePicker = { viewModel.openLanguagePicker() },
                        onAcceptFriendRequest = { viewModel.acceptFriendRequest(it) },
                        onDeclineFriendRequest = { viewModel.declineFriendRequest(it) },
                        onOpenChat = { viewModel.openChat(it) },
                        onAddInterest = { viewModel.addInterest(it) },
                        onRemoveInterest = { viewModel.removeInterest(it) }
                    )
                }
            }
        }
    }

    // Modal dialogs & bottom sheets
    if (uiState.isLanguagePickerOpen) {
        LanguagePickerDialog(
            currentLanguage = currentLanguage,
            onLanguageSelected = { viewModel.setLanguage(it) },
            onDismiss = { viewModel.closeLanguagePicker() }
        )
    }

    if (uiState.isLocalityPickerOpen) {
        LocalityPickerDialog(
            regions = regionLocations,
            selectedRegion = uiState.selectedRegion,
            onRegionSelected = { viewModel.selectRegion(it) },
            onDismiss = { viewModel.closeLocalityPicker() }
        )
    }

    if (uiState.isCreatePostDialogOpen) {
        CreatePostDialog(
            currentLanguage = currentLanguage,
            quoteThemes = quoteThemes,
            defaultLocality = "${uiState.selectedRegion.popularLocalities.first()}, ${uiState.selectedRegion.name}",
            onDismiss = { viewModel.closeCreatePostDialog() },
            onSubmitPost = { type, caption, quoteText, quoteAuthor, quoteThemeId, localityTag, mediaRes, duration, tags ->
                viewModel.createPost(
                    type = type,
                    caption = caption,
                    quoteText = quoteText,
                    quoteAuthor = quoteAuthor,
                    quoteThemeId = quoteThemeId,
                    localityTag = localityTag,
                    mediaDrawableRes = mediaRes,
                    videoDurationSec = duration,
                    tags = tags
                )
            }
        )
    }

    if (uiState.isCreateEventDialogOpen) {
        CreateEventDialog(
            defaultLocality = "${uiState.selectedRegion.popularLocalities.first()}, ${uiState.selectedRegion.name}",
            onDismiss = { viewModel.closeCreateEventDialog() },
            onSubmitEvent = { title, locality, category, dateFormatted, locationVenue, description, feeLabel ->
                viewModel.createLocalEvent(
                    title = title,
                    locality = locality,
                    category = category,
                    dateFormatted = dateFormatted,
                    locationVenue = locationVenue,
                    description = description,
                    feeLabel = feeLabel
                )
            }
        )
    }

    if (uiState.activeCommentsPostId != null) {
        val postId = uiState.activeCommentsPostId!!
        val postComments = commentsMap[postId] ?: emptyList()
        CommentsBottomSheet(
            comments = postComments,
            preferredTranslationLanguage = uiState.preferredTranslationLanguage,
            onDismiss = { viewModel.closePostComments() },
            onAddComment = { text -> viewModel.addPostComment(postId, text) }
        )
    }

    if (uiState.activeCommentsReelId != null) {
        val reelId = uiState.activeCommentsReelId!!
        val reelComments = reelCommentsMap[reelId] ?: emptyList()
        CommentsBottomSheet(
            comments = reelComments,
            preferredTranslationLanguage = uiState.preferredTranslationLanguage,
            onDismiss = { viewModel.closeReelComments() },
            onAddComment = { text -> viewModel.addReelComment(reelId, text) }
        )
    }

    if (uiState.isEditProfileOpen) {
        EditProfileDialog(
            userProfile = userProfile,
            onDismiss = { viewModel.closeEditProfile() },
            onSave = { name, bio, locality, hometown, interests, lang ->
                viewModel.updateProfile(name, bio, locality, hometown, interests, lang)
            }
        )
    }
}
