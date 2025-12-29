package com.mentalbuilding.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mentalbuilding.data.entities.*
import com.mentalbuilding.ui.components.*
import com.mentalbuilding.ui.theme.*
import com.mentalbuilding.viewmodels.TodayViewModel
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodayScreen(
    viewModel: TodayViewModel = viewModel(),
    onNavigateToEvening: () -> Unit = {}
) {
    val dailyLog by viewModel.dailyLog.collectAsState()
    val mentalStates by viewModel.mentalStates.collectAsState()
    val triggers by viewModel.triggers.collectAsState()
    val cannabisEntries by viewModel.cannabisEntries.collectAsState()
    val screenTime by viewModel.screenTime.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "DZIŚ",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BackgroundWhite,
                    titleContentColor = TextPrimary
                ),
                actions = {
                    IconButton(onClick = { /* TODO: Navigate to settings */ }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = PrimaryBlue
                        )
                    }
                    IconButton(onClick = { /* TODO: Navigate to trends */ }) {
                        Icon(
                            imageVector = Icons.Default.Analytics,
                            contentDescription = "Trends",
                            tint = PrimaryBlue
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // MORNING SECTION
            MorningSection(
                dailyLog = dailyLog,
                onWakeUpClick = { viewModel.logWakeUp() },
                onWakeFeelingSelect = { viewModel.updateWakeFeeling(it) },
                onSleepQualitySelect = { viewModel.updateSleepQuality(it) },
                onWakeUpsSelect = { viewModel.updateWakeUps(it) }
            )

            ContentDivider()

            // MENTAL STATE SECTION
            MentalStateSection(
                latestState = mentalStates.firstOrNull(),
                onLogMentalState = { anxiety, tension, brainFog, states ->
                    viewModel.logMentalState(anxiety, tension, brainFog, states)
                }
            )

            ContentDivider()

            // TRIGGER SECTION
            TriggerSection(
                triggers = triggers,
                triggerOccurred = uiState.triggerOccurred,
                onTriggerOccurredChange = { viewModel.updateTriggerOccurred(it) },
                onLogTrigger = { type, thought, other ->
                    viewModel.logTrigger(type, thought, other)
                }
            )

            ContentDivider()

            // QUICK LOGS SECTION
            QuickLogsSection(
                mealCount = dailyLog?.mealCount ?: 0,
                lastMealTime = dailyLog?.firstMealTime,
                cannabisCount = cannabisEntries.size,
                lastCannabisTime = cannabisEntries.firstOrNull()?.timestamp,
                screenTimeMinutes = screenTime?.totalMinutes,
                onAddMeal = { viewModel.logMeal() },
                onAddCannabis = { amount, effect ->
                    viewModel.logCannabis(amount, effect)
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // CLOSE DAY BUTTON (visible after 18:00)
            PrimaryButton(
                text = "ZAMKNIJ DZIEŃ",
                onClick = onNavigateToEvening,
                icon = Icons.Default.NightsStay
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// ========================================
// MORNING SECTION
// ========================================
@Composable
fun MorningSection(
    dailyLog: DailyLog?,
    onWakeUpClick: () -> Unit,
    onWakeFeelingSelect: (WakeFeeling) -> Unit,
    onSleepQualitySelect: (SleepQuality) -> Unit,
    onWakeUpsSelect: (Int) -> Unit
) {
    SectionHeader(
        title = "PORANEK",
        icon = Icons.Default.WbSunny
    )

    InfoCard {
        // Wake up button
        if (dailyLog?.wakeTime == null) {
            PrimaryButton(
                text = "WSTAŁEM",
                onClick = onWakeUpClick,
                icon = Icons.Default.WbSunny
            )
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Wstałem:",
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextSecondary
                )
                Text(
                    text = dailyLog.wakeTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryBlue
                )
            }
        }

        if (dailyLog?.wakeTime != null) {
            Spacer(modifier = Modifier.height(16.dp))

            // Wake feeling
            Text(
                text = "Jak się obudziłeś?",
                style = MaterialTheme.typography.bodyLarge,
                color = TextPrimary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                WakeFeelingChip(
                    feeling = WakeFeeling.BAD,
                    emoji = "😫",
                    label = "Zjebany",
                    selected = dailyLog.wakeFeeling == WakeFeeling.BAD,
                    onClick = { onWakeFeelingSelect(WakeFeeling.BAD) },
                    modifier = Modifier.weight(1f)
                )
                WakeFeelingChip(
                    feeling = WakeFeeling.NEUTRAL,
                    emoji = "😐",
                    label = "Normalnie",
                    selected = dailyLog.wakeFeeling == WakeFeeling.NEUTRAL,
                    onClick = { onWakeFeelingSelect(WakeFeeling.NEUTRAL) },
                    modifier = Modifier.weight(1f)
                )
                WakeFeelingChip(
                    feeling = WakeFeeling.GOOD,
                    emoji = "😊",
                    label = "Dobrze",
                    selected = dailyLog.wakeFeeling == WakeFeeling.GOOD,
                    onClick = { onWakeFeelingSelect(WakeFeeling.GOOD) },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sleep quality
            Text(
                text = "Jakość snu:",
                style = MaterialTheme.typography.bodyLarge,
                color = TextPrimary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(
                    SleepQuality.REGENERATING to "Regenerujący",
                    SleepQuality.NEUTRAL to "Neutralny",
                    SleepQuality.EXHAUSTING to "Męczący"
                ).forEach { (quality, label) ->
                    FilterChip(
                        selected = dailyLog.sleepQuality == quality,
                        onClick = { onSleepQualitySelect(quality) },
                        label = { Text(label, fontSize = 13.sp) },
                        modifier = Modifier.weight(1f),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = PrimaryBlue,
                            selectedLabelColor = androidx.compose.ui.graphics.Color.White
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Wake ups
            Text(
                text = "Przebudzenia:",
                style = MaterialTheme.typography.bodyLarge,
                color = TextPrimary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(0, 1, 2).forEach { count ->
                    FilterChip(
                        selected = dailyLog.wakeUps == count,
                        onClick = { onWakeUpsSelect(count) },
                        label = { Text(if (count == 2) "2+" else "$count", fontSize = 13.sp) },
                        modifier = Modifier.weight(1f),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = PrimaryBlue,
                            selectedLabelColor = androidx.compose.ui.graphics.Color.White
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun WakeFeelingChip(
    feeling: WakeFeeling,
    emoji: String,
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(emoji, fontSize = 24.sp)
                Text(label, fontSize = 11.sp)
            }
        },
        modifier = modifier,
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = PrimaryBlue,
            selectedLabelColor = androidx.compose.ui.graphics.Color.White
        )
    )
}

// ========================================
// MENTAL STATE SECTION
// ========================================
@Composable
fun MentalStateSection(
    latestState: MentalStateEntry?,
    onLogMentalState: (Int, Int, Int, List<MentalState>) -> Unit
) {
    SectionHeader(
        title = "SAMOPOCZUCIE TERAZ",
        icon = Icons.Default.Psychology
    )

    var anxiety by remember { mutableIntStateOf(latestState?.anxiety ?: 0) }
    var physicalTension by remember { mutableIntStateOf(latestState?.physicalTension ?: 0) }
    var brainFog by remember { mutableIntStateOf(latestState?.brainFog ?: 0) }
    var selectedStates by remember { mutableStateOf(latestState?.dominantStates ?: emptyList()) }

    InfoCard {
        // Sliders
        LabeledSlider(
            label = "Lęk",
            value = anxiety,
            onValueChange = { anxiety = it },
            color = AnxietyColor
        )

        Spacer(modifier = Modifier.height(16.dp))

        LabeledSlider(
            label = "Napięcie fizyczne",
            value = physicalTension,
            onValueChange = { physicalTension = it },
            color = PrimaryBlue
        )

        Spacer(modifier = Modifier.height(16.dp))

        LabeledSlider(
            label = "Brain fog",
            value = brainFog,
            onValueChange = { brainFog = it },
            color = PrimaryBlue
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Dominant states
        Text(
            text = "Dominujący stan (max 2):",
            style = MaterialTheme.typography.bodyLarge,
            color = TextPrimary,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        val stateOptions = listOf(
            MentalState.CALM to "Spokojny",
            MentalState.TENSE to "Napięty",
            MentalState.ANXIOUS to "Lękowy",
            MentalState.FROZEN to "Freeze",
            MentalState.OVERTHINKING to "Overthinking",
            MentalState.CLEAR to "Klarowny",
            MentalState.IRRITATED to "Rozdrażniony"
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            stateOptions.chunked(2).forEach { row ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    row.forEach { (state, label) ->
                        FilterChip(
                            selected = state in selectedStates,
                            onClick = {
                                selectedStates = if (state in selectedStates) {
                                    selectedStates - state
                                } else {
                                    if (selectedStates.size < 2) selectedStates + state
                                    else selectedStates
                                }
                            },
                            label = { Text(label, fontSize = 13.sp) },
                            modifier = Modifier.weight(1f),
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = PrimaryBlue,
                                selectedLabelColor = androidx.compose.ui.graphics.Color.White
                            )
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Save button
        SecondaryButton(
            text = "ZAPISZ STAN",
            onClick = {
                onLogMentalState(anxiety, physicalTension, brainFog, selectedStates)
            },
            icon = Icons.Default.Save
        )
    }
}

// ========================================
// TRIGGER SECTION
// ========================================
@Composable
fun TriggerSection(
    triggers: List<TriggerEntry>,
    triggerOccurred: Boolean,
    onTriggerOccurredChange: (Boolean) -> Unit,
    onLogTrigger: (TriggerType, String, String?) -> Unit
) {
    SectionHeader(
        title = "TRIGGER",
        icon = Icons.Default.Warning
    )

    var selectedTriggerType by remember { mutableStateOf<TriggerType?>(null) }
    var automaticThought by remember { mutableStateOf("") }

    InfoCard {
        Text(
            text = "Trigger wystąpił?",
            style = MaterialTheme.typography.bodyLarge,
            color = TextPrimary,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(
                selected = triggerOccurred,
                onClick = { onTriggerOccurredChange(true) },
                label = { Text("Tak") },
                modifier = Modifier.weight(1f),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = PrimaryBlue,
                    selectedLabelColor = androidx.compose.ui.graphics.Color.White
                )
            )
            FilterChip(
                selected = !triggerOccurred,
                onClick = { onTriggerOccurredChange(false) },
                label = { Text("Nie") },
                modifier = Modifier.weight(1f),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = SuccessGreen,
                    selectedLabelColor = androidx.compose.ui.graphics.Color.White
                )
            )
        }

        if (triggerOccurred) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Rodzaj:",
                style = MaterialTheme.typography.bodyLarge,
                color = TextPrimary,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            val triggerTypes = listOf(
                TriggerType.MESSAGE to "Wiadomość",
                TriggerType.WORK_THOUGHT to "Praca",
                TriggerType.AUTHORITY to "Manager",
                TriggerType.CHAOS to "Chaos",
                TriggerType.OTHER to "Inne"
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                triggerTypes.chunked(2).forEach { row ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        row.forEach { (type, label) ->
                            FilterChip(
                                selected = selectedTriggerType == type,
                                onClick = { selectedTriggerType = type },
                                label = { Text(label, fontSize = 13.sp) },
                                modifier = Modifier.weight(1f),
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = PrimaryBlue,
                                    selectedLabelColor = androidx.compose.ui.graphics.Color.White
                                )
                            )
                        }
                        if (row.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Pierwsza automatyczna myśl:",
                style = MaterialTheme.typography.bodyLarge,
                color = TextPrimary,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = automaticThought,
                onValueChange = { automaticThought = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("\"Coś znowu odjebałem...\"") },
                maxLines = 3,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryBlue,
                    unfocusedBorderColor = BorderGray
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            SecondaryButton(
                text = "ZAPISZ TRIGGER",
                onClick = {
                    selectedTriggerType?.let { type ->
                        if (automaticThought.isNotBlank()) {
                            onLogTrigger(type, automaticThought, null)
                            automaticThought = ""
                            selectedTriggerType = null
                        }
                    }
                },
                icon = Icons.Default.Save
            )
        }

        // Show logged triggers
        if (triggers.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Dziś: ${triggers.size}x trigger",
                style = MaterialTheme.typography.bodyMedium,
                color = WarningOrange,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// ========================================
// QUICK LOGS SECTION
// ========================================
@Composable
fun QuickLogsSection(
    mealCount: Int,
    lastMealTime: java.time.LocalTime?,
    cannabisCount: Int,
    lastCannabisTime: java.time.LocalDateTime?,
    screenTimeMinutes: Int?,
    onAddMeal: () -> Unit,
    onAddCannabis: (CannabisAmount, CannabisEffect) -> Unit
) {
    var showCannabisDialog by remember { mutableStateOf(false) }

    // JEDZENIE
    SectionHeader(
        title = "JEDZENIE",
        icon = Icons.Default.Restaurant
    )
    InfoCard {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Dziś: $mealCount ${if (mealCount == 1) "posiłek" else "posiłki"}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                lastMealTime?.let {
                    Text(
                        text = "Ostatni: ${it.format(DateTimeFormatter.ofPattern("HH:mm"))}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                }
            }
            IconButton(onClick = onAddMeal) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Dodaj posiłek",
                    tint = PrimaryBlue
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // ZIOŁO
    SectionHeader(
        title = "ZIOŁO",
        icon = Icons.Default.Spa
    )
    InfoCard {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Dziś: ${cannabisCount}x",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                lastCannabisTime?.let {
                    Text(
                        text = "Ostatni: ${it.format(DateTimeFormatter.ofPattern("HH:mm"))}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                }
            }
            IconButton(onClick = { showCannabisDialog = true }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Dodaj zioło",
                    tint = PrimaryBlue
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // SCREEN TIME
    SectionHeader(
        title = "SCREEN TIME",
        icon = Icons.Default.PhoneAndroid
    )
    InfoCard {
        screenTimeMinutes?.let { minutes ->
            val hours = minutes / 60
            val mins = minutes % 60
            Text(
                text = "${hours}h ${mins}min (auto)",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
        } ?: run {
            Text(
                text = "Brak danych",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )
        }
    }

    // Cannabis Dialog
    if (showCannabisDialog) {
        CannabisDialog(
            onDismiss = { showCannabisDialog = false },
            onSave = { amount, effect ->
                onAddCannabis(amount, effect)
                showCannabisDialog = false
            }
        )
    }
}

@Composable
fun CannabisDialog(
    onDismiss: () -> Unit,
    onSave: (CannabisAmount, CannabisEffect) -> Unit
) {
    var selectedAmount by remember { mutableStateOf<CannabisAmount?>(null) }
    var selectedEffect by remember { mutableStateOf<CannabisEffect?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Dodaj zioło") },
        text = {
            Column {
                Text("Ilość:", fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(
                        CannabisAmount.SMALL to "Mała",
                        CannabisAmount.MEDIUM to "Średnia",
                        CannabisAmount.LARGE to "Duża"
                    ).forEach { (amount, label) ->
                        FilterChip(
                            selected = selectedAmount == amount,
                            onClick = { selectedAmount = amount },
                            label = { Text(label, fontSize = 12.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = PrimaryBlue,
                                selectedLabelColor = androidx.compose.ui.graphics.Color.White
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("Efekt:", fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.height(8.dp))
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(
                        CannabisEffect.RELIEF to "Ulga",
                        CannabisEffect.SLOWDOWN to "Spowolnienie",
                        CannabisEffect.CLARITY to "Klarowność",
                        CannabisEffect.CUTOFF to "Odcięcie",
                        CannabisEffect.NONE to "Brak"
                    ).chunked(2).forEach { row ->
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            row.forEach { (effect, label) ->
                                FilterChip(
                                    selected = selectedEffect == effect,
                                    onClick = { selectedEffect = effect },
                                    label = { Text(label, fontSize = 12.sp) },
                                    modifier = Modifier.weight(1f),
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = PrimaryBlue,
                                        selectedLabelColor = androidx.compose.ui.graphics.Color.White
                                    )
                                )
                            }
                            if (row.size == 1) Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (selectedAmount != null && selectedEffect != null) {
                        onSave(selectedAmount!!, selectedEffect!!)
                    }
                },
                enabled = selectedAmount != null && selectedEffect != null
            ) {
                Text("ZAPISZ", color = PrimaryBlue)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("ANULUJ", color = TextSecondary)
            }
        }
    )
}
