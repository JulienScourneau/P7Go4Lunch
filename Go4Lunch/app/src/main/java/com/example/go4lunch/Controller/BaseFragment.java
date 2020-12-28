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
import com.example.go4lunch.Models.Details.PlaceDetails;
import com.example.go4lunch.Models.NearbySearch.Geometry;
import com.example.go4lunch.Models.NearbySearch.Locations;
import com.example.go4lunch.Models.NearbySearch.MyPlaces;
import com.example.go4lunch.Models.NearbySearch.Photo;
import com.example.go4lunch.Models.NearbySearch.Result;
import com.example.go4lunch.ViewModel.PlacesViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class BaseFragment extends Fragment {

    private PlacesViewModel viewModel;
    private int mRadius = 50;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlacesClient placesClient;
    protected LatLng latLng;
    protected ArrayList<String> searchPlaceId = new ArrayList<>();
    private ArrayList<PlaceDetails> mSearchPlaceList = new ArrayList<>();
    private ArrayList<Result> mResultPlaceList = new ArrayList<>();

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

    public void getSearchPlace(String searchId) {
        Log.d("getSearchDetails","SearchPlace");
        this.viewModel.getDetailPlaces(getSearchPlaceUrl(searchId)).observe(this, this::getPlacesDetails);

    }

    public void getPlacesDetails(PlaceDetails placeDetails) {
        mSearchPlaceList.add(placeDetails);
        Log.d("PlacesDetailsList", "List size: " + mSearchPlaceList.size() + " place name: " + placeDetails.getResult().getName());
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

        //loadData();
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

    public void getSearchPlaceWithAutoComplete(String search) {
        if (!Places.isInitialized()) {
            Places.initialize(Objects.requireNonNull(Objects.requireNonNull(getActivity()).getApplicationContext()), BuildConfig.PLACE_API_KEY);
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
            Log.d("getSearchPlace", "searchPlaceId");
            for (AutocompletePrediction prediction : response.getAutocompletePredictions()) {
                getSearchPlace(prediction.getPlaceId());
            }
            Log.d("getSearchPlace", "searchPlaceId in autocomplete: " + searchPlaceId.size());

        });
    }


    public String getSearchPlaceUrl(String placeId) {
        StringBuilder url = new StringBuilder();
        url.append("details/json?place_id=");
        url.append(placeId);
        url.append("&key=");
        url.append(BuildConfig.PLACE_API_KEY);

        Log.d("getUrlDetails", url.toString());
        return url.toString();
    }

    private void changeDetailsResult(ArrayList<PlaceDetails> searchPlaceList) {
        Result placeResult = new Result();
        for (int i = 0; i < searchPlaceList.size(); i++) {
            PlaceDetails placeDetails = searchPlaceList.get(i);
            Geometry geometry = new Geometry();
            Locations locations = new Locations();

            locations.setLat(placeDetails.getResult().getGeometry().getLocation().getLat());
            locations.setLng(placeDetails.getResult().getGeometry().getLocation().getLng());
            geometry.setLocations(locations);

            placeResult.setPlaceId(placeDetails.getResult().getPlaceId());
            placeResult.setName(placeDetails.getResult().getName());
            placeResult.setPhotos(placeDetails.getResult().getPhotos());
            placeResult.setVicinity(placeDetails.getResult().getVicinity());
            placeResult.setGeometry(geometry);

            mResultPlaceList.add(placeResult);
        }
    }
}