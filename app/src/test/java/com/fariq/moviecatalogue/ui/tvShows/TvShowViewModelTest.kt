package com.fariq.moviecatalogue.ui.tvShows

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
class TvShowViewModelTest {
    private lateinit var viewModel: TvShowViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var filmRepository: FilmRepository

    @Mock
    private lateinit var observer: Observer<List<FilmEntity>>

    @Before
    fun setUp() {
        viewModel = TvShowViewModel(filmRepository)
    }

    @Test
    fun getCourses() {
        val dummyTvShow = DataDummy.generatesDummyMovies()
        val tvShows = MutableLiveData<List<FilmEntity>>()
        tvShows.value = dummyTvShow

        Mockito.`when`(filmRepository.getTvShows()).thenReturn(tvShows)
        val filmEntities = viewModel.getTvShows().value
        Mockito.verify(filmRepository).getTvShows()
        Assert.assertNotNull(filmEntities)
        Assert.assertEquals(10, filmEntities?.size)

        viewModel.getTvShows().observeForever(observer)
        Mockito.verify(observer).onChanged(dummyTvShow)
    }
}