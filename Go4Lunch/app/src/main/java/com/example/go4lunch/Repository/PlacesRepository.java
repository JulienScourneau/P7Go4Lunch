package com.example.go4lunch.Repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.go4lunch.Models.MyPlaces;
import com.example.go4lunch.Models.Restaurant;
import com.example.go4lunch.Network.GoogleAPIService;
import com.example.go4lunch.Network.RetrofitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlacesRepository {
    private ArrayList<Restaurant> restaurants = new ArrayList<>();
    private MutableLiveData<MyPlaces> mutableLiveData = new MutableLiveData<>();

    public MutableLiveData<MyPlaces> getMutableLiveData() {
        GoogleAPIService apiService = RetrofitInstance.getApiService();
        Call<MyPlaces> call = apiService.getPlaces("nearbysearch/json?location=-33.8670522,151.1957362&radius=500&types=food&key=AIzaSyD6y_8l1WeKKDk0dOHxxgL_ybA4Lmjc1Cc");
        call.enqueue(new Callback<MyPlaces>() {
            @Override
            public void onResponse(Call<MyPlaces> call, Response<MyPlaces> response) {
                MyPlaces myPlaces = response.body();
                mutableLiveData.setValue(myPlaces);

            }

            @Override
            public void onFailure(Call<MyPlaces> call, Throwable t) {
                Log.d("ListSize","Error "+ "");
            }
        });
        return mutableLiveData;
    }
}
