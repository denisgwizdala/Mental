package com.mentalbuilding.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(tableName = "mental_state_entries")
data class MentalStateEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timestamp: LocalDateTime,
    val date: LocalDate,

    // Intensywność (0-5)
    val anxiety: Int,              // Lęk
    val physicalTension: Int,      // Napięcie fizyczne
    val brainFog: Int,             // Brain fog

    // Dominujący stan (max 2)
    val dominantStates: List<MentalState>,

    // Custom symptomy (z settings)
    val customSymptoms: Map<String, Int> = emptyMap()
)

enum class MentalState {
    CALM,              // Spokojny
    TENSE,             // Napięty
    ANXIOUS,           // Lękowy
    FROZEN,            // Zamrożony (freeze)
    OVERTHINKING,      // Overthinking
    CLEAR,             // Klarowny
    IRRITATED          // Rozdrażniony
}

// Additional Type Converters
class MentalStateConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromLocalDateTime(value: LocalDateTime?): String? = value?.toString()

    @TypeConverter
    fun toLocalDateTime(value: String?): LocalDateTime? = value?.let { LocalDateTime.parse(it) }

    @TypeConverter
    fun fromMentalStateList(value: List<MentalState>?): String? {
        return gson.toJson(value?.map { it.name })
    }

    @TypeConverter
    fun toMentalStateList(value: String?): List<MentalState>? {
        val type = object : TypeToken<List<String>>() {}.type
        val names: List<String>? = gson.fromJson(value, type)
        return names?.map { MentalState.valueOf(it) }
    }

    @TypeConverter
    fun fromCustomSymptoms(value: Map<String, Int>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toCustomSymptoms(value: String?): Map<String, Int>? {
        val type = object : TypeToken<Map<String, Int>>() {}.type
        return gson.fromJson(value, type)
    }
}
