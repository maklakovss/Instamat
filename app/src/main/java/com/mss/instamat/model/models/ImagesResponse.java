package com.mss.instamat.model.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImagesResponse {

    @Expose
    @SerializedName("totalHits")
    int totalHits;

    @Expose
    @SerializedName("total")
    int total;

    @Expose
    @SerializedName("hits")
    List<ImageInfo> hits;

    public int getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(int totalHits) {
        this.totalHits = totalHits;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ImageInfo> getHits() {
        return hits;
    }

    public void setHits(List<ImageInfo> hits) {
        this.hits = hits;
    }
}
