package com.example.go4lunch.Controller;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.go4lunch.Models.NearbySearch.MyPlaces;
import com.example.go4lunch.ViewModel.PlacesViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;

public abstract class BaseFragment extends Fragment {

    private PlacesViewModel viewModel;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        configureViewModel();
        getMyPlace();
    }

    private void configureViewModel() {
        this.viewModel = new ViewModelProvider(this).get(PlacesViewModel.class);

    }

    public void getMyPlace() {
        this.viewModel.getNearbyPlaces(getUrl()).observe(this, this::getNearbyPlaces);
    }

    public abstract void getNearbyPlaces(MyPlaces myPlaces);


    public String getUrl() {
        StringBuilder url = new StringBuilder();
        url.append("nearbysearch/json?");
        url.append("location=37.4067,-122.0813");
        url.append("&radius=500");
        url.append("&types=restaurant&sensor=true&key=AIzaSyD6y_8l1WeKKDk0dOHxxgL_ybA4Lmjc1Cc");
        Log.d("getUrlPlace", url.toString());

        return url.toString();
    }
}