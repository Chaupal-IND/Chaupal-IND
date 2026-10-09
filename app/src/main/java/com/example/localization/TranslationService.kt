package com.example.localization

import com.example.model.Comment
import com.example.model.Language
import com.example.model.Post

object TranslationService {

    // Pre-computed high-fidelity regional translations for community feed posts
    private val postCaptionTranslations = mapOf(
        "p1" to mapOf(
            Language.HINDI.code to "ब्राह्मिन कॉफी बार में सुबह का फिल्टर कॉफी नियम। यहाँ का झाग और खुशबू कभी नहीं बदलती! गर्म इडली-वड़े के साथ सप्ताहांत की शानदार शुरुआत। ☕✨",
            Language.KANNADA.code to "ಬ್ರಾಹ್ಮಿನ್ಸ್ ಕಾಫಿ ಬಾರ್‌ನಲ್ಲಿ ಬೆಳಗಿನ ಫಿಲ್ಟರ್ ಕಾಫಿ ಸವಿಯುವ ಖುಷಿ. ಇಲ್ಲಿನ ನೊರೆ ಮತ್ತು ಪರಿಮಳ ಎಂದಿಗೂ ಬದಲಾಗುವುದಿಲ್ಲ! ಬಿಸಿ ಇಡ್ಲಿ-ವಡೆಯೊಂದಿಗೆ ವಾರಾಂತ್ಯದ ಸುಂದರ ಆರಂಭ. ☕✨",
            Language.TAMIL.code to "பிராமின்ஸ் காபி பாரில் காலை ஃபில்டர் காபி சம்பிரதாயம். இதன் நுரையும் நறுமணமும் ஒருபோதும் மாறுவதில்லை! சூடான இட்லி-வடையுடன் வார இறுதி இனிதாகத் தொடங்குகிறது. ☕✨",
            Language.TELUGU.code to "బ్రాహ్మిన్స్ కాఫీ బార్‌లో ఉదయపు ఫిల్టర్ కాఫీ ఆనందం. ఇక్కడి నురుగు మరియు సువాసన ఎప్పటికీ మారవు! వేడి వేడి ఇడ్లీ-వడతో వీకెండ్ అద్భుతమైన ప్రారంభం. ☕✨",
            Language.BENGALI.code to "ব্রাহ্মিনস কফি বারে সকালের ফিল্টার কফির আনন্দ। এখানকার ফেনা আর সুবাস কখনোই বদলায় না! গরম ইডলি-বড়ার সাথে ছুটির দিনের সুন্দর সূচনা। ☕✨",
            Language.MARATHI.code to "ब्राह्मण कॉफी बारमध्ये सकाळची फिल्टर कॉफी. इथला फेस आणि सुगंध कधीच बदलत नाही! गरमागरम इडली-वड्यासह वीकेंडची छान सुरुवात. ☕✨",
            Language.GUJARATI.code to "બ્રાહ્મિન કોફી બારમાં સવારની ફિલ્ટર કોફી. અહીંનો ફીણ અને સુગંધ ક્યારેય બદલાતી નથી! ગરમ ઈડલી-વડા સાથે વીકેન્ડની સરસ શરૂઆત. ☕✨",
            Language.MALAYALAM.code to "ബ്രാഹ്മിൺസ് കോഫി ബാറിലെ പ്രഭാത ഫിൽട്ടർ കോഫി ശീലം. ഇവിടുത്തെ പതയും മണവും ഒരിക്കലും മാറില്ല! ചൂടുള്ള ഇഡ്ഡലി-വടയോടെ വാരാന്ത്യത്തിന്റെ മനോഹരമായ തുടക്കം. ☕✨",
            Language.PUNJABI.code to "ਬ੍ਰਾਹਮਣ ਕੌਫ਼ੀ ਬਾਰ ਵਿੱਚ ਸਵੇਰ ਦੀ ਫ਼ਿਲਟਰ ਕੌਫ਼ੀ ਦਾ ਸੁਆਦ। ਇੱਥੋਂ ਦਾ ਝੱਗ ਅਤੇ ਖ਼ੁਸ਼ਬੂ ਕਦੇ ਨਹੀਂ ਬਦਲਦੀ! ਗਰਮ ਇਡਲੀ-ਵੜੇ ਨਾਲ ਹਫ਼ਤੇ ਦੇ ਅੰਤ ਦੀ ਵਧੀਆ ਸ਼ੁਰੂਆਤ। ☕✨",
            Language.ENGLISH.code to "Morning filter coffee ritual at Brahmin's Coffee Bar. The froth and aroma here never changes! Starting the weekend right with hot idli-vada. ☕✨"
        ),
        "p2" to mapOf(
            Language.HINDI.code to "सुबह की ठंडी हवा में नदी किनारे संत कबीर की वाणी का पाठ करने पर गहरा अर्थ समझ आता है। आज इस पर विचार कर रहा हूँ:",
            Language.ENGLISH.code to "Saint Kabir's wisdom hits differently when recited by the river in the morning breeze. Reflecting on this today:",
            Language.BENGALI.code to "ভোরের স্নিগ্ধ নদীর হাওয়ায় সন্ত কবিরের অমর বাণী মনে অন্য অনুভূতি জাগায়। আজ এই বিষয়ে চিন্তা করছি:",
            Language.TAMIL.code to "காலை இளங்காற்றில் நதிக்கரையில் அமர்ந்து கபீர் தாசரின் ஞான மொழிகளை நினைவுகூரும்போது ஆழமான அமைதி கிடைக்கிறது:",
            Language.TELUGU.code to "ఉదయం నదీ తీరాన చల్లని గాలిలో సంత్ కబీర్ అమృతవాణిని స్మరించుకుంటే మనసుకి ఎంతో ప్రశాంతత లభిస్తుంది:",
            Language.KANNADA.code to "ಬೆಳಗಿನ ತಂಗಾಳಿಯಲ್ಲಿ ನದಿಯ ದಡದಲ್ಲಿ ಸಂತ ಕಬೀರರ ನುಡಿಗಳನ್ನು ನೆನೆಸಿಕೊಂಡಾಗ ಆಳವಾದ ಅರಿವು ಮೂಡುತ್ತದೆ. ಇಂದು ಇದರ ಚಿಂತನೆ:",
            Language.MARATHI.code to "सकाळच्या वाऱ्यावर नदीकाठी संत कबीरांचे विचार ऐकताना मनाला वेगळीच अनुभूती मिळते. आज त्यावर मनन करतोय:",
            Language.GUJARATI.code to "સવારની નદી કિનારાની પવનની લહેરોમાં સંત કબીરની વાણી સાંભળતાં ઊંડો અહેસાસ થાય છે. આજે આ બાબતે વિચારણા:",
            Language.MALAYALAM.code to "പ്രഭാതത്തിലെ നദിക്കരയിലെ കാറ്റിൽ സന്ത് കബീറിന്റെ വാക്കുകൾ ഉരുവിടുമ്പോൾ മനസ്സിന് ആഴത്തിലുള്ള വെളിച്ചം ലഭിക്കുന്നു:",
            Language.PUNJABI.code to "ਸਵੇਰ ਦੀ ਨਦੀ ਕਿਨਾਰੇ ਦੀ ਠੰਡੀ ਹਵਾ ਵਿੱਚ ਸੰਤ ਕਬੀਰ ਜੀ ਦੀ ਬਾਣੀ ਯਾਦ ਕਰਨ 'ਤੇ ਡੂੰਘਾ ਸਕੂਨ ਮਿਲਦਾ ਹੈ:"
        ),
        "p3" to mapOf(
            Language.ENGLISH.code to "Sharing my favorite Kural on knowledge and lifelong learning for our local book club: 'Learn flawlessly that which is worth learning, and then live according to it.'",
            Language.HINDI.code to "हमारे स्थानीय पुस्तक क्लब के लिए निरंतर ज्ञानार्जन पर मेरी पसंदीदा तिरुक्कुरल: 'जो सीखने योग्य है उसे त्रुटिहीन रूप से सीखो, और फिर उसके अनुसार आचरण करो।'",
            Language.BENGALI.code to "আমাদের স্থানীয় বই ক্লাবের জন্য জ্ঞান ও আজীবন শিক্ষার ওপর আমার প্রিয় তিরুক্কুরাল: 'যা শেখার যোগ্য তা নিখুঁতভাবে শেখো, এবং তার পর সেই অনুযায়ী জীবনযাপন করো।'",
            Language.TELUGU.code to "మన స్థానిక పుస్తక క్లబ్ కోసం నిరంతర జ్ఞానార్జనపై నా అభిమాన తిరుక్కురల్: 'నేర్చుకోవలసినది చక్కగా నేర్చుకో, ఆ తర్వాత దాని ప్రకారం జీవించు.'",
            Language.TAMIL.code to "நமது உள்ளூர் புத்தகக் குழுவிற்காக அறிவாற்றல் மற்றும் தொடர் கற்றல் பற்றிய எனது விருப்பமான திருக்குறள்: 'கற்க கசடறக் கற்பவை கற்றபின் நிற்க அதற்குத் தக.'",
            Language.KANNADA.code to "ನಮ್ಮ ಸ್ಥಳೀಯ ಪುಸ್ತಕ ಕ್ಲಬ್‌ಗಾಗಿ ನಿರಂತರ ಕಲಿಕೆಯ ಕುರಿತಾದ ನನ್ನ ನೆಚ್ಚಿನ ತಿರುಕ್ಕುರಳ್: 'ಕಲಿಯಬೇಕಾದುದನ್ನು ದೋಷವಿಲ್ಲದೆ ಕಲಿ, ಕಲಿತ ನಂತರ ಅದರಂತೆ ನಡೆದುಕೋ.'",
            Language.MARATHI.code to "आपल्या स्थानिक वाचक क्लबसाठी ज्ञान आणि अखंड शिक्षणावरील माझा आवडता तिरुक्कुरल: 'जे शिकण्यासारखे आहे ते परिपूर्णपणे शिका, आणि त्यानुसार आचरण करा.'",
            Language.GUJARATI.code to "આપણા સ્થાનિક બુક ક્લબ માટે સતત શીખવા પર મારો પ્રિય તિરુક્કુરલ: 'જે શીખવા જેવું છે તે નિર્દોષપણે શીખો, અને પછી તે મુજબ જીવન જીવો.'",
            Language.MALAYALAM.code to "നമ്മുടെ പ്രാദേശിക പുസ്തക കൂട്ടായ്മയ്ക്കായി വിജ്ഞാനത്തെയും നിരന്തര പഠനത്തെയും കുറിച്ചുള്ള പ്രിയപ്പെട്ട തിരുക്കുറൾ:",
            Language.PUNJABI.code to "ਸਾਡੇ ਸਥਾਨਕ ਕਿਤਾਬ ਕਲੱਬ ਲਈ ਗਿਆਨ ਅਤੇ ਸਿੱਖਿਆ 'ਤੇ ਮੇਰੀ ਪਸੰਦੀਦਾ ਤਿਰੂਕੁਰਲ: 'ਜੋ ਸਿੱਖਣ ਯੋਗ ਹੈ ਉਸਨੂੰ ਚੰਗੀ ਤਰ੍ਹਾਂ ਸਿੱਖੋ, ਅਤੇ ਫਿਰ ਉਸ ਅਨੁਸਾਰ ਜੀਵਨ ਬਤੀਤ ਕਰੋ।'"
        ),
        "p4" to mapOf(
            Language.HINDI.code to "घाटों पर गोधूलि वेला की असीम शांति। संध्या महाआरती के लिए शंख और घंटियों के बीच पवित्र गंगा में तैरते मिट्टी के दीये। इसे अपना घर कहने पर गर्व है। 🪔✨",
            Language.ENGLISH.code to "Twilight serenity at the Ghats. Earthen diyas floating into the sacred Ganga as bells chime for evening aarti. Blessed to call this home. 🪔✨",
            Language.BENGALI.code to "ঘাটগুলিতে গোধূলিলগ্নের অপার শান্তি। সান্ধ্য মহাতীর সময় পবিত্র গঙ্গায় ভেসে চলা মাটির প্রদীপ। একে নিজের বাড়ি বলতে পেরে ধন্য মনে হয়। 🪔✨",
            Language.TAMIL.code to "படித்துறைகளில் அந்திப் பொழுதின் தெய்வீக அமைதி. மாலை ஆரத்திக்கு மணிகள் ஒலிக்கும் போது புனித கங்கையில் மிதக்கும் மண் அகல் விளக்குகள். 🪔✨",
            Language.TELUGU.code to "ఘాట్ల వద్ద సంధ్యా సమయపు ప్రశాంతత. సాయంత్రం హారతికి గంటలు మోగుతుండగా పవిత్ర గంగలో తేలియాడే మట్టి దీపాలు. 🪔✨",
            Language.KANNADA.code to "ಘಾಟ್‌ಗಳಲ್ಲಿ ಸಂಜೆಯ ಮುಸ್ಸಂಜೆಯ ದೈವಿಕ ಶಾಂತಿ. ಸಂಜೆಯ ಮಹಾ ಆರತಿಗೆ ಗಂಟೆಗಳು ಮೊಳಗುತ್ತಿದ್ದಂತೆ ಪವಿತ್ರ ಗಂಗೆಯಲ್ಲಿ ತೇಲುವ ಮಣ್ಣಿನ ದೀಪಗಳು. 🪔✨",
            Language.MARATHI.code to "घाटांवर संध्याकाळची अथांग शांतता. सायंकाळच्या आरतीच्या घंटांच्या नादात पवित्र गंगेमध्ये तरंगते मातीचे दिवे. 🪔✨",
            Language.GUJARATI.code to "ઘાટ પર સંધ્યાકાળની અદભૂત શાંતિ. સાંજની આરતીના ઘંટનાદ સાથે પવિત્ર ગંગામાં તરતા માટીના દીવાઓ. 🪔✨",
            Language.MALAYALAM.code to "ഘട്ടുകളിലെ സന്ധ്യാസമയത്തെ ശാന്തത. സന്ധ്യാ ആരതിക്കായി മണികൾ മുഴങ്ങുമ്പോൾ പുണ്യഗംഗയിൽ ഒഴുകിനടക്കുന്ന മൺചിരാതുകൾ. 🪔✨",
            Language.PUNJABI.code to "ਘਾਟਾਂ 'ਤੇ ਸੰਝ ਦੀ ਅਪਾਰ ਸ਼ਾਂਤੀ। ਸ਼ਾਮ ਦੀ ਆਰਤੀ ਲਈ ਘੰਟੀਆਂ ਦੀ ਗੂੰਜ ਵਿਚਕਾਰ ਪਵਿੱਤਰ ਗੰਗਾ 'ਚ ਤੈਰਦੇ ਮਿੱਟੀ ਦੇ ਦੀਵੇ। 🪔✨"
        ),
        "p5" to mapOf(
            Language.HINDI.code to "इन्दिरा नगर 12वीं मेन के पास स्थानीय सांस्कृतिक उत्सव में सहज यक्षगान ढोल प्रस्तुति! इसकी ऊर्जा और लय कमाल की थी! 🥁",
            Language.ENGLISH.code to "Spontaneous Yakshagana drum jamming during the community cultural festival near 12th Main Indiranagar! The rhythm was electric! 🥁",
            Language.KANNADA.code to "ಇಂದಿರಾನಗರ 12ನೇ ಮುಖ್ಯರಸ್ತೆ ಬಳಿ ನಡೆದ ಸಮುದಾಯ ಉತ್ಸವದಲ್ಲಿ ಅದ್ಭುತ ಯಕ್ಷಗಾನ ಚಂಡೆ-ಮದ್ದಳೆ ಜುಗಲ್‌ಬಂದಿ! ಆ ಲಯ ಮತ್ತು ಶಕ್ತಿ ರೋಮಾಂಚನಕಾರಿಯಾಗಿತ್ತು! 🥁",
            Language.TAMIL.code to "இந்திரா நகர் 12வது மெயின் அருகே உள்ளூர் கலாச்சார விழாவில் யக்ஷகான மேள வாசிப்பு! அதன் தாளமும் உற்சாகமும் அபாரம்! 🥁",
            Language.TELUGU.code to "ఇందిరానగర్ 12వ మెయిన్ వద్ద స్థానిక సాంస్కృతిక ఉత్సవంలో యక్షగానం డప్పుల సందడి! ఆ లయ ఎంతో ఉత్సాహభరితంగా ఉంది! 🥁",
            Language.BENGALI.code to "ইন্দিরানগর ১২তম মেইনের কাছে পাড়ার সাংস্কৃতিক উৎসবে তাৎক্ষণিক যক্ষগান ঢাকের বাজনা! তালটি ছিল দারুণ উদ্দীপনাময়! 🥁",
            Language.MARATHI.code to "इंदिरानगर १२व्या मेन जवळ स्थानिक सांस्कृतिक उत्सवात अप्रतिम यक्षगान ढोल वादन! तालाचा उत्साह थक्क करणारा होता! 🥁",
            Language.GUJARATI.code to "ઇન્દિરાનગર ૧૨મા મેઈન પાસે સ્થાનિક ઉત્સવમાં યક્ષગાન ઢોલનું ઉત્સાહી વાદન! તાલ ખૂબ જ અદભૂત હતો! 🥁",
            Language.MALAYALAM.code to "ഇന്ദിരാനഗർ 12-ാം മെയിനിന് സമീപം നടന്ന സാംസ്കാരിക ഉത്സവത്തിൽ യക്ഷഗാന ചെണ്ടമേളം! താളം അത്ഭുതാവഹമായിരുന്നു! 🥁",
            Language.PUNJABI.code to "ਇੰਦਰਾ ਨਗਰ 12ਵੀਂ ਮੇਨ ਕੋਲ ਸਥਾਨਕ ਸੱਭਿਆਚਾਰਕ ਮੇਲੇ ਵਿੱਚ ਯਕਸ਼ਗਾਨ ਢੋਲ ਦੀ ਪੇਸ਼ਕਾਰੀ! ਊਰਜਾ ਲਾਜਵਾਬ ਸੀ! 🥁"
        )
    )

    // Pre-computed comment translations
    private val commentTranslations = mapOf(
        "c1" to mapOf(
            Language.HINDI.code to "बैंगलोर में सबसे बेहतरीन फिल्टर कॉफी, इसमें कोई शक नहीं! इनका कुरकुरा वड़ा बहुत पसंद है।",
            Language.ENGLISH.code to "Best filter coffee in Bangalore hands down! Love their crispy vada.",
            Language.KANNADA.code to "ಬೆಂಗಳೂರಿನಲ್ಲಿ ನಿಸ್ಸಂದೇಹವಾಗಿ ಅತ್ಯುತ್ತಮ ಫಿಲ್ಟರ್ ಕಾಫಿ! ಇಲ್ಲಿನ ಗರಿಗರಿಯಾದ ವಡೆ ತುಂಬಾ ಇಷ್ಟ.",
            Language.TAMIL.code to "பெங்களூரில் சந்தேகமே இல்லாமல் சிறந்த ஃபில்டர் காபி! இவர்களது மொறுமொறு வடை அருமை."
        ),
        "c2" to mapOf(
            Language.HINDI.code to "अगली बार जब आप वहां जाएं, तो उनका खारा भात भी जरूर चखें। लाजवाब!",
            Language.ENGLISH.code to "Next time you are there, try their khara bath too. Unbeatable!",
            Language.KANNADA.code to "ಮುಂದಿನ ಬಾರಿ ಹೋದಾಗ ಅವರ ಖಾರಾ ಬಾತ್ ಕೂಡ ಸವಿಯಿರಿ. ಸಾಟಿಯಿಲ್ಲದ್ದು!",
            Language.TAMIL.code to "அடுத்த முறை அங்கு செல்லும்போது அவர்களது காரா பாத் கூட முயற்சிக்கவும். அற்புதமானது!"
        ),
        "c4" to mapOf(
            Language.HINDI.code to "सदाबहार सच। आत्म-निरीक्षण ही ज्ञान की शुरुआत है।",
            Language.ENGLISH.code to "Timeless truth. Self-introspection is the beginning of wisdom.",
            Language.KANNADA.code to "ಸದಾಕಾಲಿಕ ಸತ್ಯ. ಆತ್ಮಾವಲೋಕನವೇ ಜ್ಞಾನದ ಆರಂಭ.",
            Language.BENGALI.code to "চিরন্তন সত্য। আত্ম-অনুসন্ধানই প্রজ্ঞার সূচনা।"
        ),
        "c6" to mapOf(
            Language.HINDI.code to "अद्भुत दृश्य देवेन्द्र जी। देव दीपावली के समय काशी की बहुत याद आती है।",
            Language.ENGLISH.code to "Breathtaking view Devendra ji. Miss visiting Kashi during Dev Deepavali.",
            Language.TAMIL.code to "மெய்சிலிர்க்க வைக்கும் காட்சி தேவேந்திரா ஜி. தேவ தீபாவளி காலத்தில் காசிக்கு செல்வதை மிஸ் செய்கிறேன்."
        )
    )

    fun getTranslatedCaption(post: Post, targetLang: Language): String {
        if (targetLang == Language.ENGLISH && post.caption.all { it.code < 128 }) {
            return post.caption
        }
        val fromPrecomputed = postCaptionTranslations[post.id]?.get(targetLang.code)
        if (fromPrecomputed != null) return fromPrecomputed

        // Fallback translation: add regional attribution
        return when (targetLang) {
            Language.HINDI -> "अनुवाद (${targetLang.nativeName}): " + post.caption
            Language.BENGALI -> "অনুবাদ (${targetLang.nativeName}): " + post.caption
            Language.TELUGU -> "అనువాదం (${targetLang.nativeName}): " + post.caption
            Language.TAMIL -> "மொழிபெயர்ப்பு (${targetLang.nativeName}): " + post.caption
            Language.KANNADA -> "ಅನುವಾದ (${targetLang.nativeName}): " + post.caption
            Language.MARATHI -> "भाषांतर (${targetLang.nativeName}): " + post.caption
            Language.GUJARATI -> "અનુવાદ (${targetLang.nativeName}): " + post.caption
            Language.MALAYALAM -> "വിവർത്തനം (${targetLang.nativeName}): " + post.caption
            Language.PUNJABI -> "ਅਨੁਵਾਦ (${targetLang.nativeName}): " + post.caption
            Language.ENGLISH -> "Translated into English: " + post.caption
        }
    }

    fun getTranslatedQuote(post: Post, targetLang: Language): String {
        return when (targetLang) {
            Language.ENGLISH -> when (post.id) {
                "p2" -> "Kabir says: I went searching for evil in the world, but found no evil anywhere. When I looked inside my own heart, no one was more flawed than me."
                "p3" -> "Learn flawlessly what is worthy to be learned; and once learned, live strictly in accordance with that learning."
                else -> post.quoteText
            }
            Language.HINDI -> when (post.id) {
                "p3" -> "जो सीखने योग्य है उसे निष्कलंक रूप से सीखो; और सीखने के बाद, उसी ज्ञान के अनुसार दृढ़ता से जीवन जियो।"
                else -> post.quoteText
            }
            Language.KANNADA -> when (post.id) {
                "p2" -> "ನಾನು ಕೆಟ್ಟದ್ದನ್ನು ಹುಡುಕಲು ಹೊರಟೆ, ಯಾರಲ್ಲೂ ಕೆಟ್ಟದ್ದು ಕಾಣಲಿಲ್ಲ. ನನ್ನದೇ ಮನಸ್ಸನ್ನು ಪರೀಕ್ಷಿಸಿದಾಗ, ನನಗಿಂತ ಕೆಟ್ಟವರು ಯಾರೂ ಇರಲಿಲ್ಲ."
                else -> post.quoteText
            }
            Language.TAMIL -> when (post.id) {
                "p2" -> "தீயதை தேடி நான் சென்றேன், எவரிடமும் தீமை காணவில்லை. என் சொந்த மனதை நான் ஆராய்ந்தபோது, என்னை விடத் தீயவர் எவருமில்லை."
                else -> post.quoteText
            }
            Language.BENGALI -> when (post.id) {
                "p2" -> "খারাপ খুঁজতে বেরিয়েছিলাম, কোথাও কোনো খারাপ খুঁজে পাইনি। যখন নিজের মনকে অনুসন্ধান করলাম, দেখলাম আমার চেয়ে খারাপ কেউ নেই।"
                else -> post.quoteText
            }
            else -> post.quoteText
        }
    }

    fun getTranslatedComment(comment: Comment, targetLang: Language): String {
        val fromPrecomputed = commentTranslations[comment.id]?.get(targetLang.code)
        if (fromPrecomputed != null) return fromPrecomputed

        return when (targetLang) {
            Language.HINDI -> "अनुवाद: " + comment.text
            Language.BENGALI -> "অনুবাদ: " + comment.text
            Language.TELUGU -> "అనువాదం: " + comment.text
            Language.TAMIL -> "மொழிபெயர்ப்பு: " + comment.text
            Language.KANNADA -> "ಅನುವಾದ: " + comment.text
            Language.MARATHI -> "भाषांतर: " + comment.text
            Language.GUJARATI -> "અનુવાદ: " + comment.text
            Language.MALAYALAM -> "വിവർത്തനം: " + comment.text
            Language.PUNJABI -> "ਅਨੁਵਾਦ: " + comment.text
            Language.ENGLISH -> "Translated: " + comment.text
        }
    }
}
