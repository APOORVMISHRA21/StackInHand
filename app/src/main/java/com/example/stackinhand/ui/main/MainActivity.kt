package com.example.stackinhand.ui.main

import android.R
import android.content.Intent
import android.net.Uri
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stackinhand.adapters.QuestionsFeedAdapter
import com.example.stackinhand.adapters.TagAdapter
import com.example.stackinhand.databinding.ActivityMainBinding
import com.example.stackinhand.databinding.BottomSheetDialogBinding
import com.example.stackinhand.models.QuestionModel
import com.example.stackinhand.viewmodels.GenericViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.internal.notify


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), QuestionsFeedAdapter.OnQuestionClickInterface, TagAdapter.OnTagClickInterface {

    private val genericViewModel by viewModels<GenericViewModel>()

    private var feedAdapter: QuestionsFeedAdapter? = null
    private var tagAdapter: TagAdapter? = null

    private var feedList: ArrayList<QuestionModel>? = null

    private var tagSet: HashSet<String>? = null
    private var tagList: ArrayList<String>? = null

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomSheetBinding: BottomSheetDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        bottomSheetBinding = BottomSheetDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showLoader(true)
        observeViewModel()
        setUpRecyclerView()
        setUpSearchListener()
        binding.filterBtn.setOnClickListener {
            showBottomSheetDialog()
        }
    }

    fun setUpRecyclerView() {
        feedAdapter = QuestionsFeedAdapter(this@MainActivity, this)
        binding.recyclerView.layoutManager = LinearLayoutManager(
            this@MainActivity,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.recyclerView.adapter = feedAdapter

        tagAdapter = TagAdapter(this@MainActivity, this)

    }

    fun observeViewModel() {
        genericViewModel.getQuestionsFeed()
        genericViewModel.questionFeedResponse.observe(this) {
            feedList = it.items
            findAverageStats(it.items)
            showLoader(false)
            feedAdapter!!.setData(it.items)
        }
    }

    private fun setUpSearchListener() {
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(str: String?): Boolean {
                showLoader(toShow = true)
                if (str != null) {
                    filterList(str)
                } else {
                    showLoader(toShow = false)
                }
                return false
            }

            override fun onQueryTextChange(str: String?): Boolean {
                showLoader(toShow = true)
                if (str != null) {
                    filterList(str)
                } else {
                    showLoader(toShow = false)
                }
                return false
            }
        })
    }

    private fun filterList(str: String) {
        if (feedList.isNullOrEmpty().not()) {
            val filteredList = ArrayList<QuestionModel>()
            for (question in feedList!!) {
                if (question.title?.lowercase()?.contains(str.lowercase()) == true ||
                    question.owner?.displayName?.lowercase()?.contains(str.lowercase()) == true
                ) {
                    filteredList.add(question)
                }
            }
            feedAdapter!!.setData(filteredList)
            showLoader(toShow = false)
        }
    }

    private fun filterListBasedOnTag(tag: String) {
        if(feedList.isNullOrEmpty().not()) {
            val filteredList = ArrayList<QuestionModel>()
            for (question in feedList!!) {
                if(question.tags?.contains(tag) == true)
                    filteredList.add(question)
            }

            feedAdapter!!.setData(filteredList)
            showLoader(toShow = false)
        }
    }

    private fun showLoader(toShow: Boolean) {
        binding.progressBar.visibility = if (toShow) View.VISIBLE else View.GONE
        binding.avgViewCountCard.visibility = if (toShow) View.GONE else View.VISIBLE
        binding.avgAnswerCountCard.visibility = if (toShow) View.GONE else View.VISIBLE
        binding.recyclerView.visibility = if (toShow) View.GONE else View.VISIBLE
    }

    private fun findAverageStats(feedList: ArrayList<QuestionModel>?) {
        tagSet = HashSet()
        tagList = ArrayList()
        if (feedList.isNullOrEmpty().not()) {

            val totalItems = feedList?.size
            var totalViewCount = 0
            var totalAnswerCount = 0
            for (question in feedList!!) {
                totalViewCount += question.viewCount!!
                totalAnswerCount += question.answerCount!!
                pushTagToSet(question)
            }

            try {
                binding.tvAvgViewCount.text = (totalViewCount / totalItems!!).toString()
                binding.tvAvgAnswerCount.text = (totalAnswerCount / totalItems).toString()
            } catch (e: Exception) {
                binding.avgViewCountCard.visibility = View.GONE
                binding.avgAnswerCountCard.visibility = View.GONE
                e.printStackTrace()
            }

            setUpTagAdapter()
        }
    }

    private fun setUpTagAdapter() {
        if(tagList == null)
            tagList = ArrayList()

        if(tagSet != null) {
            for (tag in tagSet!!)
                tagList!!.add(tag)
        }
        Log.e("TAGList", tagList?.size.toString())
        tagAdapter?.setData(tagList)
    }

    private fun pushTagToSet(question: QuestionModel?) {
        val tags = question?.tags
        if(tagSet == null)
            tagSet = HashSet()
        if (tags != null && tags.size > 0) {
            for (tag in tags) {
                tagSet!!.add(tag)
            }
        }
    }
    private fun openInBrowser(url: String) {
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse(url)
        startActivity(openURL)
    }

    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetBinding = BottomSheetDialogBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(bottomSheetBinding.root)
        bottomSheetBinding.tagRecyclerView.layoutManager = LinearLayoutManager(
            this@MainActivity,
            LinearLayoutManager.VERTICAL,
            false
        )
        bottomSheetBinding.tagRecyclerView.adapter = tagAdapter
        bottomSheetBinding.clearFilterBtn.setOnClickListener {
            Log.e("Clear", "Clear")
            tagAdapter!!.setAllUnselected(call = true)
            tagAdapter?.setData(tagList)
            bottomSheetBinding.tagRecyclerView.adapter = tagAdapter
            tagAdapter?.notifyDataSetChanged()
            feedAdapter!!.setData(feedList)

        }
        bottomSheetDialog.show()
    }



    override fun onQuestionCardClick(questionModel: QuestionModel?, position: Int) {
        questionModel?.questionLink?.let { openInBrowser(it) }
    }

    override fun onTagSelected(tag: String?) {
        if (tag != null) {
            showLoader(toShow = true)
            filterListBasedOnTag(tag)
        }

    }
}