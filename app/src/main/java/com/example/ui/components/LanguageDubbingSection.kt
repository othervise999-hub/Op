package com.example.ui.components

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.CultureAndLanguage
import com.example.model.GlobalCultureProvider
import com.example.model.LanguageDubbingSettings
import com.example.model.VoicePersona
import com.example.ui.theme.BorderWhite05
import com.example.ui.theme.BorderWhite10
import com.example.ui.theme.Cyan400
import com.example.ui.theme.Cyan500
import com.example.ui.theme.ElectricPurple
import com.example.ui.theme.ElectricViolet
import com.example.ui.theme.EmeraldGlow
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.ObsidianDark
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.Zinc300
import com.example.ui.theme.Zinc400
import com.example.ui.theme.Zinc500
import com.example.ui.theme.Zinc800
import com.example.ui.theme.Zinc900
import com.example.ui.theme.Zinc950

@Composable
fun LanguageDubbingSection(
    settings: LanguageDubbingSettings,
    onSettingsChange: (LanguageDubbingSettings) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var showLanguagePicker by remember { mutableStateOf(false) }
    var isPlayingPreviewVoice by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Master Enable Banner
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Zinc900),
            border = BorderStroke(1.dp, if (settings.isDubbingEnabled) Cyan400.copy(alpha = 0.5f) else BorderWhite05),
            modifier = Modifier.fillMaxWidth().testTag("language_dubbing_master_card")
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(
                                if (settings.isDubbingEnabled)
                                    Brush.linearGradient(listOf(Cyan500, ElectricPurple))
                                else
                                    Brush.linearGradient(listOf(Zinc800, Zinc900))
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Translate,
                            contentDescription = "Language Dubbing",
                            tint = if (settings.isDubbingEnabled) Color.White else Zinc400,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "AI Language & Culture Dubbing",
                                color = TextPrimary,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                color = ElectricPurple.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(6.dp),
                                border = BorderStroke(1.dp, ElectricPurple.copy(alpha = 0.4f))
                            ) {
                                Text(
                                    text = "29+ CULTURES",
                                    color = ElectricViolet,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Black,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Auto-translate speech, regional accents & neural lip-sync",
                            color = Zinc400,
                            fontSize = 11.sp
                        )
                    }
                }

                Switch(
                    checked = settings.isDubbingEnabled,
                    onCheckedChange = { onSettingsChange(settings.copy(isDubbingEnabled = it)) },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Cyan500,
                        uncheckedThumbColor = Zinc400,
                        uncheckedTrackColor = Zinc800
                    ),
                    modifier = Modifier.testTag("dubbing_enable_switch")
                )
            }
        }

        AnimatedVisibility(visible = settings.isDubbingEnabled) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

                // Target Language & Cultural Region Card
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Zinc900),
                    border = BorderStroke(1.dp, BorderWhite10),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "TARGET LANGUAGE & CULTURE",
                                color = TextMuted,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                            Surface(
                                color = Cyan400.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(1.dp, Cyan400.copy(alpha = 0.3f)),
                                modifier = Modifier.clickable { showLanguagePicker = true }
                                    .testTag("change_culture_button")
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Language,
                                        contentDescription = null,
                                        tint = Cyan400,
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Change Language",
                                        color = Cyan400,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Active Culture Card
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .background(Zinc950)
                                .border(1.dp, BorderWhite05, RoundedCornerShape(16.dp))
                                .clickable { showLanguagePicker = true }
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = settings.targetLanguage.flagEmoji,
                                fontSize = 32.sp
                            )
                            Spacer(modifier = Modifier.width(14.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = settings.targetLanguage.name,
                                    color = TextPrimary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                                Text(
                                    text = "${settings.targetLanguage.nativeName} • ${settings.targetLanguage.region}",
                                    color = Cyan400,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = settings.targetLanguage.culturalAccent,
                                    color = Zinc500,
                                    fontSize = 11.sp,
                                    lineHeight = 15.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Voice Sample Simulation Button
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.RecordVoiceOver,
                                    contentDescription = null,
                                    tint = ElectricPurple,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Sample: ${settings.targetLanguage.sampleVoiceTag}",
                                    color = TextSecondary,
                                    fontSize = 12.sp
                                )
                            }

                            Surface(
                                color = if (isPlayingPreviewVoice) EmeraldGlow.copy(alpha = 0.2f) else Zinc800,
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(1.dp, if (isPlayingPreviewVoice) EmeraldGlow else BorderWhite10),
                                modifier = Modifier.clickable {
                                    isPlayingPreviewVoice = !isPlayingPreviewVoice
                                    Toast.makeText(
                                        context,
                                        if (isPlayingPreviewVoice)
                                            "Auditioning ${settings.targetLanguage.name} (${settings.voicePersona.title})"
                                        else "Voice preview stopped",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }.testTag("preview_voice_sample_button")
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                ) {
                                    Icon(
                                        imageVector = if (isPlayingPreviewVoice) Icons.Default.Stop else Icons.Default.PlayArrow,
                                        contentDescription = null,
                                        tint = if (isPlayingPreviewVoice) EmeraldGlow else TextPrimary,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = if (isPlayingPreviewVoice) "Playing Soundwave..." else "Hear Voice",
                                        color = if (isPlayingPreviewVoice) EmeraldGlow else TextPrimary,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }

                        if (isPlayingPreviewVoice) {
                            Spacer(modifier = Modifier.height(10.dp))
                            VoiceSoundwaveAnimation()
                        }
                    }
                }

                // Voice Persona Selector
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "VOICE STYLE & PERSONA",
                        color = TextMuted,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(VoicePersona.values()) { persona ->
                            val isSelected = settings.voicePersona == persona
                            Surface(
                                color = if (isSelected) Cyan400.copy(alpha = 0.15f) else Zinc900,
                                shape = RoundedCornerShape(16.dp),
                                border = BorderStroke(
                                    1.dp,
                                    if (isSelected) Cyan400 else BorderWhite05
                                ),
                                modifier = Modifier
                                    .width(160.dp)
                                    .clickable { onSettingsChange(settings.copy(voicePersona = persona)) }
                                    .testTag("persona_${persona.name}")
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = persona.title,
                                            color = if (isSelected) Cyan400 else TextPrimary,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        if (isSelected) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = null,
                                                tint = Cyan400,
                                                modifier = Modifier.size(14.dp)
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = persona.description,
                                        color = Zinc500,
                                        fontSize = 10.sp,
                                        lineHeight = 13.sp,
                                        maxLines = 3
                                    )
                                }
                            }
                        }
                    }
                }

                // Cultural Localization & Neural Lip-Sync Switches
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Zinc900),
                    border = BorderStroke(1.dp, BorderWhite05),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Text(
                            text = "ADVANCED CULTURAL LOCALIZATION",
                            color = TextMuted,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )

                        // Lip-Sync Switch
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Neural Wav2Lip Lip-Sync",
                                    color = TextPrimary,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = "Modifies speaker mouth motion to match new phonemes seamlessly",
                                    color = Zinc500,
                                    fontSize = 11.sp
                                )
                            }
                            Switch(
                                checked = settings.lipSyncEnabled,
                                onCheckedChange = { onSettingsChange(settings.copy(lipSyncEnabled = it)) },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    checkedTrackColor = Cyan500,
                                    uncheckedThumbColor = Zinc400,
                                    uncheckedTrackColor = Zinc800
                                )
                            )
                        }

                        Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(BorderWhite05))

                        // Cultural Idiom Adaptation
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Cultural Context & Idiom Adaptation",
                                    color = TextPrimary,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = "Replaces local humor, slang & cultural analogies to resonate natively",
                                    color = Zinc500,
                                    fontSize = 11.sp
                                )
                            }
                            Switch(
                                checked = settings.culturalLocalization,
                                onCheckedChange = { onSettingsChange(settings.copy(culturalLocalization = it)) },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    checkedTrackColor = Cyan500,
                                    uncheckedThumbColor = Zinc400,
                                    uncheckedTrackColor = Zinc800
                                )
                            )
                        }

                        Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(BorderWhite05))

                        // Dual Subtitles
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Burned-in Dynamic Subtitles",
                                    color = TextPrimary,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = "Generates translated on-screen captions styled for high retention",
                                    color = Zinc500,
                                    fontSize = 11.sp
                                )
                            }
                            Switch(
                                checked = settings.dualSubtitles,
                                onCheckedChange = { onSettingsChange(settings.copy(dualSubtitles = it)) },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    checkedTrackColor = Cyan500,
                                    uncheckedThumbColor = Zinc400,
                                    uncheckedTrackColor = Zinc800
                                )
                            )
                        }
                    }
                }

                // Voice Pitch / Gender & Sliders
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Zinc900),
                    border = BorderStroke(1.dp, BorderWhite05),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "VOICE MODULATION & ACCENT INTENSITY",
                            color = TextMuted,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )

                        // Voice Gender Chips
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            listOf("Natural Match", "Male Voice", "Female Voice").forEach { gender ->
                                val isSel = settings.voiceGender == gender
                                Surface(
                                    color = if (isSel) Cyan400.copy(alpha = 0.2f) else Zinc950,
                                    shape = RoundedCornerShape(12.dp),
                                    border = BorderStroke(1.dp, if (isSel) Cyan400 else BorderWhite05),
                                    modifier = Modifier.weight(1f).clickable {
                                        onSettingsChange(settings.copy(voiceGender = gender))
                                    }
                                ) {
                                    Text(
                                        text = gender,
                                        color = if (isSel) Cyan400 else TextSecondary,
                                        fontSize = 12.sp,
                                        fontWeight = if (isSel) FontWeight.Bold else FontWeight.Medium,
                                        modifier = Modifier.padding(vertical = 8.dp),
                                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        // Accent Strength Slider
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Regional Accent Intensity", color = TextSecondary, fontSize = 12.sp)
                                Text("${settings.accentStrength}%", color = Cyan400, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                            Slider(
                                value = settings.accentStrength.toFloat(),
                                onValueChange = { onSettingsChange(settings.copy(accentStrength = it.toInt())) },
                                valueRange = 0f..100f,
                                colors = SliderDefaults.colors(
                                    thumbColor = Cyan400,
                                    activeTrackColor = Cyan400,
                                    inactiveTrackColor = Zinc800
                                )
                            )
                        }

                        // Speech Pacing Slider
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Speech Pacing", color = TextSecondary, fontSize = 12.sp)
                                val speedMultiplier = 0.75f + (settings.speechPacing / 100f) * 0.5f
                                Text(String.format("%.2fx", speedMultiplier), color = Cyan400, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                            Slider(
                                value = settings.speechPacing.toFloat(),
                                onValueChange = { onSettingsChange(settings.copy(speechPacing = it.toInt())) },
                                valueRange = 0f..100f,
                                colors = SliderDefaults.colors(
                                    thumbColor = Cyan400,
                                    activeTrackColor = Cyan400,
                                    inactiveTrackColor = Zinc800
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    // Modal Dialog: All Global Cultures & Languages Picker
    if (showLanguagePicker) {
        AllLanguagesPickerModal(
            currentSelected = settings.targetLanguage,
            onSelect = { selected ->
                onSettingsChange(settings.copy(targetLanguage = selected))
                showLanguagePicker = false
            },
            onDismiss = { showLanguagePicker = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AllLanguagesPickerModal(
    currentSelected: CultureAndLanguage,
    onSelect: (CultureAndLanguage) -> Unit,
    onDismiss: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedRegion by remember { mutableStateOf("All") }

    val regions = listOf("All", "Americas", "Europe", "Asia-Pacific", "Middle East & Africa")

    val filteredList = remember(searchQuery, selectedRegion) {
        GlobalCultureProvider.allLanguagesAndCultures.filter { culture ->
            val matchQuery = searchQuery.isBlank() ||
                culture.name.contains(searchQuery, ignoreCase = true) ||
                culture.nativeName.contains(searchQuery, ignoreCase = true) ||
                culture.culturalAccent.contains(searchQuery, ignoreCase = true) ||
                culture.code.contains(searchQuery, ignoreCase = true)

            val matchRegion = selectedRegion == "All" || culture.region == selectedRegion
            matchQuery && matchRegion
        }
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = ObsidianDark),
            border = BorderStroke(1.dp, BorderWhite10),
            modifier = Modifier
                .fillMaxWidth()
                .height(580.dp)
                .testTag("all_languages_modal")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Global Cultures & Languages",
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp
                        )
                        Text(
                            text = "${GlobalCultureProvider.allLanguagesAndCultures.size} cultures with native accent synthesis",
                            color = Cyan400,
                            fontSize = 11.sp
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Zinc400
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Search Field
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search language, dialect, country...", color = Zinc500, fontSize = 13.sp) },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = Zinc400, modifier = Modifier.size(18.dp))
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Cyan400,
                        unfocusedBorderColor = BorderWhite10,
                        focusedContainerColor = Zinc900,
                        unfocusedContainerColor = Zinc900,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Region Chips
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(regions) { region ->
                        val isSelected = selectedRegion == region
                        Surface(
                            color = if (isSelected) Cyan400.copy(alpha = 0.2f) else Zinc900,
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, if (isSelected) Cyan400 else BorderWhite05),
                            modifier = Modifier.clickable { selectedRegion = region }
                        ) {
                            Text(
                                text = region,
                                color = if (isSelected) Cyan400 else Zinc400,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Languages List
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredList) { culture ->
                        val isCurrent = currentSelected.code == culture.code
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .background(if (isCurrent) Cyan400.copy(alpha = 0.12f) else Zinc900)
                                .border(1.dp, if (isCurrent) Cyan400 else BorderWhite05, RoundedCornerShape(14.dp))
                                .clickable { onSelect(culture) }
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = culture.flagEmoji, fontSize = 26.sp)
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = culture.name,
                                        color = if (isCurrent) Cyan400 else TextPrimary,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "(${culture.code})",
                                        color = Zinc500,
                                        fontSize = 10.sp
                                    )
                                }
                                Text(
                                    text = culture.nativeName,
                                    color = Zinc300,
                                    fontSize = 11.sp
                                )
                                Text(
                                    text = culture.culturalAccent,
                                    color = Zinc500,
                                    fontSize = 10.sp,
                                    maxLines = 1
                                )
                            }

                            if (isCurrent) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = Cyan400,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun VoiceSoundwaveAnimation() {
    val infiniteTransition = rememberInfiniteTransition(label = "soundwave")
    val heights = (0..14).map { index ->
        infiniteTransition.animateFloat(
            initialValue = 0.2f,
            targetValue = 1.0f,
            animationSpec = infiniteRepeatable(
                animation = tween(400 + (index * 45), easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "bar_$index"
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(28.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Zinc950)
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        heights.forEach { animatedVal ->
            Box(
                modifier = Modifier
                    .width(3.dp)
                    .height((22.dp * animatedVal.value).coerceAtLeast(4.dp))
                    .clip(CircleShape)
                    .background(Brush.verticalGradient(listOf(Cyan400, ElectricPurple)))
            )
        }
    }
}
