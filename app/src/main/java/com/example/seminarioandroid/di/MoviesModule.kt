package com.example.seminarioandroid.di

import android.app.Application
import android.content.Context
import com.example.seminarioandroid.BuildConfig
import com.example.seminarioandroid.ddl.data.MoviesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
class MoviesModule {

    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    fun providesBoredApi(
        retrofit: Retrofit
    ): MoviesApi {
        return retrofit.create(MoviesApi::class.java)
    }
}