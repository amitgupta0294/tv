package com.hash.cotinum.model.plans

import com.google.gson.annotations.SerializedName

class PlansResponse {
    @SerializedName("status")
    var status: String? = null

    @SerializedName("package")
    private val mPackage: List<Package>? = null

    @SerializedName("data")
    var data: String? = null
}