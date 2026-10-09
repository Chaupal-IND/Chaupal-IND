package com.example.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.TerracottaPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEventDialog(
    defaultLocality: String,
    onDismiss: () -> Unit,
    onSubmitEvent: (
        title: String,
        locality: String,
        category: String,
        dateFormatted: String,
        locationVenue: String,
        description: String,
        feeLabel: String
    ) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Cultural Festival") }
    var dateFormatted by remember { mutableStateOf("Next Saturday • 6:00 PM") }
    var locationVenue by remember { mutableStateOf("") }
    var locality by remember { mutableStateOf(defaultLocality) }
    var description by remember { mutableStateOf("") }
    var feeLabel by remember { mutableStateOf("Free Entry • Open to All") }

    val categories = listOf(
        "Cultural Festival",
        "Heritage Walk",
        "Food & Chai Mela",
        "Civic & Green",
        "Sports & Cricket"
    )

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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Host a Local Event or Mela",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Invite neighbors to your community gathering in $locality",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Event Title (e.g. Assi Morning Aarti & Yoga, Gully Cricket)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("event_title_input"),
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Event Category:",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                categories.take(3).forEach { cat ->
                    val isSelected = selectedCategory == cat
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedCategory = cat },
                        label = { Text(cat, fontSize = 11.sp) }
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                categories.drop(3).forEach { cat ->
                    val isSelected = selectedCategory == cat
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedCategory = cat },
                        label = { Text(cat, fontSize = 11.sp) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = dateFormatted,
                onValueChange = { dateFormatted = it },
                label = { Text("Date & Time (e.g. This Sunday • 7:00 AM - 9:00 AM)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("event_date_input"),
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = locationVenue,
                onValueChange = { locationVenue = it },
                label = { Text("Venue / Landmark (e.g. Central Library Gate, Assi Steps)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("event_venue_input"),
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = locality,
                onValueChange = { locality = it },
                label = { Text("Locality / Neighborhood (e.g. Indiranagar, Bengaluru)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = feeLabel,
                onValueChange = { feeLabel = it },
                label = { Text("Entry / Fee Status (e.g. Free Entry, Free Seed Swap)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Event Description & Schedule") },
                placeholder = { Text("What will attendees do? What should they bring?") },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("event_desc_input"),
                minLines = 3,
                maxLines = 5,
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(18.dp))

            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        onSubmitEvent(
                            title.trim(),
                            locality.trim(),
                            selectedCategory,
                            dateFormatted.trim(),
                            locationVenue.ifBlank { locality }.trim(),
                            description.trim(),
                            feeLabel.trim()
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("submit_create_event_button"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = TerracottaPrimary)
            ) {
                Text("Publish Neighborhood Event", fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
