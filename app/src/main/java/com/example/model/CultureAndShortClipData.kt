package com.example.model

object GlobalCultureProvider {

    val allLanguagesAndCultures: List<CultureAndLanguage> = listOf(
        // Americas
        CultureAndLanguage(
            code = "en-US",
            name = "English (United States)",
            nativeName = "American English",
            flagEmoji = "🇺🇸",
            region = "Americas",
            culturalAccent = "Hollywood & West Coast neutral with casual pacing",
            sampleVoiceTag = "Alex (Dynamic Studio)"
        ),
        CultureAndLanguage(
            code = "es-MX",
            name = "Spanish (Mexico & Latin America)",
            nativeName = "Español Latinoamericano",
            flagEmoji = "🇲🇽",
            region = "Americas",
            culturalAccent = "Pan-Latino media standard with warm expressive cadence",
            sampleVoiceTag = "Mateo (Warm Baritone)"
        ),
        CultureAndLanguage(
            code = "pt-BR",
            name = "Portuguese (Brazil)",
            nativeName = "Português do Brasil",
            flagEmoji = "🇧🇷",
            region = "Americas",
            culturalAccent = "Vibrant Carioca & Paulistano rhythm with melodic flow",
            sampleVoiceTag = "Thiago (Energetic Creator)"
        ),
        CultureAndLanguage(
            code = "es-AR",
            name = "Spanish (Argentina)",
            nativeName = "Español Rioplatense",
            flagEmoji = "🇦🇷",
            region = "Americas",
            culturalAccent = "Melodic Italian-influenced Rioplatense inflection",
            sampleVoiceTag = "Julian (Cinematic)"
        ),
        CultureAndLanguage(
            code = "fr-CA",
            name = "French (Canada / Québec)",
            nativeName = "Français Canadien",
            flagEmoji = "🇨🇦",
            region = "Americas",
            culturalAccent = "Québécois authentic tone with North American rhythm",
            sampleVoiceTag = "Étienne (Friendly Casual)"
        ),
        CultureAndLanguage(
            code = "es-CO",
            name = "Spanish (Colombia)",
            nativeName = "Español Colombiano",
            flagEmoji = "🇨🇴",
            region = "Americas",
            culturalAccent = "Clear Bogotá articulation with gentle friendly warmth",
            sampleVoiceTag = "Camila (Clear Voice)"
        ),

        // Europe
        CultureAndLanguage(
            code = "en-GB",
            name = "English (United Kingdom)",
            nativeName = "British English",
            flagEmoji = "🇬🇧",
            region = "Europe",
            culturalAccent = "Received Pronunciation (RP) & London contemporary",
            sampleVoiceTag = "Oliver (BBC Documentarian)"
        ),
        CultureAndLanguage(
            code = "es-ES",
            name = "Spanish (Spain / Castilian)",
            nativeName = "Español Castellano",
            flagEmoji = "🇪🇸",
            region = "Europe",
            culturalAccent = "Castilian Madrid formal cadence with crisp consonants",
            sampleVoiceTag = "Alejandro (Broadcast)"
        ),
        CultureAndLanguage(
            code = "fr-FR",
            name = "French (France)",
            nativeName = "Français Métropolitain",
            flagEmoji = "🇫🇷",
            region = "Europe",
            culturalAccent = "Parisian haute élégance with nuanced cinematic cadence",
            sampleVoiceTag = "Antoine (Velvet Cinema)"
        ),
        CultureAndLanguage(
            code = "de-DE",
            name = "German (Germany)",
            nativeName = "Hochdeutsch",
            flagEmoji = "🇩🇪",
            region = "Europe",
            culturalAccent = "Articulate Berlin standard with technical precision",
            sampleVoiceTag = "Lukas (Precision Anchor)"
        ),
        CultureAndLanguage(
            code = "it-IT",
            name = "Italian (Italy)",
            nativeName = "Italiano Standard",
            flagEmoji = "🇮🇹",
            region = "Europe",
            culturalAccent = "Expressive Roman & Milanese passion with musical meter",
            sampleVoiceTag = "Marco (Storyteller)"
        ),
        CultureAndLanguage(
            code = "ru-RU",
            name = "Russian (Russia)",
            nativeName = "Русский",
            flagEmoji = "🇷🇺",
            region = "Europe",
            culturalAccent = "Deep Moscow resonance with authoritative depth",
            sampleVoiceTag = "Dmitri (Deep Voice)"
        ),
        CultureAndLanguage(
            code = "nl-NL",
            name = "Dutch (Netherlands)",
            nativeName = "Nederlands",
            flagEmoji = "🇳🇱",
            region = "Europe",
            culturalAccent = "Crisp Randstad modern Dutch with engaging clarity",
            sampleVoiceTag = "Daan (Modern Clean)"
        ),
        CultureAndLanguage(
            code = "pl-PL",
            name = "Polish (Poland)",
            nativeName = "Polski",
            flagEmoji = "🇵🇱",
            region = "Europe",
            culturalAccent = "Contemporary Warsaw articulation with subtle warmth",
            sampleVoiceTag = "Jan (Crisp Media)"
        ),
        CultureAndLanguage(
            code = "sv-SE",
            name = "Swedish (Sweden)",
            nativeName = "Svenska",
            flagEmoji = "🇸🇪",
            region = "Europe",
            culturalAccent = "Stockholm melodic intonation with friendly clarity",
            sampleVoiceTag = "Erik (Nordic Calm)"
        ),
        CultureAndLanguage(
            code = "pt-PT",
            name = "Portuguese (Portugal)",
            nativeName = "Português Europeu",
            flagEmoji = "🇵🇹",
            region = "Europe",
            culturalAccent = "Lisbon classical pronunciation with closed European vowels",
            sampleVoiceTag = "Rodrigo (Classical)"
        ),

        // Asia-Pacific
        CultureAndLanguage(
            code = "ja-JP",
            name = "Japanese (Tokyo Standard)",
            nativeName = "日本語 (標準語)",
            flagEmoji = "🇯🇵",
            region = "Asia-Pacific",
            culturalAccent = "Polite Kanto media broadcast with respectful modulation",
            sampleVoiceTag = "Kenji (Tokyo Studio)"
        ),
        CultureAndLanguage(
            code = "ja-ANIME",
            name = "Japanese (Anime & Expressive)",
            nativeName = "日本語 (アニメ調)",
            flagEmoji = "🇯🇵",
            region = "Asia-Pacific",
            culturalAccent = "High-energy Shonen/Shojo character voice with dramatic inflection",
            sampleVoiceTag = "Ren (Heroic Character)"
        ),
        CultureAndLanguage(
            code = "hi-IN",
            name = "Hindi (Bollywood & India)",
            nativeName = "हिन्दी (भारत)",
            flagEmoji = "🇮🇳",
            region = "Asia-Pacific",
            culturalAccent = "High-energy Mumbai cinematic flair with emotional warmth",
            sampleVoiceTag = "Aarav (Cinematic Pro)"
        ),
        CultureAndLanguage(
            code = "en-IN",
            name = "English (India / Hinglish)",
            nativeName = "Indian English",
            flagEmoji = "🇮🇳",
            region = "Asia-Pacific",
            culturalAccent = "Urban Indian tech & youth colloquial English blend",
            sampleVoiceTag = "Rohan (Startup Creator)"
        ),
        CultureAndLanguage(
            code = "zh-CN",
            name = "Mandarin Chinese (Mainland)",
            nativeName = "普通话 (中国大陆)",
            flagEmoji = "🇨🇳",
            region = "Asia-Pacific",
            culturalAccent = "Beijing CCTV broadcast standard with crisp four-tone clarity",
            sampleVoiceTag = "Wei (Anchor Standard)"
        ),
        CultureAndLanguage(
            code = "zh-TW",
            name = "Mandarin Chinese (Taiwan)",
            nativeName = "國語 (台灣)",
            flagEmoji = "🇹🇼",
            region = "Asia-Pacific",
            culturalAccent = "Gentle conversational Taipei tone with warm soft tones",
            sampleVoiceTag = "Yu-Ting (Warm Conversational)"
        ),
        CultureAndLanguage(
            code = "ko-KR",
            name = "Korean (Seoul Standard)",
            nativeName = "한국어 (서울)",
            flagEmoji = "🇰🇷",
            region = "Asia-Pacific",
            culturalAccent = "Seoul K-Wave cadence with emotive nuance & drama flair",
            sampleVoiceTag = "Min-Jun (K-Drama Star)"
        ),
        CultureAndLanguage(
            code = "vi-VN",
            name = "Vietnamese (Vietnam)",
            nativeName = "Tiếng Việt",
            flagEmoji = "🇻🇳",
            region = "Asia-Pacific",
            culturalAccent = "Saigon & Hanoi youth rhythm with melodic tonal flow",
            sampleVoiceTag = "Bao (Youth Streamer)"
        ),
        CultureAndLanguage(
            code = "id-ID",
            name = "Indonesian (Bahasa)",
            nativeName = "Bahasa Indonesia",
            flagEmoji = "🇮🇩",
            region = "Asia-Pacific",
            culturalAccent = "Modern Jakarta media standard with friendly engaging cadence",
            sampleVoiceTag = "Reza (Digital Creator)"
        ),
        CultureAndLanguage(
            code = "th-TH",
            name = "Thai (Thailand)",
            nativeName = "ภาษาไทย",
            flagEmoji = "🇹🇭",
            region = "Asia-Pacific",
            culturalAccent = "Polite Bangkok inflection with expressive dramatic curves",
            sampleVoiceTag = "Somchai (Expressive)"
        ),
        CultureAndLanguage(
            code = "en-AU",
            name = "English (Australia)",
            nativeName = "Australian English",
            flagEmoji = "🇦🇺",
            region = "Asia-Pacific",
            culturalAccent = "Coastal Sydney friendly casual with optimistic rise",
            sampleVoiceTag = "Jack (Aussie Mate)"
        ),

        // Middle East & Africa
        CultureAndLanguage(
            code = "ar-SA",
            name = "Arabic (Gulf & Modern Standard)",
            nativeName = "العربية الفصحى (الخليج)",
            flagEmoji = "🇸🇦",
            region = "Middle East & Africa",
            culturalAccent = "Prestigious Modern Standard Arabic with Gulf heritage resonance",
            sampleVoiceTag = "Tariq (Heritage Cinema)"
        ),
        CultureAndLanguage(
            code = "ar-EG",
            name = "Arabic (Egyptian Cinema)",
            nativeName = "العامية المصرية",
            flagEmoji = "🇪🇬",
            region = "Middle East & Africa",
            culturalAccent = "Pan-Arab media favorite with witty cadence & vibrant rhythm",
            sampleVoiceTag = "Omar (Cairo Star)"
        ),
        CultureAndLanguage(
            code = "tr-TR",
            name = "Turkish (Turkey)",
            nativeName = "Türkçe",
            flagEmoji = "🇹🇷",
            region = "Middle East & Africa",
            culturalAccent = "Istanbul broadcast cadence with dramatic vowel harmony",
            sampleVoiceTag = "Emre (Turkish Drama)"
        )
    )

    fun getCultureByCode(code: String): CultureAndLanguage {
        return allLanguagesAndCultures.find { it.code == code } ?: allLanguagesAndCultures.first()
    }
}

object ShortClipAnalyzer {

    fun generateViralSegmentsForMedia(metadata: VideoMetadata): List<ViralClipSegment> {
        val totalMs = if (metadata.durationMs > 10000) metadata.durationMs else 30000L

        val h1Start = (totalMs * 0.05).toLong().coerceAtLeast(1000L)
        val h1End = (h1Start + 15000L).coerceAtMost(totalMs)

        val h2Start = (totalMs * 0.35).toLong()
        val h2End = (h2Start + 22000L).coerceAtMost(totalMs)

        val h3Start = (totalMs * 0.60).toLong()
        val h3End = (h3Start + 18000L).coerceAtMost(totalMs)

        val h4Start = (totalMs * 0.15).toLong()
        val h4End = (h4Start + 30000L).coerceAtMost(totalMs)

        return listOf(
            ViralClipSegment(
                id = "hook_1",
                title = "The Viral 3-Second Hook",
                hookSummary = "High-retention opening question with peak vocal pitch & facial focus",
                startMs = h1Start,
                endMs = h1End,
                viralScore = 98,
                emojiTag = "🔥",
                category = "Viral Hook",
                hookCaption = "YOU WON'T BELIEVE WHAT HAPPENS NEXT... 🤯⚡"
            ),
            ViralClipSegment(
                id = "hook_2",
                title = "The Mind-Blowing Climax & Reveal",
                hookSummary = "Rapid visual contrast change with punchline revelation & dramatic pause",
                startMs = h2Start,
                endMs = h2End,
                viralScore = 95,
                emojiTag = "🤯",
                category = "Punchline",
                hookCaption = "AND THAT'S WHEN EVERYTHING CHANGED! 🚀🔥"
            ),
            ViralClipSegment(
                id = "hook_3",
                title = "High-Octane Action Highlight",
                hookSummary = "Fast motion optical flow with sharp kinetic camera movement",
                startMs = h3Start,
                endMs = h3End,
                viralScore = 92,
                emojiTag = "⚡",
                category = "Action Peak",
                hookCaption = "THIS 6K DETAIL IS ACTUALLY ILLEGAL 👀✨"
            ),
            ViralClipSegment(
                id = "hook_4",
                title = "Golden Masterclass Takeaway",
                hookSummary = "High-value insight segment ideal for save & share reposts",
                startMs = h4Start,
                endMs = h4End,
                viralScore = 89,
                emojiTag = "💡",
                category = "Key Insight",
                hookCaption = "REMEMBER THIS BEFORE YOU START 💡🎯"
            )
        )
    }

    val sampleTrendingCaptions: List<Pair<String, String>> = listOf(
        "STOP SCROLLING RIGHT NOW" to "👀",
        "THIS CHANGES THE ENTIRE GAME" to "🔥",
        "LOOK AT THIS 6K TEXTURE" to "⚡",
        "AI DUBBED INTO 29+ CULTURES" to "🌍",
        "YOU NEVER SAW THIS COMING" to "🤯",
        "SHARE THIS WITH A FRIEND" to "🚀",
        "PURE CINEMATIC PERFECTION" to "✨"
    )
}
