package com.example.go4lunch.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.go4lunch.Models.Details.PlaceDetails;
import com.example.go4lunch.Models.NearbySearch.MyPlaces;
import com.example.go4lunch.Repository.PlacesRepository;

public class PlacesViewModel extends AndroidViewModel {
    private PlacesRepository repository;

    public PlacesViewModel(@NonNull Application application) {
        super(application);
        repository = new PlacesRepository();
    }
    public LiveData<MyPlaces> getNearbyPlaces(String url) {
        return repository.getNearbyPlaceMutableLiveData(url);
    }

    public LiveData<PlaceDetails> getDetailPlaces(String url) {
        return repository.getDetailsMutableLiveData(url);
    }
}
