# 🎨 Compose Rich Editor Vibe

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)](https://github.com/cyanO94/compose-editor-vibe)
[![Kotlin](https://img.shields.io/badge/kotlin-2.1.21-blue.svg)](https://kotlinlang.org)
[![Compose](https://img.shields.io/badge/compose-2024.12.01-green.svg)](https://developer.android.com/jetpack/compose)
[![API](https://img.shields.io/badge/API-29%2B-brightgreen.svg)](https://android-arsenal.com/api?level=29)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

> 🚀 **현재 바이브코딩 중!** - 실시간으로 개발하고 있는 프로젝트입니다

Modern Rich Text Editor for Android using Jetpack Compose and Material3 design.  
**점진적으로 구현되는 구조화된 Rich Text Editor** - 클린 아키텍처와 모던 UI를 추구합니다.

## ✨ Features

### 🎯 **Currently Implemented** (v0.1.0)
- ✅ **Rich Text Editing** with `compose-richeditor`
- ✅ **Material3 Design** with Dark/Light themes
- ✅ **Formatting Toolbar** with essential controls
- ✅ **Real-time Statistics** (words, characters, lines)
- ✅ **HTML Preview** and export functionality
- ✅ **Clean Architecture** with modular structure

### 📝 **Text Formatting**
- **Bold (B)**, **Italic (I)**, **Underline (U)**, **Strikethrough (S)**
- **Ordered Lists (1.)**, **Unordered Lists (•)**
- **Code Blocks (<>)**
- **Text Colors** (Red, Blue, Green)
- **Undo/Redo (↶/↷)**
- **Clear Formatting (×)**

### 🔮 **Planned Features**
- 🖼️ Image insertion and management
- 🔗 Link creation and editing
- 📄 File save/load operations
- 🌐 Markdown support
- 📤 Advanced sharing options
- 🎨 Custom color picker
- 📐 Advanced typography controls
- 🔍 Find and replace functionality

## 🏗️ Architecture

```
app/
├── src/main/java/com/cyanlch/composeeditor/
│   ├── editor/                    # Rich Editor Components
│   │   ├── RichTextEditorState.kt # State Management
│   │   ├── FormattingToolbar.kt   # Toolbar UI
│   │   └── RichTextEditor.kt      # Main Editor
│   ├── ui/theme/                  # Material3 Theme
│   └── MainActivity.kt            # Entry Point
└── ...
```

**Design Principles:**
- 🎯 **Clean Architecture** - 관심사 분리
- 🔄 **Reactive UI** - State-driven Compose
- 📦 **Modular Components** - 재사용 가능한 컴포넌트
- 🎨 **Material3** - 현대적 디자인 시스템

## 🚀 Getting Started

### Prerequisites
- **Android Studio** Hedgehog 2023.1.1+
- **JDK 21**
- **Android SDK** API 29+
- **Kotlin** 2.1.21+

### Installation

1. **Clone the repository**
```bash
git clone https://github.com/cyanO94/compose-editor-vibe.git
cd compose-editor-vibe
```

2. **Open in Android Studio**
```bash
# Or open the project in Android Studio directly
```

3. **Build and Run**
```bash
./gradlew installDebug
```

## 🛠️ Tech Stack

| Category | Technology | Version |
|----------|------------|---------|
| **Language** | Kotlin | 2.1.21 |
| **UI Framework** | Jetpack Compose | 2024.12.01 |
| **Design System** | Material3 | Latest |
| **Rich Editor** | compose-richeditor | 1.0.0-rc13 |
| **Build** | Android Gradle Plugin | 8.6.1 |
| **Min SDK** | Android API | 29+ |
| **Target SDK** | Android API | 35 |

## 📱 Screenshots

| Light Theme | Dark Theme | HTML Preview |
|-------------|------------|--------------|
| ![Light](docs/screenshots/light.png) | ![Dark](docs/screenshots/dark.png) | ![HTML](docs/screenshots/html.png) |

> **Note**: Screenshots will be added as development progresses

## 🎮 Usage

### Basic Rich Text Editing
```kotlin
@Composable
fun MyEditorScreen() {
    val editorState = rememberRichTextEditorState()
    
    RichTextEditor(
        editorState = editorState,
        placeholder = "Start typing...",
        modifier = Modifier.fillMaxSize()
    )
}
```

### Custom Toolbar Configuration
```kotlin
@Composable
fun CustomEditor() {
    val editorState = rememberRichTextEditorState()
    
    Column {
        FormattingToolbar(
            editorState = editorState,
            modifier = Modifier.fillMaxWidth()
        )
        
        RichTextEditor(
            editorState = editorState,
            showToolbar = false, // Custom toolbar above
            modifier = Modifier.weight(1f)
        )
    }
}
```

### HTML Export/Import
```kotlin
// Export to HTML
val htmlContent = editorState.getHtml()

// Import from HTML
editorState.setHtml("<p><b>Bold text</b></p>")

// Get plain text
val plainText = editorState.getText()
```

## 🔧 Development Status

### 🚀 **Current Phase: Core Implementation**
- [x] Project setup and architecture
- [x] Basic rich text editing
- [x] Material3 theming
- [x] Formatting toolbar
- [x] HTML preview functionality
- [ ] Advanced formatting options
- [ ] File operations
- [ ] Image support

### 📊 **Progress Tracking**
```
Core Features     ████████░░ 80%
UI/UX Design      ███████░░░ 70%
Documentation     █████░░░░░ 50%
Testing           ██░░░░░░░░ 20%
Performance       ███░░░░░░░ 30%
```

## 🤝 Contributing

We welcome contributions! This project is being developed with **vibe coding** approach - feel free to join the journey.

### Development Workflow
1. **Fork** the repository
2. **Create** a feature branch (`git checkout -b feature/amazing-feature`)
3. **Commit** your changes (`git commit -m 'Add amazing feature'`)
4. **Push** to the branch (`git push origin feature/amazing-feature`)
5. **Open** a Pull Request

### Code Style
- **Kotlin** coding conventions
- **Compose** best practices
- **Material3** design guidelines
- **Clean Architecture** principles

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🎯 Roadmap

### 🚀 **Version 0.2.0** (Next Release)
- [ ] Image insertion and management
- [ ] Advanced color picker
- [ ] File save/load operations
- [ ] Performance optimizations

### 🌟 **Version 0.3.0** (Future)
- [ ] Markdown support
- [ ] Advanced typography
- [ ] Plugin architecture
- [ ] Collaborative editing

### 🔮 **Long-term Vision**
- [ ] Multi-platform support (Desktop, Web)
- [ ] Real-time collaboration
- [ ] Cloud synchronization
- [ ] Rich media embedding

## 💬 Contact & Support

- **GitHub Issues**: [Report bugs or request features](https://github.com/cyanO94/compose-editor-vibe/issues)
- **Discussions**: [Join community discussions](https://github.com/cyanO94/compose-editor-vibe/discussions)
- **Developer**: [@cyanO94](https://github.com/cyanO94)

---

<div align="center">

**🎨 Built with ❤️ using Jetpack Compose**

[![Kotlin](https://img.shields.io/badge/Made%20with-Kotlin-blue?style=for-the-badge&logo=kotlin)](https://kotlinlang.org)
[![Compose](https://img.shields.io/badge/Powered%20by-Jetpack%20Compose-green?style=for-the-badge&logo=android)](https://developer.android.com/jetpack/compose)

**⭐ Star this repository if you find it useful!**

</div>
