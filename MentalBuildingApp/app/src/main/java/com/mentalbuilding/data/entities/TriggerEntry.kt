package com.mentalbuilding.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(tableName = "trigger_entries")
data class TriggerEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timestamp: LocalDateTime,
    val date: LocalDate,

    val triggerType: TriggerType,
    val otherDescription: String?,        // Jeśli "INNE"

    val automaticThought: String          // "Pierwsza automatyczna myśl" - ZŁOTO dla psychologa
)

enum class TriggerType {
    MESSAGE,              // Wiadomość (Slack/mail)
    WORK_THOUGHT,         // Myśl o pracy
    AUTHORITY,            // Manager/autorytet
    CHAOS,                // Chaos dnia
    LACK_OF_STRUCTURE,    // Brak struktury
    OTHER                 // Inne
}

class TriggerConverters {
    @TypeConverter
    fun fromTriggerType(value: TriggerType?): String? = value?.name

    @TypeConverter
    fun toTriggerType(value: String?): TriggerType? = value?.let { TriggerType.valueOf(it) }
}
