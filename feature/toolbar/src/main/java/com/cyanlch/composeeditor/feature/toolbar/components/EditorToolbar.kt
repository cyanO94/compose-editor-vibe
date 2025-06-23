package com.cyanlch.composeeditor.feature.toolbar.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.cyanlch.composeeditor.core.model.EditorEvent
import com.cyanlch.composeeditor.core.model.FormatAction

/**
 * 포맷팅 버튼 데이터 클래스
 */
data class FormatButton(
    val icon: ImageVector,
    val contentDescription: String,
    val action: FormatAction,
    val isToggle: Boolean = true
)

/**
 * 에디터 툴바 컴포넌트
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorToolbar(
    onFormatAction: (FormatAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val formatButtons = remember {
        listOf(
            FormatButton(Icons.Default.FormatBold, "Bold", FormatAction.Bold),
            FormatButton(Icons.Default.FormatItalic, "Italic", FormatAction.Italic),
            FormatButton(Icons.Default.FormatUnderlined, "Underline", FormatAction.Underline),
            FormatButton(Icons.Default.FormatStrikethrough, "Strikethrough", FormatAction.Strikethrough),
            FormatButton(Icons.Default.FormatListNumbered, "Ordered List", FormatAction.OrderedList),
            FormatButton(Icons.Default.FormatListBulleted, "Unordered List", FormatAction.UnorderedList),
            FormatButton(Icons.Default.FormatIndentIncrease, "Increase Indent", FormatAction.IndentIncrease),
            FormatButton(Icons.Default.FormatIndentDecrease, "Decrease Indent", FormatAction.IndentDecrease),
            FormatButton(Icons.Default.FormatClear, "Clear Formatting", FormatAction.ClearFormatting, false)
        )
    }
    
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            // 첫 번째 행: 기본 포맷팅
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) {
                items(formatButtons.take(5)) { button ->
                    FormatIconButton(
                        button = button,
                        onClick = { onFormatAction(button.action) }
                    )
                }
            }
            
            Divider()
            
            // 두 번째 행: 리스트 및 기타
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) {
                items(formatButtons.drop(5)) { button ->
                    FormatIconButton(
                        button = button,
                        onClick = { onFormatAction(button.action) }
                    )
                }
                
                item {
                    FontSizeSelector(
                        onSizeSelected = { size ->
                            onFormatAction(FormatAction.FontSize(size))
                        }
                    )
                }
            }
        }
    }
}

/**
 * 포맷 아이콘 버튼
 */
@Composable
private fun FormatIconButton(
    button: FormatButton,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isActive by remember { mutableStateOf(false) }
    
    IconButton(
        onClick = {
            if (button.isToggle) {
                isActive = !isActive
            }
            onClick()
        },
        modifier = modifier
    ) {
        Icon(
            imageVector = button.icon,
            contentDescription = button.contentDescription,
            tint = if (isActive && button.isToggle) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onSurface
            }
        )
    }
}

/**
 * 폰트 크기 선택 드롭다운
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FontSizeSelector(
    onSizeSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedSize by remember { mutableStateOf(16) }
    
    val fontSizes = listOf(8, 10, 12, 14, 16, 18, 20, 24, 28, 32, 36, 48)
    
    Box(modifier = modifier) {
        TextButton(
            onClick = { expanded = true }
        ) {
            Text("${selectedSize}px")
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Font Size"
            )
        }
        
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            fontSizes.forEach { size ->
                DropdownMenuItem(
                    text = { Text("${size}px") },
                    onClick = {
                        selectedSize = size
                        onSizeSelected(size)
                        expanded = false
                    }
                )
            }
        }
    }
}

/**
 * 색상 선택 버튼
 */
@Composable
fun ColorPickerButton(
    onColorSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    
    val colors = listOf(
        "#000000" to "Black",
        "#FF0000" to "Red", 
        "#00FF00" to "Green",
        "#0000FF" to "Blue",
        "#FFFF00" to "Yellow",
        "#FF00FF" to "Magenta",
        "#00FFFF" to "Cyan"
    )
    
    Box(modifier = modifier) {
        IconButton(
            onClick = { expanded = true }
        ) {
            Icon(
                imageVector = Icons.Default.Palette,
                contentDescription = "Text Color"
            )
        }
        
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            colors.forEach { (colorCode, colorName) ->
                DropdownMenuItem(
                    text = { Text(colorName) },
                    onClick = {
                        onColorSelected(colorCode)
                        expanded = false
                    }
                )
            }
        }
    }
}
