package com.example.go4lunch.View.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.Controler.RestaurantActivity;
import com.example.go4lunch.Models.NearbySearch.Result;
import com.example.go4lunch.R;

import java.util.ArrayList;


public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {
    private ArrayList<Result> mRestaurantList;

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder {
        private TextView mRestaurantName;
        private TextView mRestaurantLocation;
        private TextView mRestaurantSchedule;
        private ImageView mRestaurantPictures;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_item_fragment, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Result currentRestaurant = mRestaurantList.get(position);
        String placeId = currentRestaurant.getPlaceId();

        holder.mRestaurantName.setText(currentRestaurant.getName());
        holder.mRestaurantLocation.setText(currentRestaurant.getVicinity());
        holder.mRestaurantSchedule.setText(currentRestaurant.getName());
        holder.mRestaurantDistance.setText("00m");
        holder.mWorkmateNumber.setText("2");

        if (currentRestaurant.getPhotos() != null && currentRestaurant.getPhotos().size() > 0) {

            Glide.with(holder.mRestaurantPictures.getContext())
                    .load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&maxheight=400&photoreference="+currentRestaurant.getPhotos().get(0).getPhotoReference()+"&key=AIzaSyD6y_8l1WeKKDk0dOHxxgL_ybA4Lmjc1Cc")
                    //.apply(RequestOptions.circleCropTransform())
                    .into(holder.mRestaurantPictures);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), RestaurantActivity.class);
            intent.putExtra("PLACE_ID",placeId);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mRestaurantList.size();
    }

    public void updatePlace(final ArrayList<Result> restaurantList) {
        this.mRestaurantList = restaurantList;
        notifyDataSetChanged();
    }
}
