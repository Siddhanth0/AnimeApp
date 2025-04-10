package com.example.seekhoapp.data.module

import com.example.seekhoapp.data.remote.ApiService
import com.example.seekhoapp.data.repository.AnimeRepository
import com.example.seekhoapp.data.remote.AnimeAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApi(): AnimeAPI = ApiService.animeApi

    @Provides
    @Singleton
    fun provideAnimeRepository(api: AnimeAPI): AnimeRepository = AnimeRepository(api)

}