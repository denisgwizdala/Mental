package com.mentalbuilding.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "daily_logs")
data class DailyLog(
    @PrimaryKey
    val date: LocalDate,

    // A. SEN
    val sleepTime: LocalTime?,               // Godzina snu (z poprzedniego wieczoru)
    val wakeTime: LocalTime?,                // Godzina pobudki
    val wakeUps: Int?,                       // Przebudzenia (0, 1, 2+)
    val sleepQuality: SleepQuality?,         // Jakość snu
    val wakeFeeling: WakeFeeling?,           // Jak się obudziłeś

    // B. JEDZENIE
    val mealCount: Int?,                     // Liczba posiłków
    val firstMealTime: LocalTime?,           // Pierwszy posiłek
    val mealType: MealType?,                 // Typ

    // C. RUCH
    val movement: MovementType?,             // Typ ruchu
    val movementMinutes: Int?,               // Czas (min)

    // D. BEHAVIORAL ACTIVATION
    val leftHome: Boolean?,                  // Czy wyszedłeś z domu

    // E. EVENING REFLECTION
    val dayRating: Int?,                     // Ocena dnia 0-10 (opcjonalne)
    val biggestInfluence: String?,           // Co najbardziej wpłynęło na stan
    val avoidanceAwareness: String?,         // Czy uciekłem, ale mogłem wrócić
    val whatWorked: String?                  // Co zadziałało
)

enum class SleepQuality {
    REGENERATING,      // Regenerujący
    NEUTRAL,           // Neutralny
    EXHAUSTING         // Męczący
}

enum class WakeFeeling {
    BAD,               // 😫 Zjebany
    NEUTRAL,           // 😐 Normalnie
    GOOD               // 😊 Dobrze
}

enum class MealType {
    NORMAL,            // Normalny posiłek
    TECHNICAL,         // Techniczny (byle co)
    SKIPPED            // Pominięty
}

enum class MovementType {
    NONE,              // Brak
    LIGHT,             // Lekki (spacer)
    MODERATE,          // Umiarkowany
    TRAINING           // Trening
}

// Type Converters dla Room
class Converters {
    @TypeConverter
    fun fromLocalDate(value: LocalDate?): String? = value?.toString()

    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? = value?.let { LocalDate.parse(it) }

    @TypeConverter
    fun fromLocalTime(value: LocalTime?): String? = value?.toString()

    @TypeConverter
    fun toLocalTime(value: String?): LocalTime? = value?.let { LocalTime.parse(it) }

    @TypeConverter
    fun fromSleepQuality(value: SleepQuality?): String? = value?.name

    @TypeConverter
    fun toSleepQuality(value: String?): SleepQuality? = value?.let { SleepQuality.valueOf(it) }

    @TypeConverter
    fun fromWakeFeeling(value: WakeFeeling?): String? = value?.name

    @TypeConverter
    fun toWakeFeeling(value: String?): WakeFeeling? = value?.let { WakeFeeling.valueOf(it) }

    @TypeConverter
    fun fromMealType(value: MealType?): String? = value?.name

    @TypeConverter
    fun toMealType(value: String?): MealType? = value?.let { MealType.valueOf(it) }

    @TypeConverter
    fun fromMovementType(value: MovementType?): String? = value?.name

    @TypeConverter
    fun toMovementType(value: String?): MovementType? = value?.let { MovementType.valueOf(it) }
}
