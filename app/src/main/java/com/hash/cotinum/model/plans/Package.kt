package com.hash.cotinum.model.plans

import com.google.gson.annotations.SerializedName

class Package {

    @SerializedName("day")
    var day: String? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("plan_id")
    var planId: String? = null

    @SerializedName("price")
    var price: String? = null

    @SerializedName("screens")
    var screens: Any? = null

    @SerializedName("status")
    var status: String? = null
}