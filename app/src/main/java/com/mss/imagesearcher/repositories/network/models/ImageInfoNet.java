package com.mss.imagesearcher.repositories.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageInfoNet {

    @Expose
    @SerializedName("id")
    private long id;

    @Expose
    @SerializedName("largeImageURL")
    private String largeImageURL;

    @Expose
    @SerializedName("previewURL")
    private String previewURL;

    @Expose
    @SerializedName("webformatURL")
    private String webFormatURL;

    @Expose
    @SerializedName("imageHeight")
    private int height;

    @Expose
    @SerializedName("imageWidth")
    private int width;

    @Expose
    @SerializedName("type")
    private String type;

    @Expose
    @SerializedName("tags")
    private String tags;

    @Expose
    @SerializedName("pageUrl")
    private String pageUrl;

    @Expose
    @SerializedName("likes")
    private int likes;

    @Expose
    @SerializedName("views")
    private int views;

    @Expose
    @SerializedName("comments")
    private int comments;

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

    public String getWebFormatURL() {
        return webFormatURL;
    }

    public void setWebFormatURL(String webFormatURL) {
        this.webFormatURL = webFormatURL;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }
}