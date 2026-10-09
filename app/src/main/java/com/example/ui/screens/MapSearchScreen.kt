package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.localization.AppStrings
import com.example.model.Friend
import com.example.model.FriendStatus
import com.example.model.Language
import com.example.model.RegionLocation
import com.example.ui.theme.PeacockTeal
import com.example.ui.theme.SaffronAccent
import com.example.ui.theme.TerracottaPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapSearchScreen(
    currentLanguage: Language,
    selectedRegion: RegionLocation,
    regionLocations: List<RegionLocation>,
    radiusKm: Int,
    selectedInterest: String,
    searchQuery: String,
    searchResults: List<Friend>,
    onSelectRegion: (RegionLocation) -> Unit,
    onRadiusChange: (Int) -> Unit,
    onInterestChange: (String) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onSendFriendRequest: (String) -> Unit,
    onAcceptFriendRequest: (String) -> Unit,
    onOpenChat: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isSendingNoteForFriend by remember { mutableStateOf<Friend?>(null) }
    var customNoteText by remember { mutableStateOf("") }

    val interestOptions = listOf(
        "All",
        "Heritage Walks",
        "South Indian Filter Coffee",
        "Street Food",
        "Local Cricket",
        "Tech Startups",
        "Classical Music",
        "Organic Gardening"
    )

    // Pulsing radar animation for map canvas
    val infiniteTransition = rememberInfiniteTransition(label = "radarPulse")
    val pulseFraction by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2400, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "radarPulse"
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(top = 10.dp, bottom = 90.dp)
    ) {
        // Section Header
        item {
            Column {
                Text(
                    text = "Bhoomi Map Discovery • भारत भू-खोज",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Find and connect with neighbors within your chosen radius and region",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Interactive Region Map Visualizer
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("interactive_map_canvas_card")
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    // Region Selector Pills Row
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(regionLocations) { reg ->
                            val isSelected = reg.id == selectedRegion.id
                            FilterChip(
                                selected = isSelected,
                                onClick = { onSelectRegion(reg) },
                                label = { Text(reg.name) },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Place,
                                        contentDescription = null,
                                        modifier = Modifier.size(14.dp)
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                    selectedLabelColor = TerracottaPrimary
                                ),
                                modifier = Modifier.testTag("region_chip_${reg.id}")
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Map Radar Canvas View
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF1A1F2C))
                    ) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val centerX = size.width / 2
                            val centerY = size.height / 2
                            val maxRadius = minOf(size.width, size.height) * 0.42f

                            // Draw radar grid rings
                            val ringCount = 3
                            for (i in 1..ringCount) {
                                drawCircle(
                                    color = Color(0xFF334E68).copy(alpha = 0.5f),
                                    radius = maxRadius * (i.toFloat() / ringCount),
                                    center = Offset(centerX, centerY),
                                    style = Stroke(
                                        width = 1.dp.toPx(),
                                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                                    )
                                )
                            }

                            // Dynamic pulse ring
                            drawCircle(
                                color = TerracottaPrimary.copy(alpha = (1f - pulseFraction) * 0.4f),
                                radius = maxRadius * pulseFraction,
                                center = Offset(centerX, centerY),
                                style = Stroke(width = 2.dp.toPx())
                            )

                            // Crosshairs
                            drawLine(
                                color = Color(0xFF334E68).copy(alpha = 0.4f),
                                start = Offset(centerX, centerY - maxRadius),
                                end = Offset(centerX, centerY + maxRadius),
                                strokeWidth = 1.dp.toPx()
                            )
                            drawLine(
                                color = Color(0xFF334E68).copy(alpha = 0.4f),
                                start = Offset(centerX - maxRadius, centerY),
                                end = Offset(centerX + maxRadius, centerY),
                                strokeWidth = 1.dp.toPx()
                            )

                            // Center Location Point (You)
                            drawCircle(
                                color = TerracottaPrimary,
                                radius = 7.dp.toPx(),
                                center = Offset(centerX, centerY)
                            )
                            drawCircle(
                                color = Color.White,
                                radius = 3.dp.toPx(),
                                center = Offset(centerX, centerY)
                            )

                            // Nearby People simulation points on the map
                            val simulatedOffsets = listOf(
                                Offset(centerX - 45.dp.toPx(), centerY - 30.dp.toPx()),
                                Offset(centerX + 60.dp.toPx(), centerY - 25.dp.toPx()),
                                Offset(centerX - 35.dp.toPx(), centerY + 40.dp.toPx()),
                                Offset(centerX + 50.dp.toPx(), centerY + 45.dp.toPx()),
                                Offset(centerX - 80.dp.toPx(), centerY + 10.dp.toPx())
                            )

                            simulatedOffsets.forEachIndexed { index, pos ->
                                drawCircle(
                                    color = SaffronAccent,
                                    radius = 5.dp.toPx(),
                                    center = pos
                                )
                                drawCircle(
                                    color = Color.White.copy(alpha = 0.8f),
                                    radius = 2.dp.toPx(),
                                    center = pos
                                )
                            }
                        }

                        // Map Legend & Coordinate Readout Overlay
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color.Black.copy(alpha = 0.75f),
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(10.dp)
                        ) {
                            Text(
                                text = "📍 ${selectedRegion.name} Center • ${selectedRegion.lat}°N, ${selectedRegion.lng}°E",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = TerracottaPrimary.copy(alpha = 0.85f),
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(10.dp)
                        ) {
                            Text(
                                text = "${searchResults.size} Neighbors in Radius",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Radius Slider Control
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Discovery Radius: $radiusKm km",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = when {
                                radiusKm <= 2 -> "Immediate Walk (5-10 min)"
                                radiusKm <= 5 -> "Neighborhood Area"
                                radiusKm <= 15 -> "Across District"
                                else -> "Wider Region"
                            },
                            fontSize = 11.sp,
                            color = TerracottaPrimary,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Slider(
                        value = radiusKm.toFloat(),
                        onValueChange = { onRadiusChange(it.toInt()) },
                        valueRange = 1f..50f,
                        steps = 48,
                        colors = SliderDefaults.colors(
                            thumbColor = TerracottaPrimary,
                            activeTrackColor = TerracottaPrimary
                        ),
                        modifier = Modifier.testTag("radius_slider")
                    )
                }
            }
        }

        // Search Input Field
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                placeholder = { Text("Search by name, street, or interest...") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = TerracottaPrimary)
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { onSearchQueryChange("") }) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("map_search_input"),
                singleLine = true,
                shape = RoundedCornerShape(14.dp)
            )
        }

        // Community Interests Chips
        item {
            Column {
                Text(
                    text = "Filter by Local Interest:",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(interestOptions) { interest ->
                        val isSelected = selectedInterest == interest
                        FilterChip(
                            selected = isSelected,
                            onClick = { onInterestChange(interest) },
                            label = { Text(interest, fontSize = 11.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                selectedLabelColor = TerracottaPrimary
                            )
                        )
                    }
                }
            }
        }

        // Neighbors Discovery Results Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Neighbors Found (${searchResults.size})",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Sorted by proximity",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Results List
        if (searchResults.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOff,
                            contentDescription = null,
                            tint = TerracottaPrimary,
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "No neighbors found within $radiusKm km",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Text(
                            text = "Try increasing the radius slider or changing the interest filter to discover more local residents.",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        } else {
            items(searchResults, key = { it.id }) { neighbor ->
                NeighborDiscoveryCard(
                    friend = neighbor,
                    currentLanguage = currentLanguage,
                    onConnectClick = {
                        isSendingNoteForFriend = neighbor
                        customNoteText = "Namaste! I live in the neighborhood and would love to connect."
                    },
                    onAcceptClick = { onAcceptFriendRequest(neighbor.id) },
                    onMessageClick = { onOpenChat(neighbor.id) }
                )
            }
        }
    }

    // Custom Note Connection Dialog
    if (isSendingNoteForFriend != null) {
        val friend = isSendingNoteForFriend!!
        AlertDialog(
            onDismissRequest = { isSendingNoteForFriend = null },
            title = {
                Text("Connect with ${friend.name}")
            },
            text = {
                Column {
                    Text(
                        text = "Add an optional friendly neighborhood note to your request:",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = customNoteText,
                        onValueChange = { customNoteText = it },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3,
                        shape = RoundedCornerShape(10.dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onSendFriendRequest(friend.id)
                        isSendingNoteForFriend = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = TerracottaPrimary),
                    modifier = Modifier.testTag("confirm_send_request_button")
                ) {
                    Text("Send Request")
                }
            },
            dismissButton = {
                TextButton(onClick = { isSendingNoteForFriend = null }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun NeighborDiscoveryCard(
    friend: Friend,
    currentLanguage: Language,
    onConnectClick: () -> Unit,
    onAcceptClick: () -> Unit,
    onMessageClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("neighbor_card_${friend.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar
                Box(
                    modifier = Modifier
                        .size(46.dp)
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
                }

                Spacer(modifier = Modifier.width(12.dp))

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
                            contentDescription = "Verified Local",
                            tint = PeacockTeal,
                            modifier = Modifier.size(14.dp)
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.NearMe,
                            contentDescription = null,
                            tint = TerracottaPrimary,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "${friend.distanceKm} km away • ${friend.locality}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = TerracottaPrimary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                // Regional Language badge
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Text(
                        text = friend.primaryLanguage.nativeName,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Bio
            Text(
                text = friend.bio,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 18.sp
            )

            // Shared Interests Chips
            if (friend.sharedInterests.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    friend.sharedInterests.take(3).forEach { interest ->
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f)
                        ) {
                            Text(
                                text = "• $interest",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Connection Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                when (friend.status) {
                    FriendStatus.CONNECTED -> {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFFE8F5E9)
                        ) {
                            Text(
                                text = AppStrings.connectedStatus(currentLanguage),
                                color = Color(0xFF2E7D32),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        FilledTonalButton(
                            onClick = onMessageClick,
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Chat, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Message", fontSize = 12.sp)
                        }
                    }

                    FriendStatus.PENDING_SENT -> {
                        OutlinedButton(
                            onClick = {},
                            enabled = false,
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Icon(imageVector = Icons.Default.HourglassTop, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(AppStrings.requestedStatus(currentLanguage), fontSize = 12.sp)
                        }
                    }

                    FriendStatus.PENDING_INCOMING -> {
                        Button(
                            onClick = onAcceptClick,
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = PeacockTeal),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(AppStrings.acceptButton(currentLanguage), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    FriendStatus.SUGGESTED -> {
                        Button(
                            onClick = onConnectClick,
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = TerracottaPrimary),
                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                            modifier = Modifier.testTag("connect_btn_${friend.id}")
                        ) {
                            Icon(imageVector = Icons.Default.PersonAdd, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = AppStrings.connectButton(currentLanguage),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}
