package com.example.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.localization.AppStrings
import com.example.model.Language
import com.example.ui.theme.TerracottaPrimary
import com.example.viewmodel.ChaupalTab

@Composable
fun ChaupalBottomNav(
    currentTab: ChaupalTab,
    currentLanguage: Language,
    onTabSelected: (ChaupalTab) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 6.dp
    ) {
        // Tab 1: Friends Feed
        NavigationBarItem(
            selected = currentTab == ChaupalTab.FEED,
            onClick = { onTabSelected(ChaupalTab.FEED) },
            icon = {
                Icon(
                    imageVector = if (currentTab == ChaupalTab.FEED) Icons.Filled.DynamicFeed else Icons.Outlined.DynamicFeed,
                    contentDescription = AppStrings.tabFeed(currentLanguage),
                    modifier = Modifier.size(22.dp)
                )
            },
            label = {
                Text(
                    text = AppStrings.tabFeed(currentLanguage),
                    fontSize = 10.sp,
                    fontWeight = if (currentTab == ChaupalTab.FEED) FontWeight.Bold else FontWeight.Normal,
                    maxLines = 1
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = TerracottaPrimary,
                selectedTextColor = TerracottaPrimary,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = Modifier.testTag("nav_tab_feed")
        )

        // Tab 2: Local Events & Melas
        NavigationBarItem(
            selected = currentTab == ChaupalTab.EVENTS,
            onClick = { onTabSelected(ChaupalTab.EVENTS) },
            icon = {
                Icon(
                    imageVector = if (currentTab == ChaupalTab.EVENTS) Icons.Filled.Festival else Icons.Outlined.Festival,
                    contentDescription = AppStrings.tabEvents(currentLanguage),
                    modifier = Modifier.size(22.dp)
                )
            },
            label = {
                Text(
                    text = AppStrings.tabEvents(currentLanguage),
                    fontSize = 10.sp,
                    fontWeight = if (currentTab == ChaupalTab.EVENTS) FontWeight.Bold else FontWeight.Normal,
                    maxLines = 1
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = TerracottaPrimary,
                selectedTextColor = TerracottaPrimary,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = Modifier.testTag("nav_tab_events")
        )

        // Tab 3: Map Search
        NavigationBarItem(
            selected = currentTab == ChaupalTab.MAP_SEARCH,
            onClick = { onTabSelected(ChaupalTab.MAP_SEARCH) },
            icon = {
                Icon(
                    imageVector = if (currentTab == ChaupalTab.MAP_SEARCH) Icons.Filled.Explore else Icons.Outlined.Explore,
                    contentDescription = AppStrings.tabExplore(currentLanguage),
                    modifier = Modifier.size(22.dp)
                )
            },
            label = {
                Text(
                    text = AppStrings.tabExplore(currentLanguage),
                    fontSize = 10.sp,
                    fontWeight = if (currentTab == ChaupalTab.MAP_SEARCH) FontWeight.Bold else FontWeight.Normal,
                    maxLines = 1
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = TerracottaPrimary,
                selectedTextColor = TerracottaPrimary,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = Modifier.testTag("nav_tab_map_search")
        )

        // Tab 4: Messages
        NavigationBarItem(
            selected = currentTab == ChaupalTab.MESSAGES,
            onClick = { onTabSelected(ChaupalTab.MESSAGES) },
            icon = {
                Icon(
                    imageVector = if (currentTab == ChaupalTab.MESSAGES) Icons.Filled.Forum else Icons.Outlined.Forum,
                    contentDescription = AppStrings.tabMessages(currentLanguage),
                    modifier = Modifier.size(22.dp)
                )
            },
            label = {
                Text(
                    text = AppStrings.tabMessages(currentLanguage),
                    fontSize = 10.sp,
                    fontWeight = if (currentTab == ChaupalTab.MESSAGES) FontWeight.Bold else FontWeight.Normal,
                    maxLines = 1
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = TerracottaPrimary,
                selectedTextColor = TerracottaPrimary,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = Modifier.testTag("nav_tab_messages")
        )

        // Tab 5: Local Reels
        NavigationBarItem(
            selected = currentTab == ChaupalTab.REELS,
            onClick = { onTabSelected(ChaupalTab.REELS) },
            icon = {
                Icon(
                    imageVector = if (currentTab == ChaupalTab.REELS) Icons.Filled.PlayCircleFilled else Icons.Outlined.PlayCircleOutline,
                    contentDescription = AppStrings.tabReels(currentLanguage),
                    modifier = Modifier.size(22.dp)
                )
            },
            label = {
                Text(
                    text = AppStrings.tabReels(currentLanguage),
                    fontSize = 10.sp,
                    fontWeight = if (currentTab == ChaupalTab.REELS) FontWeight.Bold else FontWeight.Normal,
                    maxLines = 1
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = TerracottaPrimary,
                selectedTextColor = TerracottaPrimary,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = Modifier.testTag("nav_tab_reels")
        )

        // Tab 6: Profile
        NavigationBarItem(
            selected = currentTab == ChaupalTab.PROFILE,
            onClick = { onTabSelected(ChaupalTab.PROFILE) },
            icon = {
                Icon(
                    imageVector = if (currentTab == ChaupalTab.PROFILE) Icons.Filled.AccountCircle else Icons.Outlined.AccountCircle,
                    contentDescription = AppStrings.tabProfile(currentLanguage),
                    modifier = Modifier.size(22.dp)
                )
            },
            label = {
                Text(
                    text = AppStrings.tabProfile(currentLanguage),
                    fontSize = 10.sp,
                    fontWeight = if (currentTab == ChaupalTab.PROFILE) FontWeight.Bold else FontWeight.Normal,
                    maxLines = 1
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = TerracottaPrimary,
                selectedTextColor = TerracottaPrimary,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = Modifier.testTag("nav_tab_profile")
        )
    }
}
