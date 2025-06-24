package com.cyanlch.composeeditor.feature.editor.domain

import com.cyanlch.composeeditor.core.model.EditorAction
import com.cyanlch.composeeditor.core.model.EditorEvent
import com.cyanlch.composeeditor.core.model.FormattingState
import com.mohamedrejeb.richeditor.model.RichTextState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Rich Editor의 핵심 비즈니스 로직을 관리하는 클래스
 * 상태 관리와 액션 처리를 담당
 */
class EditorManager {
    
    private val _richTextState = MutableStateFlow(RichTextState())
    val richTextState: StateFlow<RichTextState> = _richTextState.asStateFlow()
    
    private val _formattingState = MutableStateFlow(FormattingState())
    val formattingState: StateFlow<FormattingState> = _formattingState.asStateFlow()
    
    private val _events = MutableSharedFlow<EditorEvent>()
    val events: Flow<EditorEvent> = _events.asSharedFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    /**
     * 에디터 액션을 처리합니다
     */
    fun handleAction(action: EditorAction) {
        when (action) {
            is EditorAction.ToggleBold -> toggleBold()
            is EditorAction.ToggleItalic -> toggleItalic()
            is EditorAction.ToggleUnderline -> toggleUnderline()
            is EditorAction.ToggleStrikethrough -> toggleStrikethrough()
            is EditorAction.ToggleOrderedList -> toggleOrderedList()
            is EditorAction.ToggleUnorderedList -> toggleUnorderedList()
            is EditorAction.ToggleCodeBlock -> toggleCodeBlock()
            is EditorAction.ChangeTextColor -> changeTextColor(action.color)
            is EditorAction.ChangeBackgroundColor -> changeBackgroundColor(action.color)
            is EditorAction.ChangeFontSize -> changeFontSize(action.size)
            is EditorAction.Undo -> undo()
            is EditorAction.Redo -> redo()
            is EditorAction.ClearFormatting -> clearFormatting()
            is EditorAction.InsertText -> insertText(action.text)
            is EditorAction.ReplaceText -> replaceText(action.start, action.end, action.text)
        }
    }
    
    /**
     * 현재 서식 상태를 업데이트합니다
     */
    fun updateFormattingState() {
        val richState = _richTextState.value
        val newState = FormattingState(
            isBold = richState.currentSpanStyle.fontWeight != null,
            isItalic = richState.currentSpanStyle.fontStyle != null,
            isUnderlined = richState.currentSpanStyle.textDecoration?.contains(
                androidx.compose.ui.text.style.TextDecoration.Underline
            ) == true,
            isStrikethrough = richState.currentSpanStyle.textDecoration?.contains(
                androidx.compose.ui.text.style.TextDecoration.LineThrough
            ) == true,
            isOrderedList = richState.isOrderedList,
            isUnorderedList = richState.isUnorderedList,
            isCodeBlock = richState.isCodeSpan,
            fontSize = richState.currentSpanStyle.fontSize.value.toInt(),
            textColor = richState.currentSpanStyle.color.value.toLong(),
            backgroundColor = richState.currentSpanStyle.background.value.toLong()
        )
        
        _formattingState.value = newState
        _events.tryEmit(EditorEvent.FormattingChanged(newState))
    }
    
    private fun toggleBold() {
        _richTextState.value.toggleSpanStyle(androidx.compose.ui.text.SpanStyle(
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
        ))
        updateFormattingState()
    }
    
    private fun toggleItalic() {
        _richTextState.value.toggleSpanStyle(androidx.compose.ui.text.SpanStyle(
            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
        ))
        updateFormattingState()
    }
    
    private fun toggleUnderline() {
        _richTextState.value.toggleSpanStyle(androidx.compose.ui.text.SpanStyle(
            textDecoration = androidx.compose.ui.text.style.TextDecoration.Underline
        ))
        updateFormattingState()
    }
    
    private fun toggleStrikethrough() {
        _richTextState.value.toggleSpanStyle(androidx.compose.ui.text.SpanStyle(
            textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough
        ))
        updateFormattingState()
    }
    
    private fun toggleOrderedList() {
        _richTextState.value.toggleOrderedList()
        updateFormattingState()
    }
    
    private fun toggleUnorderedList() {
        _richTextState.value.toggleUnorderedList()
        updateFormattingState()
    }
    
    private fun toggleCodeBlock() {
        _richTextState.value.toggleCodeSpan()
        updateFormattingState()
    }
    
    private fun changeTextColor(color: Long) {
        _richTextState.value.addSpanStyle(androidx.compose.ui.text.SpanStyle(
            color = androidx.compose.ui.graphics.Color(color)
        ))
        updateFormattingState()
    }
    
    private fun changeBackgroundColor(color: Long) {
        _richTextState.value.addSpanStyle(androidx.compose.ui.text.SpanStyle(
            background = androidx.compose.ui.graphics.Color(color)
        ))
        updateFormattingState()
    }
    
    private fun changeFontSize(size: Int) {
        _richTextState.value.addSpanStyle(androidx.compose.ui.text.SpanStyle(
            fontSize = androidx.compose.ui.unit.sp(size)
        ))
        updateFormattingState()
    }
    
    private fun undo() {
        _richTextState.value.undo()
        updateFormattingState()
    }
    
    private fun redo() {
        _richTextState.value.redo()
        updateFormattingState()
    }
    
    private fun clearFormatting() {
        _richTextState.value.removeSpanStyle()
        updateFormattingState()
    }
    
    private fun insertText(text: String) {
        _richTextState.value.addText(text)
        _events.tryEmit(EditorEvent.TextChanged(_richTextState.value.annotatedString.text))
    }
    
    private fun replaceText(start: Int, end: Int, text: String) {
        // RichTextState에서 범위 교체 기능이 제한적이므로 필요에 따라 구현
        _events.tryEmit(EditorEvent.TextChanged(_richTextState.value.annotatedString.text))
    }
    
    /**
     * 에디터 초기화
     */
    fun initialize() {
        _richTextState.value = RichTextState()
        _formattingState.value = FormattingState()
    }
    
    /**
     * 현재 텍스트 가져오기
     */
    fun getCurrentText(): String {
        return _richTextState.value.annotatedString.text
    }
    
    /**
     * HTML 형태로 텍스트 가져오기
     */
    fun getHtml(): String {
        return _richTextState.value.toHtml()
    }
    
    /**
     * HTML에서 텍스트 설정하기
     */
    fun setHtml(html: String) {
        _richTextState.value.setHtml(html)
        updateFormattingState()
    }
}
