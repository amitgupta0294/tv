package com.hash.cotinum.model

import com.google.gson.annotations.SerializedName

class AddDeviceResponse {

    @SerializedName("data")
    var data: String? = null

    @SerializedName("device_name")
    var deviceName: String? = null

    @SerializedName("device_serial")
    var deviceSerial: String? = null

    @SerializedName("status")
    var status: String? = null

    @SerializedName("user_id")
    var userId: String? = null
}