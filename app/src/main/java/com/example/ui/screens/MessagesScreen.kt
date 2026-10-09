package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.localization.AppStrings
import com.example.model.ChatMessage
import com.example.model.Friend
import com.example.model.Language
import com.example.ui.theme.PeacockTeal
import com.example.ui.theme.SaffronAccent
import com.example.ui.theme.TerracottaPrimary
import kotlinx.coroutines.launch

@Composable
fun MessagesScreen(
    currentLanguage: Language,
    connectedFriends: List<Friend>,
    activeChatFriendId: String?,
    chatMessagesMap: Map<String, List<ChatMessage>>,
    onOpenChat: (String) -> Unit,
    onCloseChat: () -> Unit,
    onSendMessage: (friendId: String, text: String) -> Unit,
    onOpenExploreClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (activeChatFriendId != null) {
        val activeFriend = connectedFriends.find { it.id == activeChatFriendId }
        val messages = chatMessagesMap[activeChatFriendId] ?: emptyList()

        BackHandler {
            onCloseChat()
        }

        ConversationView(
            friend = activeFriend,
            messages = messages,
            currentLanguage = currentLanguage,
            onBack = onCloseChat,
            onSendMessage = { text -> onSendMessage(activeChatFriendId, text) }
        )
    } else {
        ChatListView(
            connectedFriends = connectedFriends,
            chatMessagesMap = chatMessagesMap,
            currentLanguage = currentLanguage,
            onOpenChat = onOpenChat,
            onOpenExploreClick = onOpenExploreClick,
            modifier = modifier
        )
    }
}

@Composable
fun ChatListView(
    connectedFriends: List<Friend>,
    chatMessagesMap: Map<String, List<ChatMessage>>,
    currentLanguage: Language,
    onOpenChat: (String) -> Unit,
    onOpenExploreClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }

    val filteredFriends = connectedFriends.filter {
        searchQuery.isEmpty() ||
            it.name.contains(searchQuery, ignoreCase = true) ||
            it.locality.contains(searchQuery, ignoreCase = true)
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(top = 10.dp, bottom = 90.dp)
    ) {
        item {
            Column {
                Text(
                    text = "Varta • निजी वार्तालाप",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "End-to-end community messaging with your verified Mitra circle",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search connected friends...") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = TerracottaPrimary)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("chat_search_input"),
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )
        }

        if (connectedFriends.isEmpty()) {
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Forum,
                            contentDescription = null,
                            tint = TerracottaPrimary,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "No private conversations yet",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Private messaging on Chaupal is reserved for accepted friends. Connect with people in your neighborhood to start chats!",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 18.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = onOpenExploreClick,
                            colors = ButtonDefaults.buttonColors(containerColor = TerracottaPrimary)
                        ) {
                            Text("Find Neighbors on Map")
                        }
                    }
                }
            }
        } else {
            items(filteredFriends, key = { it.id }) { friend ->
                val friendMessages = chatMessagesMap[friend.id] ?: emptyList()
                val lastMsg = friendMessages.lastOrNull()

                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onOpenChat(friend.id) }
                        .testTag("chat_item_${friend.id}")
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(friend.avatarColor),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = friend.initials,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp
                            )
                            if (friend.isOnline) {
                                Box(
                                    modifier = Modifier
                                        .size(14.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFF4CAF50))
                                        .border(2.dp, Color.White, CircleShape)
                                        .align(Alignment.BottomEnd)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = friend.name,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = lastMsg?.timestampFormatted ?: "Active",
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Spacer(modifier = Modifier.height(2.dp))

                            Text(
                                text = "📍 ${friend.locality} • ${friend.distanceKm} km",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = TerracottaPrimary
                            )

                            Spacer(modifier = Modifier.height(2.dp))

                            Text(
                                text = lastMsg?.let {
                                    if (it.isVoiceNote) "🎤 Voice note (${it.voiceDurationSec}s)" else it.text
                                } ?: "Start a conversation...",
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ConversationView(
    friend: Friend?,
    messages: List<ChatMessage>,
    currentLanguage: Language,
    onBack: () -> Unit,
    onSendMessage: (String) -> Unit
) {
    var inputText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        // Conversation Top Bar
        Surface(
            tonalElevation = 3.dp,
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.testTag("chat_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }

                if (friend != null) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(friend.avatarColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = friend.initials,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = friend.name,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = PeacockTeal,
                                modifier = Modifier.size(13.dp)
                            )
                        }
                        Text(
                            text = "${friend.locality} • ${if (friend.isOnline) "Online" else "Away"}",
                            fontSize = 11.sp,
                            color = if (friend.isOnline) Color(0xFF2E7D32) else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        // Messages List
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            // Local Security & Verification Notice
            item {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Text(
                        text = "🔒 Direct Private Varta • Connected local residents in India only",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(8.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }

            items(messages, key = { it.id }) { msg ->
                MessageBubble(message = msg)
            }
        }

        // Regional Quick Greetings Bar
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(AppStrings.regionalGreetings) { greeting ->
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .clickable {
                                onSendMessage(greeting)
                            }
                            .testTag("quick_greeting_chip")
                    ) {
                        Text(
                            text = greeting,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = TerracottaPrimary,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))

            // Message Input Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    placeholder = { Text("Write a message in any regional language...") },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("chat_input_field"),
                    shape = RoundedCornerShape(22.dp),
                    maxLines = 3
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = {
                        if (inputText.isNotBlank()) {
                            onSendMessage(inputText.trim())
                            inputText = ""
                            coroutineScope.launch {
                                listState.animateScrollToItem(messages.size)
                            }
                        }
                    },
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(TerracottaPrimary)
                        .testTag("chat_send_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun MessageBubble(
    message: ChatMessage,
    modifier: Modifier = Modifier
) {
    val isFromMe = message.isFromMe
    val bubbleColor = if (isFromMe) TerracottaPrimary else MaterialTheme.colorScheme.surfaceVariant
    val textColor = if (isFromMe) Color.White else MaterialTheme.colorScheme.onSurface

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = if (isFromMe) Arrangement.End else Arrangement.Start
    ) {
        Surface(
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = if (isFromMe) 16.dp else 4.dp,
                bottomEnd = if (isFromMe) 4.dp else 16.dp
            ),
            color = bubbleColor,
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                if (message.isVoiceNote) {
                    // Simulated Voice Note Player Bubble
                    var isVoicePlaying by remember { mutableStateOf(false) }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        IconButton(
                            onClick = { isVoicePlaying = !isVoicePlaying },
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(if (isFromMe) Color.White.copy(alpha = 0.2f) else MaterialTheme.colorScheme.primaryContainer)
                        ) {
                            Icon(
                                imageVector = if (isVoicePlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = "Play Voice Note",
                                tint = if (isFromMe) Color.White else TerracottaPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        // Waveform visualization
                        WaveformVisualizer(
                            isPlaying = isVoicePlaying,
                            color = if (isFromMe) Color.White else TerracottaPrimary,
                            modifier = Modifier
                                .weight(1f)
                                .height(24.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "0:${message.voiceDurationSec.toString().padStart(2, '0')}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isFromMe) Color.White.copy(alpha = 0.85f) else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    Text(
                        text = message.text,
                        fontSize = 14.sp,
                        color = textColor,
                        lineHeight = 20.sp
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))

                Row(
                    modifier = Modifier.align(Alignment.End),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = message.timestampFormatted,
                        fontSize = 10.sp,
                        color = if (isFromMe) Color.White.copy(alpha = 0.75f) else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    if (isFromMe) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.DoneAll,
                            contentDescription = "Read",
                            tint = if (message.isRead) SaffronAccent else Color.White.copy(alpha = 0.75f),
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun WaveformVisualizer(
    isPlaying: Boolean,
    color: Color,
    modifier: Modifier = Modifier
) {
    val barHeights = remember { listOf(6, 12, 18, 10, 16, 22, 14, 8, 15, 20, 12, 18, 7, 14) }

    val transition = rememberInfiniteTransition(label = "waveform")
    val waveOffset by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(animation = tween(600, easing = LinearEasing)),
        label = "waveformAnim"
    )

    Canvas(modifier = modifier) {
        val barWidth = 3.dp.toPx()
        val spacing = 4.dp.toPx()
        val totalBars = barHeights.size

        for (i in 0 until totalBars) {
            val baseHeight = barHeights[i].dp.toPx()
            val animatedHeight = if (isPlaying) {
                baseHeight * (0.6f + 0.4f * kotlin.math.sin(i + waveOffset * 6.28f).toFloat())
            } else {
                baseHeight
            }

            val x = i * (barWidth + spacing)
            val yStart = (size.height - animatedHeight) / 2
            drawLine(
                color = color,
                start = Offset(x, yStart),
                end = Offset(x, yStart + animatedHeight),
                strokeWidth = barWidth,
                cap = androidx.compose.ui.graphics.StrokeCap.Round
            )
        }
    }
}
