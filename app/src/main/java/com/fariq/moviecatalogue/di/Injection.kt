package com.fariq.moviecatalogue.di

import android.content.Context
import com.fariq.moviecatalogue.data.FilmRepository
import com.fariq.moviecatalogue.data.source.remote.RemoteDataSource
import com.fariq.moviecatalogue.utils.JsonHelper


object Injection {
    fun provideRepository(context: Context): FilmRepository {

        val remoteDataSource = RemoteDataSource.getInstance(JsonHelper(context))

        return FilmRepository.getInstance(remoteDataSource)
    }
}