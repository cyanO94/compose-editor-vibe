package com.cyanlch.composeeditor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cyanlch.composeeditor.core.common.theme.EditorTheme
import com.cyanlch.composeeditor.feature.editor.EditorViewModel
import com.cyanlch.composeeditor.feature.editor.components.HtmlPreviewComponent
import com.cyanlch.composeeditor.feature.editor.components.RichEditorComponent
import com.cyanlch.composeeditor.feature.toolbar.components.EditorToolbar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        setContent {
            EditorTheme {
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
    val viewModel: EditorViewModel = viewModel()
    val editorState by viewModel.editorState.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = if (editorState.title.isNotEmpty()) {
                            editorState.title
                        } else {
                            "Compose Rich Editor"
                        }
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // 툴바
            EditorToolbar(
                onFormatAction = { action ->
                    viewModel.onEvent(com.cyanlch.composeeditor.core.model.EditorEvent.ApplyFormat(action))
                }
            )
            
            // 메인 컨텐츠
            if (editorState.isEditing) {
                // 편집 모드
                RichEditorComponent(
                    viewModel = viewModel,
                    editorState = editorState,
                    modifier = Modifier.weight(1f)
                )
            } else {
                // 미리보기 모드
                HtmlPreviewComponent(
                    htmlContent = editorState.htmlContent,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
    
    // 초기화
    LaunchedEffect(Unit) {
        viewModel.initializeRichTextState()
    }
}

@Preview(showBackground = true)
@Composable
fun ComposeEditorAppPreview() {
    EditorTheme {
        ComposeEditorApp()
    }
}
