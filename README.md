# CoinCanvas

<p align="center">
  <img src="https://img.shields.io/badge/Compose-Multiplatform-4285F4?logo=kotlin&logoColor=white" />
  <img src="https://img.shields.io/badge/Platforms-Android%20•%20Desktop%20•%20Web%20•%20iOS-34A853" />
  <img src="https://img.shields.io/badge/Language-Kotlin-7F52FF?logo=kotlin&logoColor=white" />
  <img src="https://img.shields.io/badge/UI-Jetpack%20Compose-4285F4?logo=jetpackcompose&logoColor=white" />
  <img src="https://img.shields.io/badge/Charts-Compose%20Canvas-FF6F00" />
</p>

### **Compose Multiplatform Crypto Dashboard (Android • Desktop • Web • iOS)**

CoinCanvas is a modern, expressive cryptocurrency dashboard built with **Compose Multiplatform**, **Material 3**, and a fully custom **Expressive Design System**.  
It runs on **Android**, **Desktop**, **Web**, and **iOS** — all powered by a shared Kotlin codebase.

The app delivers real‑time market data, smooth gradient charts, and a premium UI inspired by ocean‑blue, bioluminescent aesthetics.

---

## Features

### Full Compose Multiplatform Support
- **Android** (Jetpack Compose)
- **Desktop** (Compose for Desktop)
- **Web** (Compose HTML Canvas)
- **iOS** (Compose Multiplatform for iOS)

One UI. One design system. One codebase.

---

### Real‑Time Crypto Market Data
- Live prices, market caps, volumes, supply  
- Sort by price, market cap, volume, etc.  
- Clean, expressive stat layouts  

---

### Expressive Gradient Price Charts
- Smooth cubic Bézier curves  
- Vertical gradient stroke  
- Adaptive scaling  
- Built with Compose Canvas  
- Optimized for dark mode  

---

### Custom Expressive Design System
- Ocean‑blue color palette  
- Large expressive shapes  
- Premium typography hierarchy  
- Expressive cards, list items, and top bars  
- Consistent spacing + motion patterns

## Project Structure

This is a **Kotlin Multiplatform project** targeting **Android, iOS, Web, and Desktop (JVM)**.

### **📱 `/iosApp`**
Contains the iOS application entry point.  
Even when sharing UI with Compose Multiplatform, iOS still requires:
- An Xcode project  
- Swift/SwiftUI integration points  
- App lifecycle setup  

### **🌍 `/shared`**
Shared Kotlin Multiplatform module containing:
- UI (Compose Multiplatform)
- ViewModels
- Networking (Ktor)
- Models
- Repository layer
- Design system

Subfolders:

- **`commonMain`** — shared code for all platforms  
- **`androidMain`** — Android‑specific implementations  
- **`iosMain`** — iOS‑specific implementations (e.g., CoreCrypto, platform APIs)  
- **`jvmMain`** — Desktop‑specific code  
- **`jsMain` / `wasmJsMain`** — Web‑specific code  

This structure allows platform‑specific optimizations while keeping most logic shared.

---

## Screenshots

> Add Android, Desktop, Web, and iOS screenshots here.

---

## Getting Started

### Clone the repo
```bash
git clone https://github.com/iNoles/CoinCanvas.git
cd CoinCanvas
```

### Run Android
Open in Android Studio → Run

### Run Desktop
```bash
./gradlew :desktopApp:run
```

### Run Web
```bash
./gradlew :webApp:jsBrowserDevelopmentRun
```

### Run iOS
Open iosApp in Xcode → Run on simulator or device
