package com.mentalbuilding.data.dao

import androidx.room.*
import com.mentalbuilding.data.entities.DailyLog
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface DailyLogDao {
    @Query("SELECT * FROM daily_logs WHERE date = :date")
    fun getLog(date: LocalDate): Flow<DailyLog?>

    @Query("SELECT * FROM daily_logs WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getLogsInRange(startDate: LocalDate, endDate: LocalDate): Flow<List<DailyLog>>

    @Query("SELECT * FROM daily_logs ORDER BY date DESC LIMIT :limit")
    fun getRecentLogs(limit: Int): Flow<List<DailyLog>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(log: DailyLog)

    @Update
    suspend fun update(log: DailyLog)

    @Delete
    suspend fun delete(log: DailyLog)

    @Query("SELECT * FROM daily_logs ORDER BY date DESC")
    fun getAllLogs(): Flow<List<DailyLog>>
}
