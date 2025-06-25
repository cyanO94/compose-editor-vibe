package com.cyanlch.composeeditor.feature.editor.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * 포맷팅 도구 모음
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormattingToolbar(
    editorState: RichEditorState,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        LazyRow(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 기본 포맷팅 버튼들
            items(getFormattingButtons(editorState)) { button ->
                FormattingButton(
                    icon = button.icon,
                    isSelected = button.isSelected,
                    onClick = button.onClick,
                    contentDescription = button.description
                )
            }
            
            // 구분선
            item {
                VerticalDivider(
                    modifier = Modifier.height(32.dp),
                    thickness = 1.dp
                )
            }
            
            // 색상 선택
            item {
                ColorPickerButton(
                    currentColor = editorState.textColor,
                    onColorSelected = { editorState.setTextColor(it) }
                )
            }
            
            // 구분선
            item {
                VerticalDivider(
                    modifier = Modifier.height(32.dp),
                    thickness = 1.dp
                )
            }
            
            // 폰트 크기 조절
            item {
                FontSizeSelector(
                    currentSize = editorState.fontSize,
                    onSizeChanged = { editorState.setFontSize(it) }
                )
            }
        }
    }
}

/**
 * 개별 포맷팅 버튼
 */
@Composable
private fun FormattingButton(
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.size(40.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = if (isSelected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onSurface
            },
            modifier = Modifier.size(20.dp)
        )
    }
}

/**
 * 색상 선택 버튼
 */
@Composable
private fun ColorPickerButton(
    currentColor: Color,
    onColorSelected: (Color) -> Unit,
    modifier: Modifier = Modifier
) {
    var showColorPicker by remember { mutableStateOf(false) }
    
    IconButton(
        onClick = { showColorPicker = true },
        modifier = modifier.size(40.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Palette,
            contentDescription = "Text Color",
            tint = currentColor,
            modifier = Modifier.size(20.dp)
        )
    }
    
    if (showColorPicker) {
        ColorPickerDialog(
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
    onColorSelected: (Color) -> Unit,
    onDismiss: () -> Unit
) {
    val colors = listOf(
        Color.Black, Color.Red, Color.Blue, Color.Green,
        Color.Yellow, Color.Magenta, Color.Cyan, Color.Gray
    )
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("텍스트 색상 선택") },
        text = {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(colors) { color ->
                    Card(
                        modifier = Modifier
                            .size(40.dp),
                        colors = CardDefaults.cardColors(containerColor = color),
                        onClick = { onColorSelected(color) }
                    ) {}
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("취소")
            }
        }
    )
}

/**
 * 폰트 크기 선택기
 */
@Composable
private fun FontSizeSelector(
    currentSize: Int,
    onSizeChanged: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        IconButton(
            onClick = { onSizeChanged(currentSize - 2) },
            modifier = Modifier.size(32.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = "Decrease font size",
                modifier = Modifier.size(16.dp)
            )
        }
        
        Text(
            text = "${currentSize}sp",
            fontSize = 12.sp,
            modifier = Modifier.widthIn(min = 32.dp)
        )
        
        IconButton(
            onClick = { onSizeChanged(currentSize + 2) },
            modifier = Modifier.size(32.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Increase font size",
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

/**
 * 포맷팅 버튼 데이터 클래스
 */
private data class FormattingButtonData(
    val icon: ImageVector,
    val isSelected: Boolean,
    val onClick: () -> Unit,
    val description: String
)

/**
 * 포맷팅 버튼 목록 생성
 */
private fun getFormattingButtons(editorState: RichEditorState): List<FormattingButtonData> {
    return listOf(
        FormattingButtonData(
            icon = Icons.Default.FormatBold,
            isSelected = editorState.isBold,
            onClick = { editorState.toggleBold() },
            description = "Bold"
        ),
        FormattingButtonData(
            icon = Icons.Default.FormatItalic,
            isSelected = editorState.isItalic,
            onClick = { editorState.toggleItalic() },
            description = "Italic"
        ),
        FormattingButtonData(
            icon = Icons.Default.FormatUnderlined,
            isSelected = editorState.isUnderline,
            onClick = { editorState.toggleUnderline() },
            description = "Underline"
        ),
        FormattingButtonData(
            icon = Icons.Default.FormatStrikethrough,
            isSelected = editorState.isStrikethrough,
            onClick = { editorState.toggleStrikethrough() },
            description = "Strikethrough"
        )
    )
}

/**
 * 세로 구분선
 */
@Composable
private fun VerticalDivider(
    modifier: Modifier = Modifier,
    thickness: androidx.compose.ui.unit.Dp = 1.dp,
    color: Color = MaterialTheme.colorScheme.outline
) {
    Box(
        modifier = modifier
            .width(thickness)
            .fillMaxHeight()
            .padding(vertical = 4.dp)
    ) {
        androidx.compose.material3.HorizontalDivider(
            modifier = Modifier.fillMaxSize(),
            thickness = thickness,
            color = color
        )
    }
}
