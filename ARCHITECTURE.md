# MyMess Architecture Overview

MyMess follows the **Clean Architecture** principles combined with the **MVVM (Model-View-ViewModel)** pattern, ensuring separation of concerns, testability, and scalability.

## 🏗 Layers

### 1. Presentation Layer (`app/src/main/java/.../ui`)
- **Components**: Jetpack Compose Screens, Reusable Components (`SharedComponents.kt`), and ViewModels.
- **Responsibility**: Renders the UI and observes state from ViewModels.
- **State Management**: Uses `StateFlow` to expose UI state. ViewModels handle user actions and delegate to the Domain layer.

### 2. Domain Layer (`domain/src/main/java/...`)
- **Components**: Data Models, Repository Interfaces, and Business Logic Managers (e.g., `SessionManager`).
- **Responsibility**: Defines the core business rules and data contracts. It is pure Kotlin and independent of Android frameworks (mostly).
- **Key Models**: `resident`, `transaction`, `attendance`, `service_request`.

### 3. Data Layer (`data/src/main/java/...`)
- **Components**: Room Database (`MyMessDatabase`), Entities (`UserEntity`), DAOs (`MessDao`), and Repository Implementations (`RoomMessRepository`).
- **Responsibility**: Manages data persistence and retrieval. Maps Database Entities to Domain Models.
- **Database**: 
  - **Room**: Type-safe SQLite wrapper.
  - **Pre-population**: Seeds demo data using `HardcodedData.kt` on first launch.

## 💉 Dependency Injection
**Hilt** is used for Dependency Injection across the app.
- `AppModule`: Provides application-wide dependencies.
- `DataModule`: Binds Repository implementations to their interfaces.

## 🔄 Data Flow
1. **User Action**: User clicks "Pay Bill" in `BillDetailsScreen`.
2. **ViewModel**: `ResidentPaymentViewModel` calls `repository.submitPayment()`.
3. **Repository**: `RoomMessRepository` creates a `TransactionEntity` and updates `ResidentEntity` balance in `MessDao`.
4. **Database**: Room persists changes transactionally.
5. **UI Update**: `StateFlow` emits the new state, updating the UI to show "Payment Successful".

## 🎨 Design System
- **Material 3**: The app uses Material 3 theming with dynamic color support.
- **Atomic Design**: Shared components are broken down into `Buttons.kt`, `Inputs.kt`, `Cards.kt`, and `Headers.kt` for reusability.
