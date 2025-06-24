package com.cyanlch.composeeditor.feature.toolbar.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cyanlch.composeeditor.core.model.FormattingState
import com.cyanlch.composeeditor.core.model.FormattingType

/**
 * 서식 툴바 컴포넌트
 * Rich Text Editor의 서식 기능을 제공하는 UI
 */
@Composable
fun FormattingToolbar(
    formattingState: FormattingState,
    onFormattingAction: (FormattingType) -> Unit,
    onColorAction: (FormattingType, Long) -> Unit,
    onSizeAction: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        LazyRow(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 기본 서식 버튼들
            item {
                FormattingToggleButton(
                    icon = Icons.Default.FormatBold,
                    isActive = formattingState.isBold,
                    contentDescription = "Bold",
                    onClick = { onFormattingAction(FormattingType.BOLD) }
                )
            }
            
            item {
                FormattingToggleButton(
                    icon = Icons.Default.FormatItalic,
                    isActive = formattingState.isItalic,
                    contentDescription = "Italic",
                    onClick = { onFormattingAction(FormattingType.ITALIC) }
                )
            }
            
            item {
                FormattingToggleButton(
                    icon = Icons.Default.FormatUnderlined,
                    isActive = formattingState.isUnderlined,
                    contentDescription = "Underline",
                    onClick = { onFormattingAction(FormattingType.UNDERLINE) }
                )
            }
            
            item {
                FormattingToggleButton(
                    icon = Icons.Default.FormatStrikethrough,
                    isActive = formattingState.isStrikethrough,
                    contentDescription = "Strikethrough",
                    onClick = { onFormattingAction(FormattingType.STRIKETHROUGH) }
                )
            }
            
            // 구분선
            item {
                VerticalDivider(
                    modifier = Modifier
                        .height(24.dp)
                        .padding(horizontal = 4.dp),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outline
                )
            }
            
            // 리스트 버튼들
            item {
                FormattingToggleButton(
                    icon = Icons.Default.FormatListNumbered,
                    isActive = formattingState.isOrderedList,
                    contentDescription = "Ordered List",
                    onClick = { onFormattingAction(FormattingType.ORDERED_LIST) }
                )
            }
            
            item {
                FormattingToggleButton(
                    icon = Icons.Default.FormatListBulleted,
                    isActive = formattingState.isUnorderedList,
                    contentDescription = "Unordered List",
                    onClick = { onFormattingAction(FormattingType.UNORDERED_LIST) }
                )
            }
            
            item {
                FormattingToggleButton(
                    icon = Icons.Default.Code,
                    isActive = formattingState.isCodeBlock,
                    contentDescription = "Code Block",
                    onClick = { onFormattingAction(FormattingType.CODE_BLOCK) }
                )
            }
            
            // 구분선
            item {
                VerticalDivider(
                    modifier = Modifier
                        .height(24.dp)
                        .padding(horizontal = 4.dp),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outline
                )
            }
            
            // 색상 및 크기 컨트롤
            item {
                ColorPickerButton(
                    currentColor = Color(formattingState.textColor),
                    contentDescription = "Text Color",
                    onColorSelected = { color ->
                        onColorAction(FormattingType.TEXT_COLOR, color.value.toLong())
                    }
                )
            }
            
            item {
                ColorPickerButton(
                    currentColor = Color(formattingState.backgroundColor),
                    contentDescription = "Background Color",
                    onColorSelected = { color ->
                        onColorAction(FormattingType.BACKGROUND_COLOR, color.value.toLong())
                    }
                )
            }
            
            item {
                FontSizePicker(
                    currentSize = formattingState.fontSize,
                    onSizeSelected = onSizeAction
                )
            }
        }
    }
}

/**
 * 서식 토글 버튼 컴포넌트
 */
@Composable
private fun FormattingToggleButton(
    icon: ImageVector,
    isActive: Boolean,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .size(40.dp)
            .background(
                color = if (isActive) {
                    MaterialTheme.colorScheme.primaryContainer
                } else {
                    Color.Transparent
                },
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = if (isActive) {
                MaterialTheme.colorScheme.onPrimaryContainer
            } else {
                MaterialTheme.colorScheme.onSurface
            }
        )
    }
}

/**
 * 색상 선택 버튼 컴포넌트
 */
@Composable
private fun ColorPickerButton(
    currentColor: Color,
    contentDescription: String,
    onColorSelected: (Color) -> Unit,
    modifier: Modifier = Modifier
) {
    var showColorPicker by remember { mutableStateOf(false) }
    
    IconButton(
        onClick = { showColorPicker = true },
        modifier = modifier.size(40.dp)
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(currentColor)
        )
    }
    
    if (showColorPicker) {
        ColorPickerDialog(
            currentColor = currentColor,
            onColorSelected = { color ->
                onColorSelected(color)
                showColorPicker = false
            },
            onDismiss = { showColorPicker = false }
        )
    }
}

/**
 * 색상 선택 다이얼로그
 */
@Composable
private fun ColorPickerDialog(
    currentColor: Color,
    onColorSelected: (Color) -> Unit,
    onDismiss: () -> Unit
) {
    val predefinedColors = listOf(
        Color.Black, Color.Red, Color.Green, Color.Blue,
        Color.Yellow, Color.Magenta, Color.Cyan, Color.Gray,
        Color(0xFF800080), Color(0xFF008000), Color(0xFF000080), Color(0xFF800000)
    )
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("색상 선택") },
        text = {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(predefinedColors) { color ->
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(color)
                            .then(
                                if (color == currentColor) {
                                    Modifier.background(
                                        MaterialTheme.colorScheme.outline,
                                        CircleShape
                                    )
                                } else Modifier
                            )
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("확인")
            }
        }
    )
}

/**
 * 폰트 크기 선택 컴포넌트
 */
@Composable
private fun FontSizePicker(
    currentSize: Int,
    onSizeSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val fontSizes = listOf(8, 10, 12, 14, 16, 18, 20, 24, 28, 32, 36, 48)
    
    Box(modifier = modifier) {
        IconButton(onClick = { expanded = true }) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = currentSize.toString(),
                    style = MaterialTheme.typography.bodySmall
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Font Size",
                    modifier = Modifier.size(16.dp)
                )
            }
        }
        
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            fontSizes.forEach { size ->
                DropdownMenuItem(
                    text = { Text("${size}sp") },
                    onClick = {
                        onSizeSelected(size)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun VerticalDivider(
    modifier: Modifier = Modifier,
    thickness: androidx.compose.ui.unit.Dp = 1.dp,
    color: Color = MaterialTheme.colorScheme.outline
) {
    Box(
        modifier = modifier
            .width(thickness)
            .background(color)
    )
}

@Preview(showBackground = true)
@Composable
private fun FormattingToolbarPreview() {
    FormattingToolbar(
        formattingState = FormattingState(
            isBold = true,
            isItalic = false,
            fontSize = 16
        ),
        onFormattingAction = {},
        onColorAction = { _, _ -> },
        onSizeAction = {}
    )
}
