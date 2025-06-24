package com.cyanlch.composeeditor.core.model

/**
 * 텍스트 서식 상태를 나타내는 데이터 클래스
 */
data class FormattingState(
    val isBold: Boolean = false,
    val isItalic: Boolean = false,
    val isUnderlined: Boolean = false,
    val isStrikethrough: Boolean = false,
    val isOrderedList: Boolean = false,
    val isUnorderedList: Boolean = false,
    val isCodeBlock: Boolean = false,
    val fontSize: Int = 14,
    val textColor: Long = 0xFF000000L,
    val backgroundColor: Long = 0x00000000L
)

/**
 * 서식 타입을 정의하는 enum
 */
enum class FormattingType {
    BOLD,
    ITALIC,
    UNDERLINE,
    STRIKETHROUGH,
    ORDERED_LIST,
    UNORDERED_LIST,
    CODE_BLOCK,
    FONT_SIZE,
    TEXT_COLOR,
    BACKGROUND_COLOR
}

/**
 * 툴바 아이템을 나타내는 sealed class
 */
sealed class ToolbarItem {
    data class ToggleItem(
        val type: FormattingType,
        val isActive: Boolean = false,
        val iconResId: Int? = null,
        val contentDescription: String
    ) : ToolbarItem()
    
    data class ColorItem(
        val type: FormattingType,
        val currentColor: Long,
        val contentDescription: String
    ) : ToolbarItem()
    
    data class SizeItem(
        val currentSize: Int,
        val contentDescription: String
    ) : ToolbarItem()
    
    object Divider : ToolbarItem()
}
