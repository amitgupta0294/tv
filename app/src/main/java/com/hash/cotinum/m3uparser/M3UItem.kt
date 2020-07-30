package com.hash.cotinum.m3uparser

import java.io.Serializable

class M3UItem : Serializable {
    var itemDuration: String? = null
    var itemName: String? = null
    var itemUrl: String? = null
    var itemIcon: String? = null
}