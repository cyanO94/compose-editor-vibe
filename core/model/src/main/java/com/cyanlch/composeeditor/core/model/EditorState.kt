package com.cyanlch.composeeditor.core.model

import androidx.compose.runtime.Stable

/**
 * 에디터의 현재 상태를 나타내는 데이터 클래스
 */
@Stable
data class EditorState(
    val htmlContent: String = "",
    val isEditing: Boolean = true,
    val title: String = "",
    val lastModified: Long = System.currentTimeMillis(),
    val documentId: String? = null
)

/**
 * 포맷팅 옵션을 나타내는 sealed class
 */
sealed class FormatAction {
    object Bold : FormatAction()
    object Italic : FormatAction()
    object Underline : FormatAction()
    object Strikethrough : FormatAction()
    data class FontSize(val size: Int) : FormatAction()
    data class FontColor(val color: String) : FormatAction()
    data class BackgroundColor(val color: String) : FormatAction()
    object OrderedList : FormatAction()
    object UnorderedList : FormatAction()
    object IndentIncrease : FormatAction()
    object IndentDecrease : FormatAction()
    data class Alignment(val align: TextAlignment) : FormatAction()
    object ClearFormatting : FormatAction()
}

/**
 * 텍스트 정렬 옵션
 */
enum class TextAlignment {
    LEFT, CENTER, RIGHT, JUSTIFY
}

/**
 * 에디터의 이벤트를 나타내는 sealed class
 */
sealed class EditorEvent {
    data class ContentChanged(val content: String) : EditorEvent()
    data class TitleChanged(val title: String) : EditorEvent()
    data class ApplyFormat(val action: FormatAction) : EditorEvent()
    object ToggleEditMode : EditorEvent()
    object SaveDocument : EditorEvent()
    data class LoadDocument(val documentId: String) : EditorEvent()
    object CreateNewDocument : EditorEvent()
}
