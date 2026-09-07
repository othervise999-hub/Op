package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.EmeraldGlow
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.ObsidianDark
import com.example.ui.theme.ObsidianElevated
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun PrivacyDialog(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = NeonCyan, contentColor = ObsidianDark),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.testTag("privacy_dismiss_button")
            ) {
                Text("Understood & Agree", fontWeight = FontWeight.Bold)
            }
        },
        containerColor = ObsidianElevated,
        shape = RoundedCornerShape(24.dp),
        icon = {
            Icon(
                imageVector = Icons.Default.Shield,
                contentDescription = "Privacy Shield",
                tint = NeonCyan,
                modifier = Modifier.size(36.dp)
            )
        },
        title = {
            Text(
                text = "Privacy & Processing Architecture",
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = "UltraVideo AI is built on a zero-retention, privacy-first computing framework designed for creator security.",
                    color = TextSecondary,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )

                PrivacyPillar(
                    icon = Icons.Default.PhoneAndroid,
                    iconTint = EmeraldGlow,
                    title = "On-Device Local NPU (up to 4K)",
                    description = "720p, 1080p, 2K, and 4K enhancements run directly on your smartphone's GPU/NPU hardware without leaving your device."
                )

                PrivacyPillar(
                    icon = Icons.Default.Lock,
                    iconTint = NeonCyan,
                    title = "256-Bit TLS Cloud Pipeline (6K & 9K)",
                    description = "Extreme 6K and 9K upscaling utilizes sandboxed neural cloud compute nodes via end-to-end encrypted TLS 1.3 tunnels."
                )

                PrivacyPillar(
                    icon = Icons.Default.DeleteSweep,
                    iconTint = Color(0xFFF43F5E),
                    title = "Auto-Deletion & Zero Storage",
                    description = "Temporary video render caches are automatically purged after 15 minutes. We never store, inspect, or train on user video content."
                )
            }
        },
        modifier = modifier.testTag("privacy_dialog")
    )
}

@Composable
private fun PrivacyPillar(
    icon: ImageVector,
    iconTint: Color,
    title: String,
    description: String
) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier
                .size(22.dp)
                .padding(top = 2.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = title,
                color = TextPrimary,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = description,
                color = TextMuted,
                fontSize = 12.sp,
                lineHeight = 16.sp
            )
        }
    }
}
