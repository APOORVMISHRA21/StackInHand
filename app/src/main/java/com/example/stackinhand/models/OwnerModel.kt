package com.example.stackinhand.models

import com.google.gson.annotations.SerializedName

data class OwnerModel(
    @SerializedName("reputation")
    var reputation: Int?,
    @SerializedName("user_id")
    var userId: Long?,
    @SerializedName("user_type")
    var userType: String?,
    @SerializedName("profile_image")
    var profileImgUrl: String?,
    @SerializedName("display_name")
    var displayName: String?,
    @SerializedName("link")
    var profileLink: String?
) : Data(), java.io.Serializable
