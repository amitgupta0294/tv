package com.hash.cotinum.model.login

import com.google.gson.annotations.SerializedName

class LoginResponse {

    @SerializedName("data")
    var data: String? = null

    @SerializedName("email")
    var email: String? = null

    @SerializedName("gender")
    var gender: String? = null

    @SerializedName("image_url")
    var imageUrl: String? = null

    @SerializedName("join_date")
    var joinDate: String? = null

    @SerializedName("last_login")
    var lastLogin: String? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("phone")
    var phone: Any? = null

    @SerializedName("status")
    var status: String? = null

    @SerializedName("user_id")
    var userId: String? = null
}