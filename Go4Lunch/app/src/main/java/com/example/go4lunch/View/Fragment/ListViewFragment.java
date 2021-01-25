package com.example.go4lunch.View.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.Controller.BaseFragment;
import com.example.go4lunch.Models.Details.PlaceDetails;
import com.example.go4lunch.Models.NearbySearch.MyPlaces;
import com.example.go4lunch.Models.NearbySearch.Result;
import com.example.go4lunch.R;
import com.example.go4lunch.View.Adapter.RestaurantAdapter;

import java.util.ArrayList;

public class ListViewFragment extends BaseFragment {

    private RestaurantAdapter restaurantAdapter;
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listview_fragment, container, false);
        mRecyclerView = view.findViewById(R.id.restaurant_recyclerview);

        setUpRecyclerView();
        return view;
    }

    public void getPlaceToDisplay() {
        if (mResultPlaceList.isEmpty()) {
            updatePlaceList(myPlaceList);
        } else {
            updatePlaceList(mResultPlaceList);
        }
    }

    private void updatePlaceList(ArrayList<Result> placeList) {
        restaurantAdapter.updatePlace(placeList, userLocation);
    }

    private void setUpRecyclerView() {
        restaurantAdapter = new RestaurantAdapter(myPlaceList);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(restaurantAdapter);
        restaurantAdapter.notifyDataSetChanged();
    }
}
