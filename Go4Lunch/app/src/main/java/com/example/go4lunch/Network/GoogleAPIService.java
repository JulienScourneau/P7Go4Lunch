package com.example.go4lunch.Network;

import com.example.go4lunch.Models.Details.PlaceDetails;
import com.example.go4lunch.Models.NearbySearch.MyPlaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface GoogleAPIService {
    @GET()
    Call<MyPlaces> getPlaces(@Url String url);

    @GET()
    Call<PlaceDetails> getPlacesDetails(@Url String url);
}

