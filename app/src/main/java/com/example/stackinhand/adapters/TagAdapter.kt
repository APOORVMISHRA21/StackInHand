package com.example.stackinhand.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.stackinhand.R

class TagAdapter(
    val context: Context,
    private val onTagClickInterface: OnTagClickInterface
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var tagList: ArrayList<String>? = null

    private var selectedPosition = -1

    public fun setData(data: ArrayList<String>?){
        this.tagList = data
        notifyDataSetChanged()
    }

    public fun setAllUnselected(call: Boolean) {
        if(call)
            selectedPosition = -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tag_item, parent, false)
        return TagViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is TagViewHolder) {

            if(selectedPosition == position) {
                holder.tagCard.setCardBackgroundColor(context.resources.getColor(R.color.primary_color))
            } else {
                holder.tagCard.setCardBackgroundColor(context.resources.getColor(R.color.secondary_color))
            }

            setHolderData(holder, tagList?.get(position))

            holder.itemView.setOnClickListener {
                if(selectedPosition == position) {
                    selectedPosition = -1;
                    holder.itemView.isSelected = false
                    holder.tagCard.setCardBackgroundColor(context.resources.getColor(R.color.secondary_color))
                } else {
                    selectedPosition = position
                    holder.itemView.isSelected = true
                    holder.tagCard.setCardBackgroundColor(context.resources.getColor(R.color.primary_color))
                    onTagClickInterface.onTagSelected(tag = tagList?.get(position))
                }
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        if (tagList?.isNotEmpty() == true) {
            return tagList?.size ?: 0
        }

        return 0
    }

    fun setHolderData(
        holder: TagViewHolder,
        tag: String?
    ) {
        if(tag.isNullOrEmpty().not())
            holder.tagText.text = tag
    }


    inner class TagViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tagCard = itemView.findViewById<CardView>(R.id.tag_card)
        var tagText = itemView.findViewById<TextView>(R.id.tv_tag)
    }


    interface OnTagClickInterface {
        fun onTagSelected(tag : String?) {}
    }
}