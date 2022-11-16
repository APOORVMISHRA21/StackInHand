package com.example.stackinhand.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stackinhand.models.QuestionResponseModel
import com.example.stackinhand.network.repositories.DefaultDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class GenericViewModel @Inject constructor(
    private val defaultDataRepository: DefaultDataRepository
) : ViewModel(){

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main

    val questionFeedResponse : MutableLiveData <QuestionResponseModel> = MediatorLiveData()

    fun getQuestionsFeed() {
        CoroutineScope(ioDispatcher).launch {
            val response = defaultDataRepository.getQuestionsFeed()
            withContext(mainDispatcher) {
                if (response.isSuccessful) {
                    questionFeedResponse.postValue(response.body())
                } else {
                    println("Error : ${response.message()} ")
                }
            }
        }
    }
}