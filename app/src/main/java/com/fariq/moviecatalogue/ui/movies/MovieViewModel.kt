package com.fariq.moviecatalogue.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fariq.moviecatalogue.data.FilmRepository
import com.fariq.moviecatalogue.data.source.local.entity.FilmEntity
import com.fariq.moviecatalogue.utils.DataDummy

class MovieViewModel(private val filmRepository: FilmRepository) : ViewModel() {

    fun getMovies(): LiveData<List<FilmEntity>> = filmRepository.getMovies()
}