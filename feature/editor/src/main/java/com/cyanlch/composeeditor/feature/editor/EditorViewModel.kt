package com.cyanlch.composeeditor.feature.editor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cyanlch.composeeditor.core.model.EditorEvent
import com.cyanlch.composeeditor.core.model.EditorState
import com.cyanlch.composeeditor.core.model.FormatAction
import com.mohamedrejeb.richeditor.annotation.ExperimentalRichTextApi
import com.mohamedrejeb.richeditor.model.RichTextState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Rich Editor의 상태와 비즈니스 로직을 관리하는 ViewModel
 */
class EditorViewModel : ViewModel() {
    
    private val _editorState = MutableStateFlow(EditorState())
    val editorState: StateFlow<EditorState> = _editorState.asStateFlow()
    
    // Rich Editor State
    var richTextState by mutableStateOf(RichTextState())
        private set
    
    /**
     * 에디터 이벤트 처리
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
    
    private fun updateContent(content: String) {
        _editorState.value = _editorState.value.copy(
            htmlContent = content,
            lastModified = System.currentTimeMillis()
        )
    }
    
    private fun updateTitle(title: String) {
        _editorState.value = _editorState.value.copy(
            title = title,
            lastModified = System.currentTimeMillis()
        )
    }
    
    @OptIn(ExperimentalRichTextApi::class)
    private fun applyFormatting(action: FormatAction) {
        viewModelScope.launch {
            when (action) {
                is FormatAction.Bold -> richTextState.toggleSpanStyle(com.mohamedrejeb.richeditor.model.RichSpanStyle.Bold)
                is FormatAction.Italic -> richTextState.toggleSpanStyle(com.mohamedrejeb.richeditor.model.RichSpanStyle.Italic)
                is FormatAction.Underline -> richTextState.toggleSpanStyle(com.mohamedrejeb.richeditor.model.RichSpanStyle.Underline)
                is FormatAction.Strikethrough -> richTextState.toggleSpanStyle(com.mohamedrejeb.richeditor.model.RichSpanStyle.Strikethrough)
                is FormatAction.OrderedList -> richTextState.toggleParagraphStyle(com.mohamedrejeb.richeditor.model.RichParagraphStyle.OrderedList)
                is FormatAction.UnorderedList -> richTextState.toggleParagraphStyle(com.mohamedrejeb.richeditor.model.RichParagraphStyle.UnorderedList)
                is FormatAction.FontSize -> {
                    richTextState.toggleSpanStyle(
                        com.mohamedrejeb.richeditor.model.RichSpanStyle.FontSize(action.size)
                    )
                }
                is FormatAction.ClearFormatting -> {
                    richTextState.removeSpanStyle(com.mohamedrejeb.richeditor.model.RichSpanStyle.Bold)
                    richTextState.removeSpanStyle(com.mohamedrejeb.richeditor.model.RichSpanStyle.Italic)
                    richTextState.removeSpanStyle(com.mohamedrejeb.richeditor.model.RichSpanStyle.Underline)
                    richTextState.removeSpanStyle(com.mohamedrejeb.richeditor.model.RichSpanStyle.Strikethrough)
                }
                else -> {
                    // TODO: 다른 포맷팅 액션들 구현
                }
            }
            
            // HTML 컨텐츠 업데이트
            updateContent(richTextState.toHtml())
        }
    }
    
    private fun toggleEditMode() {
        _editorState.value = _editorState.value.copy(
            isEditing = !_editorState.value.isEditing
        )
    }
    
    private fun saveDocument() {
        viewModelScope.launch {
            // TODO: 실제 저장 로직 구현 (Repository 사용)
            val currentState = _editorState.value
            println("Saving document: ${currentState.title}")
            println("Content: ${currentState.htmlContent}")
        }
    }
    
    private fun loadDocument(documentId: String) {
        viewModelScope.launch {
            // TODO: 실제 로드 로직 구현 (Repository 사용)
            _editorState.value = _editorState.value.copy(
                documentId = documentId,
                title = "Loaded Document",
                htmlContent = "<p>Loaded content...</p>"
            )
            
            // Rich Text State 업데이트
            richTextState.setHtml(_editorState.value.htmlContent)
        }
    }
    
    private fun createNewDocument() {
        richTextState = RichTextState()
        _editorState.value = EditorState()
    }
    
    /**
     * Rich Text State 초기화
     */
    fun initializeRichTextState() {
        richTextState.setHtml(_editorState.value.htmlContent)
    }
    
    /**
     * HTML 컨텐츠를 Rich Text State에 적용
     */
    fun setHtmlContent(html: String) {
        richTextState.setHtml(html)
        updateContent(html)
    }
}
