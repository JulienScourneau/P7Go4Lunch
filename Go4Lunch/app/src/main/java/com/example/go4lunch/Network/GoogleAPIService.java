package com.example.go4lunch.Network;

import com.example.go4lunch.Models.MyPlaces;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GoogleAPIService {
    @GET("nearbysearch/json?location=-33.8670522,151.1957362&radius=500&types=food&key=AIzaSyD6y_8l1WeKKDk0dOHxxgL_ybA4Lmjc1Cc")
    Call<MyPlaces> getPlaces();
}
