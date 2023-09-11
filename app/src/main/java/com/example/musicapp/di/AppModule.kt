package com.example.musicapp.di

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import com.example.musicapp.data.ApiServices
import com.example.musicapp.data.datasource.SongsDataSource
import com.example.musicapp.data.datasource.SongsDataSourceImpl
import com.example.musicapp.data.repository.SongsRepository
import com.example.musicapp.data.repository.SongsRepositoryImpl
import com.example.musicapp.domain.usecase.FetchSongsUseCase
import com.example.musicapp.utils.ApiEndPoint
import com.example.musicapp.utils.MyPlayer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiEndPoint.BaseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }


    @Provides
    @Singleton
    fun provideSongsDataSource(
        retrofit: Retrofit
    ) : SongsDataSource = SongsDataSourceImpl(retrofit)

    @Provides
    @Singleton
    fun provideSongsRepository(
        songsDataSource: SongsDataSource
    ) : SongsRepository = SongsRepositoryImpl(songsDataSource)

    @Provides
    @Singleton
    fun provideFetchSongsUseCase(
        repository: SongsRepository
    ) : FetchSongsUseCase = FetchSongsUseCase(repository)

    @Provides
    @Singleton
    fun provideExoPLayer(@ApplicationContext context: Context): ExoPlayer {
        return ExoPlayer.Builder(context).build()
    }

    @Provides
    @Singleton
    fun provideMyPlayer(player: ExoPlayer): MyPlayer {
        return MyPlayer(player)
    }
}