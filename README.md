# Fetch Rewards Coding Exercise - Android

## Overview
This Android application retrieves data from a specified API endpoint, 
processes it according to the requirements, and displays it in an organized list format.

## Features
- Fetches data from the provided endpoint
- Groups items by listId
- Sorts items first by listId, then by name within each  group
- Filters out items where the name is blank or null
- Displays the results in an easy-to-read list interface
- Handles network errors and edge cases
- Supports pull-to-refresh functionality

## Architecture
This application follows MVVM architecture with Repository pattern:
- **Model**: Data classes and repository to handle data operations
- **View**: Jetpack Compose UI components
- **ViewModel**: Manages UI-related data and business logic

## Tech Stack
- Kotlin
- Jetpack Compose for UI
- MVVM Architecture
- Hilt for Dependency Injection
- Retrofit for networking
- Coroutines for async operations
- Unit and UI testing

## How to Build  and Run
1. Clone the repository
2. Open the project in Android Studio
3. Build and run app on an emulator or physical device

## Testing
The application includes:
- Unit tests for the ViewModel and Repository
- UI tests for the Compose UI Components
- Integration tests with fake repositories

## Requirements Fulfilled
- [x] Retrieve data from the specified endpoint
- [x] Display items grouped by listId
- [x] Sort results by listId then by name
- [x] Filter out items with blank or null names
- [x] Display results in an easy-to-read list
- [x] Build with latest non-pre-release tools
- [x] Support current release mobile OS