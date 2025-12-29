package com.mentalbuilding.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.time.LocalDate

@Entity(tableName = "work_context_entries")
data class WorkContextEntry(
    @PrimaryKey
    val date: LocalDate,              // 1 per day

    val workType: WorkType,           // Zdalna / wolne
    val managerContact: Boolean,      // Czy był kontakt z managerem
    val threatLevel: Int,             // Poczucie zagrożenia 0-5
    val controlLevel: Int             // Poczucie kontroli 0-5
)

enum class WorkType {
    REMOTE,       // Zdalna praca
    DAY_OFF       // Dzień wolny
}

class WorkContextConverters {
    @TypeConverter
    fun fromWorkType(value: WorkType?): String? = value?.name

    @TypeConverter
    fun toWorkType(value: String?): WorkType? = value?.let { WorkType.valueOf(it) }
}
