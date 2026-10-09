package com.example.localization

import com.example.model.Language

object AppStrings {
    fun appTitle(lang: Language): String = when (lang) {
        Language.HINDI -> "चौपाल"
        Language.BENGALI -> "চৌপাল"
        Language.TELUGU -> "చౌపాల్"
        Language.TAMIL -> "சௌபால்"
        Language.MARATHI -> "चौपाल"
        Language.KANNADA -> "ಚೌಪಾಲ್"
        Language.GUJARATI -> "ચૌપાલ"
        Language.MALAYALAM -> "ചൗപാൽ"
        Language.PUNJABI -> "ਚੌਪਾਲ"
        Language.ENGLISH -> "Chaupal"
    }

    fun appTagline(lang: Language): String = when (lang) {
        Language.HINDI -> "भारतीय स्थानीय समुदाय मंच"
        Language.BENGALI -> "স্থানীয় ভারতীয় সম্প্রদায়"
        Language.TELUGU -> "స్థానిక భారతీయ సమాజం"
        Language.TAMIL -> "உள்ளூர் இந்திய சமூகம்"
        Language.MARATHI -> "स्थानिक भारतीय समुदाय"
        Language.KANNADA -> "ಸ್ಥಳೀಯ ಭಾರತೀಯ ಸಮುದಾಯ"
        Language.GUJARATI -> "સ્થાનિક ભારતીય સમુદાય"
        Language.MALAYALAM -> "പ്രാദേശിക ഭാരതീയ കൂട്ടായ്മ"
        Language.PUNJABI -> "ਸਥਾਨਕ ਭਾਰਤੀ ਭਾਈਚਾਰਾ"
        Language.ENGLISH -> "Local Indian Community"
    }

    // Bottom Navigation
    fun tabFeed(lang: Language): String = when (lang) {
        Language.HINDI -> "मित्र फीड"
        Language.BENGALI -> "বন্ধু ফিড"
        Language.TELUGU -> "మిత్రుల ఫీడ్"
        Language.TAMIL -> "நண்பர்கள் ஃபீட்"
        Language.MARATHI -> "मित्र फीड"
        Language.KANNADA -> "ಮಿತ್ರ ಫೀಡ್"
        Language.GUJARATI -> "મિત્ર ફીડ"
        Language.MALAYALAM -> "മിത്ര ഫീഡ്"
        Language.PUNJABI -> "ਮਿੱਤਰ ਫੀਡ"
        Language.ENGLISH -> "Friends Feed"
    }

    fun tabEvents(lang: Language): String = when (lang) {
        Language.HINDI -> "मेले व कार्यक्रम"
        Language.BENGALI -> "অনুষ্ঠান ও মেলা"
        Language.TELUGU -> "ఉత్సవాలు"
        Language.TAMIL -> "நிகழ்வுகள்"
        Language.MARATHI -> "कार्यक्रम"
        Language.KANNADA -> "ಕಾರ್ಯಕ್ರಮಗಳು"
        Language.GUJARATI -> "કાર્યક્રમો"
        Language.MALAYALAM -> "പരിപാടികൾ"
        Language.PUNJABI -> "ਮੇਲੇ ਤੇ ਸਮਾਗਮ"
        Language.ENGLISH -> "Local Events"
    }

    fun translateAction(lang: Language): String = when (lang) {
        Language.HINDI -> "अनुवाद देखें"
        Language.BENGALI -> "অনুবাদ দেখুন"
        Language.TELUGU -> "అనువాదం చూడండి"
        Language.TAMIL -> "மொழிபெயர்ப்பு"
        Language.MARATHI -> "भाषांतर पहा"
        Language.KANNADA -> "ಅನುವಾದ ನೋಡಿ"
        Language.GUJARATI -> "અનુવાદ જુઓ"
        Language.MALAYALAM -> "വിവർത്തനം കാണുക"
        Language.PUNJABI -> "ਅਨੁਵਾਦ ਵੇਖੋ"
        Language.ENGLISH -> "Translate"
    }

    fun showOriginalAction(lang: Language): String = when (lang) {
        Language.HINDI -> "मूल रूप देखें"
        Language.BENGALI -> "মূল দেখুন"
        Language.TELUGU -> "అసలుది చూడండి"
        Language.TAMIL -> "அசல் உரை"
        Language.MARATHI -> "मूळ मजकूर"
        Language.KANNADA -> "ಮೂಲ ಪಠ್ಯ"
        Language.GUJARATI -> "મૂળ જુઓ"
        Language.MALAYALAM -> "യഥാർത്ഥ രൂപം"
        Language.PUNJABI -> "ਅਸਲ ਵੇਖੋ"
        Language.ENGLISH -> "Show Original"
    }

    fun tabExplore(lang: Language): String = when (lang) {
        Language.HINDI -> "भू-खोज"
        Language.BENGALI -> "ভূ-অনুসন্ধান"
        Language.TELUGU -> "ప్రాంత శోధన"
        Language.TAMIL -> "புவி தேடல்"
        Language.MARATHI -> "भू-शोध"
        Language.KANNADA -> "ಭೂ-ಶೋಧನೆ"
        Language.GUJARATI -> "ભૂ-શોધ"
        Language.MALAYALAM -> "ഭൂപടം"
        Language.PUNJABI -> "ਖੇਤਰੀ ਖੋਜ"
        Language.ENGLISH -> "Map Search"
    }

    fun tabMessages(lang: Language): String = when (lang) {
        Language.HINDI -> "वार्ता"
        Language.BENGALI -> "বার্তা"
        Language.TELUGU -> "సందేశాలు"
        Language.TAMIL -> "உரையாடல்"
        Language.MARATHI -> "संवाद"
        Language.KANNADA -> "ಸಂದೇಶಗಳು"
        Language.GUJARATI -> "વાર્તાલાપ"
        Language.MALAYALAM -> "സന്ദേശങ്ങൾ"
        Language.PUNJABI -> "ਗੱਲਬਾਤ"
        Language.ENGLISH -> "Messages"
    }

    fun tabReels(lang: Language): String = when (lang) {
        Language.HINDI -> "गली रील्स"
        Language.BENGALI -> "পাড়া রিলস"
        Language.TELUGU -> "వీధి రీల్స్"
        Language.TAMIL -> "தெரு ரீல்ஸ்"
        Language.MARATHI -> "गल्ली रील्स"
        Language.KANNADA -> "ಗಲ್ಲಿ ರೀಲ್ಸ್"
        Language.GUJARATI -> "શેરી રીલ્સ"
        Language.MALAYALAM -> "നാട്ടു റീൽസ്"
        Language.PUNJABI -> "ਗਲੀ ਰੀਲਜ਼"
        Language.ENGLISH -> "Local Reels"
    }

    fun tabProfile(lang: Language): String = when (lang) {
        Language.HINDI -> "मेरी पहचान"
        Language.BENGALI -> "আমার পরিচয়"
        Language.TELUGU -> "నా ప్రొఫైల్"
        Language.TAMIL -> "என் சுயவிவரம்"
        Language.MARATHI -> "माझी ओळख"
        Language.KANNADA -> "ನನ್ನ ಪರಿಚಯ"
        Language.GUJARATI -> "મારી ઓળખ"
        Language.MALAYALAM -> "എന്റെ പ്രൊഫൈൽ"
        Language.PUNJABI -> "ਮੇਰੀ ਪਛਾਣ"
        Language.ENGLISH -> "Profile"
    }

    // Common Buttons & Labels
    fun connectButton(lang: Language): String = when (lang) {
        Language.HINDI -> "मित्रता जोड़ें"
        Language.BENGALI -> "বন্ধু বানান"
        Language.TELUGU -> "స్నేహం చేసుకోండి"
        Language.TAMIL -> "இணையுங்கள்"
        Language.MARATHI -> "मित्रता करा"
        Language.KANNADA -> "ಸ್ನೇಹ ಬೆಳೆಸಿ"
        Language.GUJARATI -> "મિત્રતા જોડો"
        Language.MALAYALAM -> "കൂട്ടുകൂടുക"
        Language.PUNJABI -> "ਮਿੱਤਰ ਬਣੋ"
        Language.ENGLISH -> "Connect"
    }

    fun requestedStatus(lang: Language): String = when (lang) {
        Language.HINDI -> "अनुरोध भेजा गया"
        Language.BENGALI -> "অনুরোধ পাঠানো হয়েছে"
        Language.TELUGU -> "అభ్యర్థన పంపబడింది"
        Language.TAMIL -> "கோரப்பட்டது"
        Language.MARATHI -> "विनंती पाठवली"
        Language.KANNADA -> "ವಿನಂತಿ ಕಳುಹಿಸಲಾಗಿದೆ"
        Language.GUJARATI -> "વિનંતી મોકલાઈ"
        Language.MALAYALAM -> "അഭ്യർത്ഥിച്ചു"
        Language.PUNJABI -> "ਬੇਨਤੀ ਭੇਜੀ"
        Language.ENGLISH -> "Requested"
    }

    fun connectedStatus(lang: Language): String = when (lang) {
        Language.HINDI -> "मित्र हैं ✓"
        Language.BENGALI -> "বন্ধু ✓"
        Language.TELUGU -> "స్నేహితులు ✓"
        Language.TAMIL -> "நண்பர் ✓"
        Language.MARATHI -> "मित्र ✓"
        Language.KANNADA -> "ಸ್ನೇಹಿತರು ✓"
        Language.GUJARATI -> "મિત્ર ✓"
        Language.MALAYALAM -> "കൂട്ടുകാർ ✓"
        Language.PUNJABI -> "ਮਿੱਤਰ ✓"
        Language.ENGLISH -> "Friends ✓"
    }

    fun acceptButton(lang: Language): String = when (lang) {
        Language.HINDI -> "स्वीकार करें"
        Language.BENGALI -> "গ্রহণ করুন"
        Language.TELUGU -> "అంగీకరించు"
        Language.TAMIL -> "ஏற்றுக்கொள்"
        Language.MARATHI -> "स्वीकारा"
        Language.KANNADA -> "ಸ್ವೀಕರಿಸಿ"
        Language.GUJARATI -> "સ્વીકારો"
        Language.MALAYALAM -> "സ്വീകരിക്കുക"
        Language.PUNJABI -> "ਸਵੀਕਾਰ ਕਰੋ"
        Language.ENGLISH -> "Accept"
    }

    fun declineButton(lang: Language): String = when (lang) {
        Language.HINDI -> "अस्वीकार"
        Language.BENGALI -> "প্রত্যাখ্যান"
        Language.TELUGU -> "తిరస్కరించు"
        Language.TAMIL -> "நிராகரி"
        Language.MARATHI -> "नाकारा"
        Language.KANNADA -> "ತಿರಸ್ಕರಿಸಿ"
        Language.GUJARATI -> "અસ્વીકાર"
        Language.MALAYALAM -> "നിരസിക്കുക"
        Language.PUNJABI -> "ਰੱਦ ਕਰੋ"
        Language.ENGLISH -> "Decline"
    }

    fun createPost(lang: Language): String = when (lang) {
        Language.HINDI -> "नई पोस्ट साझा करें"
        Language.BENGALI -> "নতুন পোস্ট শেয়ার করুন"
        Language.TELUGU -> "కొత్త పోస్ట్ పంచుకోండి"
        Language.TAMIL -> "புதிய பதிவு"
        Language.MARATHI -> "नवीन पोस्ट शेअर करा"
        Language.KANNADA -> "ಹೊಸ ಪೋಸ್ಟ್ ಹಂಚಿಕೊಳ್ಳಿ"
        Language.GUJARATI -> "નવી પોસ્ટ શેર કરો"
        Language.MALAYALAM -> "പുതിയ പോസ്റ്റ്"
        Language.PUNJABI -> "ਨਵੀਂ ਪੋਸਟ ਸਾਂਝੀ ਕਰੋ"
        Language.ENGLISH -> "Share Post"
    }

    fun shareQuote(lang: Language): String = when (lang) {
        Language.HINDI -> "सुविचार लिखें"
        Language.BENGALI -> "উক্তি লিখুন"
        Language.TELUGU -> "సూక్తి రాయండి"
        Language.TAMIL -> "பொன்மொழி"
        Language.MARATHI -> "सुविचार लिहा"
        Language.KANNADA -> "ಸುಭಾಷಿತ ಬರೆಯಿರಿ"
        Language.GUJARATI -> "સુવિચાર લખો"
        Language.MALAYALAM -> "പൊൻമൊഴി"
        Language.PUNJABI -> "ਸੁਵਿਚਾਰ ਲਿਖੋ"
        Language.ENGLISH -> "Write Quote"
    }

    fun sharePhoto(lang: Language): String = when (lang) {
        Language.HINDI -> "तस्वीर"
        Language.BENGALI -> "ছবি"
        Language.TELUGU -> "చిత్రం"
        Language.TAMIL -> "புகைப்படம்"
        Language.MARATHI -> "फोटो"
        Language.KANNADA -> "ಚಿತ್ರ"
        Language.GUJARATI -> "ફોટો"
        Language.MALAYALAM -> "ചിത്രം"
        Language.PUNJABI -> "ਤਸਵੀਰ"
        Language.ENGLISH -> "Photo"
    }

    fun shareVideo(lang: Language): String = when (lang) {
        Language.HINDI -> "वीडियो"
        Language.BENGALI -> "ভিডিও"
        Language.TELUGU -> "వీడియో"
        Language.TAMIL -> "வீடியோ"
        Language.MARATHI -> "व्हिडिओ"
        Language.KANNADA -> "ವೀಡಿಯೊ"
        Language.GUJARATI -> "વિડીયો"
        Language.MALAYALAM -> "വീഡിയോ"
        Language.PUNJABI -> "ਵੀਡੀਓ"
        Language.ENGLISH -> "Video"
    }

    fun friendsOnlyBanner(lang: Language): String = when (lang) {
        Language.HINDI -> "🔒 निजी मित्र चौपाल — केवल स्वीकृत मित्रों की पोस्ट यहाँ दिखती हैं"
        Language.BENGALI -> "🔒 ব্যক্তিগত চৌপাল — কেবল সংযুক্ত বন্ধুদের পোস্ট এখানে দৃশ্যমান"
        Language.TELUGU -> "🔒 ప్రైవేట్ చౌపాల్ — కేవలం ఆమోదించబడిన మిత్రుల పోస్ట్‌లు మాత్రమే ఇక్కడ కనిపిస్తాయి"
        Language.TAMIL -> "🔒 நண்பர்கள் மட்டும் — அங்கீகரிக்கப்பட்ட நண்பர்களின் பதிவுகள் மட்டும்"
        Language.MARATHI -> "🔒 खाजगी मित्र चौपाल — केवळ जोडलेल्या मित्रांच्या पोस्ट इथे दिसतील"
        Language.KANNADA -> "🔒 ಮಿತ್ರರ ಚೌಪಾಲ್ — ಸ್ವೀಕರಿಸಲ್ಪಟ್ಟ ಸ್ನೇಹಿತರ ಪೋಸ್ಟ್‌ಗಳು ಮಾತ್ರ ಇಲ್ಲಿ ಕಾಣಿಸುತ್ತವೆ"
        Language.GUJARATI -> "🔒 મિત્રો માત્ર — સ્વીકારેલા મિત્રોની પોસ્ટ જ અહીં દેખાશે"
        Language.MALAYALAM -> "🔒 സ്വകാര്യ ചൗപാൽ — അംഗീകരിച്ച കൂട്ടുകാരുടെ പോസ്റ്റുകൾ മാത്രം"
        Language.PUNJABI -> "🔒 ਨਿੱਜੀ ਚੌਪਾਲ — ਸਿਰਫ਼ ਜੁੜੇ ਮਿੱਤਰਾਂ ਦੀਆਂ ਪੋਸਟਾਂ ਹੀ ਇੱਥੇ ਦਿਖਣਗੀਆਂ"
        Language.ENGLISH -> "🔒 Friends Chaupal — Only posts from your connected friends appear here"
    }

    // Regional Quick Greetings for Chat
    val regionalGreetings = listOf(
        "नमस्ते 🙏",
        "राम राम जी 🙏",
        "নমস্কার ☕",
        "வணக்கம் 🙏",
        "నమస్కారం ☕",
        "नमस्कार 👋",
        "ನಮಸ್ಕಾರ 🍛",
        "કેમ છો? 👋",
        "Sat Sri Akaal 🙏",
        "Chai ho jaye? ☕"
    )
}
