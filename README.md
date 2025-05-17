# Superformula Mobile Developer Coding Test

### Demo video
Here you can see how the user opens the app and lands on the home screen, where they have the option to generate a new seed. This seed expires after some time, which is reflected in the UI with a reddish color and a text indicating the seed has expired.

There's a Floating Action Button with a small unfolding animation.

Before using the camera, the app requests the necessary permissions from the user. If the user denies the permission once, the app explains why it needs it. If permissions are permanently denied, the user is informed that they need to manually enable them in the device settings, with an option to navigate there.

The QR scan screen features a camera view. Once a QR code is detected, a Bottom Sheet appears showing the seed from the QR. This sheet offers options to copy and share the retrieved structure. Once the QR expires, the Bottom Sheet updates to reflect the new state.

https://github.com/user-attachments/assets/e4106303-330b-4679-8351-90893b69eed8

----------------

## Cloning and running the project
#### 1. Clone the repository
```
git clone https://github.com/GMFrontier/mobile-test.git
cd mobile-test
```

#### 2. Open in Android Studio
- Open Android Studio.
- Choose "Open an existing project" and select the project folder.
- Wait for Gradle scripts to sync.

#### 3. Requirements
- Android Studio Giraffe or later (Kotlin DSL compatible)
- Java 17 (preferably OpenJDK)
- Internet connection to download dependencies

#### 4. Project configuration
- Create a file named `config.properties` in the root directory with:
```
BASE_URL=https://8mpaf1q1g5.execute-api.us-west-1.amazonaws.com/default/
API_KEY=VVTUTAdalB55yRKQzsM7u6RTowrcUUhJLA82hoN6
```
`This is mentioned in the notes section — we know it's not ideal`

#### 5. Run the App
- From Android Studio:
  - Select the app module
  - Choose an emulator or device
  - Click ▶️ Run

----------------

## Project structure

```
app/
├── di/                
│   └── screen/
│       ├── scan/        
│       └── qrcode/
├── ui/                
│   ├── navigation/ 
│   └── theme/
buildSrc/
│   └── dependencies/
common/
│   ├── model/ 
│   └── util/
core/
│   
core-ui/
│   ├── components/ 
│   └── util/
feature/ 
    ├── qr-generator/
    │     ├── data/
    │     │       ├── di/ 
    │     │       ├── repository/ 
    │     │       └── service/
    │     ├── domain/
    │     │       ├── repository/ 
    │     │       └── use_case/
    │     └── presentation/
    └── qr-scanner/    
            └── presentation/    
                    ├── composables/ 
                    └── util/     
```

#### Architecture explanation:
- **app**: Responsible for orchestrating other modules, controls app navigation, and contains unit & integration tests.
- **buildSrc**: Exposes all dependencies and plugins used in the project. This centralized approach simplifies version control and configuration.
- **common**: Provides common elements shared across modules (e.g., extensions, network results, strings, etc.).
- **core**: Centralizes common dependencies used across modules like data or domain (non-UI).
- **core-ui**: Centralizes common UI dependencies, especially for presentation modules or composables.
- **feature**: This module holds the sub-modules representing each feature in the app, and each is divided into data, domain, and presentation layers.

----------------

### Libraries

- **Jetpack Compose** 
- **Jetpack Navigation**  
- **Dagger Hilt** 
- **ZXing** 
- **ML Kit Barcode Scanning**
- **CameraX**  
- **Accompanist Permissions**
- **kotlinx.serialization**
- **uiAutomator, Jupiter, AssertK, Mockk** 

----------------

### Pre-commit Ktlint
Ktlint is implemented in the project to ensure consistent code style. The script setup (`./scripts/pre-commit`) is prepared for this multi-module project, currently only running in the `app` module for simplicity.  
To use this feature, just build the project once to install the git hook.

----------------

## Considerations and retrospective

- The `config.properties` file currently contains the BASE_URL and API_KEY. Ideally, this should come from the backend. In this project, a lot of time was invested in setting up structure and user experience.
- The project was developed with a strong focus on modular architecture to reflect a real-world scenario.
- Two unit tests and one integration test were added, located in the app module.
- Internationalization of strings was implemented, along with a `UiText` util to simplify handling string resources from anywhere in the project.
- Two Gradle files `base-module.gradle` and `compose-module.gradle` were created and imported by other Gradle files to share dependencies and unify common logic.
