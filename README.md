# MVVM-Demo

📱 Android MVVM Clean Architecture Sample App
📌 Overview

This project is a sample Android application built to demonstrate MVVM architecture with Clean Architecture principles.
The app showcases a scalable, maintainable, and testable Android codebase using modern Android development technologies.

🏗 Architecture

The application follows Clean Architecture with a clear separation of concerns:

Presentation Layer

Jetpack Compose for UI

ViewModel (MVVM)

LiveData & State for UI state management

Domain Layer

Business logic implemented using Use Cases

Platform-independent and testable

Contains domain models and contracts

Data Layer

Handles data sources (API / local)

Repository pattern implementation

DTO → Domain model mapping

🔄 MVVM Pattern

Model: Domain & data models

View: Jetpack Compose UI

ViewModel:

Handles UI logic

Exposes state via LiveData / State

Interacts with domain Use Cases

🌐 API Handling

API calls are executed through Use Cases

Clean separation between UI and data logic

Uses Kotlin Coroutines & Flow for asynchronous operations

Proper loading, success, error, and empty state handling

🛠 Tech Stack

Language: Kotlin

UI: Jetpack Compose

Architecture: MVVM + Clean Architecture

Asynchronous: Kotlin Coroutines & Flow

State Management: LiveData, Compose State

Dependency Injection: Hilt

Networking: Retrofit + Gson

Logging: Timber

✅ Key Features

Clean Architecture implementation

MVVM with lifecycle-aware components

Modern UI using Jetpack Compose

Use Case driven business logic

Reactive and asynchronous data flow

Scalable and maintainable project structure

🚀 Purpose of the Project

Demonstrate best practices in modern Android development

Serve as a reference for MVVM + Clean Architecture

Showcase clean code, modularity, and separation of concerns

Useful for learning, interviews, and real-world app design

app 

│── ui (Compose screens, ViewModels) 

domain 

│── model 

│── usecase 

│── repository 

data 

│── api 

│── mapper 

│── repository 


🧑‍💻 Author

Rohit More
Senior Android Developer
