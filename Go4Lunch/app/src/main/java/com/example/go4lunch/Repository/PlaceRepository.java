package com.example.go4lunch.Repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.go4lunch.Models.MyPlaces;
import com.example.go4lunch.Models.Restaurant;
import com.example.go4lunch.Network.GoogleAPIService;
import com.example.go4lunch.Network.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceRepository {
    private ArrayList<Restaurant> restaurants = new ArrayList<>();
    private MutableLiveData<List<Restaurant>> mutableLiveData = new MutableLiveData<>();

    public MutableLiveData<List<Restaurant>> getMutableLiveData() {
        GoogleAPIService apiService = RetrofitInstance.getApiService();
        Call<MyPlaces> call = apiService.getPlaces();
        call.enqueue(new Callback<MyPlaces>() {
            @Override
            public void onResponse(Call<MyPlaces> call, Response<MyPlaces> response) {
                MyPlaces myPlaces = response.body();

            }

            @Override
            public void onFailure(Call<MyPlaces> call, Throwable t) {
                Log.d("ListSize","Error "+t.getMessage());
            }
        });
        return mutableLiveData;
    }
}
