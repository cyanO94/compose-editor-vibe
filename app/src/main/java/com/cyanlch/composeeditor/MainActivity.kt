package com.cyanlch.composeeditor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cyanlch.composeeditor.ui.theme.ComposeEditorTheme
import com.cyanlch.composeeditor.feature.editor.ui.RichEditor
import com.cyanlch.composeeditor.feature.editor.ui.rememberRichEditorState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        setContent {
            ComposeEditorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ComposeEditorApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposeEditorApp() {
    val editorManager = rememberRichEditorState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Compose Rich Editor") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "Rich Text Editor with Formatting Toolbar",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            // Rich Editor with Toolbar
            RichEditor(
                editorManager = editorManager,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                placeholder = "여기에 풍부한 텍스트를 입력하세요...\n\n• Bold, Italic, Underline 사용 가능\n• 리스트와 코드 블록 지원\n• 색상과 폰트 크기 변경 가능\n• 링크 및 고급 포맷팅 지원",
                minHeight = 300.dp,
                showToolbar = true,
                showAdvancedToolbar = true
            )
            
            // Action buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        // Markdown 내보내기 예제
                        val markdown = editorManager.getMarkdown()
                        println("Markdown: $markdown")
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Markdown 내보내기")
                }
                
                OutlinedButton(
                    onClick = {
                        // 샘플 Markdown 로드 예제
                        val sampleMarkdown = """
                            # 제목 1
                            
                            **Bold text** and *italic text*
                            
                            ~~strikethrough~~ and `code`
                            
                            - List item 1
                            - List item 2
                            
                            1. Numbered item 1
                            2. Numbered item 2
                            
                            [Link example](https://example.com)
                        """.trimIndent()
                        editorManager.setMarkdown(sampleMarkdown)
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("샘플 로드")
                }
                
                OutlinedButton(
                    onClick = {
                        editorManager.initialize()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("초기화")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ComposeEditorAppPreview() {
    ComposeEditorTheme {
        ComposeEditorApp()
    }
}
