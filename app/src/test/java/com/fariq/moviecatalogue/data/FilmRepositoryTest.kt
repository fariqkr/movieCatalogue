package com.fariq.moviecatalogue.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fariq.moviecatalogue.data.source.remote.RemoteDataSource
import com.fariq.moviecatalogue.utils.DataDummy
import com.fariq.moviecatalogue.utils.LiveDataTestUtil

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.doAnswer


class FilmRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val filmRepository = FakeFilmRepository(remote)

    private val movieResponses = DataDummy.generatesRemoteDummyMovies()
    private val movieId = movieResponses[0].id
    private val tvShowResponses = DataDummy.generatesRemoteDummyTVShows()
    private val tvShowId = tvShowResponses[0].id


    @Test
    fun getMovies() {
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.LoadMoviesCallback)
                    .onMoviesReceived(movieResponses)
            null
        }.`when`(remote).getMovies(any())
        val movieEntities = LiveDataTestUtil.getValue(filmRepository.getMovies())
        verify(remote).getMovies(any())
        assertNotNull(movieEntities)
        assertEquals(movieResponses.size.toLong(), movieEntities.size.toLong())
    }

    @Test
    fun getTvShows() {
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.LoadTvShowsCallback)
                .onTvShowsReceived(tvShowResponses)
            null
        }.`when`(remote).getTvShows(any())
        val tvShowEntities = LiveDataTestUtil.getValue(filmRepository.getTvShows())
        verify(remote).getTvShows(any())
        assertNotNull(tvShowEntities)
        assertEquals(movieResponses.size.toLong(), tvShowEntities.size.toLong())
    }

    @Test
    fun getMovieDetail() {
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.LoadMoviesCallback)
                .onMoviesReceived(movieResponses)
            null
        }.`when`(remote).getMovies(any())

        val filmEntities = LiveDataTestUtil.getValue(filmRepository.getMovieDetail(movieId))

        verify(remote).getMovies(any())

        assertNotNull(filmEntities)
        assertNotNull(filmEntities.title)
        assertEquals(movieResponses[0].title, filmEntities.title)
    }

    @Test
    fun getTvShowDetail() {
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.LoadTvShowsCallback)
                .onTvShowsReceived(tvShowResponses)
            null
        }.`when`(remote).getTvShows(any())

        val filmEntities = LiveDataTestUtil.getValue(filmRepository.getTvShowDetail(tvShowId))

        verify(remote).getTvShows(any())

        assertNotNull(filmEntities)
        assertNotNull(filmEntities.title)
        assertEquals(tvShowResponses[0].title, filmEntities.title)
    }
}