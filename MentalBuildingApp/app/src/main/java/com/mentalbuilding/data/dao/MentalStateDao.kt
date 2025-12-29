package com.mentalbuilding.data.dao

import androidx.room.*
import com.mentalbuilding.data.entities.MentalStateEntry
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface MentalStateDao {
    @Query("SELECT * FROM mental_state_entries WHERE date = :date ORDER BY timestamp DESC")
    fun getEntriesForDate(date: LocalDate): Flow<List<MentalStateEntry>>

    @Query("SELECT * FROM mental_state_entries WHERE date BETWEEN :startDate AND :endDate ORDER BY timestamp DESC")
    fun getEntriesInRange(startDate: LocalDate, endDate: LocalDate): Flow<List<MentalStateEntry>>

    @Query("SELECT * FROM mental_state_entries ORDER BY timestamp DESC LIMIT :limit")
    fun getRecentEntries(limit: Int): Flow<List<MentalStateEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: MentalStateEntry)

    @Delete
    suspend fun delete(entry: MentalStateEntry)

    @Query("DELETE FROM mental_state_entries WHERE date = :date")
    suspend fun deleteByDate(date: LocalDate)
}
