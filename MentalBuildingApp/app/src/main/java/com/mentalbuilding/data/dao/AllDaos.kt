package com.mentalbuilding.data.dao

import androidx.room.*
import com.mentalbuilding.data.entities.*
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

// ========================================
// TRIGGER DAO
// ========================================
data class ThoughtCount(
    val automaticThought: String,
    val count: Int
)

@Dao
interface TriggerDao {
    @Query("SELECT * FROM trigger_entries WHERE date = :date ORDER BY timestamp DESC")
    fun getTriggersForDate(date: LocalDate): Flow<List<TriggerEntry>>

    @Query("SELECT * FROM trigger_entries WHERE date BETWEEN :startDate AND :endDate ORDER BY timestamp DESC")
    fun getTriggersInRange(startDate: LocalDate, endDate: LocalDate): Flow<List<TriggerEntry>>

    @Query("SELECT automaticThought, COUNT(*) as count FROM trigger_entries WHERE date >= :since GROUP BY automaticThought ORDER BY count DESC")
    suspend fun getRepetitiveThoughts(since: LocalDate): List<ThoughtCount>

    @Insert
    suspend fun insert(entry: TriggerEntry)

    @Delete
    suspend fun delete(entry: TriggerEntry)
}

// ========================================
// AVOIDANCE DAO
// ========================================
@Dao
interface AvoidanceDao {
    @Query("SELECT * FROM avoidance_entries WHERE date = :date ORDER BY timestamp DESC")
    fun getEntriesForDate(date: LocalDate): Flow<List<AvoidanceEntry>>

    @Query("SELECT * FROM avoidance_entries WHERE date BETWEEN :startDate AND :endDate ORDER BY timestamp DESC")
    fun getEntriesInRange(startDate: LocalDate, endDate: LocalDate): Flow<List<AvoidanceEntry>>

    @Query("SELECT SUM(durationMinutes) FROM avoidance_entries WHERE date = :date")
    suspend fun getTotalDurationForDate(date: LocalDate): Int?

    @Insert
    suspend fun insert(entry: AvoidanceEntry)

    @Delete
    suspend fun delete(entry: AvoidanceEntry)
}

// ========================================
// AGENCY DAO
// ========================================
@Dao
interface AgencyDao {
    @Query("SELECT * FROM agency_entries WHERE date = :date ORDER BY timestamp DESC")
    fun getEntriesForDate(date: LocalDate): Flow<List<AgencyEntry>>

    @Query("SELECT * FROM agency_entries WHERE date BETWEEN :startDate AND :endDate ORDER BY timestamp DESC")
    fun getEntriesInRange(startDate: LocalDate, endDate: LocalDate): Flow<List<AgencyEntry>>

    @Query("SELECT COUNT(*) FROM agency_entries WHERE date = :date")
    suspend fun getCountForDate(date: LocalDate): Int

    @Insert
    suspend fun insert(entry: AgencyEntry)

    @Delete
    suspend fun delete(entry: AgencyEntry)
}

// ========================================
// CANNABIS DAO
// ========================================
@Dao
interface CannabisDao {
    @Query("SELECT * FROM cannabis_entries WHERE date = :date ORDER BY timestamp DESC")
    fun getEntriesForDate(date: LocalDate): Flow<List<CannabisEntry>>

    @Query("SELECT * FROM cannabis_entries WHERE date BETWEEN :startDate AND :endDate ORDER BY timestamp DESC")
    fun getEntriesInRange(startDate: LocalDate, endDate: LocalDate): Flow<List<CannabisEntry>>

    @Query("SELECT COUNT(*) FROM cannabis_entries WHERE date = :date")
    suspend fun getCountForDate(date: LocalDate): Int

    @Insert
    suspend fun insert(entry: CannabisEntry)

    @Delete
    suspend fun delete(entry: CannabisEntry)
}

// ========================================
// WORK CONTEXT DAO
// ========================================
@Dao
interface WorkContextDao {
    @Query("SELECT * FROM work_context_entries WHERE date = :date")
    fun getEntryForDate(date: LocalDate): Flow<WorkContextEntry?>

    @Query("SELECT * FROM work_context_entries WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getEntriesInRange(startDate: LocalDate, endDate: LocalDate): Flow<List<WorkContextEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: WorkContextEntry)

    @Delete
    suspend fun delete(entry: WorkContextEntry)
}

// ========================================
// SCREEN TIME DAO
// ========================================
@Dao
interface ScreenTimeDao {
    @Query("SELECT * FROM screen_time_entries WHERE date = :date")
    fun getEntryForDate(date: LocalDate): Flow<ScreenTimeEntry?>

    @Query("SELECT * FROM screen_time_entries WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getEntriesInRange(startDate: LocalDate, endDate: LocalDate): Flow<List<ScreenTimeEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: ScreenTimeEntry)

    @Query("SELECT AVG(totalMinutes) FROM screen_time_entries WHERE date BETWEEN :startDate AND :endDate")
    suspend fun getAverageInRange(startDate: LocalDate, endDate: LocalDate): Float?
}

// ========================================
// WEIGHT DAO
// ========================================
@Dao
interface WeightDao {
    @Query("SELECT * FROM weight_entries ORDER BY timestamp DESC LIMIT :limit")
    fun getRecentEntries(limit: Int): Flow<List<WeightEntry>>

    @Query("SELECT * FROM weight_entries WHERE timestamp >= :since ORDER BY timestamp DESC")
    fun getEntriesSince(since: String): Flow<List<WeightEntry>>

    @Insert
    suspend fun insert(entry: WeightEntry)

    @Delete
    suspend fun delete(entry: WeightEntry)
}

// ========================================
// MEDICATION DAO
// ========================================
@Dao
interface MedicationDao {
    @Query("SELECT * FROM medications WHERE isActive = 1 ORDER BY timeOfDay")
    fun getActiveMedications(): Flow<List<Medication>>

    @Query("SELECT * FROM medications WHERE id = :id")
    fun getMedication(id: Long): Flow<Medication?>

    @Insert
    suspend fun insert(medication: Medication): Long

    @Update
    suspend fun update(medication: Medication)

    @Delete
    suspend fun delete(medication: Medication)
}

@Dao
interface MedicationLogDao {
    @Query("SELECT * FROM medication_logs WHERE date = :date")
    fun getLogsForDate(date: LocalDate): Flow<List<MedicationLog>>

    @Query("SELECT * FROM medication_logs WHERE date = :date AND medicationId = :medicationId")
    fun getLogForDateAndMedication(date: LocalDate, medicationId: Long): Flow<MedicationLog?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(log: MedicationLog)

    @Update
    suspend fun update(log: MedicationLog)
}

// ========================================
// BREATHING EXERCISE DAO
// ========================================
@Dao
interface BreathingExerciseDao {
    @Query("SELECT * FROM breathing_exercise_entries WHERE date = :date ORDER BY timestamp DESC")
    fun getEntriesForDate(date: LocalDate): Flow<List<BreathingExerciseEntry>>

    @Query("SELECT COUNT(*) FROM breathing_exercise_entries WHERE date = :date AND completed = 1")
    suspend fun getCompletedCountForDate(date: LocalDate): Int

    @Insert
    suspend fun insert(entry: BreathingExerciseEntry)

    @Delete
    suspend fun delete(entry: BreathingExerciseEntry)
}

// ========================================
// VOICE NOTES DAO
// ========================================
@Dao
interface VoiceNoteDao {
    @Query("SELECT * FROM voice_notes WHERE date = :date ORDER BY timestamp DESC")
    fun getNotesForDate(date: LocalDate): Flow<List<VoiceNote>>

    @Query("SELECT * FROM voice_notes ORDER BY timestamp DESC LIMIT :limit")
    fun getRecentNotes(limit: Int): Flow<List<VoiceNote>>

    @Insert
    suspend fun insert(note: VoiceNote)

    @Delete
    suspend fun delete(note: VoiceNote)
}
