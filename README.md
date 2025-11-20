# <img src="assets/logo.png" alt="Vista previa" width="30" height="30"/> Runique App

Runique is a fitness tracking Android application built with Kotlin and Jetpack Compose. The
application enables users to track running activities with GPS location tracking, view run history,
and synchronize data across devices. The app follows modern Android development practices
including multi-module architecture, clean architecture patterns, and offline-first data management.

# 🏛️ Architecture

Runique follows a multi-module, clean architecture approach with these core principles:

- **Feature Isolation**:    Each feature (auth, run, analytics) is a self-contained set of modules
- **Dependency Inversion**:    All features depend on core domain interfaces, not concrete
  implementations
- **Layer Separation**:    Strict separation between `presentation` → `domain` ← `data` layers
- **Offline-First**:    Local data sources are the source of truth; remote sync is secondary
- **Modular Testing**:    Test utilities in `core:test` and `core:android-test` are shared across
  modules
- **Build Standardization**:    Custom Gradle convention plugins enforce consistent configurations