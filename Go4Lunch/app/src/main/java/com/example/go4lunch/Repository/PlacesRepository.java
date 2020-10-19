package com.example.go4lunch.Repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.go4lunch.Models.Details.PlaceDetails;
import com.example.go4lunch.Models.NearbySearch.MyPlaces;
import com.example.go4lunch.Network.GoogleAPIService;
import com.example.go4lunch.Network.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlacesRepository {
    private MutableLiveData<MyPlaces> mNearbyPlaceMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<PlaceDetails> mDetailsPlaceMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<MyPlaces> getNearbyPlaceMutableLiveData(String url) {
        GoogleAPIService apiService = RetrofitInstance.getApiService();

        Call<MyPlaces> call = apiService.getPlaces(url);
        call.enqueue(new Callback<MyPlaces>() {
            @Override
            public void onResponse(Call<MyPlaces> call, Response<MyPlaces> response) {
                MyPlaces myPlaces = response.body();
                if (myPlaces != null && myPlaces.getResults() != null) {
                    mNearbyPlaceMutableLiveData.setValue(myPlaces);
                }
            }

            @Override
            public void onFailure(Call<MyPlaces> call, Throwable t) {
                Log.d("ListSize", "Error " + t);
            }
        });
        return mNearbyPlaceMutableLiveData;
    }

    public MutableLiveData<PlaceDetails> getDetailsMutableLiveData(String url) {
        GoogleAPIService apiService = RetrofitInstance.getApiService();


        return mDetailsPlaceMutableLiveData;
    }
}
