package com.mss.instamat.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AvatarData {

    @Expose
    @SerializedName("login")
    String login;

    @Expose
    @SerializedName("avatar_url")
    String avatarUrl;
}
