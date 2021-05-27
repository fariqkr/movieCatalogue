package com.fariq.moviecatalogue.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fariq.moviecatalogue.data.source.local.entity.FilmEntity
import com.fariq.moviecatalogue.data.source.remote.RemoteDataSource
import com.fariq.moviecatalogue.data.source.remote.reponse.FilmResponse

class FilmRepository private constructor(private val remoteDataSource: RemoteDataSource) : FilmDataSource {
    companion object {
        @Volatile
        private var instance: FilmRepository? = null

        fun getInstance(remoteData: RemoteDataSource): FilmRepository =
            instance ?: synchronized(this) {
                FilmRepository(remoteData).apply { instance = this }
            }
    }

    override fun getMovies(): LiveData<List<FilmEntity>> {
        val movieResults = MutableLiveData<List<FilmEntity>>()
        remoteDataSource.getMovies(object : RemoteDataSource.LoadMoviesCallback {
            override fun onMoviesReceived(filmResponse : List<FilmResponse>) {
                val movieList = ArrayList<FilmEntity>()
                for (response in filmResponse) {
                    val film = FilmEntity(response.id,
                        response.title,
                        response.image,
                        response.desc,
                        response.genre,
                        response.year
                    )
                    movieList.add(film)
                }
                movieResults.postValue(movieList)
            }
        })

        return movieResults
    }

    override fun getTvShows(): LiveData<List<FilmEntity>> {
        val tvShowResults = MutableLiveData<List<FilmEntity>>()
        remoteDataSource.getTvShows(object : RemoteDataSource.LoadTvShowsCallback {

            override fun onTvShowsReceived(filmResponses: List<FilmResponse>) {
                val tvShowList = ArrayList<FilmEntity>()
                for (response in filmResponses) {
                    val film = FilmEntity(response.id,
                            response.title,
                            response.image,
                            response.desc,
                            response.genre,
                            response.year
                    )
                    tvShowList.add(film)
                }
                tvShowResults.postValue(tvShowList)            }

        })

        return tvShowResults
    }

    override fun getMovieDetail(filmId: String): LiveData<FilmEntity> {
        val filmResult = MutableLiveData<FilmEntity>()
        remoteDataSource.getMovies(object : RemoteDataSource.LoadMoviesCallback{
            override fun onMoviesReceived(filmResponses: List<FilmResponse>) {
                lateinit var movie: FilmEntity
                for (response in filmResponses) {
                    if (response.id == filmId) {
                        movie = FilmEntity(response.id,
                            response.title,
                            response.image,
                            response.desc,
                            response.genre,
                            response.year
                        )
                    }
                }
                filmResult.postValue(movie)
            }
        })
        return filmResult
    }

    override fun getTvShowDetail(filmId: String): LiveData<FilmEntity> {
        val filmResult = MutableLiveData<FilmEntity>()
        remoteDataSource.getTvShows(object : RemoteDataSource.LoadTvShowsCallback{
            override fun onTvShowsReceived(filmResponses: List<FilmResponse>) {
                lateinit var tvShow: FilmEntity
                for (response in filmResponses) {
                    if (response.id == filmId) {
                        tvShow = FilmEntity(response.id,
                                response.title,
                                response.image,
                                response.desc,
                                response.genre,
                                response.year
                        )
                    }
                }
                filmResult.postValue(tvShow)
            }
        })
        return filmResult
    }
}