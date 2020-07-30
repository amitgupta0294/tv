package com.hash.cotinum.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "channel")
class ChannelItem {

    @Element(name = "display-name")
    var name : String ?= null

    @Element(name = "icon")
    var icon : String ?= null
}