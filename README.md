# 🚖 Yatri-Mitra

**Yatri-Mitra** is a modern ride-booking Android application built using the latest Android development technologies.  
It delivers a seamless transportation experience with real-time booking, location integration, secure authentication, and an intuitive UI powered by Jetpack Compose.

---

# ✨ Features

## 🔐 Secure Authentication
- Firebase Authentication powered Login & Signup
- Persistent user sessions

## 📍 Real-Time Location
- Automatic pickup detection using Google Play Services
- Accurate location updates with FusedLocationProvider

## 🚗 Flexible Ride Booking
Choose from multiple vehicle options:
- 🏍️ Bike
- 🛺 Auto
- 🚕 Cab

## 💰 Fare Estimation
- Instant fare calculation based on:
    - Distance
    - Vehicle type

## ⏲️ Live Tracking
- Simulated real-time driver tracking
- ETA updates

## 👤 Profile Management
- Manage personal details
- Update contact information

## 📜 Ride History
- Stores previous rides using Firebase Firestore

## 💳 Payment Ready
- Payment flow integrated and ready for gateway implementation

---

# 🛠 Tech Stack

| Category | Technology |
|----------|-------------|
| Language | Kotlin |
| UI Toolkit | Jetpack Compose |
| Navigation | Compose Navigation |
| Backend | Firebase |
| Database | Cloud Firestore |
| Authentication | Firebase Auth |
| Location Services | Google Play Services |
| Async Programming | Kotlin Coroutines |
| Design System | Material 3 |

---

# 🏗 Architecture

The project follows a **Modern Android Development** approach.

## UI Layer
- Fully built with Jetpack Compose
- Declarative UI state management

## Navigation
- Centralized `NavHost`
- Clean screen transitions

## Data Layer
- Firebase Firestore for real-time synchronization

> ⚠️ Current implementation follows a UI-centric approach.  
> Migrating to **MVVM Architecture** is recommended for scalability and testability.

---

# 📸 Screenshots

| Splash Screen | Login Screen | Home Dashboard | Booking Screen |
|---------------|--------------|----------------|----------------|
| Splash | Login | Home | Booking |

> Add screenshots inside a `/screenshots` folder and update image paths here.

---

# ⚙️ Installation & Setup

## Prerequisites

- Android Studio Ladybug (2024.2.1+)
- JDK 11+
- Firebase Project

---

# 🚀 Setup Steps

## 1️⃣ Clone Repository

```bash
git clone https://github.com/codewithsush/mind-matrix-yatri-mitr.git
```

## 2️⃣ Firebase Setup

- Open Firebase Console
- Create a new Firebase project
- Add Android app with package name:

```text
com.example.yarti
```

- Download `google-services.json`
- Place it inside:

```text
app/
```

- Enable:
    - Email/Password Authentication
    - Cloud Firestore Database

---

## 3️⃣ Open Project

- Open in Android Studio
- Wait for Gradle Sync to complete

---

## 4️⃣ Run Application

- Connect Android device or emulator
- Click ▶ Run

---

# 📂 Project Structure

```text
yarti/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   ├── com/example/yarti/
│   │   │   │   └── screens/
│   │   │   ├── res/
│   │   │   └── AndroidManifest.xml
│   ├── build.gradle.kts
├── gradle/
└── build.gradle.kts
```

---

# 📦 Build Instructions

## Generate Debug APK

```bash
./gradlew assembleDebug
```

## Run Unit Tests

```bash
./gradlew test
```

---

# 🔐 Permissions Used

| Permission | Purpose |
|------------|----------|
| ACCESS_FINE_LOCATION | Precise location |
| ACCESS_COARSE_LOCATION | Approximate location |

---

# 📈 Future Improvements

- [ ] Implement MVVM Architecture
- [ ] Add Hilt Dependency Injection
- [ ] Improve Error Handling
- [ ] Add Unit Testing
- [ ] Optimize Dark Mode
- [ ] Integrate Real Payment Gateway
- [ ] Add Google Maps Live Tracking

---

# 📄 License

Distributed under the MIT License.  
See `LICENSE` for more information.

---

# 🤝 Contact

## 👨‍💻 Developer
**codewithsush**

## 🔗 Project Repository
https://github.com/codewithsush/mind-matrix-yatri-mitr

---

<p align="center">
Developed with ❤️ as part of the <b>Yatri-Mitra Initiative</b>
</p>