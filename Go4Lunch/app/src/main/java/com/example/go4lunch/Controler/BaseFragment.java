package com.example.go4lunch.Controler;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.go4lunch.Models.NearbySearch.MyPlaces;
import com.example.go4lunch.ViewModel.MyPlacesViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;

public abstract class BaseFragment extends Fragment {

    private MyPlacesViewModel viewModel;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        configureViewModel();
        getMyPlace();
    }

    private void configureViewModel() {
        this.viewModel = new ViewModelProvider(this).get(MyPlacesViewModel.class);

    }

    public void getMyPlace() {
        this.viewModel.getAllPlaces(getUrl()).observe(this, this::getPlaces);
    }

    public abstract void getPlaces(MyPlaces myPlaces);


    public String getUrl() {
        StringBuilder url = new StringBuilder();
        url.append("nearbysearch/json?");
        url.append("location=37.4067,-122.0813");
        url.append("&radius=500");
        url.append("&types=restaurant&sensor=true&key=AIzaSyD6y_8l1WeKKDk0dOHxxgL_ybA4Lmjc1Cc");
        Log.d("getUrl", url.toString());

        return url.toString();
    }
}