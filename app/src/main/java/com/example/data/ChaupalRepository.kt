package com.example.data

import androidx.compose.ui.graphics.Color
import com.example.R
import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ChaupalRepository {

    val quoteThemes = listOf(
        QuoteTheme(
            id = "terracotta",
            name = "Terracotta Saffron",
            backgroundBrushColors = listOf(Color(0xFFBF360C), Color(0xFFD84315), Color(0xFFFF6F00)),
            textColor = Color(0xFFFFF8E1),
            accentColor = Color(0xFFFFE082)
        ),
        QuoteTheme(
            id = "indigo",
            name = "Royal Indigo",
            backgroundBrushColors = listOf(Color(0xFF0D1B2A), Color(0xFF1B263B), Color(0xFF415A77)),
            textColor = Color(0xFFF0F4F8),
            accentColor = Color(0xFF90E0EF)
        ),
        QuoteTheme(
            id = "sandalwood",
            name = "Sandalwood Silk",
            backgroundBrushColors = listOf(Color(0xFFF7EFE2), Color(0xFFEFE2CF), Color(0xFFDEC5A5)),
            textColor = Color(0xFF2C1810),
            accentColor = Color(0xFF8D6E63)
        ),
        QuoteTheme(
            id = "marigold",
            name = "Vedic Marigold",
            backgroundBrushColors = listOf(Color(0xFFE65100), Color(0xFFF57C00), Color(0xFFFFB300)),
            textColor = Color(0xFFFFFFFF),
            accentColor = Color(0xFFFFF9C4)
        ),
        QuoteTheme(
            id = "peacock",
            name = "Peacock Forest",
            backgroundBrushColors = listOf(Color(0xFF004D40), Color(0xFF00796B), Color(0xFF009688)),
            textColor = Color(0xFFE0F2F1),
            accentColor = Color(0xFF80CBC4)
        )
    )

    val regionLocations = listOf(
        RegionLocation(
            id = "blr",
            name = "Bengaluru",
            state = "Karnataka",
            pincode = "560038",
            lat = 12.9716,
            lng = 77.5946,
            popularLocalities = listOf("Indiranagar", "Koramangala", "Jayanagar", "Malleshwaram", "Whitefield"),
            activeNeighborsCount = 1420
        ),
        RegionLocation(
            id = "vns",
            name = "Varanasi (Kashi)",
            state = "Uttar Pradesh",
            pincode = "221005",
            lat = 25.3176,
            lng = 82.9739,
            popularLocalities = listOf("Assi Ghat", "Dashashwamedh", "Godowlia", "BHU Campus", "Cantt"),
            activeNeighborsCount = 890
        ),
        RegionLocation(
            id = "pun",
            name = "Pune",
            state = "Maharashtra",
            pincode = "411004",
            lat = 18.5204,
            lng = 73.8567,
            popularLocalities = listOf("Kothrud", "Deccan Gymkhana", "FC Road", "Viman Nagar", "Aundh"),
            activeNeighborsCount = 1150
        ),
        RegionLocation(
            id = "kol",
            name = "Kolkata",
            state = "West Bengal",
            pincode = "700029",
            lat = 22.5726,
            lng = 88.3639,
            popularLocalities = listOf("Gariahat", "Salt Lake", "College Street", "Park Street", "Shyambazar"),
            activeNeighborsCount = 1310
        ),
        RegionLocation(
            id = "chn",
            name = "Chennai",
            state = "Tamil Nadu",
            pincode = "600004",
            lat = 13.0827,
            lng = 80.2707,
            popularLocalities = listOf("Mylapore", "Besant Nagar", "T. Nagar", "Adyar", "Anna Nagar"),
            activeNeighborsCount = 980
        ),
        RegionLocation(
            id = "jpr",
            name = "Jaipur",
            state = "Rajasthan",
            pincode = "302001",
            lat = 26.9124,
            lng = 75.7873,
            popularLocalities = listOf("C-Scheme", "Malviya Nagar", "Bapu Bazaar", "Vaishali Nagar", "Hawa Mahal Area"),
            activeNeighborsCount = 760
        ),
        RegionLocation(
            id = "cok",
            name = "Kochi",
            state = "Kerala",
            pincode = "682001",
            lat = 9.9312,
            lng = 76.2673,
            popularLocalities = listOf("Fort Kochi", "Mattancherry", "Panampilly Nagar", "Marine Drive", "Kakkanad"),
            activeNeighborsCount = 640
        ),
        RegionLocation(
            id = "del",
            name = "Delhi NCR",
            state = "Delhi",
            pincode = "110001",
            lat = 28.6139,
            lng = 77.2090,
            popularLocalities = listOf("Chandni Chowk", "Hauz Khas", "Connaught Place", "Lajpat Nagar", "Karol Bagh"),
            activeNeighborsCount = 2100
        )
    )

    private val _currentLanguage = MutableStateFlow(Language.ENGLISH)
    val currentLanguage: StateFlow<Language> = _currentLanguage.asStateFlow()

    private val _userProfile = MutableStateFlow(UserProfile())
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    private val _friends = MutableStateFlow(
        listOf(
            Friend(
                id = "f_ananya",
                name = "Ananya Kulkarni",
                handle = "ananya_kulkarni",
                locality = "Indiranagar, Bengaluru",
                state = "Karnataka",
                distanceKm = 1.2,
                bio = "Passionate about Kannada folk theatre, indie coffee shops, and cycling around Cubbon Park.",
                sharedInterests = listOf("South Indian Filter Coffee", "Heritage Walks", "Tech Startups"),
                primaryLanguage = Language.KANNADA,
                status = FriendStatus.CONNECTED,
                avatarColor = Color(0xFF00796B),
                isOnline = true,
                mutualFriendsCount = 5
            ),
            Friend(
                id = "f_devendra",
                name = "Devendra Mishra",
                handle = "devendra_kashi",
                locality = "Assi Ghat, Varanasi",
                state = "Uttar Pradesh",
                distanceKm = 0.8,
                bio = "Morning boatman & Hindustani classical sitar player. Promoting clean Ghat initiatives.",
                sharedInterests = listOf("Heritage Walks", "Classical Music", "Local Cricket"),
                primaryLanguage = Language.HINDI,
                status = FriendStatus.CONNECTED,
                avatarColor = Color(0xFFD84315),
                isOnline = true,
                mutualFriendsCount = 4
            ),
            Friend(
                id = "f_priya",
                name = "Priya Sundaram",
                handle = "priya_mylapore",
                locality = "Mylapore, Chennai",
                state = "Tamil Nadu",
                distanceKm = 2.4,
                bio = "Carnatic vocalist, filter kaapi fan, curating temple architecture trails.",
                sharedInterests = listOf("South Indian Filter Coffee", "Heritage Walks", "Classical Dance"),
                primaryLanguage = Language.TAMIL,
                status = FriendStatus.CONNECTED,
                avatarColor = Color(0xFF8E24AA),
                isOnline = false,
                mutualFriendsCount = 3
            ),
            Friend(
                id = "f_subhash",
                name = "Subhash Roy",
                handle = "subhash_adda",
                locality = "Gariahat, Kolkata",
                state = "West Bengal",
                distanceKm = 3.5,
                bio = "Book lover, street food connoisseur, hosting neighborhood tea-stall adda debates.",
                sharedInterests = listOf("Street Food", "Literature", "Adda Debates"),
                primaryLanguage = Language.BENGALI,
                status = FriendStatus.PENDING_INCOMING,
                avatarColor = Color(0xFF1565C0),
                incomingRequestMessage = "Saw your love for heritage walks and street food. Would love to connect on Chaupal!"
            ),
            Friend(
                id = "f_meera",
                name = "Meera Joshi",
                handle = "meera_pune",
                locality = "Kothrud, Pune",
                state = "Maharashtra",
                distanceKm = 1.9,
                bio = "Trekker in the Sahyadris, organic terrace gardener & Marathi theatre enthusiast.",
                sharedInterests = listOf("Heritage Walks", "Organic Gardening", "Theatre"),
                primaryLanguage = Language.MARATHI,
                status = FriendStatus.PENDING_INCOMING,
                avatarColor = Color(0xFFC2185B),
                incomingRequestMessage = "Namaskar! Fellow terrace gardener and Sahyadri lover. Let's exchange neighborhood tips."
            ),
            Friend(
                id = "f_rohan",
                name = "Rohan Deshmukh",
                handle = "rohan_blr",
                locality = "Jayanagar 4th Block, Bengaluru",
                state = "Karnataka",
                distanceKm = 2.8,
                bio = "Weekend gully cricket captain and founder of Green Bengaluru neighborhood drive.",
                sharedInterests = listOf("Local Cricket", "Green Bengaluru", "South Indian Filter Coffee"),
                primaryLanguage = Language.KANNADA,
                status = FriendStatus.SUGGESTED,
                avatarColor = Color(0xFF2E7D32)
            ),
            Friend(
                id = "f_kavya",
                name = "Kavya Nair",
                handle = "kavya_kochi",
                locality = "Fort Kochi, Kerala",
                state = "Kerala",
                distanceKm = 4.1,
                bio = "Biennale art guide, cafe storyteller, discovering colonial heritage streets.",
                sharedInterests = listOf("Heritage Walks", "Modern Art", "Coffee"),
                primaryLanguage = Language.MALAYALAM,
                status = FriendStatus.SUGGESTED,
                avatarColor = Color(0xFF00838F)
            ),
            Friend(
                id = "f_gurpreet",
                name = "Gurpreet Singh",
                handle = "gurpreet_amritsar",
                locality = "Heritage Street, Amritsar",
                state = "Punjab",
                distanceKm = 3.2,
                bio = "Langar volunteer, kulcha enthusiast, preserving local oral histories of Punjab.",
                sharedInterests = listOf("Community Service", "Local Food", "Heritage Walks"),
                primaryLanguage = Language.PUNJABI,
                status = FriendStatus.SUGGESTED,
                avatarColor = Color(0xFFF57F17)
            ),
            Friend(
                id = "f_harshit",
                name = "Harshit Parekh",
                handle = "harshit_amd",
                locality = "Navrangpura, Ahmedabad",
                state = "Gujarat",
                distanceKm = 2.1,
                bio = "Pola architecture enthusiast, weekend badminton player, heritage conservationist.",
                sharedInterests = listOf("Heritage Walks", "Badminton", "Street Food"),
                primaryLanguage = Language.GUJARATI,
                status = FriendStatus.SUGGESTED,
                avatarColor = Color(0xFFE64A19)
            )
        )
    )
    val friends: StateFlow<List<Friend>> = _friends.asStateFlow()

    private val _posts = MutableStateFlow(
        listOf(
            Post(
                id = "p1",
                authorId = "f_ananya",
                authorName = "Ananya Kulkarni",
                authorHandle = "ananya_kulkarni",
                authorLocality = "Indiranagar, Bengaluru",
                authorInitials = "AK",
                authorAvatarColor = Color(0xFF00796B),
                type = PostType.PHOTO,
                caption = "Morning filter coffee ritual at Brahmin's Coffee Bar. The froth and aroma here never changes! Starting the weekend right with hot idli-vada. ☕✨",
                mediaDrawableRes = R.drawable.post_filter_coffee_1791558743086,
                timestampFormatted = "2h ago",
                likesCount = 38,
                isLiked = true,
                commentsCount = 6,
                localityTag = "Shankarpuram • Bengaluru South",
                tags = listOf("#FilterCoffee", "#NammaBengaluru", "#MorningAdda")
            ),
            Post(
                id = "p2",
                authorId = "f_devendra",
                authorName = "Devendra Mishra",
                authorHandle = "devendra_kashi",
                authorLocality = "Assi Ghat, Varanasi",
                authorInitials = "DM",
                authorAvatarColor = Color(0xFFD84315),
                type = PostType.QUOTE,
                caption = "Saint Kabir's wisdom hits differently when recited by the river in the morning breeze. Reflecting on this today:",
                quoteText = "बुरा जो देखन मैं चला, बुरा न मिलिया कोय।\nजो दिल खोजा आपना, मुझसे बुरा न कोय॥",
                quoteAuthor = "— संत कबीर (Sant Kabir Das)",
                quoteThemeId = "terracotta",
                timestampFormatted = "4h ago",
                likesCount = 92,
                isLiked = false,
                commentsCount = 14,
                localityTag = "Assi Ghat • Kashi",
                tags = listOf("#KabirKeDohe", "#VaranasiVichar", "#PratahVandan")
            ),
            Post(
                id = "p3",
                authorId = "f_priya",
                authorName = "Priya Sundaram",
                authorHandle = "priya_mylapore",
                authorLocality = "Mylapore, Chennai",
                authorInitials = "PS",
                authorAvatarColor = Color(0xFF8E24AA),
                type = PostType.QUOTE,
                caption = "Sharing my favorite Kural on knowledge and lifelong learning for our local book club:",
                quoteText = "கற்க கசடறக் கற்பவை கற்றபின்\nநிற்க அதற்குத் தக.",
                quoteAuthor = "— திருக்குறள் (Thirukkural 391)",
                quoteThemeId = "indigo",
                timestampFormatted = "6h ago",
                likesCount = 47,
                isLiked = false,
                commentsCount = 8,
                localityTag = "Mylapore • Chennai",
                tags = listOf("#Thirukkural", "#TamilWisdom", "#MylaporeAdda")
            ),
            Post(
                id = "p4",
                authorId = "f_devendra",
                authorName = "Devendra Mishra",
                authorHandle = "devendra_kashi",
                authorLocality = "Assi Ghat, Varanasi",
                authorInitials = "DM",
                authorAvatarColor = Color(0xFFD84315),
                type = PostType.PHOTO,
                caption = "Twilight serenity at the Ghats. Earthen diyas floating into the sacred Ganga as the bells chime for evening aarti. Blessed to call this home. 🪔✨",
                mediaDrawableRes = R.drawable.post_varanasi_ghat_1791558794787,
                timestampFormatted = "1d ago",
                likesCount = 154,
                isLiked = true,
                commentsCount = 22,
                localityTag = "Dashashwamedh Ghat • Varanasi",
                tags = listOf("#GangaAarti", "#Deepotsav", "#BanarasDiaries")
            ),
            Post(
                id = "p5",
                authorId = "f_ananya",
                authorName = "Ananya Kulkarni",
                authorHandle = "ananya_kulkarni",
                authorLocality = "Indiranagar, Bengaluru",
                authorInitials = "AK",
                authorAvatarColor = Color(0xFF00796B),
                type = PostType.VIDEO,
                caption = "Spontaneous Yakshagana drum jamming during the community cultural festival near 12th Main Indiranagar! The rhythm was electric! 🥁",
                mediaDrawableRes = R.drawable.reel_chai_stall_1791558766878,
                videoDurationSec = 45,
                timestampFormatted = "2d ago",
                likesCount = 76,
                isLiked = false,
                commentsCount = 11,
                localityTag = "Indiranagar • Bengaluru East",
                tags = listOf("#Yakshagana", "#IndiranagarCulture", "#CommunityRhythms")
            )
        )
    )
    val posts: StateFlow<List<Post>> = _posts.asStateFlow()

    private val _comments = MutableStateFlow<Map<String, List<Comment>>>(
        mapOf(
            "p1" to listOf(
                Comment("c1", "Aarav Sharma", "@aarav_local", "Best filter coffee in Bangalore hands down! Love their crispy vada.", "Indiranagar", "1h ago"),
                Comment("c2", "Rohan Deshmukh", "@rohan_blr", "Next time you are there, try their khara bath too. Unbeatable!", "Jayanagar", "45m ago"),
                Comment("c3", "Priya Sundaram", "@priya_mylapore", "Looks authentic! Reminds me of our Mylapore Rayar's Mess.", "Mylapore", "20m ago")
            ),
            "p2" to listOf(
                Comment("c4", "Aarav Sharma", "@aarav_local", "Timeless truth. Self-introspection is the beginning of wisdom.", "Indiranagar", "3h ago"),
                Comment("c5", "Ananya Kulkarni", "@ananya_kulkarni", "Beautifully said. Kabir's words resonate across all eras.", "Indiranagar", "2h ago")
            ),
            "p4" to listOf(
                Comment("c6", "Priya Sundaram", "@priya_mylapore", "Breathtaking view Devendra ji. Miss visiting Kashi during Dev Deepavali.", "Mylapore", "18h ago"),
                Comment("c7", "Aarav Sharma", "@aarav_local", "Har Har Mahadev! Hope to join you for the morning aarti next month.", "Indiranagar", "14h ago")
            )
        )
    )
    val comments: StateFlow<Map<String, List<Comment>>> = _comments.asStateFlow()

    private val _chatMessages = MutableStateFlow<Map<String, List<ChatMessage>>>(
        mapOf(
            "f_ananya" to listOf(
                ChatMessage("m1", "f_ananya", "Namaskara Aarav! Are you joining the Cubbon Park heritage walk this Sunday morning?", false, 0, "Yesterday, 6:30 PM", false),
                ChatMessage("m2", "my_user_id", "Namaskara Ananya! Yes, definitely! Starting 7 AM from the Central Library entrance.", false, 0, "Yesterday, 6:45 PM", true),
                ChatMessage("m3", "f_ananya", "Voice message (0:18)", true, 18, "Yesterday, 7:02 PM", false),
                ChatMessage("m4", "my_user_id", "Sounds perfect, let's grab filter coffee at CTR or Brahmin's right after!", false, 0, "Yesterday, 7:05 PM", true),
                ChatMessage("m5", "f_ananya", "Deal! See you Sunday. ☕", false, 0, "Today, 8:15 AM", false)
            ),
            "f_devendra" to listOf(
                ChatMessage("m6", "f_devendra", "नमस्ते आरव भाई! काशी में मौसम बहुत सुहावना है।", false, 0, "2 days ago", false),
                ChatMessage("m7", "my_user_id", "नमस्ते देवेन्द्र जी! अस्सी घाट की सुबह की क्या बात है।", false, 0, "2 days ago", true),
                ChatMessage("m8", "f_devendra", "Voice message (0:24)", true, 24, "Yesterday", false),
                ChatMessage("m9", "f_devendra", "इस बार देव दीपावली पर जरूर आइएगा, पूरा घाट दीयों से जगमगाएगा।", false, 0, "Today, 9:30 AM", false)
            ),
            "f_priya" to listOf(
                ChatMessage("m10", "f_priya", "Vanakkam Aarav! Curating the December music season map for Mylapore Sabha walkers.", false, 0, "3 days ago", false),
                ChatMessage("m11", "my_user_id", "Vanakkam Priya! That would be fantastic for out-of-town visitors too.", false, 0, "3 days ago", true)
            )
        )
    )
    val chatMessages: StateFlow<Map<String, List<ChatMessage>>> = _chatMessages.asStateFlow()

    private val _reels = MutableStateFlow(
        listOf(
            Reel(
                id = "r1",
                creatorName = "Pandit ji Chai Adda",
                creatorHandle = "panditji_chai",
                creatorInitials = "PC",
                locality = "Gali No. 4, Old Delhi & Varanasi",
                title = "Secret Masala Adrak Chai brewing on coals! ☕🔥",
                description = "Crushing fresh ginger and cardamom into thick boiling milk. The real taste of Indian mornings in earthenware kulhad.",
                thumbnailRes = R.drawable.reel_chai_stall_1791558766878,
                audioTrackName = "Morning Raga Beats (Acoustic Flute & Sitar)",
                likesCount = 842,
                isLiked = false,
                commentsCount = 94,
                shareCount = 310,
                tags = listOf("#KulhadChai", "#StreetCulture", "#DesiFlavours")
            ),
            Reel(
                id = "r2",
                creatorName = "Namma Kaapi Club",
                creatorHandle = "kaapi_club_blr",
                creatorInitials = "NK",
                locality = "Malleshwaram, Bengaluru",
                title = "The 2-foot meter tea & filter kaapi meter pour! ☕",
                description = "Mastering the iconic meter froth pour without spilling a single drop. Pure liquid silk in the dabara tumbler.",
                thumbnailRes = R.drawable.post_filter_coffee_1791558743086,
                audioTrackName = "Carnatic Fusion Basslines - Indiranagar Sessions",
                likesCount = 1205,
                isLiked = true,
                commentsCount = 142,
                shareCount = 520,
                tags = listOf("#FilterKaapi", "#BengaluruEats", "#MorningFoam")
            ),
            Reel(
                id = "r3",
                creatorName = "Ghat Heritage Volunteers",
                creatorHandle = "kashi_heritage",
                creatorInitials = "GH",
                locality = "Assi to Dashashwamedh, Varanasi",
                title = "Golden Hour Aarti preparation at stone steps 🪔",
                description = "10,000 diyas lined up for the evening Ganga Maha Aarti. The spirit of community devotion.",
                thumbnailRes = R.drawable.post_varanasi_ghat_1791558794787,
                audioTrackName = "Bhairavi Chants & Temple Bells",
                likesCount = 2390,
                isLiked = false,
                commentsCount = 318,
                shareCount = 890,
                tags = listOf("#KashiVibes", "#Deepotsav", "#LocalSpiritual")
            )
        )
    )
    val reels: StateFlow<List<Reel>> = _reels.asStateFlow()

    private val _reelComments = MutableStateFlow<Map<String, List<Comment>>>(
        mapOf(
            "r1" to listOf(
                Comment("rc1", "Kabir Sen", "@kabir_s", "That aroma through the screen! Which street in Varanasi is this?", "Assi Ghat", "3h ago"),
                Comment("rc2", "Ramesh K", "@ramesh_k", "Kulhad chai with fresh ginger beats any fancy cafe coffee.", "Bengaluru", "1h ago"),
                Comment("rc3", "Meera Joshi", "@meera_pune", "Adding this spot to my travel list next month!", "Pune", "30m ago")
            ),
            "r2" to listOf(
                Comment("rc4", "Devendra Mishra", "@devendra_kashi", "Incredible technique! Filter coffee foam is an art form.", "Varanasi", "5h ago"),
                Comment("rc5", "Aarav Sharma", "@aarav_local", "Proud to have this 15 mins away from home!", "Indiranagar", "2h ago")
            ),
            "r3" to listOf(
                Comment("rc6", "Priya Sundaram", "@priya_mylapore", "Divine and serene! Jai Ganga Maiya.", "Chennai", "1d ago"),
                Comment("rc7", "Ananya Kulkarni", "@ananya_kulkarni", "The reflection of lamps on the holy river is mesmerizing.", "Bengaluru", "12h ago")
            )
        )
    )
    val reelComments: StateFlow<Map<String, List<Comment>>> = _reelComments.asStateFlow()

    private val _events = MutableStateFlow(
        listOf(
            LocalEvent(
                id = "ev_cubbon",
                title = "Sunday Morning Tree Heritage & Botanical Walk",
                organizerName = "Ananya Kulkarni",
                organizerHandle = "@ananya_kulkarni",
                organizerAvatarColor = Color(0xFF00796B),
                regionId = "blr",
                locality = "Cubbon Park, Bengaluru",
                category = "Heritage Walk",
                dateFormatted = "This Sunday • 7:00 AM - 9:00 AM",
                locationVenue = "State Central Library Gate, Cubbon Park",
                description = "Join our local neighborhood nature walk exploring 100+ year-old indigenous trees and heritage colonial bandstands, followed by traditional filter coffee at CTR Malleshwaram.",
                imageDrawableRes = R.drawable.post_filter_coffee_1791558743086,
                rsvpGoingCount = 42,
                rsvpInterestedCount = 88,
                isUserGoing = true,
                feeLabel = "Free Walk • Community Led"
            ),
            LocalEvent(
                id = "ev_kashi",
                title = "Dev Deepavali 10,000 Earthen Lamps Community Drive",
                organizerName = "Devendra Mishra",
                organizerHandle = "@devendra_kashi",
                organizerAvatarColor = Color(0xFFD84315),
                regionId = "vns",
                locality = "Assi Ghat, Varanasi",
                category = "Cultural Festival",
                dateFormatted = "Nov 15, 2026 • 5:00 PM - 8:30 PM",
                locationVenue = "Assi to Dashashwamedh Ghat Steps",
                description = "Volunteers needed to light and arrange thousands of terracotta diyas on the stone ghat steps for the sacred river offering. Earthen diyas and cotton wicks provided.",
                imageDrawableRes = R.drawable.post_varanasi_ghat_1791558794787,
                rsvpGoingCount = 135,
                rsvpInterestedCount = 290,
                isUserGoing = false,
                isUserInterested = true,
                feeLabel = "Open Community Service"
            ),
            LocalEvent(
                id = "ev_chai",
                title = "Gali Chai & Spiced Adda Masterclass",
                organizerName = "Pandit ji Chai Adda",
                organizerHandle = "@panditji_chai",
                organizerAvatarColor = Color(0xFFE65100),
                regionId = "vns",
                locality = "Gali No. 4, Old City, Varanasi",
                category = "Food & Chai Mela",
                dateFormatted = "Saturday, Oct 18 • 4:00 PM",
                locationVenue = "Gali No. 4 Earthenware Corner",
                description = "Learn how to blend whole ginger, green cardamom pods, and lemongrass with steaming coal embers for the classic Banarasi spiced kulhad chai.",
                imageDrawableRes = R.drawable.reel_chai_stall_1791558766878,
                rsvpGoingCount = 28,
                rsvpInterestedCount = 64,
                isUserGoing = false,
                feeLabel = "Free Tasting Session"
            ),
            LocalEvent(
                id = "ev_pune",
                title = "Kothrud Organic Terrace Gardening Exchange",
                organizerName = "Meera Joshi",
                organizerHandle = "@meera_pune",
                organizerAvatarColor = Color(0xFFC2185B),
                regionId = "pun",
                locality = "Kothrud, Pune",
                category = "Civic & Green",
                dateFormatted = "Next Saturday • 9:30 AM",
                locationVenue = "Mayur Colony Community Hall, Kothrud",
                description = "Exchange heirloom native vegetable seeds, organic compost starters, and microgreens cultivation tips with fellow Pune urban gardeners.",
                imageDrawableRes = null,
                rsvpGoingCount = 31,
                rsvpInterestedCount = 57,
                isUserGoing = false,
                feeLabel = "Free Seed Swap"
            ),
            LocalEvent(
                id = "ev_chennai",
                title = "Mylapore Margazhi Music Trail & Temple Architectural Walk",
                organizerName = "Priya Sundaram",
                organizerHandle = "@priya_mylapore",
                organizerAvatarColor = Color(0xFF8E24AA),
                regionId = "chn",
                locality = "Mylapore, Chennai",
                category = "Heritage Walk",
                dateFormatted = "Dec 20, 2026 • 6:30 AM",
                locationVenue = "Kapaleeshwarar Temple East Gate",
                description = "Explore sacred temple carvings, agraharam architecture, and stop by traditional music sabhas for morning concerts with seasoned Carnatic artists.",
                imageDrawableRes = null,
                rsvpGoingCount = 54,
                rsvpInterestedCount = 112,
                isUserGoing = false,
                feeLabel = "Free Community Walk"
            )
        )
    )
    val events: StateFlow<List<LocalEvent>> = _events.asStateFlow()

    fun createLocalEvent(
        title: String,
        locality: String,
        regionId: String,
        category: String,
        dateFormatted: String,
        locationVenue: String,
        description: String,
        imageDrawableRes: Int? = null,
        feeLabel: String = "Free Entry • Open to All"
    ) {
        val user = _userProfile.value
        val newEvent = LocalEvent(
            id = "ev_${System.currentTimeMillis()}",
            title = title,
            organizerName = user.name,
            organizerHandle = "@${user.handle}",
            organizerAvatarColor = Color(0xFFD84315),
            regionId = regionId,
            locality = locality,
            category = category,
            dateFormatted = dateFormatted,
            locationVenue = locationVenue,
            description = description,
            imageDrawableRes = imageDrawableRes,
            rsvpGoingCount = 1,
            rsvpInterestedCount = 0,
            isUserGoing = true,
            isUserInterested = false,
            feeLabel = feeLabel
        )
        _events.update { listOf(newEvent) + it }
    }

    fun toggleEventGoing(eventId: String) {
        _events.update { list ->
            list.map { ev ->
                if (ev.id == eventId) {
                    val newGoing = !ev.isUserGoing
                    ev.copy(
                        isUserGoing = newGoing,
                        isUserInterested = if (newGoing) false else ev.isUserInterested,
                        rsvpGoingCount = if (newGoing) ev.rsvpGoingCount + 1 else maxOf(0, ev.rsvpGoingCount - 1)
                    )
                } else ev
            }
        }
    }

    fun toggleEventInterested(eventId: String) {
        _events.update { list ->
            list.map { ev ->
                if (ev.id == eventId) {
                    val newInterested = !ev.isUserInterested
                    ev.copy(
                        isUserInterested = newInterested,
                        isUserGoing = if (newInterested) false else ev.isUserGoing,
                        rsvpInterestedCount = if (newInterested) ev.rsvpInterestedCount + 1 else maxOf(0, ev.rsvpInterestedCount - 1)
                    )
                } else ev
            }
        }
    }

    fun setLanguage(language: Language) {
        _currentLanguage.value = language
    }

    fun updateProfile(
        name: String,
        bio: String,
        locality: String,
        hometown: String,
        interests: List<String>,
        primaryLanguage: Language
    ) {
        _userProfile.update {
            it.copy(
                name = name,
                bio = bio,
                locality = locality,
                hometown = hometown,
                interests = interests,
                primaryLanguage = primaryLanguage
            )
        }
    }

    fun addInterest(interest: String) {
        _userProfile.update {
            if (!it.interests.contains(interest)) {
                it.copy(interests = it.interests + interest)
            } else it
        }
    }

    fun removeInterest(interest: String) {
        _userProfile.update {
            it.copy(interests = it.interests.filter { item -> item != interest })
        }
    }

    fun sendFriendRequest(friendId: String) {
        _friends.update { list ->
            list.map { friend ->
                if (friend.id == friendId) {
                    friend.copy(status = FriendStatus.PENDING_SENT)
                } else friend
            }
        }
    }

    fun acceptFriendRequest(friendId: String) {
        _friends.update { list ->
            list.map { friend ->
                if (friend.id == friendId) {
                    friend.copy(status = FriendStatus.CONNECTED)
                } else friend
            }
        }
        _userProfile.update { it.copy(friendsCount = it.friendsCount + 1) }

        // If newly connected friend has posts, they automatically appear in the feed
        // Also ensure chat thread exists
        if (!_chatMessages.value.containsKey(friendId)) {
            val friend = _friends.value.find { it.id == friendId }
            val greeting = friend?.primaryLanguage?.greeting ?: "Namaste 🙏"
            _chatMessages.update {
                it + (friendId to listOf(
                    ChatMessage(
                        id = "welcome_${System.currentTimeMillis()}",
                        senderId = friendId,
                        text = "$greeting Thanks for accepting my connection request! Glad to connect on Chaupal.",
                        timestampFormatted = "Just now",
                        isFromMe = false
                    )
                ))
            }
        }
    }

    fun declineFriendRequest(friendId: String) {
        _friends.update { list ->
            list.map { friend ->
                if (friend.id == friendId) {
                    friend.copy(status = FriendStatus.SUGGESTED)
                } else friend
            }
        }
    }

    fun createPost(
        type: PostType,
        caption: String,
        quoteText: String = "",
        quoteAuthor: String = "",
        quoteThemeId: String = "terracotta",
        localityTag: String = _userProfile.value.locality,
        mediaDrawableRes: Int? = null,
        videoDurationSec: Int = 0,
        tags: List<String> = emptyList()
    ) {
        val user = _userProfile.value
        val newPost = Post(
            id = "p_user_${System.currentTimeMillis()}",
            authorId = user.id,
            authorName = user.name,
            authorHandle = user.handle,
            authorLocality = user.locality,
            authorInitials = user.name.take(2).uppercase(),
            authorAvatarColor = Color(0xFFD84315),
            type = type,
            caption = caption,
            quoteText = quoteText,
            quoteAuthor = quoteAuthor,
            quoteThemeId = quoteThemeId,
            mediaDrawableRes = mediaDrawableRes,
            videoDurationSec = videoDurationSec,
            timestampFormatted = "Just now",
            likesCount = 1,
            isLiked = true,
            commentsCount = 0,
            localityTag = localityTag,
            tags = tags,
            isFriendsOnly = true
        )
        _posts.update { listOf(newPost) + it }
        _userProfile.update { it.copy(postsCount = it.postsCount + 1) }
    }

    fun togglePostLike(postId: String) {
        _posts.update { list ->
            list.map { post ->
                if (post.id == postId) {
                    val newLiked = !post.isLiked
                    post.copy(
                        isLiked = newLiked,
                        likesCount = if (newLiked) post.likesCount + 1 else maxOf(0, post.likesCount - 1)
                    )
                } else post
            }
        }
    }

    fun addPostComment(postId: String, text: String) {
        val user = _userProfile.value
        val newComment = Comment(
            id = "c_${System.currentTimeMillis()}",
            authorName = user.name,
            authorHandle = "@${user.handle}",
            text = text,
            locality = user.locality.substringBefore(","),
            timestampFormatted = "Just now"
        )
        _comments.update { map ->
            val currentList = map[postId] ?: emptyList()
            map + (postId to (currentList + newComment))
        }
        _posts.update { list ->
            list.map { post ->
                if (post.id == postId) {
                    post.copy(commentsCount = post.commentsCount + 1)
                } else post
            }
        }
    }

    fun toggleReelLike(reelId: String) {
        _reels.update { list ->
            list.map { reel ->
                if (reel.id == reelId) {
                    val newLiked = !reel.isLiked
                    reel.copy(
                        isLiked = newLiked,
                        likesCount = if (newLiked) reel.likesCount + 1 else maxOf(0, reel.likesCount - 1)
                    )
                } else reel
            }
        }
    }

    fun addReelComment(reelId: String, text: String) {
        val user = _userProfile.value
        val newComment = Comment(
            id = "rc_${System.currentTimeMillis()}",
            authorName = user.name,
            authorHandle = "@${user.handle}",
            text = text,
            locality = user.locality.substringBefore(","),
            timestampFormatted = "Just now"
        )
        _reelComments.update { map ->
            val currentList = map[reelId] ?: emptyList()
            map + (reelId to (currentList + newComment))
        }
        _reels.update { list ->
            list.map { reel ->
                if (reel.id == reelId) {
                    reel.copy(commentsCount = reel.commentsCount + 1)
                } else reel
            }
        }
    }

    fun sendMessage(friendId: String, text: String) {
        val newMsg = ChatMessage(
            id = "m_${System.currentTimeMillis()}",
            senderId = _userProfile.value.id,
            text = text,
            timestampFormatted = "Just now",
            isFromMe = true,
            isDelivered = true,
            isRead = false
        )
        _chatMessages.update { map ->
            val currentList = map[friendId] ?: emptyList()
            map + (friendId to (currentList + newMsg))
        }

        // Simulate a friendly regional reply from the friend
        simulateFriendReply(friendId, text)
    }

    private fun simulateFriendReply(friendId: String, userText: String) {
        val friend = _friends.value.find { it.id == friendId } ?: return
        val reply = when {
            userText.contains("coffee", ignoreCase = true) || userText.contains("chai", ignoreCase = true) ->
                "Sounds wonderful! There is a great spot nearby on 80 Feet Road. Let's meet up around 5:30 PM!"
            userText.contains("namaste", ignoreCase = true) || userText.contains("राम", ignoreCase = true) ->
                "${friend.primaryLanguage.greeting} Kaise hain aap? Glad to hear from a local neighbor on Chaupal."
            userText.contains("kural", ignoreCase = true) || userText.contains("quote", ignoreCase = true) ->
                "Loved reading that! Inspires me to read more classical regional poetry."
            else ->
                "Thanks for the message! Always happy to connect with folks in our neighborhood. How is your day going?"
        }

        val responseMsg = ChatMessage(
            id = "m_resp_${System.currentTimeMillis()}",
            senderId = friendId,
            text = reply,
            timestampFormatted = "Just now",
            isFromMe = false,
            isDelivered = true,
            isRead = true
        )

        _chatMessages.update { map ->
            val currentList = map[friendId] ?: emptyList()
            map + (friendId to (currentList + responseMsg))
        }
    }
}
