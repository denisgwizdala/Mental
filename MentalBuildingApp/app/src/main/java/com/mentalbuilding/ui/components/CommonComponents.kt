package com.mentalbuilding.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mentalbuilding.ui.theme.*

// ========================================
// SECTION HEADER (nagłówki sekcji)
// ========================================
@Composable
fun SectionHeader(
    title: String,
    icon: ImageVector? = null,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon?.let {
            Icon(
                imageVector = it,
                contentDescription = null,
                tint = PrimaryBlue,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Medium,
            color = TextPrimary
        )
    }
}

// ========================================
// PRIMARY BUTTON (główny przycisk niebieski)
// ========================================
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector? = null
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryBlue,
            contentColor = Color.White,
            disabledContainerColor = BorderGray,
            disabledContentColor = TextSecondary
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        icon?.let {
            Icon(
                imageVector = it,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

// ========================================
// SECONDARY BUTTON (outline button)
// ========================================
@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryBlue),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = PrimaryBlue
        )
    ) {
        icon?.let {
            Icon(
                imageVector = it,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

// ========================================
// INFO CARD (białe karty z border)
// ========================================
@Composable
fun InfoCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val clickModifier = if (onClick != null) {
        Modifier.clickable(onClick = onClick)
    } else Modifier

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(BackgroundWhite)
            .border(
                width = 1.dp,
                color = BorderGray,
                shape = RoundedCornerShape(12.dp)
            )
            .then(clickModifier)
            .padding(16.dp),
        content = content
    )
}

// ========================================
// SLIDER WITH LABEL (0-5 scale)
// ========================================
@Composable
fun LabeledSlider(
    label: String,
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    range: IntRange = 0..5,
    color: Color = PrimaryBlue
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                color = TextPrimary
            )
            Text(
                text = "$value/${range.last}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Slider(
            value = value.toFloat(),
            onValueChange = { onValueChange(it.toInt()) },
            valueRange = range.first.toFloat()..range.last.toFloat(),
            steps = range.last - range.first - 1,
            colors = SliderDefaults.colors(
                thumbColor = color,
                activeTrackColor = color,
                inactiveTrackColor = BorderGray
            )
        )
    }
}

// ========================================
// CHIP GROUP (multiple selection)
// ========================================
@Composable
fun ChipGroup(
    label: String,
    options: List<String>,
    selectedOptions: List<String>,
    onSelectionChange: (List<String>) -> Unit,
    modifier: Modifier = Modifier,
    singleSelection: Boolean = false
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = TextPrimary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            options.chunked(3).forEach { row ->
                Column(modifier = Modifier.weight(1f)) {
                    row.forEach { option ->
                        val isSelected = option in selectedOptions
                        FilterChip(
                            selected = isSelected,
                            onClick = {
                                if (singleSelection) {
                                    onSelectionChange(listOf(option))
                                } else {
                                    onSelectionChange(
                                        if (isSelected) selectedOptions - option
                                        else selectedOptions + option
                                    )
                                }
                            },
                            label = { Text(option, fontSize = 13.sp) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = PrimaryBlue,
                                selectedLabelColor = Color.White,
                                containerColor = BackgroundWhite,
                                labelColor = TextPrimary
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = isSelected,
                                borderColor = if (isSelected) PrimaryBlue else BorderGray,
                                selectedBorderColor = PrimaryBlue,
                                borderWidth = 1.dp
                            )
                        )
                    }
                }
            }
        }
    }
}

// ========================================
// QUICK LOG BUTTON (+ button dla quick actions)
// ========================================
@Composable
fun QuickLogButton(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    count: Int? = null
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, BorderGray),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = TextPrimary
        )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = PrimaryBlue
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            fontSize = 14.sp
        )
        count?.let {
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "($it)",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryBlue
            )
        }
    }
}

// ========================================
// TIME DISPLAY (pokazuje timestamp)
// ========================================
@Composable
fun TimeDisplay(
    label: String,
    time: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = TextSecondary
        )
        Text(
            text = time,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            color = TextPrimary
        )
    }
}

// ========================================
// DIVIDER (separator)
// ========================================
@Composable
fun ContentDivider(modifier: Modifier = Modifier) {
    HorizontalDivider(
        modifier = modifier.padding(vertical = 16.dp),
        thickness = 1.dp,
        color = DividerGray
    )
}
