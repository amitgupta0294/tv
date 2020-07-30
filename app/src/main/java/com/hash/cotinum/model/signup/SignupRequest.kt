package com.hash.cotinum.model.signup

import com.google.gson.annotations.SerializedName

class SignupRequest {

    @SerializedName("email")
    var email: String? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("password")
    var password: String? = null
}