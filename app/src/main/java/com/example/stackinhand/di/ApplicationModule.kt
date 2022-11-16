package com.example.stackinhand.di

import com.example.stackinhand.BuildConfig
import com.example.stackinhand.constants.AppConstants
import com.example.stackinhand.network.retrofit.RetrofitService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit.Builder {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = if(BuildConfig.DEBUG){
            OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .callTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build()
        } else{
            OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .callTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build()
        }
        return Retrofit.Builder()
            .client(client)
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Singleton
    @Provides
    fun provideNetworkService(retrofit: Retrofit.Builder): RetrofitService {
        return retrofit.build()
            .create(RetrofitService::class.java)
    }

}

