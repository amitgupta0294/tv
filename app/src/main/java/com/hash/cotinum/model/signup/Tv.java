package com.hash.cotinum.model.signup;

import com.hash.cotinum.model.ChannelItem;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

@Root(name = "tv")
public class Tv {

    @Element(name = "")
    ArrayList<ChannelItem> channelItems;
}
