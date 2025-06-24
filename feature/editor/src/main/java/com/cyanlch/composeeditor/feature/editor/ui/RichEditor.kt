package com.cyanlch.composeeditor.feature.editor.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cyanlch.composeeditor.core.model.EditorAction
import com.cyanlch.composeeditor.core.model.FormattingState
import com.cyanlch.composeeditor.core.model.FormattingType
import com.cyanlch.composeeditor.feature.editor.domain.EditorManager
import com.cyanlch.composeeditor.feature.toolbar.ui.FormattingToolbar
import com.mohamedrejeb.richeditor.ui.BasicRichTextEditor
import kotlinx.coroutines.flow.collectLatest

/**
 * Rich Text Editor 메인 컴포넌트
 * 에디터와 툴바를 통합하여 제공하는 완전한 에디터 UI
 */
@Composable
fun RichEditor(
    editorManager: EditorManager,
    modifier: Modifier = Modifier,
    placeholder: String = "여기에 텍스트를 입력하세요...",
    readOnly: Boolean = false,
    minHeight: androidx.compose.ui.unit.Dp = 200.dp,
    maxHeight: androidx.compose.ui.unit.Dp = androidx.compose.ui.unit.Dp.Unspecified,
    showToolbar: Boolean = true
) {
    val richTextState by editorManager.richTextState.collectAsState()
    val formattingState by editorManager.formattingState.collectAsState()
    val isLoading by editorManager.isLoading.collectAsState()
    val focusManager = LocalFocusManager.current
    
    // 에디터 상태 변경 감지
    LaunchedEffect(richTextState) {
        editorManager.events.collectLatest { event ->
            // 이벤트 처리 (예: 자동 저장, 알림 등)
            when (event) {
                is com.cyanlch.composeeditor.core.model.EditorEvent.FormattingChanged -> {
                    // 서식 변경 처리
                }
                is com.cyanlch.composeeditor.core.model.EditorEvent.TextChanged -> {
                    // 텍스트 변경 처리
                }
                is com.cyanlch.composeeditor.core.model.EditorEvent.SelectionChanged -> {
                    // 선택 영역 변경 처리
                }
                is com.cyanlch.composeeditor.core.model.EditorEvent.EditorFocused -> {
                    // 포커스 획득 처리
                }
                is com.cyanlch.composeeditor.core.model.EditorEvent.EditorLostFocus -> {
                    // 포커스 잃음 처리
                }
            }
        }
    }
    
    Column(modifier = modifier) {
        // 툴바
        if (showToolbar && !readOnly) {
            FormattingToolbar(
                formattingState = formattingState,
                onFormattingAction = { type ->
                    val action = when (type) {
                        FormattingType.BOLD -> EditorAction.ToggleBold
                        FormattingType.ITALIC -> EditorAction.ToggleItalic
                        FormattingType.UNDERLINE -> EditorAction.ToggleUnderline
                        FormattingType.STRIKETHROUGH -> EditorAction.ToggleStrikethrough
                        FormattingType.ORDERED_LIST -> EditorAction.ToggleOrderedList
                        FormattingType.UNORDERED_LIST -> EditorAction.ToggleUnorderedList
                        FormattingType.CODE_BLOCK -> EditorAction.ToggleCodeBlock
                        else -> return@FormattingToolbar
                    }
                    editorManager.handleAction(action)
                },
                onColorAction = { type, color ->
                    val action = when (type) {
                        FormattingType.TEXT_COLOR -> EditorAction.ChangeTextColor(color)
                        FormattingType.BACKGROUND_COLOR -> EditorAction.ChangeBackgroundColor(color)
                        else -> return@FormattingToolbar
                    }
                    editorManager.handleAction(action)
                },
                onSizeAction = { size ->
                    editorManager.handleAction(EditorAction.ChangeFontSize(size))
                },
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
                if (isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = androidx.compose.ui.Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    BasicRichTextEditor(
                        state = richTextState,
                        modifier = Modifier
                            .fillMaxSize()
                            .onFocusChanged { focusState ->
                                if (focusState.isFocused) {
                                    editorManager.updateFormattingState()
                                }
                            }
                            .then(
                                if (maxHeight != androidx.compose.ui.unit.Dp.Unspecified) {
                                    Modifier.verticalScroll(rememberScrollState())
                                } else Modifier
                            ),
                        readOnly = readOnly,
                        placeholder = {
                            if (richTextState.annotatedString.text.isEmpty()) {
                                Text(
                                    text = placeholder,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        },
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
                                innerTextField()
                            }
                        }
                    )
                }
            }
        }
        
        // 에디터 통계 정보 (선택사항)
        if (!readOnly) {
            EditorStats(
                wordCount = richTextState.annotatedString.text.split("\\s+".toRegex()).filter { it.isNotBlank() }.size,
                characterCount = richTextState.annotatedString.text.length,
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
    wordCount: Int,
    characterCount: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "단어: $wordCount",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        Text(
            text = "문자: $characterCount",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}

/**
 * Rich Editor ViewModel
 * 상태 관리와 비즈니스 로직을 처리
 */
@Composable
fun rememberRichEditorState(): EditorManager {
    return remember { EditorManager() }
}

/**
 * Rich Editor의 간단한 사용 예제
 */
@Composable
fun SimpleRichEditor(
    modifier: Modifier = Modifier,
    placeholder: String = "여기에 텍스트를 입력하세요...",
    onTextChanged: (String) -> Unit = {}
) {
    val editorManager = rememberRichEditorState()
    
    LaunchedEffect(editorManager) {
        editorManager.events.collectLatest { event ->
            if (event is com.cyanlch.composeeditor.core.model.EditorEvent.TextChanged) {
                onTextChanged(event.text)
            }
        }
    }
    
    RichEditor(
        editorManager = editorManager,
        modifier = modifier,
        placeholder = placeholder
    )
}

@Preview(showBackground = true)
@Composable
private fun RichEditorPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            SimpleRichEditor(
                placeholder = "Rich Text Editor 미리보기..."
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RichEditorReadOnlyPreview() {
    val editorManager = rememberRichEditorState()
    
    LaunchedEffect(editorManager) {
        editorManager.setHtml("<p><b>Bold text</b> and <i>italic text</i> with <u>underline</u></p>")
    }
    
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            RichEditor(
                editorManager = editorManager,
                readOnly = true,
                showToolbar = false,
                placeholder = "읽기 전용 에디터"
            )
        }
    }
}
