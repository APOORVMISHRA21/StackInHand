package com.example.stackinhand.network.repositories

import com.example.stackinhand.network.retrofit.RetrofitService
import javax.inject.Inject

class DefaultDataRepository @Inject constructor(private val retrofitService: RetrofitService) {
    suspend fun getQuestionsFeed() = retrofitService.getQuestionsFeed()
}