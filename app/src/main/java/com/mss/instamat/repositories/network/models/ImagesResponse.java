package com.mss.instamat.repositories.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImagesResponse {

    @Expose
    @SerializedName("totalHits")
    private int totalHits;

    @Expose
    @SerializedName("total")
    private int total;

    @Expose
    @SerializedName("hits")
    private List<ImageInfoNet> hits;

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

    public List<ImageInfoNet> getHits() {
        return hits;
    }

    public void setHits(List<ImageInfoNet> hits) {
        this.hits = hits;
    }
}
