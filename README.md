# Bardly: Your AI-Powered Companion App

**ai-bardly** is a cutting-edge, multiplatform Android application built with Kotlin and Jetpack
Compose. It leverages AI technologies and modern Android development practices to provide a unique
user experience. This project serves as an example of a modern, scalable Android application.

## Features

- **Multiplatform Development:** Built using Kotlin Multiplatform Mobile (KMP), allowing code
  sharing across Android and iOS platforms.
- **Jetpack Compose:** Modern UI development using declarative UI patterns.
- **Dependency Injection:** Utilizes `kotlin-inject` for a clean and testable architecture.
- **Modular Architecture:** Organized into feature and library modules for better scalability and
  maintainability.
- **Networking:** Employs Ktor for HTTP networking.
- **Data Persistence:** Uses Room for local data storage.
- **Paging:** Uses the Android Paging library for efficient data loading.
- **Analytics:** Integration with Firebase analytics.
- **Crashlytics:** Integration with Firebase Crashlytics for crash reporting.
- **Authentication:** Firebase and Google authentication using `kmpauth`.
- **Decompose:** Integration of the `Decompose` library for navigation.
- **Markdown:** Implementation of markdown support with `multiplatform-markdown-renderer-m3`.
- **Multiplatform Settings:** Use of the `multiplatform-settings` library for settings management.
- **Translations:** Use of compose resources for providing translations.

## Project Structure

The project is organized into several modules, each serving a specific purpose:

### Application Module (`composeApp`)

- **Location:** `composeApp/`
- **Purpose:** The main application module. It contains the UI, app logic, and orchestrates
  interactions between feature modules.
- **Technology:** `Kotlin Multiplatform`, `Jetpack Compose`, `Decompose`, `Kermit`,
  `kotlin-inject`, `Multiplatform-Markdown-Renderer`, `kmpauth`, `Coil`, `Ktor`.

### Feature Modules

These modules contain specific application features:

- `feature:games`:
    - `feature:games:data`
        - **Purpose:** Data layer for games.
        - **Technology:** `Room`, `Ktor`.
    - `feature:games:domain`
        - **Purpose:** Business logic and use cases for games.
        - **Technology:** `kotlin-inject`, `Coroutines`.
    - `feature:games:ui`
        - **Purpose:** UI for the games feature.
        - **Technology:** `Jetpack Compose`, `Decompose`, `kotlin-inject`.
- `feature:chats`:
    - `feature:chats:data`
        - **Purpose:** Data layer for chats.
    - `feature:chats:domain`
        - **Purpose:** Business logic and use cases for chats.
    - `feature:chats:ui`
        - **Purpose:** UI for the chats feature.
- `feature:home`:
    - `feature:home:ui`
        - **Purpose:** UI for the home feature.
- `feature:auth`:
    - `feature:auth:ui`
        - **Purpose:** UI for the authentication feature.
- `feature:onboarding`:
    - `feature:onboarding:ui`
        - **Purpose:** UI for the onboarding feature.
- `feature:shelfs`:
    - `feature:shelfs:data`
        - **Purpose:** Data layer for shelfs.
    - `feature:shelfs:domain`
        - **Purpose:** Business logic and use cases for shelfs.
    - `feature:shelfs:ui`
        - **Purpose:** UI for the shelfs feature.
- `feature:user`:
    - `feature:user:data`
        - **Purpose:** Data layer for user.
    - `feature:user:domain`
        - **Purpose:** Business logic and use cases for user.
    - `feature:user:ui`
        - **Purpose:** UI for the user feature.
- `feature:main`
    - **Purpose:** Main feature module that coordinates other features.

### Library Modules

These modules provide reusable components:

- `lib:network`:
    - `lib:network:api`
        - **Purpose:** Defines the network layer's API.
        - **Technology:** `Ktor`, `kotlinx.serialization`.
    - `lib:network:implementation`
        - **Purpose:** Implements the network layer's logic.
        - **Technology:** `Ktor`, `kotlinx.serialization`.
- `lib:preferences`:
    - `lib:preferences:api`
        - **Purpose:** Defines the interface for shared preferences.
    - `lib:preferences:implementation`
        - **Purpose:** Implementation of shared preferences.
        - **Technology:** `Multiplatform-settings`.
- `lib:paging`:
    - `lib:paging:domain`
        - **Purpose:** Business logic and use cases for paging.
        - **Technology:** `Android Paging`.
    - `lib:paging:ui`
        - **Purpose:** UI implementation for paging.
        - **Technology:** `Android Paging`.
- `lib:database`:
    - `lib:database:api`
        - **Purpose:** Defines the API for database operations.
    - `lib:database:implementation`
        - **Purpose:** Implements the database operations.
        - **Technology:** `Room`.
- `lib:base-ui`
    - **Purpose:** Contains the base components for the UI.
    - **Technology:** `Jetpack Compose`.
- `lib:base`
    - **Purpose:** Contains base common logic.
- `lib:analytics`
    - `lib:analytics:api`
        - **Purpose:** Defines the API for the analytics.
    - `lib:analytics:implementation`
        - **Purpose:** Implements the analytics using firebase.
- `lib:snackbar`:
    - `lib:snackbar:api`
        - **Purpose:** Defines the API for snackbar functionality.
    - `lib:snackbar:implementation`
        - **Purpose:** Implements the snackbar functionality.
- `lib:translations`
    - **Purpose:** Contains the logic for translations.
    - **Technology:** `Compose Resources`.

## Technologies Used

- **Kotlin:** Main programming language.
- **Kotlin Multiplatform Mobile (KMM):** For sharing code between Android and iOS.
- **Jetpack Compose:** Declarative UI toolkit for Android.
- **Ktor:** Multiplatform HTTP client.
- **Room:** Persistence library for SQLite.
- **Kotlin Coroutines:** For asynchronous programming.
- **kotlin-inject:** For dependency injection.
- **Compose Multiplatform:** For compose on multiple platforms.
- **Decompose:** For navigation.
- **Android Paging:** For paging.
- **Kermit:** For logging.
- **Firebase:** For authentication, analytics, crash reporting.
- **Coil:** For image loading.
- **Multiplatform-Markdown-Renderer:** For markdown support.
- **Multiplatform-Settings:** For settings management.
- **Compose resources:** For translations.
- **KSP:** For code generation.
