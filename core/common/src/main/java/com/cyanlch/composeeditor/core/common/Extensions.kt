package com.cyanlch.composeeditor.core.common

import com.cyanlch.composeeditor.core.model.DocumentMetadata

/**
 * Common extension functions for the editor
 */

/**
 * Calculate word count from text content
 */
fun String.wordCount(): Int {
    return if (isBlank()) 0
    else trim().split("\\s+".toRegex()).size
}

/**
 * Calculate character count excluding HTML tags
 */
fun String.characterCountWithoutTags(): Int {
    return replace("<[^>]*>".toRegex(), "").length
}

/**
 * Check if content contains images
 */
fun String.hasImages(): Boolean {
    return contains("<img", ignoreCase = true) || contains("![", ignoreCase = false)
}

/**
 * Check if content contains links
 */
fun String.hasLinks(): Boolean {
    return contains("<a", ignoreCase = true) || contains("[", ignoreCase = false)
}

/**
 * Check if content contains code blocks
 */
fun String.hasCodeBlocks(): Boolean {
    return contains("<code", ignoreCase = true) || 
           contains("```", ignoreCase = false) ||
           contains("`", ignoreCase = false)
}

/**
 * Generate metadata from content
 */
fun String.generateMetadata(): DocumentMetadata {
    return DocumentMetadata(
        wordCount = wordCount(),
        characterCount = characterCountWithoutTags(),
        hasImages = hasImages(),
        hasLinks = hasLinks(),
        hasCodeBlocks = hasCodeBlocks()
    )
}
