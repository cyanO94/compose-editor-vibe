package com.cyanlch.composeeditor.editor

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import com.mohamedrejeb.richeditor.model.RichTextState

/**
 * Rich Text Editor의 상태를 관리하는 클래스
 * 서식 상태와 에디터 기능을 캡슐화
 */
class RichTextEditorState(
    private val richTextState: RichTextState = RichTextState()
) {
    
    // Rich Text State 노출
    val state: RichTextState = richTextState
    
    // 현재 서식 상태
    val isBold: Boolean
        get() = richTextState.currentSpanStyle.fontWeight == FontWeight.Bold
    
    val isItalic: Boolean
        get() = richTextState.currentSpanStyle.fontStyle == FontStyle.Italic
    
    val isUnderlined: Boolean
        get() = richTextState.currentSpanStyle.textDecoration?.contains(TextDecoration.Underline) == true
    
    val isStrikethrough: Boolean
        get() = richTextState.currentSpanStyle.textDecoration?.contains(TextDecoration.LineThrough) == true
    
    val isOrderedList: Boolean
        get() = richTextState.isOrderedList
    
    val isUnorderedList: Boolean
        get() = richTextState.isUnorderedList
    
    val isCodeSpan: Boolean
        get() = richTextState.isCodeSpan
    
    // 서식 토글 메서드들
    fun toggleBold() {
        richTextState.toggleSpanStyle(
            SpanStyle(fontWeight = FontWeight.Bold)
        )
    }
    
    fun toggleItalic() {
        richTextState.toggleSpanStyle(
            SpanStyle(fontStyle = FontStyle.Italic)
        )
    }
    
    fun toggleUnderline() {
        richTextState.toggleSpanStyle(
            SpanStyle(textDecoration = TextDecoration.Underline)
        )
    }
    
    fun toggleStrikethrough() {
        richTextState.toggleSpanStyle(
            SpanStyle(textDecoration = TextDecoration.LineThrough)
        )
    }
    
    fun toggleOrderedList() {
        richTextState.toggleOrderedList()
    }
    
    fun toggleUnorderedList() {
        richTextState.toggleUnorderedList()
    }
    
    fun toggleCodeSpan() {
        richTextState.toggleCodeSpan()
    }
    
    fun setTextColor(color: Color) {
        richTextState.addSpanStyle(
            SpanStyle(color = color)
        )
    }
    
    fun setBackgroundColor(color: Color) {
        richTextState.addSpanStyle(
            SpanStyle(background = color)
        )
    }
    
    fun clearFormatting() {
        // 현재 선택 영역의 모든 서식을 제거
        val currentSpanStyle = richTextState.currentSpanStyle
        richTextState.removeSpanStyle(currentSpanStyle)
    }
    
    fun undo() {
        // RichTextState에 undo 기능이 없으므로 임시로 비워둡니다
        // TODO: 필요시 별도의 히스토리 관리 구현
    }
    
    fun redo() {
        // RichTextState에 redo 기능이 없으므로 임시로 비워둡니다
        // TODO: 필요시 별도의 히스토리 관리 구현
    }
    
    // 텍스트 관련 메서드
    fun getText(): String = richTextState.annotatedString.text
    
    fun getHtml(): String = richTextState.toHtml()
    
    fun setHtml(html: String) = richTextState.setHtml(html)
    
    fun setText(text: String) = richTextState.setText(text)
}

/**
 * Rich Text Editor State를 기억하는 Composable 함수
 */
@Composable
fun rememberRichTextEditorState(): RichTextEditorState {
    return remember { RichTextEditorState() }
}
