package com.example.go4lunch.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.go4lunch.Models.NearbySearch.MyPlaces;
import com.example.go4lunch.ViewModel.PlacesViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;

import java.util.Objects;

public abstract class BaseFragment extends Fragment {

    private PlacesViewModel viewModel;
    private int mRadius = 500;
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
        url.append("&radius=");
        url.append(mRadius);
        url.append("&types=restaurant&sensor=true&key=AIzaSyD6y_8l1WeKKDk0dOHxxgL_ybA4Lmjc1Cc");
        Log.d("getUrlPlace", url.toString());

        return url.toString();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("OnResume", "Enter on resume method");
        loadData();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = Objects.requireNonNull(getContext()).getSharedPreferences("SharedPrefs", Context.MODE_PRIVATE);
        mRadius = sharedPreferences.getInt("RadiusSetting",500);
    }
}