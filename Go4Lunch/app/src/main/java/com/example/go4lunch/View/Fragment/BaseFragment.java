package com.example.go4lunch.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.go4lunch.Models.MyPlaces;
import com.example.go4lunch.ViewModel.MyPlacesViewModel;

public abstract class BaseFragment extends Fragment {

    private MyPlacesViewModel viewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        configureViewModel();
    }

    private void configureViewModel() {
        this.viewModel = new ViewModelProvider(this).get(MyPlacesViewModel.class);
        viewModel.getAllPlaces().observe(this,this::updateMyPlace);
    }

    public static String getUrl(){

        return null;
    }

    private void updateMyPlace(MyPlaces myPlaces) {

    }

}