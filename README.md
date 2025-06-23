# Compose Rich Editor

> A modular Android rich text editor built with Jetpack Compose and [Compose Rich Editor](https://github.com/MohamedRejeb/compose-rich-editor) library.

## 🚀 Features

- **Rich Text Editing**: Bold, Italic, Underline, Strikethrough
- **Lists**: Ordered and Unordered lists
- **Font Formatting**: Font size and color customization
- **HTML Export**: Real-time HTML preview and export
- **Modular Architecture**: Clean, maintainable code structure
- **Material 3 Design**: Modern UI following Material Design guidelines

## 🏗️ Architecture

This project follows a modular architecture pattern:

```
app/                    # Main application module
├── MainActivity.kt     # Compose UI entry point

core/
├── model/             # Data models and state management
├── data/              # Data layer (repositories, etc.)
└── common/            # Shared utilities and themes

feature/
├── editor/            # Rich text editor functionality
└── toolbar/           # Formatting toolbar components
```

## 🛠️ Tech Stack

- **Kotlin 2.1.21** + **JDK 21**
- **Jetpack Compose** with BOM 2024.12.01
- **Material 3** design system
- **Compose Rich Editor v1.0.0-rc13**
- **MVVM** architecture pattern
- **StateFlow** for state management

## 🎯 Purpose

This project serves as:
1. **Learning Platform**: Exploring rich text editing in Compose
2. **Testing Ground**: For the Compose Rich Editor library
3. **Contribution Base**: Contributing back to the open-source community
4. **Reference Implementation**: Well-structured modular Android app

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog+ 
- JDK 21
- Android SDK 35

### Setup
1. Clone the repository
```bash
git clone <repository-url>
cd composeeditor
```

2. Open in Android Studio
3. Sync Gradle files
4. Run the app

## 📦 Modules

### Core Modules
- **`:core:model`**: Data models, states, and events
- **`:core:data`**: Data layer implementation
- **`:core:common`**: Shared utilities, themes, and extensions

### Feature Modules
- **`:feature:editor`**: Rich text editor components and ViewModels
- **`:feature:toolbar`**: Formatting toolbar and controls

## 🔧 Development

### Adding New Features
1. Create feature module under `feature/`
2. Add module to `settings.gradle.kts`
3. Implement following clean architecture principles
4. Update dependencies in `app/build.gradle.kts`

### Code Style
- Follow Kotlin coding conventions
- Use meaningful variable names
- Write self-documenting code
- Add KDoc for public APIs

## 🤝 Contributing

Contributions are welcome! This project aims to:
- Test and improve the Compose Rich Editor library
- Provide feedback and bug reports
- Contribute new features and improvements

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

## 🙏 Acknowledgments

- [MohamedRejeb/compose-rich-editor](https://github.com/MohamedRejeb/compose-rich-editor) - The amazing rich text editor library
- Android Jetpack Compose team
- Material Design team

---

## 🚧 Development Status

- ✅ Project setup and modular architecture
- ✅ Basic rich text editing functionality
- ✅ Formatting toolbar
- ✅ HTML preview and export
- 🚧 Advanced formatting options
- 🚧 Image insertion
- 🚧 Table support
- 🚧 File save/load functionality
- 🚧 Theme customization
