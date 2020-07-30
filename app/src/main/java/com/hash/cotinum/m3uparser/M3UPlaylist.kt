package com.hash.cotinum.m3uparser

class M3UPlaylist {
    var playlistName: String? = null
    var playlistParams: String? = null
    var playlistItems: List<M3UItem>? = null

    fun getSingleParameter(paramName: String): String {
        val paramsArray =
            playlistParams!!.split(" ".toRegex()).toTypedArray()
        for (i in paramsArray.indices) {
            val parameter = paramsArray[i]
            if (parameter.contains(paramName)) {
                return parameter.substring(parameter.indexOf(paramName) + paramName.length)
                    .replace("=", "")
            }
        }
        return ""
    }
}