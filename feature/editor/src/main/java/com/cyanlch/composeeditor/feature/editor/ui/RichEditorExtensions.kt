package com.cyanlch.composeeditor.feature.editor.ui

import androidx.compose.ui.graphics.Color

/**
 * RichEditor 확장 기능들
 */

/**
 * 미리 정의된 색상 팔레트
 */
object EditorColorPalette {
    val Black = Color(0xFF000000)
    val DarkGray = Color(0xFF424242)
    val Gray = Color(0xFF757575)
    val LightGray = Color(0xFFBDBDBD)
    val Red = Color(0xFFF44336)
    val Pink = Color(0xFFE91E63)
    val Purple = Color(0xFF9C27B0)
    val DeepPurple = Color(0xFF673AB7)
    val Indigo = Color(0xFF3F51B5)
    val Blue = Color(0xFF2196F3)
    val LightBlue = Color(0xFF03A9F4)
    val Cyan = Color(0xFF00BCD4)
    val Teal = Color(0xFF009688)
    val Green = Color(0xFF4CAF50)
    val LightGreen = Color(0xFF8BC34A)
    val Lime = Color(0xFFCDDC39)
    val Yellow = Color(0xFFFFEB3B)
    val Amber = Color(0xFFFFC107)
    val Orange = Color(0xFFFF9800)
    val DeepOrange = Color(0xFFFF5722)
    val Brown = Color(0xFF795548)
    
    fun getAllColors(): List<Color> = listOf(
        Black, DarkGray, Gray, LightGray,
        Red, Pink, Purple, DeepPurple,
        Indigo, Blue, LightBlue, Cyan,
        Teal, Green, LightGreen, Lime,
        Yellow, Amber, Orange, DeepOrange,
        Brown
    )
}

/**
 * 미리 정의된 폰트 크기
 */
object EditorFontSizes {
    const val TINY = 10
    const val SMALL = 12
    const val NORMAL = 14
    const val MEDIUM = 16
    const val LARGE = 18
    const val EXTRA_LARGE = 24
    const val HUGE = 32
    
    fun getAllSizes(): List<Int> = listOf(
        TINY, SMALL, NORMAL, MEDIUM, LARGE, EXTRA_LARGE, HUGE
    )
}

/**
 * 텍스트 포맷 유틸리티
 */
object TextFormatUtils {
    
    /**
     * HTML 태그를 제거하고 순수 텍스트만 반환
     */
    fun stripHtmlTags(html: String): String {
        return html.replace(Regex("<[^>]+>"), "")
    }
    
    /**
     * 간단한 마크다운을 HTML로 변환
     */
    fun markdownToHtml(markdown: String): String {
        var html = markdown
        
        // Bold: **text** or __text__
        html = html.replace(Regex("\\*\\*(.*?)\\*\\*"), "<b>$1</b>")
        html = html.replace(Regex("__(.*?)__"), "<b>$1</b>")
        
        // Italic: *text* or _text_
        html = html.replace(Regex("\\*(.*?)\\*"), "<i>$1</i>")
        html = html.replace(Regex("_(.*?)_"), "<i>$1</i>")
        
        // Code: `text`
        html = html.replace(Regex("`(.*?)`"), "<code>$1</code>")
        
        // Strikethrough: ~~text~~
        html = html.replace(Regex("~~(.*?)~~"), "<s>$1</s>")
        
        // Line breaks
        html = html.replace("\n", "<br>")
        
        return "<p>$html</p>"
    }
    
    /**
     * HTML을 간단한 마크다운으로 변환
     */
    fun htmlToMarkdown(html: String): String {
        var markdown = html
        
        // Remove paragraph tags
        markdown = markdown.replace(Regex("</?p>"), "")
        
        // Bold
        markdown = markdown.replace(Regex("<b>(.*?)</b>"), "**$1**")
        markdown = markdown.replace(Regex("<strong>(.*?)</strong>"), "**$1**")
        
        // Italic
        markdown = markdown.replace(Regex("<i>(.*?)</i>"), "*$1*")
        markdown = markdown.replace(Regex("<em>(.*?)</em>"), "*$1*")
        
        // Code
        markdown = markdown.replace(Regex("<code>(.*?)</code>"), "`$1`")
        
        // Strikethrough
        markdown = markdown.replace(Regex("<s>(.*?)</s>"), "~~$1~~")
        markdown = markdown.replace(Regex("<strike>(.*?)</strike>"), "~~$1~~")
        
        // Line breaks
        markdown = markdown.replace("<br>", "\n")
        
        return markdown
    }
    
    /**
     * 줄 바꿈을 적절히 처리
     */
    fun normalizeLineBreaks(text: String): String {
        return text.replace("\r\n", "\n").replace("\r", "\n")
    }
    
    /**
     * 텍스트의 단어 수 계산
     */
    fun getWordCount(text: String): Int {
        if (text.isBlank()) return 0
        return text.trim().split(Regex("\\s+")).size
    }
    
    /**
     * 텍스트의 줄 수 계산
     */
    fun getLineCount(text: String): Int {
        if (text.isEmpty()) return 1
        return text.count { it == '\n' } + 1
    }
}

/**
 * 에디터 액션들
 */
object EditorActions {
    
    /**
     * 선택된 텍스트를 포맷팅
     */
    fun formatSelectedText(
        text: String,
        selectionStart: Int,
        selectionEnd: Int,
        formatTag: String
    ): String {
        if (selectionStart >= selectionEnd || selectionStart < 0 || selectionEnd > text.length) {
            return text
        }
        
        val beforeSelection = text.substring(0, selectionStart)
        val selectedText = text.substring(selectionStart, selectionEnd)
        val afterSelection = text.substring(selectionEnd)
        
        val formattedText = when (formatTag) {
            "bold" -> "**$selectedText**"
            "italic" -> "*$selectedText*"
            "code" -> "`$selectedText`"
            "strikethrough" -> "~~$selectedText~~"
            else -> selectedText
        }
        
        return beforeSelection + formattedText + afterSelection
    }
    
    /**
     * 현재 커서 위치에 텍스트 삽입
     */
    fun insertTextAtCursor(
        text: String,
        insertText: String,
        cursorPosition: Int
    ): Pair<String, Int> {
        val newText = text.substring(0, cursorPosition) + insertText + text.substring(cursorPosition)
        val newCursorPosition = cursorPosition + insertText.length
        return Pair(newText, newCursorPosition)
    }
    
    /**
     * 리스트 아이템 토글
     */
    fun toggleListItem(text: String, cursorPosition: Int): String {
        val lines = text.split('\n').toMutableList()
        val currentLineIndex = text.substring(0, cursorPosition).count { it == '\n' }
        
        if (currentLineIndex < lines.size) {
            val currentLine = lines[currentLineIndex]
            lines[currentLineIndex] = if (currentLine.startsWith("• ")) {
                currentLine.removePrefix("• ")
            } else {
                "• $currentLine"
            }
        }
        
        return lines.joinToString("\n")
    }
    
    /**
     * 번호 매기기 리스트 토글
     */
    fun toggleNumberedList(text: String, cursorPosition: Int): String {
        val lines = text.split('\n').toMutableList()
        val currentLineIndex = text.substring(0, cursorPosition).count { it == '\n' }
        
        if (currentLineIndex < lines.size) {
            val currentLine = lines[currentLineIndex]
            lines[currentLineIndex] = if (currentLine.matches(Regex("^\\d+\\. .*"))) {
                currentLine.replaceFirst(Regex("^\\d+\\. "), "")
            } else {
                "1. $currentLine"
            }
        }
        
        return lines.joinToString("\n")
    }
}

/**
 * 키보드 단축키 정의
 */
object EditorShortcuts {
    const val BOLD = "Ctrl+B"
    const val ITALIC = "Ctrl+I"
    const val UNDERLINE = "Ctrl+U"
    const val STRIKETHROUGH = "Ctrl+Shift+X"
    const val SAVE = "Ctrl+S"
    const val UNDO = "Ctrl+Z"
    const val REDO = "Ctrl+Y"
    const val SELECT_ALL = "Ctrl+A"
    const val COPY = "Ctrl+C"
    const val PASTE = "Ctrl+V"
    const val CUT = "Ctrl+X"
}
