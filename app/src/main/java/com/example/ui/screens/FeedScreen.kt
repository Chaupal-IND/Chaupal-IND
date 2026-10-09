package com.example.ui.screens

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.localization.AppStrings
import com.example.localization.TranslationService
import com.example.model.*
import com.example.ui.theme.PeacockTeal
import com.example.ui.theme.SaffronAccent
import com.example.ui.theme.TerracottaPrimary

@Composable
fun FeedScreen(
    currentLanguage: Language,
    preferredTranslationLanguage: Language,
    translatedPostIds: Set<String>,
    posts: List<Post>,
    friends: List<Friend>,
    quoteThemes: List<QuoteTheme>,
    onLikePost: (String) -> Unit,
    onOpenComments: (String) -> Unit,
    onCreatePostClick: () -> Unit,
    onOpenExploreClick: () -> Unit,
    onOpenEventsClick: () -> Unit,
    onTogglePostTranslation: (String) -> Unit,
    onSelectTranslationLanguage: (Language) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var isTranslationLangMenuOpen by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(top = 10.dp, bottom = 90.dp)
        ) {
            // Feed / Events Quick Switcher & Translation Preference Bar
            item {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.surface,
                    tonalElevation = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Section Switcher
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(MaterialTheme.colorScheme.surfaceVariant)
                                    .padding(3.dp),
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = "👥 Mitra Feed",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TerracottaPrimary
                                    )
                                }

                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color.Transparent,
                                    modifier = Modifier
                                        .clickable(onClick = onOpenEventsClick)
                                        .padding(horizontal = 10.dp, vertical = 6.dp)
                                        .testTag("feed_switch_to_events")
                                ) {
                                    Text(
                                        text = "🎪 Local Events & Melas",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // In-App Translation Setting Bar
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Translate,
                                    contentDescription = "Translate",
                                    tint = TerracottaPrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Translate feed to:",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Box {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable { isTranslationLangMenuOpen = true }
                                        .testTag("feed_translation_lang_picker")
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    ) {
                                        Text(
                                            text = "${preferredTranslationLanguage.nativeName} ▾",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = TerracottaPrimary
                                        )
                                    }
                                }

                                DropdownMenu(
                                    expanded = isTranslationLangMenuOpen,
                                    onDismissRequest = { isTranslationLangMenuOpen = false }
                                ) {
                                    Language.values().forEach { lang ->
                                        DropdownMenuItem(
                                            text = {
                                                Text(
                                                    text = "${lang.nativeName} (${lang.englishName})",
                                                    fontWeight = if (lang == preferredTranslationLanguage) FontWeight.Bold else FontWeight.Normal,
                                                    color = if (lang == preferredTranslationLanguage) TerracottaPrimary else MaterialTheme.colorScheme.onSurface
                                                )
                                            },
                                            onClick = {
                                                onSelectTranslationLanguage(lang)
                                                isTranslationLangMenuOpen = false
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Local Community Friends Story/Circle Bar
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Local Neighbors • Active Nearby",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(friends.filter { it.status == FriendStatus.CONNECTED }) { friend ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .width(72.dp)
                                    .testTag("friend_story_${friend.id}")
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(56.dp)
                                        .border(2.dp, TerracottaPrimary, CircleShape)
                                        .padding(3.dp)
                                        .clip(CircleShape)
                                        .background(friend.avatarColor),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = friend.initials,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                    if (friend.isOnline) {
                                        Box(
                                            modifier = Modifier
                                                .size(12.dp)
                                                .clip(CircleShape)
                                                .background(Color(0xFF4CAF50))
                                                .border(1.5.dp, Color.White, CircleShape)
                                                .align(Alignment.BottomEnd)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = friend.name.substringBefore(" "),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = "${friend.distanceKm} km",
                                    fontSize = 10.sp,
                                    color = TerracottaPrimary
                                )
                            }
                        }
                    }
                }
            }

            // Friends-Only Community Guarantee Banner
            item {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = TerracottaPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = AppStrings.friendsOnlyBanner(currentLanguage),
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            if (posts.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.PeopleOutline,
                                contentDescription = null,
                                tint = TerracottaPrimary,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Your Chaupal circle is quiet",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Connect with neighbors in your region or share your first local photo, video, or quote!",
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = onOpenExploreClick,
                                colors = ButtonDefaults.buttonColors(containerColor = TerracottaPrimary)
                            ) {
                                Text("Find Local Neighbors")
                            }
                        }
                    }
                }
            } else {
                items(posts, key = { it.id }) { post ->
                    val isTranslated = translatedPostIds.contains(post.id)
                    PostCard(
                        post = post,
                        isTranslated = isTranslated,
                        preferredTranslationLanguage = preferredTranslationLanguage,
                        quoteThemes = quoteThemes,
                        onLike = { onLikePost(post.id) },
                        onComment = { onOpenComments(post.id) },
                        onToggleTranslation = { onTogglePostTranslation(post.id) },
                        onShare = {
                            val shareIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    "Check out this local update from ${post.authorName} on Chaupal: \n${post.caption.ifEmpty { post.quoteText }} #ChaupalLocal"
                                )
                                type = "text/plain"
                            }
                            context.startActivity(Intent.createChooser(shareIntent, "Share with neighbors"))
                        }
                    )
                }
            }
        }

        // Floating Action Button: Create Post
        FloatingActionButton(
            onClick = onCreatePostClick,
            containerColor = TerracottaPrimary,
            contentColor = Color.White,
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp, bottom = 100.dp)
                .testTag("fab_create_post")
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = AppStrings.createPost(currentLanguage),
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
fun PostCard(
    post: Post,
    isTranslated: Boolean,
    preferredTranslationLanguage: Language,
    quoteThemes: List<QuoteTheme>,
    onLike: () -> Unit,
    onComment: () -> Unit,
    onToggleTranslation: () -> Unit,
    onShare: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Dynamic text selection based on translation state
    val displayCaption = if (isTranslated) {
        TranslationService.getTranslatedCaption(post, preferredTranslationLanguage)
    } else {
        post.caption
    }

    val displayQuote = if (isTranslated) {
        TranslationService.getTranslatedQuote(post, preferredTranslationLanguage)
    } else {
        post.quoteText
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("post_card_${post.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Post Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Author Avatar
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(post.authorAvatarColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = post.authorInitials,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = post.authorName,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Verified Local",
                            tint = PeacockTeal,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = post.localityTag,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = TerracottaPrimary
                        )
                        Text(
                            text = " • ${post.timestampFormatted}",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Type Badge
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Text(
                        text = when (post.type) {
                            PostType.QUOTE -> "सुविचार Quote"
                            PostType.PHOTO -> "Photo"
                            PostType.VIDEO -> "Video"
                        },
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            // In-App Translation Action Banner on Card
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = if (isTranslated) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .clickable(onClick = onToggleTranslation)
                        .testTag("translate_toggle_btn_${post.id}")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Translate,
                            contentDescription = null,
                            tint = TerracottaPrimary,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (isTranslated) "✓ Translated (${preferredTranslationLanguage.nativeName}) • See Original" else "Translate to ${preferredTranslationLanguage.nativeName}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if (isTranslated) TerracottaPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Post Content
            when (post.type) {
                PostType.QUOTE -> {
                    val theme = quoteThemes.find { it.id == post.quoteThemeId } ?: quoteThemes.first()
                    if (displayCaption.isNotBlank()) {
                        Text(
                            text = displayCaption,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(Brush.linearGradient(theme.backgroundBrushColors))
                            .padding(22.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.FormatQuote,
                                contentDescription = null,
                                tint = theme.accentColor,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = displayQuote,
                                color = theme.textColor,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontStyle = FontStyle.Italic,
                                textAlign = TextAlign.Center,
                                lineHeight = 24.sp
                            )
                            if (post.quoteAuthor.isNotBlank()) {
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = post.quoteAuthor,
                                    color = theme.accentColor,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }

                PostType.PHOTO -> {
                    if (displayCaption.isNotBlank()) {
                        Text(
                            text = displayCaption,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                        )
                    }
                    if (post.mediaDrawableRes != null) {
                        Image(
                            painter = painterResource(id = post.mediaDrawableRes),
                            contentDescription = post.caption,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp)
                        )
                    }
                }

                PostType.VIDEO -> {
                    var isPlaying by remember { mutableStateOf(false) }
                    var isMuted by remember { mutableStateOf(false) }

                    if (displayCaption.isNotBlank()) {
                        Text(
                            text = displayCaption,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(230.dp)
                            .clip(RoundedCornerShape(8.dp))
                    ) {
                        if (post.mediaDrawableRes != null) {
                            Image(
                                painter = painterResource(id = post.mediaDrawableRes),
                                contentDescription = post.caption,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Black)
                            )
                        }

                        // Play/Pause overlay
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Black.copy(alpha = if (isPlaying) 0.15f else 0.45f))
                                .clickable { isPlaying = !isPlaying },
                            contentAlignment = Alignment.Center
                        ) {
                            if (!isPlaying) {
                                Box(
                                    modifier = Modifier
                                        .size(54.dp)
                                        .clip(CircleShape)
                                        .background(Color.Black.copy(alpha = 0.6f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PlayArrow,
                                        contentDescription = "Play Video",
                                        tint = Color.White,
                                        modifier = Modifier.size(36.dp)
                                    )
                                }
                            }
                        }

                        // Video status indicators
                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = Color.Black.copy(alpha = 0.7f)
                            ) {
                                Text(
                                    text = if (isPlaying) "Playing • ${post.videoDurationSec}s" else "${post.videoDurationSec}s",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(6.dp))
                            IconButton(
                                onClick = { isMuted = !isMuted },
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(Color.Black.copy(alpha = 0.7f))
                            ) {
                                Icon(
                                    imageVector = if (isMuted) Icons.Default.VolumeOff else Icons.Default.VolumeUp,
                                    contentDescription = "Toggle Audio",
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Tags row
            if (post.tags.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    post.tags.forEach { tag ->
                        Text(
                            text = tag,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TerracottaPrimary
                        )
                    }
                }
            }

            HorizontalDivider(
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f),
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
            )

            // Interaction Bar (Like, Comment, Share)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Like Button
                val heartTint by animateColorAsState(
                    targetValue = if (post.isLiked) Color(0xFFE53935) else MaterialTheme.colorScheme.onSurfaceVariant,
                    label = "likeTint"
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable(onClick = onLike)
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                        .testTag("like_button_${post.id}")
                ) {
                    Icon(
                        imageVector = if (post.isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = "Like",
                        tint = heartTint,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${post.likesCount}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                // Comment Button
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable(onClick = onComment)
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                        .testTag("comment_button_${post.id}")
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ChatBubbleOutline,
                        contentDescription = "Comment",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "${post.commentsCount}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                // Share Button
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable(onClick = onShare)
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                        .testTag("share_button_${post.id}")
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Share,
                        contentDescription = "Share",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Share",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
