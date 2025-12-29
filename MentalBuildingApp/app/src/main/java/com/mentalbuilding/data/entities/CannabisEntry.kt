package com.mentalbuilding.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(tableName = "cannabis_entries")
data class CannabisEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timestamp: LocalDateTime,
    val date: LocalDate,

    val amount: CannabisAmount,           // Ilość
    val effect: CannabisEffect            // Efekt (self-reported)
)

enum class CannabisAmount {
    SMALL,       // Mała
    MEDIUM,      // Średnia
    LARGE        // Duża
}

enum class CannabisEffect {
    RELIEF,      // Ulga
    SLOWDOWN,    // Spowolnienie
    CLARITY,     // Klarowność
    CUTOFF,      // Odcięcie
    NONE         // Brak efektu
}

class CannabisConverters {
    @TypeConverter
    fun fromCannabisAmount(value: CannabisAmount?): String? = value?.name

    @TypeConverter
    fun toCannabisAmount(value: String?): CannabisAmount? = value?.let { CannabisAmount.valueOf(it) }

    @TypeConverter
    fun fromCannabisEffect(value: CannabisEffect?): String? = value?.name

    @TypeConverter
    fun toCannabisEffect(value: String?): CannabisEffect? = value?.let { CannabisEffect.valueOf(it) }
}
