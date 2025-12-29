package com.mentalbuilding.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalDate
import java.time.LocalDateTime

// ========================================
// AVOIDANCE (unikanie)
// ========================================
@Entity(tableName = "avoidance_entries")
data class AvoidanceEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timestamp: LocalDateTime,
    val date: LocalDate,

    val avoidanceTypes: List<AvoidanceType>,  // Można wybrać wiele
    val durationMinutes: Int                   // Szacunkowy czas
)

enum class AvoidanceType {
    PROCRASTINATION,    // Odkładanie
    SCROLL_TV,          // Scroll / TV
    SLEEP,              // Sen (jako ucieczka)
    CANNABIS,           // Zioło
    OTHER               // Inne
}

// ========================================
// AGENCY (działanie mimo)
// ========================================
@Entity(tableName = "agency_entries")
data class AgencyEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timestamp: LocalDateTime,
    val date: LocalDate,

    val agencyType: AgencyType,
    val description: String               // "Zrobiłem X mimo Y"
)

enum class AgencyType {
    DID_DESPITE_RESISTANCE,    // Zrobiłem coś mimo oporu
    DELAYED_RESPONSE,          // Odpowiedziałem później (zamiast reactive)
    RETURNED_AFTER_BREAK       // Wróciłem do zadania po przerwie
}

// ========================================
// Type Converters
// ========================================
class AvoidanceAgencyConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromAvoidanceTypeList(value: List<AvoidanceType>?): String? {
        return gson.toJson(value?.map { it.name })
    }

    @TypeConverter
    fun toAvoidanceTypeList(value: String?): List<AvoidanceType>? {
        val type = object : TypeToken<List<String>>() {}.type
        val names: List<String>? = gson.fromJson(value, type)
        return names?.map { AvoidanceType.valueOf(it) }
    }

    @TypeConverter
    fun fromAgencyType(value: AgencyType?): String? = value?.name

    @TypeConverter
    fun toAgencyType(value: String?): AgencyType? = value?.let { AgencyType.valueOf(it) }
}
