package com.example.go4lunch.View.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.Controler.RestaurantActivity;
import com.example.go4lunch.Models.Photo;
import com.example.go4lunch.Models.Result;
import com.example.go4lunch.R;

import java.util.ArrayList;


public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {
    private ArrayList<Result> mRestaurantList;

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder{
        private TextView mRestaurantName;
        private TextView mRestaurantLocation;
        private TextView mRestaurantSchedule;
        private TextView mRestaurantPictures;
        private TextView mRestaurantDistance;
        private TextView mWorkmateNumber;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            mRestaurantName = itemView.findViewById(R.id.restaurant_fragment_name);
            mRestaurantLocation = itemView.findViewById(R.id.restaurant_fragment_location);
            mRestaurantSchedule = itemView.findViewById(R.id.restaurant_fragment_schedule);
            mRestaurantPictures = itemView.findViewById(R.id.restaurant_fragment_picture);
            mRestaurantDistance = itemView.findViewById(R.id.restaurant_fragment_distance);
            mWorkmateNumber = itemView.findViewById(R.id.restaurant_fragment_workmates_number);

        }
    }

    public RestaurantAdapter(ArrayList<Result> restaurantList) {
        this.mRestaurantList = restaurantList;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_item_fragment,parent,false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Result currentRestaurant = mRestaurantList.get(position);

        holder.mRestaurantName.setText(currentRestaurant.getName());
        holder.mRestaurantLocation.setText(currentRestaurant.getVicinity());
        //holder.mRestaurantSchedule.setText(currentRestaurant.getName());
        holder.mRestaurantPictures.set(currentRestaurant.getPhotos());
        holder.mRestaurantDistance.setText("00m");
        holder.mWorkmateNumber.setText("2");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RestaurantActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRestaurantList.size();
    }

    public void updatePlace(final ArrayList<Result> restaurantList){
        this.mRestaurantList = restaurantList;
        notifyDataSetChanged();
    }
}
