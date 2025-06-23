package com.cyanlch.composeeditor.feature.editor.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.cyanlch.composeeditor.core.model.EditorEvent
import com.cyanlch.composeeditor.core.model.EditorState
import com.cyanlch.composeeditor.feature.editor.EditorViewModel
import com.mohamedrejeb.richeditor.compose.RichTextEditor
import com.mohamedrejeb.richeditor.model.RichTextState

/**
 * Rich Text Editor 메인 컴포넌트
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RichEditorComponent(
    viewModel: EditorViewModel,
    editorState: EditorState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // 제목 입력 필드
        OutlinedTextField(
            value = editorState.title,
            onValueChange = { viewModel.onEvent(EditorEvent.TitleChanged(it)) },
            label = { Text("제목") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            singleLine = true
        )
        
        // Rich Text Editor
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(8.dp)),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            RichTextEditor(
                state = viewModel.richTextState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                placeholder = {
                    Text(
                        text = "여기에 내용을 입력하세요...",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            )
        }
        
        // 편집 모드 토글 버튼
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { viewModel.onEvent(EditorEvent.ToggleEditMode) }
            ) {
                Text(if (editorState.isEditing) "미리보기" else "편집")
            }
            
            Button(
                onClick = { viewModel.onEvent(EditorEvent.SaveDocument) }
            ) {
                Text("저장")
            }
        }
    }
    
    // 컨텐츠 변경 감지
    LaunchedEffect(viewModel.richTextState.annotatedString) {
        val htmlContent = viewModel.richTextState.toHtml()
        if (htmlContent != editorState.htmlContent) {
            viewModel.onEvent(EditorEvent.ContentChanged(htmlContent))
        }
    }
}

/**
 * HTML 미리보기 컴포넌트
 */
@Composable
fun HtmlPreviewComponent(
    htmlContent: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "HTML 미리보기",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            OutlinedTextField(
                value = htmlContent,
                onValueChange = { },
                modifier = Modifier.fillMaxSize(),
                readOnly = true,
                textStyle = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                )
            )
        }
    }
}
