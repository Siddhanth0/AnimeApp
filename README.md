# Anime App - Jikan API Integration

## Overview

This is a simple Android app that integrates the Jikan API to fetch and display a list of popular anime series. The app provides a home screen with a list of anime series, and upon selecting an anime, users are taken to a detail page where they can view more information, including trailers, plot, genre, and ratings.

## Features

1. **Anime List Page (Home Screen):**
   - Displays a list of top-rated anime series.
   - Each anime item shows the following:
     - Title
     - Number of Episodes
     - Rating (e.g., MyAnimeList score)
     - Poster Image
   - The list is fetched from the [Jikan API - Top Anime Endpoint](https://api.jikan.moe/v4/top/anime).

2. **Anime Detail Page:**
   - Displays detailed information about the anime when an item is clicked.
   - Features:
     - Video player to play the trailer if available, or shows the poster image if no trailer is found.
     - Title
     - Plot/Synopsis
     - Genre(s)
     - Number of Episodes
     - Rating
   - The details are fetched from the [Jikan API - Anime Details Endpoint](https://api.jikan.moe/v4/anime/{anime_id}).

## Assumptions

- The app assumes that the user has an active internet connection to fetch anime data from the Jikan API.
- If a trailer is not available for an anime, a fallback poster image is displayed on the detail page.
- The app uses Retrofit for networking, Gson for parsing JSON responses, and Jetpack Compose for UI components.
- The app is designed to handle cases where data might be missing (e.g., missing trailer or cast details).

## Features Implemented

1. **Anime List Screen:**
   - Displays a scrollable list of anime series with title, number of episodes, rating, and poster image.
   
2. **Anime Detail Screen:**
   - Shows detailed information about the selected anime, including the trailer (if available), plot, genre(s), main cast, episodes, and rating.

3. **Smooth Transitions:**
   - Smooth UI transitions between the list screen and the detail screen.

## Limitations

- The Jikan API may not always return complete data (e.g., missing trailer, cast, etc.). In such cases, the app provides fallback content like a poster image.
- The app assumes the user has a stable internet connection; poor connectivity may result in slow loading or failed requests.

## Technologies Used

- **Kotlin:** Primary programming language for Android development.
- **Jetpack Compose:** For building the UI components.
- **Retrofit:** For making network requests to the Jikan API.
- **Gson:** For parsing the JSON data from the API.
- **Coil:** For loading images (e.g., anime poster).
- **Hilt:** For dependency injection.
