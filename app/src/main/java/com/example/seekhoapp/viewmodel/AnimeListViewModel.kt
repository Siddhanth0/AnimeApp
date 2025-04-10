package com.example.seekhoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.seekhoapp.data.model.Data
import com.example.seekhoapp.data.remote.Resource
import com.example.seekhoapp.data.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeListViewModel @Inject constructor(private val repository: AnimeRepository): ViewModel() {

    private val _topAnime = MutableStateFlow<Resource<List<Data>>>(Resource.Loading())
    val topAnime: StateFlow<Resource<List<Data>>> = _topAnime

    private val _animeDetail = MutableStateFlow<Resource<Data>>(Resource.Loading())
    val animeDetail: StateFlow<Resource<Data>> = _animeDetail

    fun getAnime() {
        viewModelScope.launch {
            _topAnime.value = Resource.Loading()
            _topAnime.value = repository.getTopAnime()
        }
    }

    fun getAnimeDetail(id: Int) {
        viewModelScope.launch {
            _animeDetail.value = Resource.Loading()
            _animeDetail.value = repository.getAnimeDetail(id)
        }
    }
}