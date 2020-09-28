package com.example.go4lunch.Network;

import com.example.go4lunch.Models.MyPlaces;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GoogleAPIService {
    @GET
    Call<MyPlaces> getPlaces();
}

