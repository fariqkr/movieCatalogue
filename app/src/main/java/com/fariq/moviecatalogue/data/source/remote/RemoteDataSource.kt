package com.fariq.moviecatalogue.data.source.remote

import android.os.Handler
import android.os.Looper
import com.fariq.moviecatalogue.data.source.remote.reponse.FilmResponse
import com.fariq.moviecatalogue.utils.EspressoIdlingResource
import com.fariq.moviecatalogue.utils.JsonHelper


class RemoteDataSource private constructor(private val jsonHelper: JsonHelper) {

    private val handler = Handler(Looper.getMainLooper())

    companion object {
        private const val SERVICE_LATENCY_IN_MILLIS: Long = 2000

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(helper: JsonHelper): RemoteDataSource =
            instance ?: synchronized(this) {
                RemoteDataSource(helper).apply { instance = this }
            }
    }

    fun getMovies(callback: LoadMoviesCallback) {
        EspressoIdlingResource.increment()
        handler.postDelayed({
            callback.onMoviesReceived(jsonHelper.loadMovies())
            EspressoIdlingResource.decrement()
        }, SERVICE_LATENCY_IN_MILLIS)
    }

    fun getTvShows(callback: LoadTvShowsCallback) {
        EspressoIdlingResource.increment()
        handler.postDelayed({
            callback.onTvShowsReceived(jsonHelper.loadTvShows())
            EspressoIdlingResource.decrement()
        }, SERVICE_LATENCY_IN_MILLIS)
    }


    interface LoadMoviesCallback {
        fun onMoviesReceived(filmResponses: List<FilmResponse>)
    }

    interface LoadTvShowsCallback {
        fun onTvShowsReceived(filmResponses: List<FilmResponse>)
    }
}
