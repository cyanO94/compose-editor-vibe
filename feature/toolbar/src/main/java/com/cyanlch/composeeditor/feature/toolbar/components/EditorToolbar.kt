package com.cyanlch.composeeditor.feature.toolbar.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.cyanlch.composeeditor.core.model.FormatAction

/**
 * 포맷팅 버튼 설정을 위한 데이터 클래스
 */
data class FormatButtonConfig(
    val icon: ImageVector,
    val contentDescription: String,
    val action: FormatAction,
    val isToggle: Boolean = true
)

/**
 * 포맷팅 버튼 그룹을 정의하는 sealed class
 */
sealed class FormatButtonGroup(val buttons: List<FormatButtonConfig>) {
    data object TextFormatting : FormatButtonGroup(
        listOf(
            FormatButtonConfig(Icons.Default.FormatBold, "Bold", FormatAction.Bold),
            FormatButtonConfig(Icons.Default.FormatItalic, "Italic", FormatAction.Italic),
            FormatButtonConfig(Icons.Default.FormatUnderlined, "Underline", FormatAction.Underline),
            FormatButtonConfig(Icons.Default.FormatStrikethrough, "Strikethrough", FormatAction.Strikethrough),
            FormatButtonConfig(Icons.Default.FormatClear, "Clear Formatting", FormatAction.ClearFormatting, false)
        )
    )
    
    data object ListFormatting : FormatButtonGroup(
        listOf(
            FormatButtonConfig(Icons.Default.FormatListNumbered, "Ordered List", FormatAction.OrderedList),
            FormatButtonConfig(Icons.AutoMirrored.Filled.FormatListBulleted, "Unordered List", FormatAction.UnorderedList),
            FormatButtonConfig(Icons.AutoMirrored.Filled.FormatIndentIncrease, "Increase Indent", FormatAction.IndentIncrease),
            FormatButtonConfig(Icons.AutoMirrored.Filled.FormatIndentDecrease, "Decrease Indent", FormatAction.IndentDecrease)
        )
    )
}

/**
 * 에디터 툴바 컴포넌트
 * 
 * 구조화된 포맷팅 버튼들을 제공하며, 확장 가능한 구조로 설계됨
 */
@Composable
fun EditorToolbar(
    onFormatAction: (FormatAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            // 텍스트 포맷팅 섹션
            ToolbarSection(
                buttonGroup = FormatButtonGroup.TextFormatting,
                onFormatAction = onFormatAction
            )
            
            HorizontalDivider()
            
            // 리스트 포맷팅 섹션
            ToolbarSection(
                buttonGroup = FormatButtonGroup.ListFormatting,
                onFormatAction = onFormatAction,
                includeExtras = true
            ) {
                FontSizeSelector(
                    onSizeSelected = { size ->
                        onFormatAction(FormatAction.FontSize(size))
                    }
                )
                
                ColorPickerButton(
                    onColorSelected = { _ ->
                        // TODO: FormatAction에 텍스트 색상 액션 추가 필요
                    }
                )
            }
        }
    }
}

/**
 * 툴바 섹션 컴포넌트
 */
@Composable
private fun ToolbarSection(
    buttonGroup: FormatButtonGroup,
    onFormatAction: (FormatAction) -> Unit,
    modifier: Modifier = Modifier,
    includeExtras: Boolean = false,
    extraContent: @Composable RowScope.() -> Unit = {}
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        items(buttonGroup.buttons) { buttonConfig ->
            FormatIconButton(
                config = buttonConfig,
                onClick = { onFormatAction(buttonConfig.action) }
            )
        }
        
        if (includeExtras) {
            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    extraContent()
                }
            }
        }
    }
}

/**
 * 포맷 아이콘 버튼
 * 
 * 상태 관리가 포함된 재사용 가능한 포맷팅 버튼
 */
@Composable
private fun FormatIconButton(
    config: FormatButtonConfig,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isActive by remember { mutableStateOf(false) }
    
    IconButton(
        onClick = {
            if (config.isToggle) {
                isActive = !isActive
            }
            onClick()
        },
        modifier = modifier
    ) {
        Icon(
            imageVector = config.icon,
            contentDescription = config.contentDescription,
            tint = if (isActive && config.isToggle) {
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
@Composable
private fun FontSizeSelector(
    onSizeSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedSize by remember { mutableIntStateOf(16) }
    
    val fontSizes = remember {
        listOf(8, 10, 12, 14, 16, 18, 20, 24, 28, 32, 36, 48)
    }
    
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
private fun ColorPickerButton(
    onColorSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    
    val colors = remember {
        listOf(
            "#000000" to "Black",
            "#FF0000" to "Red", 
            "#00FF00" to "Green",
            "#0000FF" to "Blue",
            "#FFFF00" to "Yellow",
            "#FF00FF" to "Magenta",
            "#00FFFF" to "Cyan"
        )
    }
    
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
