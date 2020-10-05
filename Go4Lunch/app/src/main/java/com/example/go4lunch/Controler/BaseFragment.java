package com.example.go4lunch.Controler;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.go4lunch.Models.MyPlaces;
import com.example.go4lunch.Models.Result;
import com.example.go4lunch.ViewModel.MyPlacesViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;

import java.util.ArrayList;

public abstract class BaseFragment extends Fragment {

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private ArrayList<Result> myPlacesList;
    private MyPlacesViewModel viewModel;

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
        this.viewModel.getAllPlaces(getUrl()).observe(this, this::updateMyPlace);
    }

    public abstract void updateMyPlace(MyPlaces myPlaces);


    public String getUrl() {
        StringBuilder url = new StringBuilder();
        url.append("nearbysearch/json?");
        url.append("location=-33.8670522,151.1957362");
        url.append("&radius=500");
        url.append("&types=food&key=AIzaSyD6y_8l1WeKKDk0dOHxxgL_ybA4Lmjc1Cc");
        Log.d("getUrl", url.toString());

        return url.toString();
    }

    //private void getDeviceLocation() {
    //    mFusedLocationProviderClient.getLastLocation()
    //            .addOnCompleteListener(new OnCompleteListener<Location>() {
    //                @Override
    //                public void onComplete(@NonNull Task<Location> task) {
//
    //                }
    //            });
    //}

}