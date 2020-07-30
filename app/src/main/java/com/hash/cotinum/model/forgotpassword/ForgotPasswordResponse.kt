package com.hash.cotinum.model.forgotpassword

import com.google.gson.annotations.SerializedName

class ForgotPasswordResponse {

    @SerializedName("data")
    var data: String? = null

    @SerializedName("message")
    var message: String? = null

    @SerializedName("status")
    var status: String? = null
}