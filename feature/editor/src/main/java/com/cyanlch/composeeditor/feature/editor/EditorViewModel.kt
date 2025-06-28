package com.cyanlch.composeeditor.feature.editor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cyanlch.composeeditor.core.model.EditorEvent
import com.cyanlch.composeeditor.core.model.EditorState
import com.cyanlch.composeeditor.core.model.FormatAction
import com.mohamedrejeb.richeditor.model.RichTextState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * Rich Editor의 상태와 비즈니스 로직을 관리하는 ViewModel
 *
 * 주요 책임:
 * - 에디터 상태 관리 (EditorState)
 * - Rich Text 상태 관리 (RichTextState)
 * - 포맷팅 작업 처리
 * - 문서 생명주기 관리 (생성, 저장, 로드)
 */
class EditorViewModel : ViewModel() {

    private val _editorState = MutableStateFlow(EditorState())

    // Rich Editor State - Compose UI에서 직접 접근 가능
    var richTextState by mutableStateOf(RichTextState())
        private set

    /**
     * 에디터 이벤트 처리
     *
     * @param event 처리할 에디터 이벤트
     */
    fun onEvent(event: EditorEvent) {
        when (event) {
            is EditorEvent.ContentChanged -> {
                updateContent(event.content)
            }

            is EditorEvent.TitleChanged -> {
                updateTitle(event.title)
            }

            is EditorEvent.ApplyFormat -> {
                applyFormatting(event.action)
            }

            is EditorEvent.ToggleEditMode -> {
                toggleEditMode()
            }

            is EditorEvent.SaveDocument -> {
                saveDocument()
            }

            is EditorEvent.LoadDocument -> {
                loadDocument(event.documentId)
            }

            is EditorEvent.CreateNewDocument -> {
                createNewDocument()
            }
        }
    }

    /**
     * 에디터 컨텐츠 업데이트
     */
    private fun updateContent(content: String) {
        _editorState.value = _editorState.value.copy(
            htmlContent = content,
            lastModified = System.currentTimeMillis()
        )
    }

    /**
     * 문서 제목 업데이트
     */
    private fun updateTitle(title: String) {
        _editorState.value = _editorState.value.copy(
            title = title,
            lastModified = System.currentTimeMillis()
        )
    }

    /**
     * 텍스트 포맷팅 적용
     *
     * @param action 적용할 포맷팅 액션
     */
    private fun applyFormatting(action: FormatAction) {
        viewModelScope.launch {
            when (action) {
                is FormatAction.Bold -> {
                    richTextState.toggleSpanStyle(
                        SpanStyle(fontWeight = FontWeight.Bold)
                    )
                }

                is FormatAction.Italic -> {
                    richTextState.toggleSpanStyle(
                        SpanStyle(fontStyle = FontStyle.Italic)
                    )
                }

                is FormatAction.Underline -> {
                    richTextState.toggleSpanStyle(
                        SpanStyle(textDecoration = TextDecoration.Underline)
                    )
                }

                is FormatAction.Strikethrough -> {
                    richTextState.toggleSpanStyle(
                        SpanStyle(textDecoration = TextDecoration.LineThrough)
                    )
                }

                is FormatAction.OrderedList -> {
                    richTextState.toggleOrderedList()
                }

                is FormatAction.UnorderedList -> {
                    richTextState.toggleUnorderedList()
                }

                is FormatAction.FontSize -> {
                    richTextState.toggleSpanStyle(
                        SpanStyle(fontSize = action.size.sp)
                    )
                }

                is FormatAction.ClearFormatting -> {
                    clearAllFormatting()
                }

                else -> {
                    // TODO: 다른 포맷팅 액션들 구현 (색상, 정렬 등)
                }
            }

            // HTML 컨텐츠 업데이트
            updateContent(richTextState.toHtml())
        }
    }

    /**
     * 모든 포맷팅 제거
     */
    private fun clearAllFormatting() {
        // 현재 선택된 텍스트의 모든 스타일을 제거
        richTextState.addParagraphStyle(
            androidx.compose.ui.text.ParagraphStyle()
        )
    }

    /**
     * 편집 모드 토글
     */
    private fun toggleEditMode() {
        _editorState.value = _editorState.value.copy(
            isEditing = !_editorState.value.isEditing
        )
    }

    /**
     * 문서 저장
     * TODO: Repository 패턴을 통한 실제 저장 로직 구현
     */
    private fun saveDocument() {
        viewModelScope.launch {
            try {
                val currentState = _editorState.value
                // Repository를 통한 저장 로직 추가 예정
                println("Document saved: ${currentState.title}")
                println("Content: ${currentState.htmlContent}")

                _editorState.value = currentState.copy(
                    lastModified = System.currentTimeMillis()
                )
            } catch (e: Exception) {
                // 에러 처리 로직 추가
                println("Error saving document: ${e.message}")
            }
        }
    }

    /**
     * 문서 로드
     * TODO: Repository 패턴을 통한 실제 로드 로직 구현
     *
     * @param documentId 로드할 문서의 ID
     */
    private fun loadDocument(documentId: String) {
        viewModelScope.launch {
            try {
                // Repository를 통한 로드 로직 추가 예정
                _editorState.value = _editorState.value.copy(
                    documentId = documentId,
                    title = "Loaded Document",
                    htmlContent = "<p>Loaded content...</p>"
                )

                // Rich Text State 업데이트
                richTextState.setHtml(_editorState.value.htmlContent)
            } catch (e: Exception) {
                // 에러 처리 로직 추가
                println("Error loading document: ${e.message}")
            }
        }
    }

    /**
     * 새 문서 생성
     */
    private fun createNewDocument() {
        richTextState = RichTextState()
        _editorState.value = EditorState()
    }

    /**
     * HTML 컨텐츠를 Rich Text State에 적용
     *
     * @param html 적용할 HTML 컨텐츠
     */
    fun setHtmlContent(html: String) {
        richTextState.setHtml(html)
        updateContent(html)
    }

    /**
     * 현재 에디터 상태 가져오기
     */
    fun getCurrentState(): EditorState = _editorState.value
}
