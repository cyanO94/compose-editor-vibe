package com.cyanlch.composeeditor.core.model

/**
 * 에디터에서 수행할 수 있는 액션들을 정의
 */
sealed class EditorAction {
    object ToggleBold : EditorAction()
    object ToggleItalic : EditorAction()
    object ToggleUnderline : EditorAction()
    object ToggleStrikethrough : EditorAction()
    object ToggleOrderedList : EditorAction()
    object ToggleUnorderedList : EditorAction()
    object ToggleCodeBlock : EditorAction()
    
    data class ChangeTextColor(val color: Long) : EditorAction()
    data class ChangeBackgroundColor(val color: Long) : EditorAction()
    data class ChangeFontSize(val size: Int) : EditorAction()
    
    object Undo : EditorAction()
    object Redo : EditorAction()
    object ClearFormatting : EditorAction()
    
    data class InsertText(val text: String) : EditorAction()
    data class ReplaceText(val start: Int, val end: Int, val text: String) : EditorAction()
}

/**
 * 에디터 선택 상태
 */
data class EditorSelection(
    val start: Int,
    val end: Int
) {
    val length: Int get() = end - start
    val hasSelection: Boolean get() = length > 0
}

/**
 * 에디터 커서 정보
 */
data class CursorInfo(
    val position: Int,
    val selection: EditorSelection
)
