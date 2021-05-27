package com.fariq.moviecatalogue.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fariq.moviecatalogue.data.FilmRepository
import com.fariq.moviecatalogue.data.source.local.entity.FilmEntity

class DetailViewModel(private val filmRepository: FilmRepository) : ViewModel() {
    private lateinit var filmId: String

    fun setSelectedFilm(filmId: String) {
        this.filmId = filmId
    }

    fun getMovie(): LiveData<FilmEntity> = filmRepository.getMovieDetail(filmId)
    fun getTvShow(): LiveData<FilmEntity> = filmRepository.getTvShowDetail(filmId)

}