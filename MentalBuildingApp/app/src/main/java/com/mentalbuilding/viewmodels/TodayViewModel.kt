package com.mentalbuilding.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mentalbuilding.data.database.AppDatabase
import com.mentalbuilding.data.entities.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class TodayViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getDatabase(application)
    private val dailyLogDao = database.dailyLogDao()
    private val mentalStateDao = database.mentalStateDao()
    private val triggerDao = database.triggerDao()
    private val cannabisDao = database.cannabisDao()
    private val screenTimeDao = database.screenTimeDao()

    private val today = LocalDate.now()

    // Daily Log
    val dailyLog: StateFlow<DailyLog?> = dailyLogDao.getLog(today)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // Mental State entries for today
    val mentalStates: StateFlow<List<MentalStateEntry>> = mentalStateDao.getEntriesForDate(today)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Triggers for today
    val triggers: StateFlow<List<TriggerEntry>> = triggerDao.getTriggersForDate(today)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Cannabis entries for today
    val cannabisEntries: StateFlow<List<CannabisEntry>> = cannabisDao.getEntriesForDate(today)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Screen time for today
    val screenTime: StateFlow<ScreenTimeEntry?> = screenTimeDao.getEntryForDate(today)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // UI State
    private val _uiState = MutableStateFlow(TodayUiState())
    val uiState: StateFlow<TodayUiState> = _uiState.asStateFlow()

    // ========================================
    // MORNING CHECK-IN
    // ========================================
    fun logWakeUp() {
        viewModelScope.launch {
            val currentLog = dailyLog.value ?: DailyLog(
                date = today,
                sleepTime = null,
                wakeTime = LocalTime.now(),
                wakeUps = null,
                sleepQuality = null,
                wakeFeeling = null,
                mealCount = null,
                firstMealTime = null,
                mealType = null,
                movement = null,
                movementMinutes = null,
                leftHome = null,
                dayRating = null,
                biggestInfluence = null,
                avoidanceAwareness = null,
                whatWorked = null
            )
            dailyLogDao.insert(currentLog.copy(wakeTime = LocalTime.now()))
        }
    }

    fun updateWakeFeeling(feeling: WakeFeeling) {
        viewModelScope.launch {
            dailyLog.value?.let { log ->
                dailyLogDao.update(log.copy(wakeFeeling = feeling))
            }
        }
    }

    fun updateSleepQuality(quality: SleepQuality) {
        viewModelScope.launch {
            dailyLog.value?.let { log ->
                dailyLogDao.update(log.copy(sleepQuality = quality))
            }
        }
    }

    fun updateWakeUps(count: Int) {
        viewModelScope.launch {
            dailyLog.value?.let { log ->
                dailyLogDao.update(log.copy(wakeUps = count))
            }
        }
    }

    // ========================================
    // MENTAL STATE
    // ========================================
    fun logMentalState(
        anxiety: Int,
        physicalTension: Int,
        brainFog: Int,
        dominantStates: List<MentalState>
    ) {
        viewModelScope.launch {
            val entry = MentalStateEntry(
                timestamp = LocalDateTime.now(),
                date = today,
                anxiety = anxiety,
                physicalTension = physicalTension,
                brainFog = brainFog,
                dominantStates = dominantStates
            )
            mentalStateDao.insert(entry)
        }
    }

    // ========================================
    // TRIGGER
    // ========================================
    fun logTrigger(
        triggerType: TriggerType,
        automaticThought: String,
        otherDescription: String? = null
    ) {
        viewModelScope.launch {
            val entry = TriggerEntry(
                timestamp = LocalDateTime.now(),
                date = today,
                triggerType = triggerType,
                otherDescription = otherDescription,
                automaticThought = automaticThought
            )
            triggerDao.insert(entry)
        }
    }

    // ========================================
    // QUICK LOGS
    // ========================================
    fun logMeal() {
        viewModelScope.launch {
            val currentLog = dailyLog.value ?: DailyLog(
                date = today,
                sleepTime = null,
                wakeTime = null,
                wakeUps = null,
                sleepQuality = null,
                wakeFeeling = null,
                mealCount = 0,
                firstMealTime = null,
                mealType = null,
                movement = null,
                movementMinutes = null,
                leftHome = null,
                dayRating = null,
                biggestInfluence = null,
                avoidanceAwareness = null,
                whatWorked = null
            )

            val newMealCount = (currentLog.mealCount ?: 0) + 1
            val updatedLog = currentLog.copy(
                mealCount = newMealCount,
                firstMealTime = currentLog.firstMealTime ?: LocalTime.now()
            )
            dailyLogDao.insert(updatedLog)
        }
    }

    fun logCannabis(amount: CannabisAmount, effect: CannabisEffect) {
        viewModelScope.launch {
            val entry = CannabisEntry(
                timestamp = LocalDateTime.now(),
                date = today,
                amount = amount,
                effect = effect
            )
            cannabisDao.insert(entry)
        }
    }

    // ========================================
    // UI STATE MANAGEMENT
    // ========================================
    fun toggleMorningSection() {
        _uiState.update { it.copy(isMorningSectionExpanded = !it.isMorningSectionExpanded) }
    }

    fun toggleMentalStateSection() {
        _uiState.update { it.copy(isMentalStateSectionExpanded = !it.isMentalStateSectionExpanded) }
    }

    fun toggleTriggerSection() {
        _uiState.update { it.copy(isTriggerSectionExpanded = !it.isTriggerSectionExpanded) }
    }

    fun updateTriggerOccurred(occurred: Boolean) {
        _uiState.update { it.copy(triggerOccurred = occurred) }
    }
}

data class TodayUiState(
    val isMorningSectionExpanded: Boolean = true,
    val isMentalStateSectionExpanded: Boolean = false,
    val isTriggerSectionExpanded: Boolean = false,
    val triggerOccurred: Boolean = false
)
