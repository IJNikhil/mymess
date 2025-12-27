# MyMess Enterprise 🍱
### Advanced Android Hostel Mess Management System

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack_Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)](https://developer.android.com/jetpack/compose)
[![Clean Architecture](https://img.shields.io/badge/Architecture-Clean_MVVM-green?style=for-the-badge)](https://developer.android.com/topic/architecture)
[![Room](https://img.shields.io/badge/Database-Room-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com/training/data-storage/room)

> [!WARNING]
> **Project Status: Under Development**
> This project is currently in active development. Features and architecture are subject to frequent changes.

**MyMess Enterprise** is a sophisticated mobile solution designed to digitize and optimize traditional hostel mess operations. Built with a "Security-First" mindset, it provides high-performance tracking for financial transactions, biometric attendance, and inventory management.

---

## 🏗 System Architecture & Design

The application is engineered using **Clean Architecture** principles to ensure the business logic is entirely decoupled from the UI and Data frameworks, promoting high testability and scalability.



### The Layered Strategy

* **Presentation Layer (`ui`)**: Powered by **Jetpack Compose** and **Material 3**. ViewModels utilize `StateFlow` to provide reactive, lifecycle-aware state updates while strictly following Unidirectional Data Flow (UDF).
* **Domain Layer (`domain`)**: A pure-Kotlin layer containing Business Logic and Repository Interfaces. This is the core "brain" of the application, independent of any Android-specific libraries.
* **Data Layer (`data`)**: Manages data persistence through the **Room (SQLite)** library. It maps Database Entities to Domain Models and handles the single source of truth for the application.

---

## 🛠 Tech Stack

| Category | Technology |
| :--- | :--- |
| **Language** | Kotlin 1.9+ |
| **UI Framework** | Jetpack Compose (Material 3) |
| **Dependency Injection** | Hilt (Dagger) |
| **Persistence** | Room Database (SQLite) |
| **Concurrency** | Coroutines & Flow |
| **Architecture** | MVVM + Clean Architecture |

---

## 🚀 Key Feature Modules

### 👑 Admin Console (The Command Center)
- **Dashboard Analytics**: Real-time KPI tracking including Revenue, Profit, and Due Payments.
- **Biometric Attendance**: Simulates fingerprint scanning for precise, fraud-proof daily meal tracking.
- **Financial Reports**: Comprehensive analytics with daily, weekly, and custom date filtering.
- **Inventory Control**: Automated stock tracking with "Low Stock" indicators and wastage logging.

### 👤 Resident Platform (The End-User Experience)
- **Digital Mess Card**: Secure QR-based identity verification for dining.
- **Financial Transparency**: Instant view of outstanding dues and simulated UPI payment flow with receipt generation.
- **Smart Menu**: View upcoming meals (Breakfast, Lunch, Dinner) in real-time.
- **Service Requests**: Integrated module for guest bookings, special diets, or feedback.

---

## 🧪 Data Flow Logic

To maintain data integrity and a predictable UI state, **MyMess** follows a strict data flow pattern:



1.  **User Action**: User triggers a `Payment` in the `BillDetailsScreen`.
2.  **ViewModel**: The `ResidentPaymentViewModel` calls the corresponding repository method.
3.  **Data Persistence**: The `RoomMessRepository` creates a `TransactionEntity` and updates the `ResidentEntity` balance.
4.  **UI Update**: `StateFlow` emits the updated state, automatically re-composing the UI to reflect the "Payment Successful" status.

---

## 📂 Project Structure

```bash
com.antigravity.mymess
├── core              # Reusable components & Base classes
├── data              # DAOs, Entities, and Repository Implementations
├── domain            # Pure-Kotlin Models & Business Logic interfaces
└── ui                # Feature-based Compose screens
    ├── admin         # Dashboard, Inventory, Reporting UI
    ├── resident      # Payments, Menu, Profile UI
    └── shared        # Common Jetpack Compose components

## 🛡️ License

Private Enterprise License. Copyright © 2025 Antigravity.
