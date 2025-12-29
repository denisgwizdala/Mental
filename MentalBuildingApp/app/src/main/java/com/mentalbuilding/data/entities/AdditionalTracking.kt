package com.mentalbuilding.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

// ========================================
// SCREEN TIME (auto-tracked)
// ========================================
@Entity(tableName = "screen_time_entries")
data class ScreenTimeEntry(
    @PrimaryKey
    val date: LocalDate,
    val totalMinutes: Int         // Auto-tracked przez UsageStatsManager
)

// ========================================
// WAGA
// ========================================
@Entity(tableName = "weight_entries")
data class WeightEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timestamp: LocalDateTime,
    val weight: Float             // kg
)

// ========================================
// MEDICATION (leki/suplementy)
// ========================================
@Entity(tableName = "medications")
data class Medication(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,             // Nazwa leku
    val dosage: String,           // Dawka (np. "10mg")
    val timeOfDay: LocalTime,     // Godzina przypomnienia
    val isActive: Boolean = true  // Czy aktywny
)

@Entity(tableName = "medication_logs")
data class MedicationLog(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: LocalDate,
    val medicationId: Long,       // FK do Medication
    val taken: Boolean,           // Czy wzięty
    val takenAt: LocalDateTime?   // Kiedy wzięty (jeśli taken = true)
)

// ========================================
// BREATHING EXERCISES
// ========================================
@Entity(tableName = "breathing_exercise_entries")
data class BreathingExerciseEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timestamp: LocalDateTime,
    val date: LocalDate,
    val completed: Boolean,       // Czy ukończony
    val durationSeconds: Int = 120 // Default 2 min (Box breathing)
)

// ========================================
// VOICE NOTES
// ========================================
@Entity(tableName = "voice_notes")
data class VoiceNote(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timestamp: LocalDateTime,
    val date: LocalDate,
    val transcription: String,    // Auto-transcribed text
    val audioFilePath: String?    // Path do audio (opcjonalnie)
)
