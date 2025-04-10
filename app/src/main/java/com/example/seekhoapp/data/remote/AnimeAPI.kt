package com.example.seekhoapp.data.remote

import com.example.seekhoapp.data.model.AnimeData
import com.example.seekhoapp.data.model.AnimeDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AnimeAPI {
        @GET("top/anime")
        suspend fun getAnime(): Response<AnimeData>

        @GET("anime/{id}")
        suspend fun getAnimeDetails(@Path("id") id: Int): Response<AnimeDetailResponse>
}
