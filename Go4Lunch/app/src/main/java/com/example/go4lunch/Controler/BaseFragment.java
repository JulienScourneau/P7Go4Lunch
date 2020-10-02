package com.example.go4lunch.Controler;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.go4lunch.Models.MyPlaces;
import com.example.go4lunch.R;
import com.example.go4lunch.ViewModel.MyPlacesViewModel;

public abstract class BaseFragment extends Fragment {

    private String Url;
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
        StringBuilder url = new StringBuilder();
        url.append("nearbysearch/json?");
        url.append("location=-33.8670522,151.1957362" );
        url.append("&radius=500");
        url.append("&types=food&key=AIzaSyD6y_8l1WeKKDk0dOHxxgL_ybA4Lmjc1Cc");
        Log.d("getUrl",url.toString());

        return url.toString();
    }

    private void updateMyPlace(MyPlaces myPlaces) {

    }

}