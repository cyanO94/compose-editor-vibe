package com.cyanlch.composeeditor.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
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

/**
 * 서식 설정을 위한 툴바 컴포넌트
 */
@Composable
fun FormattingToolbar(
    editorState: RichTextEditorState,
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
                FormattingButton(
                    icon = Icons.Default.FormatBold,
                    isActive = editorState.isBold,
                    contentDescription = "Bold",
                    onClick = { editorState.toggleBold() }
                )
            }
            
            item {
                FormattingButton(
                    icon = Icons.Default.FormatItalic,
                    isActive = editorState.isItalic,
                    contentDescription = "Italic",
                    onClick = { editorState.toggleItalic() }
                )
            }
            
            item {
                FormattingButton(
                    icon = Icons.Default.FormatUnderlined,
                    isActive = editorState.isUnderlined,
                    contentDescription = "Underline",
                    onClick = { editorState.toggleUnderline() }
                )
            }
            
            item {
                FormattingButton(
                    icon = Icons.Default.FormatStrikethrough,
                    isActive = editorState.isStrikethrough,
                    contentDescription = "Strikethrough",
                    onClick = { editorState.toggleStrikethrough() }
                )
            }
            
            // 구분선
            item {
                VerticalDivider(
                    modifier = Modifier
                        .height(24.dp)
                        .padding(horizontal = 4.dp)
                )
            }
            
            // 리스트 버튼들
            item {
                FormattingButton(
                    icon = Icons.Default.FormatListNumbered,
                    isActive = editorState.isOrderedList,
                    contentDescription = "Ordered List",
                    onClick = { editorState.toggleOrderedList() }
                )
            }
            
            item {
                FormattingButton(
                    icon = Icons.Default.FormatListBulleted,
                    isActive = editorState.isUnorderedList,
                    contentDescription = "Unordered List",
                    onClick = { editorState.toggleUnorderedList() }
                )
            }
            
            item {
                FormattingButton(
                    icon = Icons.Default.Code,
                    isActive = editorState.isCodeSpan,
                    contentDescription = "Code",
                    onClick = { editorState.toggleCodeSpan() }
                )
            }
            
            // 구분선
            item {
                VerticalDivider(
                    modifier = Modifier
                        .height(24.dp)
                        .padding(horizontal = 4.dp)
                )
            }
            
            // 색상 버튼들
            item {
                ColorButton(
                    color = Color.Red,
                    contentDescription = "Red Text",
                    onClick = { editorState.setTextColor(Color.Red) }
                )
            }
            
            item {
                ColorButton(
                    color = Color.Blue,
                    contentDescription = "Blue Text",
                    onClick = { editorState.setTextColor(Color.Blue) }
                )
            }
            
            item {
                ColorButton(
                    color = Color.Green,
                    contentDescription = "Green Text",
                    onClick = { editorState.setTextColor(Color.Green) }
                )
            }
            
            // 구분선
            item {
                VerticalDivider(
                    modifier = Modifier
                        .height(24.dp)
                        .padding(horizontal = 4.dp)
                )
            }
            
            // 실행 취소/다시 실행
            item {
                FormattingButton(
                    icon = Icons.Default.Undo,
                    isActive = false,
                    contentDescription = "Undo",
                    onClick = { editorState.undo() }
                )
            }
            
            item {
                FormattingButton(
                    icon = Icons.Default.Redo,
                    isActive = false,
                    contentDescription = "Redo",
                    onClick = { editorState.redo() }
                )
            }
            
            // 서식 지우기
            item {
                FormattingButton(
                    icon = Icons.Default.FormatClear,
                    isActive = false,
                    contentDescription = "Clear Formatting",
                    onClick = { editorState.clearFormatting() }
                )
            }
        }
    }
}

/**
 * 서식 버튼 컴포넌트
 */
@Composable
private fun FormattingButton(
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
 * 색상 버튼 컴포넌트
 */
@Composable
private fun ColorButton(
    color: Color,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.size(40.dp)
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(color)
        )
    }
}

/**
 * 수직 구분선 컴포넌트
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
            .background(color)
    )
}

@Preview(showBackground = true)
@Composable
private fun FormattingToolbarPreview() {
    MaterialTheme {
        FormattingToolbar(
            editorState = rememberRichTextEditorState()
        )
    }
}
