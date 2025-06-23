package com.cyanlch.composeeditor.core.common.utils

import android.text.Html
import android.text.Spanned

/**
 * HTML 관련 유틸리티 함수들
 */
object HtmlUtils {
    
    /**
     * HTML 문자열을 플레인 텍스트로 변환
     */
    fun htmlToPlainText(html: String): String {
        return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString().trim()
    }
    
    /**
     * HTML 문자열을 Spanned 객체로 변환
     */
    fun htmlToSpanned(html: String): Spanned {
        return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
    }
    
    /**
     * HTML 문자열에서 모든 태그 제거
     */
    fun stripHtmlTags(html: String): String {
        return html.replace(Regex("<[^>]*>"), "")
    }
    
    /**
     * HTML 문자열의 유효성 검사
     */
    fun isValidHtml(html: String): Boolean {
        return try {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
            true
        } catch (e: Exception) {
            false
        }
    }
    
    /**
     * HTML 문자열에서 특정 태그만 추출
     */
    fun extractTag(html: String, tag: String): List<String> {
        val pattern = Regex("<$tag[^>]*>(.*?)</$tag>")
        return pattern.findAll(html).map { it.groupValues[1] }.toList()
    }
    
    /**
     * 기본 HTML 템플릿 생성
     */
    fun createHtmlTemplate(title: String = "", content: String = ""): String {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>$title</title>
                <style>
                    body { font-family: Arial, sans-serif; margin: 20px; }
                    .editor-content { line-height: 1.6; }
                </style>
            </head>
            <body>
                <div class="editor-content">
                    $content
                </div>
            </body>
            </html>
        """.trimIndent()
    }
}
