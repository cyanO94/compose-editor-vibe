package com.cyanlch.composeeditor.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohamedrejeb.richeditor.ui.BasicRichTextEditor

/**
 * Rich Text Editor 메인 컴포넌트
 * 에디터와 툴바를 통합하여 제공하는 완전한 에디터 UI
 */
@Composable
fun RichTextEditor(
    modifier: Modifier = Modifier,
    editorState: RichTextEditorState = rememberRichTextEditorState(),
    placeholder: String = "여기에 텍스트를 입력하세요...",
    readOnly: Boolean = false,
    minHeight: androidx.compose.ui.unit.Dp = 200.dp,
    maxHeight: androidx.compose.ui.unit.Dp = androidx.compose.ui.unit.Dp.Unspecified,
    showToolbar: Boolean = true,
    showStats: Boolean = true
) {
    Column(modifier = modifier) {
        // 툴바
        if (showToolbar && !readOnly) {
            FormattingToolbar(
                editorState = editorState,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        
        // 에디터 영역
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (maxHeight != androidx.compose.ui.unit.Dp.Unspecified) {
                        Modifier.heightIn(min = minHeight, max = maxHeight)
                    } else {
                        Modifier.defaultMinSize(minHeight = minHeight)
                    }
                ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                BasicRichTextEditor(
                    state = editorState.state,
                    modifier = Modifier
                        .fillMaxSize()
                        .then(
                            if (maxHeight != androidx.compose.ui.unit.Dp.Unspecified) {
                                Modifier.verticalScroll(rememberScrollState())
                            } else Modifier
                        ),
                    readOnly = readOnly,
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 24.sp
                    ),
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    if (readOnly) {
                                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                                    } else {
                                        Color.Transparent
                                    }
                                )
                                .padding(8.dp)
                        ) {
                            if (editorState.getText().isEmpty()) {
                                Text(
                                    text = placeholder,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }
        }
        
        // 에디터 통계 정보 (선택사항)
        if (showStats && !readOnly) {
            EditorStats(
                text = editorState.getText(),
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

/**
 * 에디터 통계 정보 컴포넌트
 */
@Composable
private fun EditorStats(
    text: String,
    modifier: Modifier = Modifier
) {
    val wordCount = text.split("\\s+".toRegex()).filter { it.isNotBlank() }.size
    val characterCount = text.length
    val lineCount = text.split('\n').size
    
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatItem(
                label = "단어",
                value = wordCount.toString()
            )
            
            VerticalDivider(
                modifier = Modifier.height(24.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outline
            )
            
            StatItem(
                label = "문자",
                value = characterCount.toString()
            )
            
            VerticalDivider(
                modifier = Modifier.height(24.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outline
            )
            
            StatItem(
                label = "줄",
                value = lineCount.toString()
            )
        }
    }
}

/**
 * 통계 아이템 컴포넌트
 */
@Composable
private fun StatItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
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
private fun RichTextEditorPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            RichTextEditor(
                placeholder = "Rich Text Editor 미리보기..."
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RichTextEditorReadOnlyPreview() {
    val editorState = rememberRichTextEditorState()
    
    LaunchedEffect(editorState) {
        editorState.setHtml("<p><b>Bold text</b> and <i>italic text</i> with <u>underline</u></p>")
    }
    
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            RichTextEditor(
                editorState = editorState,
                readOnly = true,
                showToolbar = false,
                placeholder = "읽기 전용 에디터"
            )
        }
    }
}
