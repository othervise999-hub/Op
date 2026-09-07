package com.example

import android.content.Context
import android.net.Uri
import androidx.test.core.app.ApplicationProvider
import com.example.billing.BillingManager
import com.example.model.EnhancementSettings
import com.example.model.FilterType
import com.example.model.QualityOption
import com.example.model.VideoMetadata
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("UltraVideo AI", appName)
    }

    @Test
    fun `verify quality options coverage and pro flags`() {
        val options = QualityOption.values()
        assertEquals(7, options.size)

        // Free qualities
        assertFalse(QualityOption.HD_720P.isPro)
        assertFalse(QualityOption.FHD_1080P.isPro)

        // Pro qualities
        assertTrue(QualityOption.QHD_2K.isPro)
        assertTrue(QualityOption.UHD_4K.isPro)
        assertTrue(QualityOption.UHD_6K.isPro)
        assertTrue(QualityOption.UHD_9K.isPro)

        // Cloud upscaling for extreme resolutions
        assertTrue(QualityOption.UHD_6K.isCloud)
        assertTrue(QualityOption.UHD_9K.isCloud)
        assertFalse(QualityOption.UHD_4K.isCloud)
    }

    @Test
    fun `verify all 17 cinematic and creative filters exist`() {
        val filters = FilterType.values()
        assertEquals(17, filters.size)
        assertTrue(filters.any { it == FilterType.CINEMATIC })
        assertTrue(filters.any { it == FilterType.HDR })
        assertTrue(filters.any { it == FilterType.VIBRANT })
        assertTrue(filters.any { it == FilterType.BLACK_AND_WHITE })
        assertTrue(filters.any { it == FilterType.NIGHT })
        assertTrue(filters.any { it == FilterType.LUXURY })
        assertTrue(filters.any { it == FilterType.GOLDEN })
        assertTrue(filters.any { it == FilterType.PORTRAIT })
    }

    @Test
    fun `verify video metadata formatting and aspect ratio`() {
        val metadata = VideoMetadata(
            uri = Uri.parse("content://media/external/video/media/1"),
            fileName = "sample_test.mp4",
            width = 3840,
            height = 2160,
            fps = 60,
            durationMs = 65000,
            fileSizeBytes = 52428800L,
            aspectRatio = "16:9",
            isDemo = false
        )

        assertEquals("01:05", metadata.formattedDuration())
        assertEquals("50.0 MB", metadata.formattedFileSize())
        assertEquals("3840 × 2160", metadata.resolutionText())
    }

    @Test
    fun `verify billing manager subscription upgrade and restore`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val billing = BillingManager.getInstance(context)

        // Test purchase flow
        billing.purchaseSubscription("ultravideo_yearly")
        assertTrue(billing.isPremium.value)

        // Test cancellation
        billing.setPremiumStatus(false)
        assertFalse(billing.isPremium.value)

        // Test restore
        billing.purchaseSubscription("ultravideo_monthly")
        billing.restorePurchases { restored ->
            assertTrue(restored)
        }
        assertTrue(billing.isPremium.value)
    }

    @Test
    fun `verify enhancement settings defaults`() {
        val settings = EnhancementSettings()
        assertEquals(85, settings.superResolution)
        assertEquals(70, settings.sharpness)
        assertEquals(80, settings.detailRecovery)
        assertEquals(60, settings.noiseReduction)
        assertEquals(55, settings.contrast)
        assertEquals(50, settings.brightness)
    }
}
