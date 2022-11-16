package com.example.stackinhand.models

import com.google.gson.annotations.SerializedName

data class QuestionModel(
    @SerializedName("tags")
    var tags: ArrayList<String>?,
    @SerializedName("owner")
    var owner: OwnerModel?,
    @SerializedName("is_answered")
    var isAnswered: Boolean?,
    @SerializedName("view_count")
    var viewCount: Int?,
    @SerializedName("answer_count")
    var answerCount: Int?,
    @SerializedName("score")
    var score: Int?,
    @SerializedName("last_activity_date")
    var lastActivityDate: Long?,
    @SerializedName("creation_date")
    var creationDate: Long?,
    @SerializedName("question_id")
    var questionId: Long?,
    @SerializedName("content_license")
    var contentLicense: String?,
    @SerializedName("link")
    var questionLink: String?,
    @SerializedName("title")
    var title: String?
) : Data(), java.io.Serializable
