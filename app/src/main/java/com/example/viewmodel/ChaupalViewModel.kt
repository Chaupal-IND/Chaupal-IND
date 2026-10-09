package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.ChaupalRepository
import com.example.model.*
import kotlinx.coroutines.flow.*

enum class ChaupalTab {
    FEED,
    EVENTS,
    MAP_SEARCH,
    MESSAGES,
    REELS,
    PROFILE
}

data class ChaupalUiState(
    val currentTab: ChaupalTab = ChaupalTab.FEED,
    val activeChatFriendId: String? = null,
    val selectedRegion: RegionLocation,
    val searchRadiusKm: Int = 5,
    val selectedInterestFilter: String = "All",
    val mapSearchQuery: String = "",
    val activeCommentsPostId: String? = null,
    val activeCommentsReelId: String? = null,
    val isCreatePostDialogOpen: Boolean = false,
    val isCreateEventDialogOpen: Boolean = false,
    val isLanguagePickerOpen: Boolean = false,
    val isEditProfileOpen: Boolean = false,
    val isLocalityPickerOpen: Boolean = false,
    val customNoteFriendId: String? = null,
    val selectedEventCategory: String = "All",
    val eventSearchQuery: String = "",
    val translatedPostIds: Set<String> = emptySet(),
    val translatedCommentIds: Set<String> = emptySet(),
    val preferredTranslationLanguage: Language = Language.HINDI
)

class ChaupalViewModel(
    private val repository: ChaupalRepository = ChaupalRepository()
) : ViewModel() {

    val currentLanguage: StateFlow<Language> = repository.currentLanguage
    val userProfile: StateFlow<UserProfile> = repository.userProfile
    val friends: StateFlow<List<Friend>> = repository.friends
    val posts: StateFlow<List<Post>> = repository.posts
    val comments: StateFlow<Map<String, List<Comment>>> = repository.comments
    val chatMessages: StateFlow<Map<String, List<ChatMessage>>> = repository.chatMessages
    val reels: StateFlow<List<Reel>> = repository.reels
    val reelComments: StateFlow<Map<String, List<Comment>>> = repository.reelComments
    val events: StateFlow<List<LocalEvent>> = repository.events
    val quoteThemes = repository.quoteThemes
    val regionLocations = repository.regionLocations

    private val _uiState = MutableStateFlow(
        ChaupalUiState(selectedRegion = repository.regionLocations.first())
    )
    val uiState: StateFlow<ChaupalUiState> = _uiState.asStateFlow()

    // Feed is strictly for accepted friends + user's own posts
    val feedPosts: StateFlow<List<Post>> = combine(posts, friends, userProfile) { postList, friendList, user ->
        val connectedFriendIds = friendList
            .filter { it.status == FriendStatus.CONNECTED }
            .map { it.id }
            .toSet()

        postList.filter { post ->
            post.authorId == user.id || connectedFriendIds.contains(post.authorId)
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // Connected friends for Messages tab
    val connectedFriends: StateFlow<List<Friend>> = friends.map { list ->
        list.filter { it.status == FriendStatus.CONNECTED }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // Pending incoming requests for Profile tab
    val pendingIncomingRequests: StateFlow<List<Friend>> = friends.map { list ->
        list.filter { it.status == FriendStatus.PENDING_INCOMING }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // Filtered people for Region Map Search
    val mapSearchResults: StateFlow<List<Friend>> = combine(
        friends,
        _uiState
    ) { friendList, state ->
        val query = state.mapSearchQuery.trim().lowercase()
        val radius = state.searchRadiusKm
        val interest = state.selectedInterestFilter
        val selectedRegionName = state.selectedRegion.name.lowercase()

        friendList.filter { friend ->
            val matchesRadius = friend.distanceKm <= radius
            val matchesInterest = interest == "All" ||
                friend.sharedInterests.any { it.equals(interest, ignoreCase = true) }

            val matchesQuery = query.isEmpty() ||
                friend.name.lowercase().contains(query) ||
                friend.locality.lowercase().contains(query) ||
                friend.bio.lowercase().contains(query) ||
                friend.sharedInterests.any { it.lowercase().contains(query) }

            val matchesRegion = friend.locality.lowercase().contains(selectedRegionName) ||
                friend.state.equals(state.selectedRegion.state, ignoreCase = true) ||
                friend.distanceKm <= radius

            matchesRadius && matchesInterest && matchesQuery && matchesRegion
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // Filtered Events for Local Events & Happenings
    val filteredEvents: StateFlow<List<LocalEvent>> = combine(
        events,
        _uiState
    ) { eventList, state ->
        val query = state.eventSearchQuery.trim().lowercase()
        val category = state.selectedEventCategory
        val region = state.selectedRegion

        eventList.filter { event ->
            val matchesRegion = event.regionId == region.id ||
                event.locality.contains(region.name, ignoreCase = true) ||
                region.popularLocalities.any { event.locality.contains(it, ignoreCase = true) }

            val matchesCategory = category == "All" ||
                event.category.equals(category, ignoreCase = true)

            val matchesQuery = query.isEmpty() ||
                event.title.lowercase().contains(query) ||
                event.description.lowercase().contains(query) ||
                event.locationVenue.lowercase().contains(query) ||
                event.locality.lowercase().contains(query) ||
                event.category.lowercase().contains(query)

            matchesCategory && matchesQuery && (matchesRegion || query.isNotEmpty())
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun selectTab(tab: ChaupalTab) {
        _uiState.update { it.copy(currentTab = tab, activeChatFriendId = null) }
    }

    fun openChat(friendId: String) {
        _uiState.update { it.copy(activeChatFriendId = friendId, currentTab = ChaupalTab.MESSAGES) }
    }

    fun closeChat() {
        _uiState.update { it.copy(activeChatFriendId = null) }
    }

    fun selectRegion(region: RegionLocation) {
        _uiState.update { it.copy(selectedRegion = region, isLocalityPickerOpen = false) }
    }

    fun setRadiusKm(radius: Int) {
        _uiState.update { it.copy(searchRadiusKm = radius) }
    }

    fun setInterestFilter(interest: String) {
        _uiState.update { it.copy(selectedInterestFilter = interest) }
    }

    fun setMapSearchQuery(query: String) {
        _uiState.update { it.copy(mapSearchQuery = query) }
    }

    fun setLanguage(language: Language) {
        repository.setLanguage(language)
        _uiState.update { it.copy(isLanguagePickerOpen = false, preferredTranslationLanguage = language) }
    }

    fun openCreatePostDialog() {
        _uiState.update { it.copy(isCreatePostDialogOpen = true) }
    }

    fun closeCreatePostDialog() {
        _uiState.update { it.copy(isCreatePostDialogOpen = false) }
    }

    fun openCreateEventDialog() {
        _uiState.update { it.copy(isCreateEventDialogOpen = true) }
    }

    fun closeCreateEventDialog() {
        _uiState.update { it.copy(isCreateEventDialogOpen = false) }
    }

    fun setEventCategory(category: String) {
        _uiState.update { it.copy(selectedEventCategory = category) }
    }

    fun setEventSearchQuery(query: String) {
        _uiState.update { it.copy(eventSearchQuery = query) }
    }

    fun toggleEventGoing(eventId: String) {
        repository.toggleEventGoing(eventId)
    }

    fun toggleEventInterested(eventId: String) {
        repository.toggleEventInterested(eventId)
    }

    fun createLocalEvent(
        title: String,
        locality: String,
        category: String,
        dateFormatted: String,
        locationVenue: String,
        description: String,
        feeLabel: String = "Free Entry • Open to All",
        imageDrawableRes: Int? = null
    ) {
        repository.createLocalEvent(
            title = title,
            locality = locality,
            regionId = _uiState.value.selectedRegion.id,
            category = category,
            dateFormatted = dateFormatted,
            locationVenue = locationVenue,
            description = description,
            imageDrawableRes = imageDrawableRes,
            feeLabel = feeLabel
        )
        _uiState.update { it.copy(isCreateEventDialogOpen = false) }
    }

    fun togglePostTranslation(postId: String) {
        _uiState.update { state ->
            val newSet = if (state.translatedPostIds.contains(postId)) {
                state.translatedPostIds - postId
            } else {
                state.translatedPostIds + postId
            }
            state.copy(translatedPostIds = newSet)
        }
    }

    fun toggleCommentTranslation(commentId: String) {
        _uiState.update { state ->
            val newSet = if (state.translatedCommentIds.contains(commentId)) {
                state.translatedCommentIds - commentId
            } else {
                state.translatedCommentIds + commentId
            }
            state.copy(translatedCommentIds = newSet)
        }
    }

    fun setPreferredTranslationLanguage(language: Language) {
        _uiState.update { it.copy(preferredTranslationLanguage = language) }
    }

    fun openLanguagePicker() {
        _uiState.update { it.copy(isLanguagePickerOpen = true) }
    }

    fun closeLanguagePicker() {
        _uiState.update { it.copy(isLanguagePickerOpen = false) }
    }

    fun openLocalityPicker() {
        _uiState.update { it.copy(isLocalityPickerOpen = true) }
    }

    fun closeLocalityPicker() {
        _uiState.update { it.copy(isLocalityPickerOpen = false) }
    }

    fun openEditProfile() {
        _uiState.update { it.copy(isEditProfileOpen = true) }
    }

    fun closeEditProfile() {
        _uiState.update { it.copy(isEditProfileOpen = false) }
    }

    fun openPostComments(postId: String) {
        _uiState.update { it.copy(activeCommentsPostId = postId) }
    }

    fun closePostComments() {
        _uiState.update { it.copy(activeCommentsPostId = null) }
    }

    fun openReelComments(reelId: String) {
        _uiState.update { it.copy(activeCommentsReelId = reelId) }
    }

    fun closeReelComments() {
        _uiState.update { it.copy(activeCommentsReelId = null) }
    }

    fun sendFriendRequest(friendId: String) {
        repository.sendFriendRequest(friendId)
    }

    fun acceptFriendRequest(friendId: String) {
        repository.acceptFriendRequest(friendId)
    }

    fun declineFriendRequest(friendId: String) {
        repository.declineFriendRequest(friendId)
    }

    fun togglePostLike(postId: String) {
        repository.togglePostLike(postId)
    }

    fun addPostComment(postId: String, text: String) {
        repository.addPostComment(postId, text)
    }

    fun toggleReelLike(reelId: String) {
        repository.toggleReelLike(reelId)
    }

    fun addReelComment(reelId: String, text: String) {
        repository.addReelComment(reelId, text)
    }

    fun sendMessage(friendId: String, text: String) {
        repository.sendMessage(friendId, text)
    }

    fun createPost(
        type: PostType,
        caption: String,
        quoteText: String = "",
        quoteAuthor: String = "",
        quoteThemeId: String = "terracotta",
        localityTag: String = _uiState.value.selectedRegion.name,
        mediaDrawableRes: Int? = null,
        videoDurationSec: Int = 0,
        tags: List<String> = emptyList()
    ) {
        repository.createPost(
            type = type,
            caption = caption,
            quoteText = quoteText,
            quoteAuthor = quoteAuthor,
            quoteThemeId = quoteThemeId,
            localityTag = localityTag,
            mediaDrawableRes = mediaDrawableRes,
            videoDurationSec = videoDurationSec,
            tags = tags
        )
        _uiState.update { it.copy(isCreatePostDialogOpen = false) }
    }

    fun updateProfile(
        name: String,
        bio: String,
        locality: String,
        hometown: String,
        interests: List<String>,
        primaryLanguage: Language
    ) {
        repository.updateProfile(name, bio, locality, hometown, interests, primaryLanguage)
        _uiState.update { it.copy(isEditProfileOpen = false) }
    }

    fun addInterest(interest: String) {
        repository.addInterest(interest)
    }

    fun removeInterest(interest: String) {
        repository.removeInterest(interest)
    }
}
