package com.example.stackinhand.adapters

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.stackinhand.R
import com.example.stackinhand.SharedPref
import com.example.stackinhand.constants.AppConstants
import com.example.stackinhand.models.QuestionModel
import com.example.stackinhand.utils.Utils
import org.w3c.dom.Text
import java.util.Date

class QuestionsFeedAdapter(
    val context: Context,
    private val onQuestionClickInterface: OnQuestionClickInterface
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var feedModelList: ArrayList<QuestionModel>? = null

    public fun setData(data: ArrayList<QuestionModel>?){
        this.feedModelList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if(viewType == AppConstants.QUESTION_TYPE) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.question_item, parent, false)
            return QuestionFeedViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.placeholder_image_view, parent, false)
            return PlaceHolderViewHolder(view)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is QuestionFeedViewHolder) {
            val questionModel = feedModelList!![position]

            setHolderData(holder, questionModel, position)

            holder.itemView.setOnClickListener {
                onQuestionClickInterface.onQuestionCardClick(questionModel = questionModel, position = position)
            }
        }

        if(holder is PlaceHolderViewHolder) {
            val sharedPref = SharedPref(context)
            if(sharedPref.discardBtnClickCount >= 3) {
                holder.itemView.visibility = View.GONE
            }

            holder.discardBtn.setOnClickListener {

                sharedPref.recordDiscardBtnClick(sharedPref.discardBtnClickCount + 1)

                if(sharedPref.discardBtnClickCount >= 3) {
                    holder.itemView.visibility = View.GONE
                }
            }
        }
    }

    override fun getItemCount(): Int {
        if (feedModelList?.isNotEmpty() == true) {
            return feedModelList?.size?.plus(1) ?: 0
        }

        return 0
    }

    fun setHolderData(
        holder: QuestionFeedViewHolder,
        questionModel: QuestionModel,
        position: Int
    ) {
        Glide.with(context).load(questionModel.owner?.profileImgUrl).into(holder.authorImg)
        holder.authorName.text = questionModel.owner?.displayName
        holder.questionTitle.text = questionModel.title
        holder.answerCount.text = questionModel.answerCount.toString()
        holder.viewsCount.text = questionModel.viewCount.toString()
        holder.publishedDate.text = questionModel.creationDate?.let {
            Utils.convertTimestampToDate(it)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if(position == feedModelList?.size)
            return AppConstants.PLACEHOLDER_TYPE
        else
            return AppConstants.QUESTION_TYPE
    }

    inner class QuestionFeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var authorImg = itemView.findViewById<ImageView>(R.id.iv_avatar_item)
        var questionTitle = itemView.findViewById<TextView>(R.id.tv_question_item)
        var authorName = itemView.findViewById<TextView>(R.id.tv_question_author)
        var answerCount = itemView.findViewById<TextView>(R.id.tv_answers_count_item)
        var viewsCount = itemView.findViewById<TextView>(R.id.tv_views_count_item)
        var publishedDate = itemView.findViewById<TextView>(R.id.tv_date_item)
        var votesCount = itemView.findViewById<TextView>(R.id.tv_votes_count_item)
    }

    inner class PlaceHolderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var discardBtn = itemView.findViewById<ImageView>(R.id.placeholder_discard_btn)
    }

    interface OnQuestionClickInterface {
        fun onQuestionCardClick(questionModel : QuestionModel?, position : Int) {}

    }
}