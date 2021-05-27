package com.fariq.moviecatalogue.data

import androidx.lifecycle.LiveData
import com.fariq.moviecatalogue.data.source.local.entity.FilmEntity

interface FilmDataSource {

    fun getMovies(): LiveData<List<FilmEntity>>
    fun getTvShows() : LiveData<List<FilmEntity>>
    fun getMovieDetail(filmId : String) : LiveData<FilmEntity>
    fun getTvShowDetail(filmId : String) : LiveData<FilmEntity>

}