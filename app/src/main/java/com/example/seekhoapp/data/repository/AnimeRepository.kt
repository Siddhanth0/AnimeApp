package com.example.seekhoapp.data.repository

import com.example.seekhoapp.data.model.AnimeData
import com.example.seekhoapp.data.model.AnimeDetailResponse
import com.example.seekhoapp.data.model.Data
import com.example.seekhoapp.data.remote.AnimeAPI
import com.example.seekhoapp.data.remote.Resource
import javax.inject.Inject

class AnimeRepository @Inject constructor(
    private val api: AnimeAPI
) {

    suspend fun getTopAnime(): Resource<List<Data>> {
        return try {
            val response = api.getAnime()
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!.data)
            } else {
                Resource.Error("Error: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("Exception: ${e.localizedMessage ?: "Unknown error"}")
        }
    }

    suspend fun getAnimeDetail(id: Int): Resource<Data> {
        return try {
            val response = api.getAnimeDetails(id)
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!.data)
            } else {
                Resource.Error("Error: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("Exception: ${e.localizedMessage ?: "Unknown error"}")
        }
    }
}