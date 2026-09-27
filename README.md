# PPB Mod 2 Kelompok 07

An Android anime browser built for the PPB practical module (praktikum pemrograman perangkat bergerak). Browse top anime and characters, search and sort lists, open anime details, and mark anime as favorites. Anime and character data come from the [Jikan API](https://jikan.moe/); sample character cards are shown if the character request fails. Favorites are held in memory and reset when the app process is restarted.

## Tech stack

- Kotlin and Jetpack Compose (Material 3)
- Navigation Compose for bottom-bar and detail navigation
- Retrofit with Gson for Jikan API requests
- Coil for loading poster and character images
- ViewModel, StateFlow, and coroutines for screen state and network work
- Gradle Kotlin DSL; Android Gradle Plugin 9.4.1

## Run

1. Open this project in Android Studio and let Gradle sync.
2. Install Android SDK Platform 37 and use the configured Gradle JDK 25 toolchain.
3. Select an emulator or USB-connected Android device (minimum Android API 24), then run the `app` configuration.

Or build/install from the project root:

```shell
./gradlew installDebug       # macOS / Linux
gradlew.bat installDebug     # Windows
```

The app needs internet access to load anime, character data, and images. The Android manifest already declares the internet permission.

## Main files

```text
app/build.gradle.kts                    # App configuration and dependencies
gradle/libs.versions.toml               # Dependency and plugin versions
gradlew, gradlew.bat                    # Gradle wrapper launchers
app/src/main/AndroidManifest.xml         # App entry point and internet permission
app/src/main/java/.../
  MainActivity.kt                        # Compose app, navigation graph, bottom bar
  Screen.kt                              # Navigation routes and labels
  AnimeViewModel.kt                      # API loading and screen/favorite state
  AnimeListScreen.kt                     # Anime list, search, sort, favorites and cards
  AnimeDetailScreen.kt                   # Anime poster, metadata and synopsis
  CharacterListScreen.kt                 # Character list, search, sort and fallback display
  AboutScreen.kt                         # About page content
  model/AnimeResponse.kt                 # Anime, image and airing data models
  model/AnimeListResponse.kt              # Anime and character list response models
  network/ApiClient.kt                    # Retrofit client and Jikan base URL
  network/ApiService.kt                   # Jikan anime and character endpoints
  ui/theme/                               # Compose colors, typography and theme
```
