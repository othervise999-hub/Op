package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "projects")
data class ProjectEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val sourceUri: String,
    val thumbnailResId: Int? = null,
    val thumbnailPath: String? = null,
    val durationMs: Long = 0,
    val originalResolution: String = "1920 × 1080",
    val targetQuality: String = "4K Ultra HD",
    val filterType: String = "Cinematic",
    val superResolution: Int = 85,
    val sharpness: Int = 70,
    val detailRecovery: Int = 80,
    val noiseReduction: Int = 60,
    val outputResolution: String = "3840 × 2160",
    val outputSizeBytes: Long = 0,
    val outputPath: String? = null,
    val timestamp: Long = System.currentTimeMillis(),
    val isCloudProcessed: Boolean = false
)
