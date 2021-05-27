package com.fariq.moviecatalogue.ui.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.fariq.moviecatalogue.data.FilmRepository
import com.fariq.moviecatalogue.data.source.local.entity.FilmEntity
import com.fariq.moviecatalogue.utils.DataDummy
import junit.framework.Assert
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {
    private lateinit var viewModel: DetailViewModel
    private val dummyMovie = DataDummy.generatesDummyMovies()[0]
    private val movieId = dummyMovie.id
    private val dummyTvShow = DataDummy.generatesDummyTVShows()[0]
    private val tvShowId = dummyTvShow.id

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var filmRepository: FilmRepository

    @Mock
    private lateinit var filmObserver: Observer<FilmEntity>

    @Before
    fun setUp() {
        viewModel = DetailViewModel(filmRepository)
    }

    @Test
    fun getMovie() {
        viewModel.setSelectedFilm(movieId)
        val movie = MutableLiveData<FilmEntity>()
        movie.value = dummyMovie

        Mockito.`when`(filmRepository.getMovieDetail(movieId)).thenReturn(movie)
        val filmEntity = viewModel.getMovie().value as FilmEntity
        Mockito.verify(filmRepository).getMovieDetail(movieId)
        Assert.assertNotNull(filmEntity)
        assertEquals(dummyMovie.id, filmEntity.id)
        assertEquals(dummyMovie.desc, filmEntity.desc)
        assertEquals(dummyMovie.title, filmEntity.title)

        viewModel.getMovie().observeForever(filmObserver)
        Mockito.verify(filmObserver).onChanged(dummyMovie)
    }

    @Test
    fun getTvShow() {
        viewModel.setSelectedFilm(tvShowId)
        val tvShow = MutableLiveData<FilmEntity>()
        tvShow.value = dummyTvShow

        Mockito.`when`(filmRepository.getTvShowDetail(tvShowId)).thenReturn(tvShow)
        val filmEntity = viewModel.getTvShow().value as FilmEntity
        Mockito.verify(filmRepository).getTvShowDetail(tvShowId)
        Assert.assertNotNull(filmEntity)
        assertEquals(dummyTvShow.id, filmEntity.id)
        assertEquals(dummyTvShow.desc, filmEntity.desc)
        assertEquals(dummyTvShow.title, filmEntity.title)

        viewModel.getTvShow().observeForever(filmObserver)
        Mockito.verify(filmObserver).onChanged(dummyTvShow)
    }
}