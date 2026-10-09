package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.localization.AppStrings
import com.example.model.Language
import com.example.model.PostType
import com.example.model.QuoteTheme
import com.example.ui.theme.TerracottaPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePostDialog(
    currentLanguage: Language,
    quoteThemes: List<QuoteTheme>,
    defaultLocality: String,
    onDismiss: () -> Unit,
    onSubmitPost: (
        type: PostType,
        caption: String,
        quoteText: String,
        quoteAuthor: String,
        quoteThemeId: String,
        localityTag: String,
        mediaDrawableRes: Int?,
        videoDurationSec: Int,
        tags: List<String>
    ) -> Unit
) {
    var selectedType by remember { mutableStateOf(PostType.QUOTE) }
    var caption by remember { mutableStateOf("") }
    var quoteText by remember { mutableStateOf("कर्मण्येवाधिकारस्ते मा फलेषु कदाचन।\n(Focus on sincere effort and duty; the fruits will naturally follow.)") }
    var quoteAuthor by remember { mutableStateOf("— श्रीमद्भगवद्गीता (Bhagavad Gita)") }
    var selectedThemeId by remember { mutableStateOf("terracotta") }
    var localityTag by remember { mutableStateOf(defaultLocality) }
    var tagsInput by remember { mutableStateOf("#LocalWisdom #ChaupalVichar") }
    var selectedPhotoPreset by remember { mutableStateOf(R.drawable.post_filter_coffee_1791558743086) }
    var selectedVideoPreset by remember { mutableStateOf(R.drawable.reel_chai_stall_1791558766878) }

    val activeQuoteTheme = quoteThemes.find { it.id == selectedThemeId } ?: quoteThemes.first()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp)
                .verticalScroll(rememberScrollState())
                .navigationBarsPadding()
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = AppStrings.createPost(currentLanguage),
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Shared exclusively with your connected friends",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Post Type Selector Segmented Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Quote Tab
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (selectedType == PostType.QUOTE) MaterialTheme.colorScheme.surface else Color.Transparent,
                    tonalElevation = if (selectedType == PostType.QUOTE) 2.dp else 0.dp,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { selectedType = PostType.QUOTE }
                        .testTag("tab_type_quote")
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 10.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.FormatQuote,
                            contentDescription = null,
                            tint = if (selectedType == PostType.QUOTE) TerracottaPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = AppStrings.shareQuote(currentLanguage),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (selectedType == PostType.QUOTE) TerracottaPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Photo Tab
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (selectedType == PostType.PHOTO) MaterialTheme.colorScheme.surface else Color.Transparent,
                    tonalElevation = if (selectedType == PostType.PHOTO) 2.dp else 0.dp,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { selectedType = PostType.PHOTO }
                        .testTag("tab_type_photo")
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 10.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.PhotoCamera,
                            contentDescription = null,
                            tint = if (selectedType == PostType.PHOTO) TerracottaPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = AppStrings.sharePhoto(currentLanguage),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (selectedType == PostType.PHOTO) TerracottaPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Video Tab
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (selectedType == PostType.VIDEO) MaterialTheme.colorScheme.surface else Color.Transparent,
                    tonalElevation = if (selectedType == PostType.VIDEO) 2.dp else 0.dp,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { selectedType = PostType.VIDEO }
                        .testTag("tab_type_video")
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 10.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Videocam,
                            contentDescription = null,
                            tint = if (selectedType == PostType.VIDEO) TerracottaPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = AppStrings.shareVideo(currentLanguage),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (selectedType == PostType.VIDEO) TerracottaPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Specific content controls depending on PostType
            when (selectedType) {
                PostType.QUOTE -> {
                    Text(
                        text = "Quote / Wisdom Card Theme",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        quoteThemes.forEach { theme ->
                            val isThemeSelected = theme.id == selectedThemeId
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(Brush.linearGradient(theme.backgroundBrushColors))
                                    .border(
                                        width = if (isThemeSelected) 3.dp else 1.dp,
                                        color = if (isThemeSelected) MaterialTheme.colorScheme.primary else Color.White,
                                        shape = CircleShape
                                    )
                                    .clickable { selectedThemeId = theme.id }
                                    .testTag("quote_theme_${theme.id}"),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isThemeSelected) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = theme.textColor,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    OutlinedTextField(
                        value = quoteText,
                        onValueChange = { quoteText = it },
                        label = { Text("Quote Text (Dohe / Kural / Shloka / Words of Wisdom)") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("quote_text_input"),
                        minLines = 3,
                        maxLines = 5,
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = quoteAuthor,
                        onValueChange = { quoteAuthor = it },
                        label = { Text("Author / Source (e.g. — कबीर दास, Thiruvalluvar)") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("quote_author_input"),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Live Card Preview
                    Text(
                        text = "Card Preview:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(Brush.linearGradient(activeQuoteTheme.backgroundBrushColors))
                            .padding(20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.FormatQuote,
                                contentDescription = null,
                                tint = activeQuoteTheme.accentColor,
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = quoteText.ifEmpty { "Write your wisdom here..." },
                                color = activeQuoteTheme.textColor,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontStyle = FontStyle.Italic,
                                textAlign = TextAlign.Center,
                                lineHeight = 22.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = quoteAuthor,
                                color = activeQuoteTheme.accentColor,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                PostType.PHOTO -> {
                    Text(
                        text = "Select Local Photographic Asset",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        val photoOptions = listOf(
                            Triple("Filter Coffee", R.drawable.post_filter_coffee_1791558743086, "Bengaluru"),
                            Triple("Varanasi Ghats", R.drawable.post_varanasi_ghat_1791558794787, "Kashi"),
                            Triple("Street Chai", R.drawable.reel_chai_stall_1791558766878, "Old City")
                        )

                        photoOptions.forEach { (label, res, loc) ->
                            val isSelected = selectedPhotoPreset == res
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant,
                                border = if (isSelected) androidx.compose.foundation.BorderStroke(2.dp, TerracottaPrimary) else null,
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable {
                                        selectedPhotoPreset = res
                                        localityTag = "$loc Community"
                                    }
                            ) {
                                Column(
                                    modifier = Modifier.padding(8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = label,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center,
                                        color = if (isSelected) TerracottaPrimary else MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = loc,
                                        fontSize = 10.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = caption,
                        onValueChange = { caption = it },
                        label = { Text("Photo Caption / Story") },
                        placeholder = { Text("Describe what makes this neighborhood spot special...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("photo_caption_input"),
                        minLines = 2,
                        maxLines = 4,
                        shape = RoundedCornerShape(12.dp)
                    )
                }

                PostType.VIDEO -> {
                    Text(
                        text = "Select Local Video Snippet",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        val videoOptions = listOf(
                            Triple("Chai Boiling", R.drawable.reel_chai_stall_1791558766878, 30),
                            Triple("Coffee Meter Pour", R.drawable.post_filter_coffee_1791558743086, 25),
                            Triple("River Aarti Lamps", R.drawable.post_varanasi_ghat_1791558794787, 45)
                        )

                        videoOptions.forEach { (label, res, sec) ->
                            val isSelected = selectedVideoPreset == res
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant,
                                border = if (isSelected) androidx.compose.foundation.BorderStroke(2.dp, TerracottaPrimary) else null,
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { selectedVideoPreset = res }
                            ) {
                                Column(
                                    modifier = Modifier.padding(8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PlayCircle,
                                        contentDescription = null,
                                        tint = if (isSelected) TerracottaPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Text(
                                        text = label,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center
                                    )
                                    Text(
                                        text = "${sec}s video",
                                        fontSize = 10.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = caption,
                        onValueChange = { caption = it },
                        label = { Text("Video Caption & Context") },
                        placeholder = { Text("Live rhythm, mela, festival sounds or street beats...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("video_caption_input"),
                        minLines = 2,
                        maxLines = 4,
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Locality and Hashtags Row
            OutlinedTextField(
                value = localityTag,
                onValueChange = { localityTag = it },
                label = { Text("Neighborhood Locality Tag") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = TerracottaPrimary)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("post_locality_input"),
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = tagsInput,
                onValueChange = { tagsInput = it },
                label = { Text("Tags (#Neighborhood #Culture)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Submit Button
            Button(
                onClick = {
                    val parsedTags = tagsInput.split(" ").filter { it.startsWith("#") }
                    val drawableRes = when (selectedType) {
                        PostType.PHOTO -> selectedPhotoPreset
                        PostType.VIDEO -> selectedVideoPreset
                        PostType.QUOTE -> null
                    }
                    val videoDuration = if (selectedType == PostType.VIDEO) 30 else 0

                    onSubmitPost(
                        selectedType,
                        caption,
                        quoteText,
                        quoteAuthor,
                        selectedThemeId,
                        localityTag,
                        drawableRes,
                        videoDuration,
                        parsedTags
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("submit_post_button"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = TerracottaPrimary)
            ) {
                Icon(imageVector = Icons.Default.Send, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Share with Mitra Circle",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
