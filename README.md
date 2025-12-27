# MyMess Enterprise 3.0

> [!WARNING]
> **Status: Under Development**
> This project is currently in active development. Features and architecture are subject to frequent changes.

**MyMess** is a modern, enterprise-grade Android application designed for efficient Hostel Mess Management. Built with **Kotlin** and **Jetpack Compose**, it provides a seamless experience for both Mess Owners and Residents, leveraging Clean Architecture and MVVM patterns.

## 🚀 Key Features

### For Owners (Admin Console)
- **Dashboard Analytics**: Real-time KPI tracking including Revenue, Profit, and Due Payments.
- **Biometric Attendance**: Simulates fingerprint scanning for precise daily meal tracking.
- **Financial Reports**: Comprehensive analytics with Daily, Weekly, Monthly, and Custom Date filtering capabilities.
- **Inventory Management**: Automated stock tracking with "Low Stock" indicators and wastage logging.
- **Menu Planning**: Flexible weekly menu editor to manage daily offerings.
- **Resident Verification**: Streamlined process for approving new resident registrations.

### For Residents (The Platform)
- **Digital Mess Card**: Secure QR-based or Biometric identity verification for dining.
- **Smart Menu**: View upcoming meals (Breakfast, Lunch, Dinner) in real-time.
- **Guest Booking**: Integrated service request system for guest meals, special diets, or complaints.
- **Bill Payments**: Instant view of outstanding dues and simulated UPI payment flow with receipt generation.
- **Profile Management**: comprehensive profile controls including digital ID proof management.

## 🏗 Architecture & Design

MyMess follows the **Clean Architecture** principles combined with the **MVVM (Model-View-ViewModel)** pattern, ensuring separation of concerns, testability, and scalability.

### Layers

#### 1. Presentation Layer (`app/src/main/java/.../ui`)
- **Components**: Jetpack Compose Screens, Reusable Components (`SharedComponents.kt`), and ViewModels.
- **Responsibility**: Renders the UI and observes state from ViewModels.
- **State Management**: Uses `StateFlow` to expose UI state. ViewModels handle user actions and delegate to the Domain layer.

#### 2. Domain Layer (`domain/src/main/java/...`)
- **Components**: Data Models, Repository Interfaces, and Business Logic Managers (e.g., `SessionManager`).
- **Responsibility**: Defines the core business rules and data contracts. Pure Kotlin, independent of Android frameworks.
- **Key Models**: `resident`, `transaction`, `attendance`, `service_request`.

#### 3. Data Layer (`data/src/main/java/...`)
- **Components**: Room Database (`MyMessDatabase`), Entities (`UserEntity`), DAOs (`MessDao`), and Repository Implementations (`RoomMessRepository`).
- **Responsibility**: Manages data persistence and retrieval. Maps Database Entities to Domain Models.
- **Data Sources**: 
  - **Room**: Type-safe SQLite wrapper for local persistence.
  - **Pre-population**: Seeds demo data automatically on first launch.

### Data Flow Example
1. **User Action**: User clicks "Pay Bill" in `BillDetailsScreen`.
2. **ViewModel**: `ResidentPaymentViewModel` calls `repository.submitPayment()`.
3. **Repository**: `RoomMessRepository` creates a `TransactionEntity` and updates `ResidentEntity` balance in `MessDao`.
4. **Database**: Room persists changes transactionally.
5. **UI Update**: `StateFlow` emits the new state, updating the UI to show "Payment Successful".

## 🛠 Tech Stack

- **Language**: Kotlin 1.9+
- **UI Toolkit**: Jetpack Compose (Material 3 Design System)
- **Architecture**: Clean Architecture + MVVM
- **Dependency Injection**: Hilt
- **Database**: Room Database (SQLite)
- **Async**: Coroutines & Flow
- **Navigation**: Jetpack Compose Navigation

## 🛡️ License

Private Enterprise License. Copyright © 2025 Antigravity.
