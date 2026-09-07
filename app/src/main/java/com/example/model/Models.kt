package com.example.model

import android.net.Uri

enum class QualityOption(
    val title: String,
    val resolutionLabel: String,
    val targetWidth: Int,
    val targetHeight: Int,
    val estProcessingTime: String,
    val estSizeFactor: Float,
    val isPro: Boolean,
    val isCloud: Boolean,
    val badge: String
) {
    ORIGINAL(
        title = "Original",
        resolutionLabel = "Source",
        targetWidth = 0,
        targetHeight = 0,
        estProcessingTime = "10s",
        estSizeFactor = 1.0f,
        isPro = false,
        isCloud = false,
        badge = "SOURCE"
    ),
    HD_720P(
        title = "720p HD",
        resolutionLabel = "1280 × 720",
        targetWidth = 1280,
        targetHeight = 720,
        estProcessingTime = "25s",
        estSizeFactor = 0.8f,
        isPro = false,
        isCloud = false,
        badge = "FAST"
    ),
    FHD_1080P(
        title = "1080p FHD",
        resolutionLabel = "1920 × 1080",
        targetWidth = 1920,
        targetHeight = 1080,
        estProcessingTime = "45s",
        estSizeFactor = 1.4f,
        isPro = false,
        isCloud = false,
        badge = "CRISP"
    ),
    QHD_2K(
        title = "2K QHD",
        resolutionLabel = "2560 × 1440",
        targetWidth = 2560,
        targetHeight = 1440,
        estProcessingTime = "1m 30s",
        estSizeFactor = 2.4f,
        isPro = true,
        isCloud = false,
        badge = "PRO 2X"
    ),
    UHD_4K(
        title = "4K Ultra HD",
        resolutionLabel = "3840 × 2160",
        targetWidth = 3840,
        targetHeight = 2160,
        estProcessingTime = "2m 50s",
        estSizeFactor = 4.8f,
        isPro = true,
        isCloud = false,
        badge = "PRO 4X"
    ),
    UHD_6K(
        title = "6K Ultra HD",
        resolutionLabel = "5760 × 3240",
        targetWidth = 5760,
        targetHeight = 3240,
        estProcessingTime = "4m 20s",
        estSizeFactor = 8.5f,
        isPro = true,
        isCloud = true,
        badge = "CLOUD AI"
    ),
    UHD_9K(
        title = "9K Ultra HD",
        resolutionLabel = "9216 × 5184",
        targetWidth = 9216,
        targetHeight = 5184,
        estProcessingTime = "7m 40s",
        estSizeFactor = 16.0f,
        isPro = true,
        isCloud = true,
        badge = "NEURAL MAX"
    )
}

enum class FilterType(
    val title: String,
    val description: String,
    val isPro: Boolean,
    val previewHexColor: Long
) {
    CINEMATIC("Cinematic", "Teal & Orange Hollywood color grade", false, 0xFF0284C7),
    HDR("HDR", "Deep dynamic contrast & shadow clarity", false, 0xFF0D9488),
    VIBRANT("Vibrant", "Rich punchy saturation & vivid palette", false, 0xFFEC4899),
    NATURAL("Natural", "True-to-life studio colorimetry", false, 0xFF10B981),
    WARM("Warm", "Golden amber comforting glow", false, 0xFFF59E0B),
    COOL("Cool", "Modern Nordic frost cyan tint", true, 0xFF06B6D4),
    BLACK_AND_WHITE("Black & White", "High-contrast silver monochrome", true, 0xFF94A3B8),
    VINTAGE("Vintage", "70s analog warmth and sepia grain", true, 0xFFB45309),
    FILM("Film", "35mm cinema negative emulation", true, 0xFF6366F1),
    NIGHT("Night", "Low-light shadow boost & noir blue", true, 0xFF1E1B4B),
    DRAMATIC("Dramatic", "Intense shadows & edge clarity", true, 0xFF475569),
    PORTRAIT("Portrait", "Soft facial radiance & skin smoothing", true, 0xFFFB7185),
    ANIME("Anime", "Crisp cell outlines & vivid saturation", true, 0xFFA855F7),
    SHARP("Sharp", "Precision micro-contrast refinement", true, 0xFF38BDF8),
    LUXURY("Luxury", "Velvet blacks & champagne highlights", true, 0xFFD97706),
    GOLDEN("Golden", "Sunset magic hour specular warmth", true, 0xFFEAB308),
    DARK("Dark", "Subdued midtones & deep moody shadows", true, 0xFF0F172A)
}

enum class MediaType {
    VIDEO,
    PHOTO
}

data class EnhancementSettings(
    val superResolution: Int = 85,
    val sharpness: Int = 70,
    val detailRecovery: Int = 80,
    val noiseReduction: Int = 60,
    val faceEnhancement: Int = 75,
    val motionEnhancement: Int = 50,
    val hdrEnhancement: Int = 65,
    val brightness: Int = 50, // 50 is neutral
    val contrast: Int = 55,   // 50 is neutral
    val saturation: Int = 55, // 50 is neutral
    val exposure: Int = 50,   // 50 is neutral
    // Photo-specific adjustment options
    val highlights: Int = 50, // 50 is neutral
    val shadows: Int = 50,    // 50 is neutral
    val warmth: Int = 50,     // 50 is neutral (Color temperature)
    val tint: Int = 50,       // 50 is neutral (Green-Magenta tint)
    val clarity: Int = 50,    // 50 is neutral (Micro-contrast structure)
    val vignette: Int = 0,    // 0 is none (0 - 100)
    val filmGrain: Int = 0    // 0 is none (0 - 100)
)

enum class ProcessingStage(val stepNumber: Int, val title: String, val detail: String) {
    ANALYZING(1, "Analyzing media", "Inspecting stream profile, noise floors & pixel dynamic range..."),
    NOISE_REDUCTION(2, "AI noise reduction", "Applying spatio-temporal deep denoising filter..."),
    DETAIL_ENHANCEMENT(3, "AI detail enhancement", "Synthesizing micro-textures, facial contours & edge recovery..."),
    UPSCALING(4, "AI upscaling", "Deep neural super-resolution interpolation..."),
    APPLYING_FILTER(5, "Applying filter", "Color grading LUTs, tone mapping & exposure equalization..."),
    RENDERING(6, "Rendering output", "Hardware-accelerated frame composition pass..."),
    EXPORTING(7, "Exporting final file", "Finalizing container stream, metadata tags & disk write...")
}

enum class ExportFormat(val label: String, val codec: String, val extension: String, val isPhotoFormat: Boolean = false) {
    MP4_H264("MP4 (H.264)", "Advanced Video Coding", "mp4", false),
    MP4_HEVC("MP4 (H.265 / HEVC)", "High Efficiency Video Coding", "mp4", false),
    JPG_HIGH("JPEG (Ultra 100%)", "High Quality Compressed Image", "jpg", true),
    PNG_LOSSLESS("PNG (Lossless)", "Uncompressed True-Color Image", "png", true),
    WEBP_OPTIMIZED("WebP (Neural Optimized)", "Modern High-Efficiency Web Image", "webp", true)
}

data class VideoMetadata(
    val uri: Uri,
    val fileName: String,
    val width: Int,
    val height: Int,
    val fps: Int,
    val durationMs: Long,
    val fileSizeBytes: Long,
    val aspectRatio: String,
    val isDemo: Boolean = false,
    val thumbnailDrawableId: Int? = null,
    val isPhoto: Boolean = false
) {
    fun formattedDuration(): String {
        if (isPhoto) return "STILL PHOTO"
        val seconds = (durationMs / 1000) % 60
        val minutes = (durationMs / (1000 * 60))
        return String.format("%02d:%02d", minutes, seconds)
    }

    fun formattedFileSize(): String {
        val mb = fileSizeBytes.toDouble() / (1024 * 1024)
        return if (mb >= 1.0) String.format("%.1f MB", mb) else String.format("%d KB", fileSizeBytes / 1024)
    }

    fun resolutionText(): String = "${width} × ${height}"
}

// ==========================================
// 1. Language & Cultural Dubbing Models
// ==========================================

data class CultureAndLanguage(
    val code: String,
    val name: String,
    val nativeName: String,
    val flagEmoji: String,
    val region: String,
    val culturalAccent: String,
    val sampleVoiceTag: String
)

enum class VoicePersona(val title: String, val description: String) {
    CINEMATIC_NARRATOR("Cinematic Storyteller", "Deep, articulate & immersive Hollywood trailer resonance"),
    VIRAL_CREATOR("Dynamic Viral Creator", "High energy, punchy & expressive YouTube/Reels pacing"),
    NATURAL_PODCAST("Casual Conversation", "Relaxed, genuine & warm human conversational tone"),
    NEWS_ANCHOR("Global News Anchor", "Authoritative, crisp & professional broadcast cadence"),
    ANIME_EXPRESSIVE("Anime & Character", "High emotion, dynamic inflection & dramatic stylized flair"),
    CALM_DOCUMENTARY("Mindful Documentary", "Soothing, gentle & reflective nature narration")
}

data class LanguageDubbingSettings(
    val isDubbingEnabled: Boolean = false,
    val sourceLanguage: String = "Auto Detect",
    val targetLanguage: CultureAndLanguage = CultureAndLanguage(
        code = "es-MX",
        name = "Spanish (Latin America)",
        nativeName = "Español Latinoamericano",
        flagEmoji = "🇲🇽",
        region = "Americas",
        culturalAccent = "Pan-Latino colloquial warmth & expressive cadence",
        sampleVoiceTag = "Carlos (Warm Studio)"
    ),
    val voicePersona: VoicePersona = VoicePersona.VIRAL_CREATOR,
    val voiceGender: String = "Natural Match", // "Natural Match", "Male", "Female"
    val lipSyncEnabled: Boolean = true, // Neural Wav2Lip sync
    val culturalLocalization: Boolean = true, // Adapts idioms, regional humor & cultural metaphors
    val accentStrength: Int = 85, // 0 - 100%
    val speechPacing: Int = 50, // 0 - 100% (50 = 1.0x)
    val dualSubtitles: Boolean = true // Generates localized bilingual dynamic subtitles
)

// ==========================================
// 2. Video to Short Clip Finder & Auto-Edit Models
// ==========================================

data class ViralClipSegment(
    val id: String,
    val title: String,
    val hookSummary: String,
    val startMs: Long,
    val endMs: Long,
    val viralScore: Int, // 0 - 100
    val emojiTag: String, // e.g. "🔥", "🤯", "⚡", "💡"
    val category: String, // "Viral Hook", "Punchline", "Action Peak", "Masterclass Quote"
    val hookCaption: String
) {
    fun durationSeconds(): Int = ((endMs - startMs) / 1000).toInt()
    fun durationFormatted(): String = "${durationSeconds()}s"
    fun timecodeRange(): String {
        val sMin = (startMs / 1000) / 60
        val sSec = (startMs / 1000) % 60
        val eMin = (endMs / 1000) / 60
        val eSec = (endMs / 1000) % 60
        return String.format("%02d:%02d - %02d:%02d", sMin, sSec, eMin, eSec)
    }
}

enum class EmojiAnimationStyle(val label: String, val description: String, val sampleEmojis: String) {
    POP_BOUNCE("Pop & Bounce", "Spring physics bounce on accented words", "🔥 🚀 🤯"),
    KINETIC_NEON("Kinetic Neon", "Glowing neon aura with explosive bursts", "⚡ 💎 ✨"),
    MR_BEAST("MrBeast Pop", "Bold yellow stroke with high-frequency triggers", "👑 💥 🎯"),
    MINIMAL_LUXE("Minimalist Smooth", "Gentle floating scale with clean editorial fade", "💡 🕊️ 🌿")
}

enum class FramingRatio(val label: String, val ratio: String, val aspectDescriptor: String) {
    VERTICAL_9_16("9:16 Vertical", "Shorts / Reels / TikTok", "Smart Auto-Reframing"),
    SQUARE_1_1("1:1 Square", "Instagram / Facebook Feed", "Centered Focal Tracking"),
    CINEMATIC_16_9("16:9 Landscape", "YouTube / TV Widescreen", "Cinematic Full Horizon")
}

data class ShortClipSettings(
    val isShortClipMode: Boolean = false,
    val selectedSegmentId: String = "hook_1",
    val customStartMs: Long = 3000L,
    val customEndMs: Long = 18000L,
    val framingRatio: FramingRatio = FramingRatio.VERTICAL_9_16,
    val smoothFocalTracking: Boolean = true, // "edit smoothly unique part"
    val smoothPanSpeed: Int = 75, // 0 - 100
    val addDynamicEmojis: Boolean = true, // "add emoji"
    val emojiAnimationStyle: EmojiAnimationStyle = EmojiAnimationStyle.POP_BOUNCE,
    val emojiDensity: String = "High Viral", // "High Viral", "Balanced", "Punchlines Only"
    val targetShortQuality: QualityOption = QualityOption.UHD_6K, // "6k quility"
    val autoCaptionsEnabled: Boolean = true,
    val soundEffectsOnEmoji: Boolean = true
)
