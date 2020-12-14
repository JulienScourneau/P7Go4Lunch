package com.example.go4lunch.Controller;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.go4lunch.BuildConfig;
import com.example.go4lunch.Models.NearbySearch.MyPlaces;
import com.example.go4lunch.ViewModel.PlacesViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Objects;

import static android.content.ContentValues.TAG;

public abstract class BaseFragment extends Fragment {

    private PlacesViewModel viewModel;
    private int mRadius = 50;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlacesClient placesClient;
    protected LatLng latLng;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(getActivity()));
        if (!Places.isInitialized()) {
            Places.initialize(Objects.requireNonNull(getActivity().getApplicationContext()), BuildConfig.PLACE_API_KEY);

        }
        placesClient = Places.createClient(getActivity().getApplicationContext());
        configureViewModel();
        getLocation();
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
        if (latLng != null) {
            url.append("nearbysearch/json?");
            url.append("location=");
            url.append(latLng.latitude);
            url.append(",");
            url.append(latLng.longitude);
            url.append("&radius=");
            url.append(mRadius);
            url.append("&types=restaurant&sensor=true&key=");
            url.append(BuildConfig.PLACE_API_KEY);
        }


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
        mRadius = sharedPreferences.getInt("RadiusSetting", 50);
        getMyPlace();
        Log.d("loadSharedPref", "Radius: " + mRadius);
    }

    private void getLocation() {
        Log.d("GetLocation", "getLastKnowLocation");
        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mFusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {
            Location location = task.getResult();
            if (location != null) {
                latLng = new LatLng(location.getLatitude(), location.getLongitude());
                Log.d("Location", "LatLong" + latLng);
                getMyPlace();
            }
        });
    }

    public void getSearchPlace(String search) {
        if (!Places.isInitialized()) {
            Places.initialize(Objects.requireNonNull(getActivity().getApplicationContext()), BuildConfig.PLACE_API_KEY);
            Log.d("getSearchPlace", "Initialise Place");
        }

        AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();
        Log.d("getSearchPlace", "Get Token");

        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                .setCountries("FR", "BE")
                .setSessionToken(token)
                .setQuery(search)
                .build();

        placesClient.findAutocompletePredictions(request).addOnSuccessListener((response) -> {
            for (AutocompletePrediction prediction : response.getAutocompletePredictions()) {

                Log.i(TAG, prediction.getPlaceId());
                Log.i(TAG, prediction.getPrimaryText(null).toString());
            }
        });

        Log.d("getSearchPlace", "Search: " + search);
    }
}