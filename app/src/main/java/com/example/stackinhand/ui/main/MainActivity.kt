package com.example.stackinhand.ui.main

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stackinhand.R
import com.example.stackinhand.adapters.QuestionsFeedAdapter
import com.example.stackinhand.databinding.ActivityMainBinding
import com.example.stackinhand.models.QuestionModel
import com.example.stackinhand.ui.base.BaseActivity
import com.example.stackinhand.viewmodels.GenericViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), QuestionsFeedAdapter.OnQuestionClickInterface{

    private val genericViewModel by viewModels<GenericViewModel>()

    private var feedAdapter: QuestionsFeedAdapter? = null

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showLoader(true)
        observeViewModel()
    }

    fun observeViewModel() {
        genericViewModel.getQuestionsFeed()
        feedAdapter = QuestionsFeedAdapter(this@MainActivity, this)
        binding.recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.recyclerView.adapter = feedAdapter
        genericViewModel.questionFeedResponse.observe(this, {
            findAverageStats(it.items)
            showLoader(false)
            feedAdapter!!.setData(it.items)
        })
    }

    private fun showLoader(toShow: Boolean) {
        binding.progressBar.visibility = if (toShow) View.VISIBLE else View.GONE
        binding.avgViewCountCard.visibility = if (toShow) View.GONE else View.VISIBLE
        binding.avgAnswerCountCard.visibility = if(toShow) View.GONE else View.VISIBLE
        binding.recyclerView.visibility = if(toShow) View.GONE else View.VISIBLE
    }

    private fun findAverageStats(feedList: ArrayList<QuestionModel>?) {
        if(feedList.isNullOrEmpty().not()) {
            val totalItems = feedList?.size
            var totalViewCount = 0
            var totalAnswerCount = 0
            for (question in feedList!!) {
                totalViewCount += question.viewCount!!
                totalAnswerCount += question.answerCount!!
            }
            Log.e("VIEW", (totalViewCount / totalItems!!).toString())
            Log.e("ANS", (totalAnswerCount / totalItems).toString())
            try {
                binding.tvAvgViewCount.text = (totalViewCount / totalItems).toString()
                binding.tvAvgAnswerCount.text = (totalAnswerCount / totalItems).toString()
            } catch (e:Exception) {
                binding.avgViewCountCard.visibility = View.GONE
                binding.avgAnswerCountCard.visibility = View.GONE
                e.printStackTrace()
            }
        }
    }

    private fun openInBrowser(url: String) {
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse(url)
        startActivity(openURL)
    }

    override fun onQuestionCardClick(questionModel: QuestionModel?, position: Int) {
        questionModel?.questionLink?.let { openInBrowser(it) }
    }


}