package com.mss.instamat.network;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mss.instamat.R;

public class GsonActivity extends AppCompatActivity {

    private final String jsonData = "{\"time_of_year\": \"summer\", \"year\": \"2019\"}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gson);
        GsonData gsonData = new GsonBuilder().create().fromJson(jsonData, GsonData.class);
        Log.d("TAG", gsonData.timeOfYear + " " + String.valueOf(gsonData.year));
    }

    class GsonData {

        @Expose
        @SerializedName("time_of_year")
        String timeOfYear;

        @Expose
        int year;
    }
}
