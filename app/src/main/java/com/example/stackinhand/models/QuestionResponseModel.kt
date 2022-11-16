package com.example.stackinhand.models

import com.google.gson.annotations.SerializedName

data class QuestionResponseModel(
    @SerializedName("items")
    var items:ArrayList<QuestionModel>?,
    @SerializedName("has_more")
    var hasMore: Boolean?,
    @SerializedName("quota_max")
    var quotaMax: Int?,
    @SerializedName("quota_remaining")
    var quota_remaining: Int?
) : Data(), java.io.Serializable
