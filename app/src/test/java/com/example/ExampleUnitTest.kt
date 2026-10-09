package com.example

import com.example.data.ChaupalRepository
import com.example.localization.TranslationService
import com.example.model.FriendStatus
import com.example.model.Language
import com.example.model.PostType
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun testTranslationServiceReturnsTranslations() {
        val repo = ChaupalRepository()
        val post = repo.posts.value.first()
        val hindiTranslation = TranslationService.getTranslatedCaption(post, Language.HINDI)
        assertNotNull(hindiTranslation)
        assertTrue(hindiTranslation.isNotBlank())

        val tamilTranslation = TranslationService.getTranslatedCaption(post, Language.TAMIL)
        assertNotNull(tamilTranslation)
        assertTrue(tamilTranslation.isNotBlank())
    }

    @Test
    fun testEventRsvpGoingAndInterested() {
        val repo = ChaupalRepository()
        val firstEvent = repo.events.value.first()
        val initialGoingCount = firstEvent.rsvpGoingCount

        repo.toggleEventGoing(firstEvent.id)
        val updatedEvent = repo.events.value.first { it.id == firstEvent.id }
        assertNotEquals(firstEvent.isUserGoing, updatedEvent.isUserGoing)

        repo.toggleEventInterested(firstEvent.id)
        val eventAfterInterested = repo.events.value.first { it.id == firstEvent.id }
        assertTrue(eventAfterInterested.isUserInterested)
        assertFalse(eventAfterInterested.isUserGoing)
    }

    @Test
    fun testCreateLocalEvent() {
        val repo = ChaupalRepository()
        val initialCount = repo.events.value.size
        repo.createLocalEvent(
            title = "Assi Ghat Morning Yoga",
            locality = "Assi Ghat, Varanasi",
            regionId = "vns",
            category = "Cultural Festival",
            dateFormatted = "Tomorrow • 6:00 AM",
            locationVenue = "Assi Steps",
            description = "Community morning sun salutations by the Ganga."
        )
        val updatedCount = repo.events.value.size
        assertEquals(initialCount + 1, updatedCount)
    }

    @Test
    fun testFriendAcceptanceExpandsMitraCircle() {
        val repo = ChaupalRepository()
        val pendingFriend = repo.friends.value.first { it.status == FriendStatus.PENDING_INCOMING }
        repo.acceptFriendRequest(pendingFriend.id)
        val acceptedFriend = repo.friends.value.first { it.id == pendingFriend.id }
        assertEquals(FriendStatus.CONNECTED, acceptedFriend.status)
    }
}
