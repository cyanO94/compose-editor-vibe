package com.cyanlch.composeeditor.feature.editor.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohamedrejeb.richeditor.ui.BasicRichTextEditor

/**
 * 리치 텍스트 에디터 컴포넌트
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RichEditor(
    editorManager: RichEditorState,
    modifier: Modifier = Modifier,
    placeholder: String = "텍스트를 입력하세요...",
    minHeight: Dp = 200.dp,
    showToolbar: Boolean = true,
    showAdvancedToolbar: Boolean = false
) {
    var showLinkDialog by remember { mutableStateOf(false) }
    var showTableDialog by remember { mutableStateOf(false) }
    
    Column(modifier = modifier) {
        // 기본 포맷팅 툴바
        if (showToolbar) {
            FormattingToolbar(
                editorState = editorManager,
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(8.dp))
        }
        
        // 고급 포맷팅 툴바
        if (showAdvancedToolbar) {
            AdvancedToolbar(
                editorState = editorManager,
                modifier = Modifier.fillMaxWidth(),
                onListToggle = { editorManager.toggleUnorderedList() },
                onNumberedListToggle = { editorManager.toggleOrderedList() },
                onLinkInsert = { showLinkDialog = true },
                onImageInsert = { /* TODO: 이미지 삽입 구현 */ },
                onTableInsert = { showTableDialog = true },
                onCodeBlockInsert = { editorManager.toggleCodeSpan() }
            )
            
            Spacer(modifier = Modifier.height(8.dp))
        }
        
        // 에디터 영역
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = minHeight),
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
                    state = editorManager.richTextState,
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    textStyle = TextStyle(
                        fontSize = editorManager.fontSize.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = (editorManager.fontSize * 1.4).sp
                    ),
                    decorationBox = { innerTextField ->
                        Box {
                            if (editorManager.content.isEmpty()) {
                                Text(
                                    text = placeholder,
                                    style = TextStyle(
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                                        fontSize = editorManager.fontSize.sp
                                    )
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }
        }
        
        // 상태 표시
        if (showToolbar) {
            Spacer(modifier = Modifier.height(8.dp))
            StatusBar(editorState = editorManager)
        }
    }
    
    // 다이얼로그들
    if (showLinkDialog) {
        LinkInsertDialog(
            onLinkInsert = { text, url ->
                editorManager.addLink(text, url)
                showLinkDialog = false
            },
            onDismiss = { showLinkDialog = false }
        )
    }
    
    if (showTableDialog) {
        TableInsertDialog(
            onTableInsert = { rows, cols ->
                // TODO: 테이블 삽입 구현
                showTableDialog = false
            },
            onDismiss = { showTableDialog = false }
        )
    }
}

/**
 * 에디터 상태 표시 바
 */
@Composable
private fun StatusBar(
    editorState: RichEditorState,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 왼쪽: 문자 수 및 단어 수 정보
            Column {
                Text(
                    text = "글자 수: ${editorState.content.length}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "단어 수: ${TextFormatUtils.getWordCount(editorState.content)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            // 오른쪽: 현재 포맷팅 상태
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (editorState.isBold || editorState.isItalic || 
                    editorState.isUnderline || editorState.isStrikethrough) {
                    
                    val activeFormats = mutableListOf<String>()
                    if (editorState.isBold) activeFormats.add("B")
                    if (editorState.isItalic) activeFormats.add("I")
                    if (editorState.isUnderline) activeFormats.add("U")
                    if (editorState.isStrikethrough) activeFormats.add("S")
                    
                    Text(
                        text = activeFormats.joinToString("/"),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                } else {
                    Text(
                        text = "일반",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                VerticalDivider(
                    modifier = Modifier.height(16.dp),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outline
                )
                
                Text(
                    text = "${editorState.fontSize}sp",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

/**
 * 세로 구분선 (StatusBar용)
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
    ) {
        androidx.compose.material3.HorizontalDivider(
            modifier = Modifier.fillMaxSize(),
            thickness = thickness,
            color = color
        )
    }
}
