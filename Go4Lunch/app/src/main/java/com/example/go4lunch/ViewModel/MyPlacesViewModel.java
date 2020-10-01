package com.example.go4lunch.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.go4lunch.Models.MyPlaces;
import com.example.go4lunch.Repository.PlacesRepository;

public class MyPlacesViewModel extends AndroidViewModel {
    private PlacesRepository repository;

    public MyPlacesViewModel(@NonNull Application application) {
        super(application);
        repository = new PlacesRepository();
    }
    public LiveData<MyPlaces> getAllPlaces() {
        return repository.getMutableLiveData();
    }
}
