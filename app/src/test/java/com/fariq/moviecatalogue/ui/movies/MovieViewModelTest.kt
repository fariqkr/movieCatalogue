package com.fariq.moviecatalogue.ui.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.fariq.moviecatalogue.data.FilmRepository
import com.fariq.moviecatalogue.data.source.local.entity.FilmEntity
import com.fariq.moviecatalogue.utils.DataDummy
import junit.framework.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {
    private lateinit var viewModel: MovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var filmRepository: FilmRepository

    @Mock
    private lateinit var observer: Observer<List<FilmEntity>>

    @Before
    fun setUp() {
        viewModel = MovieViewModel(filmRepository)
    }

    @Test
    fun getCourses() {
        val dummyMovie = DataDummy.generatesDummyMovies()
        val movies = MutableLiveData<List<FilmEntity>>()
        movies.value = dummyMovie

        Mockito.`when`(filmRepository.getMovies()).thenReturn(movies)
        val filmEntities = viewModel.getMovies().value
        Mockito.verify(filmRepository).getMovies()
        Assert.assertNotNull(filmEntities)
        Assert.assertEquals(10, filmEntities?.size)

        viewModel.getMovies().observeForever(observer)
        Mockito.verify(observer).onChanged(dummyMovie)
    }
}