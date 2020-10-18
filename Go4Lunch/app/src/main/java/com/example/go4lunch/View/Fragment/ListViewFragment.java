package com.example.go4lunch.View.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.Controler.BaseFragment;
import com.example.go4lunch.Models.NearbySearch.MyPlaces;
import com.example.go4lunch.Models.NearbySearch.Result;
import com.example.go4lunch.R;
import com.example.go4lunch.View.Adapter.RestaurantAdapter;

import java.util.ArrayList;

public class ListViewFragment extends BaseFragment {

    private RestaurantAdapter restaurantAdapter;
    private RecyclerView mRecyclerView;
    private ArrayList<Result> myPlaceList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listview_fragment, container, false);
        mRecyclerView = view.findViewById(R.id.restaurant_recyclerview);

        setUpRecyclerView();

        return view;
    }

    public void getPlaces(MyPlaces myPlaces) {
        myPlaceList.clear();
        myPlaceList.addAll(myPlaces.getResults());
        updatePlaceList();
    }

    private void updatePlaceList() {
        restaurantAdapter.updatePlace(myPlaceList);
    }

    private void setUpRecyclerView() {
        restaurantAdapter = new RestaurantAdapter(myPlaceList);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(restaurantAdapter);
        restaurantAdapter.notifyDataSetChanged();
    }
}
