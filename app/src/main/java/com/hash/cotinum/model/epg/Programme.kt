package com.hash.cotinum.model.epg

import com.google.gson.annotations.SerializedName

class Programme {

    @SerializedName("title")
    var title : String? = null

    @SerializedName("sub-title")
    var sub_title : String? = null

    @SerializedName("desc")
    var desc : String? = null

    @SerializedName("category")
    var category : String? = null

    @SerializedName("value")
    var value : String? = null

    @SerializedName("startDate")
    var startDate : String? = null

    @SerializedName("stopDate")
    var stopDate : String? = null
}