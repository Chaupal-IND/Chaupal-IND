package com.example.model

import androidx.compose.ui.graphics.Color

enum class Language(
    val code: String,
    val nativeName: String,
    val englishName: String,
    val greeting: String
) {
    ENGLISH("en", "English", "English", "Namaste 🙏"),
    HINDI("hi", "हिन्दी", "Hindi", "नमस्ते 🙏"),
    BENGALI("bn", "বাংলা", "Bengali", "নমস্কার 🙏"),
    TELUGU("te", "తెలుగు", "Telugu", "నమస్కారం 🙏"),
    TAMIL("ta", "தமிழ்", "Tamil", "வணக்கம் 🙏"),
    MARATHI("mr", "मराठी", "Marathi", "नमस्कार 🙏"),
    KANNADA("kn", "ಕನ್ನಡ", "Kannada", "ನಮಸ್ಕಾರ 🙏"),
    GUJARATI("gu", "ગુજરાતી", "Gujarati", "કેમ છો 🙏"),
    MALAYALAM("ml", "മലയാളം", "Malayalam", "നമസ്കാരം 🙏"),
    PUNJABI("pa", "ਪੰਜਾਬੀ", "Punjabi", "ਸਤਿ ਸ੍ਰੀ ਅਕਾਲ ਜੀ 🙏")
}

enum class PostType {
    PHOTO,
    VIDEO,
    QUOTE
}

enum class FriendStatus {
    CONNECTED,
    PENDING_INCOMING,
    PENDING_SENT,
    SUGGESTED
}

data class QuoteTheme(
    val id: String,
    val name: String,
    val backgroundBrushColors: List<Color>,
    val textColor: Color,
    val accentColor: Color
)

data class UserProfile(
    val id: String = "my_user_id",
    val name: String = "Aarav Sharma",
    val handle: String = "aarav_local",
    val bio: String = "Architect by day, street food enthusiast & heritage walk volunteer on weekends. Loving life in Bangalore!",
    val locality: String = "Indiranagar, Bengaluru",
    val hometown: String = "Varanasi, UP",
    val state: String = "Karnataka",
    val phoneVerified: Boolean = true,
    val verifiedBadge: String = "Verified Local Resident 🇮🇳",
    val interests: List<String> = listOf("Heritage Walks", "South Indian Filter Coffee", "Local Cricket", "Kannada Literature", "Tech Startups"),
    val primaryLanguage: Language = Language.HINDI,
    val postsCount: Int = 18,
    val friendsCount: Int = 12,
    val communityKarma: Int = 340
)

data class Friend(
    val id: String,
    val name: String,
    val handle: String,
    val locality: String,
    val state: String,
    val distanceKm: Double,
    val bio: String,
    val sharedInterests: List<String>,
    val primaryLanguage: Language,
    val status: FriendStatus,
    val avatarColor: Color = Color(0xFFD84315),
    val initials: String = name.take(2).uppercase(),
    val isOnline: Boolean = false,
    val mutualFriendsCount: Int = 3,
    val incomingRequestMessage: String = ""
)

data class Post(
    val id: String,
    val authorId: String,
    val authorName: String,
    val authorHandle: String,
    val authorLocality: String,
    val authorInitials: String,
    val authorAvatarColor: Color = Color(0xFFD84315),
    val type: PostType,
    val caption: String = "",
    val mediaDrawableRes: Int? = null,
    val videoDurationSec: Int = 0,
    val quoteText: String = "",
    val quoteAuthor: String = "",
    val quoteThemeId: String = "terracotta",
    val timestampFormatted: String,
    val likesCount: Int,
    val isLiked: Boolean = false,
    val commentsCount: Int,
    val localityTag: String,
    val tags: List<String> = emptyList(),
    val isFriendsOnly: Boolean = true,
    val translations: Map<String, String> = emptyMap()
)

data class Comment(
    val id: String,
    val authorName: String,
    val authorHandle: String,
    val text: String,
    val locality: String,
    val timestampFormatted: String,
    val translations: Map<String, String> = emptyMap()
)

data class LocalEvent(
    val id: String,
    val title: String,
    val organizerName: String,
    val organizerHandle: String,
    val organizerAvatarColor: Color = Color(0xFFD84315),
    val regionId: String,
    val locality: String,
    val category: String,
    val dateFormatted: String,
    val locationVenue: String,
    val description: String,
    val imageDrawableRes: Int? = null,
    val rsvpGoingCount: Int,
    val rsvpInterestedCount: Int,
    val isUserGoing: Boolean = false,
    val isUserInterested: Boolean = false,
    val feeLabel: String = "Free Entry • Open to All"
)

data class ChatMessage(
    val id: String,
    val senderId: String,
    val text: String,
    val isVoiceNote: Boolean = false,
    val voiceDurationSec: Int = 0,
    val timestampFormatted: String,
    val isFromMe: Boolean,
    val isDelivered: Boolean = true,
    val isRead: Boolean = true
)

data class Reel(
    val id: String,
    val creatorName: String,
    val creatorHandle: String,
    val creatorInitials: String,
    val locality: String,
    val title: String,
    val description: String,
    val thumbnailRes: Int? = null,
    val audioTrackName: String,
    val likesCount: Int,
    val isLiked: Boolean = false,
    val commentsCount: Int,
    val shareCount: Int,
    val tags: List<String>
)

data class RegionLocation(
    val id: String,
    val name: String,
    val state: String,
    val pincode: String,
    val lat: Double,
    val lng: Double,
    val popularLocalities: List<String>,
    val activeNeighborsCount: Int
)
