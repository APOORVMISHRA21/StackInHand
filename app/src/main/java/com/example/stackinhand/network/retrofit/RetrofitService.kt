package com.example.stackinhand.network.retrofit

import com.example.stackinhand.models.QuestionResponseModel
import dagger.Provides
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface RetrofitService {

    @GET("questions?key=ZiXCZbWaOwnDgpVT9Hx8IA((&order=desc&sort=activity&site=stackoverflow")
    suspend fun getQuestionsFeed() : Response<QuestionResponseModel>
}