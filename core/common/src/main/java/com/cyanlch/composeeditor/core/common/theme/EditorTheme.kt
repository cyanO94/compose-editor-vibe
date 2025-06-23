package com.cyanlch.composeeditor.core.common.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * 에디터 전용 색상 정의
 */
object EditorColors {
    val ToolbarBackground = Color(0xFFF5F5F5)
    val ToolbarBackgroundDark = Color(0xFF2D2D2D)
    val EditorBackground = Color.White
    val EditorBackgroundDark = Color(0xFF1E1E1E)
    val BorderColor = Color(0xFFE0E0E0)
    val BorderColorDark = Color(0xFF404040)
    val SelectionColor = Color(0xFF1976D2)
    val SelectionColorDark = Color(0xFF90CAF9)
}

/**
 * 라이트 테마 색상 스킴
 */
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF1976D2),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE3F2FD),
    onPrimaryContainer = Color(0xFF0D47A1),
    secondary = Color(0xFF455A64),
    onSecondary = Color.White,
    surface = Color.White,
    onSurface = Color(0xFF1C1B1F),
    surfaceVariant = Color(0xFFF3F3F3),
    onSurfaceVariant = Color(0xFF49454F)
)

/**
 * 다크 테마 색상 스킴
 */
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF90CAF9),
    onPrimary = Color(0xFF0D47A1),
    primaryContainer = Color(0xFF1565C0),
    onPrimaryContainer = Color(0xFFE3F2FD),
    secondary = Color(0xFF90A4AE),
    onSecondary = Color(0xFF263238),
    surface = Color(0xFF1C1B1F),
    onSurface = Color(0xFFE6E1E5),
    surfaceVariant = Color(0xFF2B2930),
    onSurfaceVariant = Color(0xFFCAC4D0)
)

/**
 * 에디터 테마 Composable
 */
@Composable
fun EditorTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
