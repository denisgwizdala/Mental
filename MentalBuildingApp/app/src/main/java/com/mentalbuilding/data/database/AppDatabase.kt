package com.mentalbuilding.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mentalbuilding.data.dao.*
import com.mentalbuilding.data.entities.*

@Database(
    entities = [
        DailyLog::class,
        MentalStateEntry::class,
        TriggerEntry::class,
        AvoidanceEntry::class,
        AgencyEntry::class,
        CannabisEntry::class,
        WorkContextEntry::class,
        ScreenTimeEntry::class,
        WeightEntry::class,
        Medication::class,
        MedicationLog::class,
        BreathingExerciseEntry::class,
        VoiceNote::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    Converters::class,
    MentalStateConverters::class,
    TriggerConverters::class,
    AvoidanceAgencyConverters::class,
    CannabisConverters::class,
    WorkContextConverters::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dailyLogDao(): DailyLogDao
    abstract fun mentalStateDao(): MentalStateDao
    abstract fun triggerDao(): TriggerDao
    abstract fun avoidanceDao(): AvoidanceDao
    abstract fun agencyDao(): AgencyDao
    abstract fun cannabisDao(): CannabisDao
    abstract fun workContextDao(): WorkContextDao
    abstract fun screenTimeDao(): ScreenTimeDao
    abstract fun weightDao(): WeightDao
    abstract fun medicationDao(): MedicationDao
    abstract fun medicationLogDao(): MedicationLogDao
    abstract fun breathingExerciseDao(): BreathingExerciseDao
    abstract fun voiceNoteDao(): VoiceNoteDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mental_building_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
