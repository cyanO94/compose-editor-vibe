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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

/**
 * 고급 포맷팅 도구 모음
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdvancedToolbar(
    editorState: RichEditorState,
    modifier: Modifier = Modifier,
    onListToggle: () -> Unit = {},
    onNumberedListToggle: () -> Unit = {},
    onLinkInsert: () -> Unit = {},
    onImageInsert: () -> Unit = {},
    onTableInsert: () -> Unit = {},
    onCodeBlockInsert: () -> Unit = {}
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
            // 리스트 관련 버튼들
            items(getListButtons(onListToggle, onNumberedListToggle)) { button ->
                AdvancedToolbarButton(
                    icon = button.icon,
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
            
            // 삽입 관련 버튼들
            items(getInsertButtons(onLinkInsert, onImageInsert, onTableInsert, onCodeBlockInsert)) { button ->
                AdvancedToolbarButton(
                    icon = button.icon,
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
            
            // 텍스트 정렬 버튼들
            items(getAlignmentButtons()) { button ->
                AdvancedToolbarButton(
                    icon = button.icon,
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
            
            // 들여쓰기 버튼들
            items(getIndentButtons()) { button ->
                AdvancedToolbarButton(
                    icon = button.icon,
                    onClick = button.onClick,
                    contentDescription = button.description
                )
            }
        }
    }
}

/**
 * 고급 툴바 버튼
 */
@Composable
private fun AdvancedToolbarButton(
    icon: ImageVector,
    onClick: () -> Unit,
    contentDescription: String,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false
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
 * 세로 구분선
 */
@Composable
private fun VerticalDivider(
    modifier: Modifier = Modifier,
    thickness: androidx.compose.ui.unit.Dp = 1.dp,
    color: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.outline
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

/**
 * 고급 툴바 버튼 데이터 클래스
 */
private data class AdvancedToolbarButtonData(
    val icon: ImageVector,
    val onClick: () -> Unit,
    val description: String,
    val isSelected: Boolean = false
)

/**
 * 리스트 관련 버튼들
 */
private fun getListButtons(
    onListToggle: () -> Unit,
    onNumberedListToggle: () -> Unit
): List<AdvancedToolbarButtonData> {
    return listOf(
        AdvancedToolbarButtonData(
            icon = Icons.Default.FormatListBulleted,
            onClick = onListToggle,
            description = "Bullet List"
        ),
        AdvancedToolbarButtonData(
            icon = Icons.Default.FormatListNumbered,
            onClick = onNumberedListToggle,
            description = "Numbered List"
        )
    )
}

/**
 * 삽입 관련 버튼들
 */
private fun getInsertButtons(
    onLinkInsert: () -> Unit,
    onImageInsert: () -> Unit,
    onTableInsert: () -> Unit,
    onCodeBlockInsert: () -> Unit
): List<AdvancedToolbarButtonData> {
    return listOf(
        AdvancedToolbarButtonData(
            icon = Icons.Default.Link,
            onClick = onLinkInsert,
            description = "Insert Link"
        ),
        AdvancedToolbarButtonData(
            icon = Icons.Default.Image,
            onClick = onImageInsert,
            description = "Insert Image"
        ),
        AdvancedToolbarButtonData(
            icon = Icons.Default.TableChart,
            onClick = onTableInsert,
            description = "Insert Table"
        ),
        AdvancedToolbarButtonData(
            icon = Icons.Default.Code,
            onClick = onCodeBlockInsert,
            description = "Insert Code Block"
        )
    )
}

/**
 * 텍스트 정렬 버튼들
 */
private fun getAlignmentButtons(): List<AdvancedToolbarButtonData> {
    return listOf(
        AdvancedToolbarButtonData(
            icon = Icons.Default.FormatAlignLeft,
            onClick = { /* TODO: Implement left align */ },
            description = "Align Left"
        ),
        AdvancedToolbarButtonData(
            icon = Icons.Default.FormatAlignCenter,
            onClick = { /* TODO: Implement center align */ },
            description = "Align Center"
        ),
        AdvancedToolbarButtonData(
            icon = Icons.Default.FormatAlignRight,
            onClick = { /* TODO: Implement right align */ },
            description = "Align Right"
        ),
        AdvancedToolbarButtonData(
            icon = Icons.Default.FormatAlignJustify,
            onClick = { /* TODO: Implement justify */ },
            description = "Justify"
        )
    )
}

/**
 * 들여쓰기 버튼들
 */
private fun getIndentButtons(): List<AdvancedToolbarButtonData> {
    return listOf(
        AdvancedToolbarButtonData(
            icon = Icons.Default.FormatIndentDecrease,
            onClick = { /* TODO: Implement decrease indent */ },
            description = "Decrease Indent"
        ),
        AdvancedToolbarButtonData(
            icon = Icons.Default.FormatIndentIncrease,
            onClick = { /* TODO: Implement increase indent */ },
            description = "Increase Indent"
        )
    )
}

/**
 * 링크 삽입 다이얼로그
 */
@Composable
fun LinkInsertDialog(
    onLinkInsert: (String, String) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var linkText by remember { mutableStateOf("") }
    var linkUrl by remember { mutableStateOf("") }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("링크 삽입") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = linkText,
                    onValueChange = { linkText = it },
                    label = { Text("링크 텍스트") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = linkUrl,
                    onValueChange = { linkUrl = it },
                    label = { Text("URL") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (linkText.isNotBlank() && linkUrl.isNotBlank()) {
                        onLinkInsert(linkText, linkUrl)
                    }
                },
                enabled = linkText.isNotBlank() && linkUrl.isNotBlank()
            ) {
                Text("삽입")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("취소")
            }
        },
        modifier = modifier
    )
}

/**
 * 테이블 삽입 다이얼로그
 */
@Composable
fun TableInsertDialog(
    onTableInsert: (Int, Int) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var rows by remember { mutableStateOf(3) }
    var columns by remember { mutableStateOf(3) }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("테이블 삽입") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("행:")
                    IconButton(onClick = { if (rows > 1) rows-- }) {
                        Icon(Icons.Default.Remove, contentDescription = "Decrease rows")
                    }
                    Text("$rows")
                    IconButton(onClick = { if (rows < 10) rows++ }) {
                        Icon(Icons.Default.Add, contentDescription = "Increase rows")
                    }
                }
                
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("열:")
                    IconButton(onClick = { if (columns > 1) columns-- }) {
                        Icon(Icons.Default.Remove, contentDescription = "Decrease columns")
                    }
                    Text("$columns")
                    IconButton(onClick = { if (columns < 10) columns++ }) {
                        Icon(Icons.Default.Add, contentDescription = "Increase columns")
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onTableInsert(rows, columns) }
            ) {
                Text("삽입")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("취소")
            }
        },
        modifier = modifier
    )
}
