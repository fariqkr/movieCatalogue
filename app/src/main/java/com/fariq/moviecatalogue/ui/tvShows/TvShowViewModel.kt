package com.fariq.moviecatalogue.ui.tvShows

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fariq.moviecatalogue.data.FilmRepository
import com.fariq.moviecatalogue.data.source.local.entity.FilmEntity

class TvShowViewModel(private val filmRepository: FilmRepository) : ViewModel() {

    fun getTvShows(): LiveData<List<FilmEntity>> = filmRepository.getTvShows()
}