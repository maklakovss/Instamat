package com.mss.instamat.tests.mockito.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @Expose
    @SerializedName("login")
    public String login;
}