package com.mss.instamat.repositories.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageInfoNet {

    @Expose
    @SerializedName("id")
    private
    long id;

    @Expose
    @SerializedName("largeImageURL")
    private
    String largeImageURL;

    @Expose
    @SerializedName("previewURL")
    private
    String previewURL;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLargeImageURL() {
        return largeImageURL;
    }

    public void setLargeImageURL(String largeImageURL) {
        this.largeImageURL = largeImageURL;
    }

    public String getPreviewURL() {
        return previewURL;
    }

    public void setPreviewURL(String previewURL) {
        this.previewURL = previewURL;
    }
}