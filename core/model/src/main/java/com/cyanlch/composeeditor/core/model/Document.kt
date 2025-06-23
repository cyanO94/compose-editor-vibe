package com.cyanlch.composeeditor.core.model

import kotlinx.serialization.Serializable

/**
 * Represents a rich text document in the editor
 */
@Serializable
data class Document(
    val id: String,
    val title: String,
    val content: String,
    val format: DocumentFormat,
    val metadata: DocumentMetadata,
    val createdAt: Long,
    val updatedAt: Long
)

/**
 * Supported document formats
 */
@Serializable
enum class DocumentFormat {
    HTML,
    MARKDOWN,
    RICH_TEXT
}

/**
 * Document metadata for tracking changes and features
 */
@Serializable
data class DocumentMetadata(
    val wordCount: Int = 0,
    val characterCount: Int = 0,
    val hasImages: Boolean = false,
    val hasLinks: Boolean = false,
    val hasCodeBlocks: Boolean = false,
    val tags: List<String> = emptyList()
)
