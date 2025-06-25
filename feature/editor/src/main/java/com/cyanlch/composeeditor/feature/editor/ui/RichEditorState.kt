package com.cyanlch.composeeditor.feature.editor.ui

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.mohamedrejeb.richeditor.model.RichTextState

/**
 * Rich Editor의 상태를 관리하는 클래스
 */
@Stable
class RichEditorState {
    // RichTextState 인스턴스
    val richTextState = RichTextState()
    
    // 현재 포맷팅 상태
    private var _isBold by mutableStateOf(false)
    val isBold: Boolean get() = _isBold
    
    private var _isItalic by mutableStateOf(false)
    val isItalic: Boolean get() = _isItalic
    
    private var _isUnderline by mutableStateOf(false)
    val isUnderline: Boolean get() = _isUnderline
    
    private var _isStrikethrough by mutableStateOf(false)
    val isStrikethrough: Boolean get() = _isStrikethrough
    
    private var _textColor by mutableStateOf(Color.Black)
    val textColor: Color get() = _textColor
    
    private var _fontSize by mutableStateOf(14)
    val fontSize: Int get() = _fontSize
    
    // 에디터 내용
    val content: String get() = richTextState.annotatedString.text
    
    // 현재 포맷팅 스타일
    val currentFontWeight: FontWeight
        get() = if (_isBold) FontWeight.Bold else FontWeight.Normal
    
    val currentTextDecoration: TextDecoration
        get() = when {
            _isUnderline && _isStrikethrough -> TextDecoration.combine(
                listOf(TextDecoration.Underline, TextDecoration.LineThrough)
            )
            _isUnderline -> TextDecoration.Underline
            _isStrikethrough -> TextDecoration.LineThrough
            else -> TextDecoration.None
        }
    
    // 에디터 액션들
    fun updateContent(newContent: String) {
        richTextState.setHtml(newContent)
    }
    
    fun toggleBold() {
        _isBold = !_isBold
        richTextState.toggleSpanStyle(
            androidx.compose.ui.text.SpanStyle(fontWeight = if (_isBold) FontWeight.Bold else FontWeight.Normal)
        )
    }
    
    fun toggleItalic() {
        _isItalic = !_isItalic
        richTextState.toggleSpanStyle(
            androidx.compose.ui.text.SpanStyle(fontStyle = if (_isItalic) androidx.compose.ui.text.font.FontStyle.Italic else androidx.compose.ui.text.font.FontStyle.Normal)
        )
    }
    
    fun toggleUnderline() {
        _isUnderline = !_isUnderline
        updateTextDecoration()
    }
    
    fun toggleStrikethrough() {
        _isStrikethrough = !_isStrikethrough
        updateTextDecoration()
    }
    
    private fun updateTextDecoration() {
        richTextState.toggleSpanStyle(
            androidx.compose.ui.text.SpanStyle(textDecoration = currentTextDecoration)
        )
    }
    
    fun setTextColor(color: Color) {
        _textColor = color
        richTextState.toggleSpanStyle(
            androidx.compose.ui.text.SpanStyle(color = color)
        )
    }
    
    fun setFontSize(size: Int) {
        _fontSize = size.coerceIn(8, 72)
        richTextState.toggleSpanStyle(
            androidx.compose.ui.text.SpanStyle(fontSize = _fontSize.sp)
        )
    }
    
    fun insertText(text: String) {
        richTextState.addText(text)
    }
    
    fun setHtml(html: String) {
        richTextState.setHtml(html)
    }
    
    fun getHtml(): String {
        return richTextState.toHtml()
    }
    
    fun getMarkdown(): String {
        return richTextState.toMarkdown()
    }
    
    fun setMarkdown(markdown: String) {
        richTextState.setMarkdown(markdown)
    }
    
    fun initialize() {
        richTextState.clear()
        _isBold = false
        _isItalic = false
        _isUnderline = false
        _isStrikethrough = false
        _textColor = Color.Black
        _fontSize = 14
    }
    
    // 리스트 관련 기능
    fun toggleUnorderedList() {
        richTextState.toggleUnorderedList()
    }
    
    fun toggleOrderedList() {
        richTextState.toggleOrderedList()
    }
    
    // 코드 블록 관련 기능
    fun toggleCodeSpan() {
        richTextState.toggleCodeSpan()
    }
    
    // 링크 관련 기능
    fun addLink(text: String, url: String) {
        richTextState.addLink(text = text, url = url)
    }
    
    // Undo/Redo 기능
    fun undo() {
        // RichTextState에서 undo 기능이 있다면 사용
        // 현재 버전에서는 직접 구현 필요
    }
    
    fun redo() {
        // RichTextState에서 redo 기능이 있다면 사용
        // 현재 버전에서는 직접 구현 필요
    }
}

/**
 * RichEditorState를 생성하고 remember하는 Composable 함수
 */
@Composable
fun rememberRichEditorState(): RichEditorState {
    return remember { RichEditorState() }
}
