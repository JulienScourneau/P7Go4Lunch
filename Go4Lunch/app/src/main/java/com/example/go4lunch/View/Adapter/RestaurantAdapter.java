package com.example.go4lunch.View.Adapter;

import android.content.Intent;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.go4lunch.Controller.RestaurantActivity;
import com.example.go4lunch.Models.NearbySearch.Result;
import com.example.go4lunch.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;


public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {
    private ArrayList<Result> mRestaurantList;
    private LatLng mCurrentPosition;
    private float[] result = new float[3];

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder {
        private TextView mRestaurantName;
        private TextView mRestaurantLocation;
        private TextView mRestaurantSchedule;
        private ImageView mRestaurantPictures;
        private TextView mRestaurantDistance;
        private TextView mWorkmateNumber;
        private RatingBar mRestaurantRatingBar;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            mRestaurantName = itemView.findViewById(R.id.restaurant_fragment_name);
            mRestaurantLocation = itemView.findViewById(R.id.restaurant_fragment_location);
            mRestaurantSchedule = itemView.findViewById(R.id.restaurant_fragment_schedule);
            mRestaurantPictures = itemView.findViewById(R.id.restaurant_fragment_picture);
            mRestaurantDistance = itemView.findViewById(R.id.restaurant_fragment_distance);
            mWorkmateNumber = itemView.findViewById(R.id.restaurant_fragment_workmates_number);
            mRestaurantRatingBar = itemView.findViewById(R.id.restaurant_fragment_ratingBar);

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
        holder.mWorkmateNumber.setText("2");

        Location.distanceBetween(mCurrentPosition.latitude, mCurrentPosition.longitude, currentRestaurant.getGeometry().getLocation().getLat(), currentRestaurant.getGeometry().getLocation().getLng(), result);
        int distance = (int) result[0];
        String currentDistance = distance + "m";
        holder.mRestaurantDistance.setText(currentDistance);

        float mRating = (float) (currentRestaurant.getRating() / 5) * 3;
        holder.mRestaurantRatingBar.setRating(mRating);

        if (currentRestaurant.getPhotos() != null && currentRestaurant.getPhotos().size() > 0) {
            Glide.with(holder.mRestaurantPictures.getContext())
                    .load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=200&maxheight=200&photoreference=" + currentRestaurant.getPhotos().get(0).getPhotoReference() + "&key=AIzaSyD6y_8l1WeKKDk0dOHxxgL_ybA4Lmjc1Cc")
                    .into(holder.mRestaurantPictures);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), RestaurantActivity.class);
            intent.putExtra("PLACE_ID", placeId);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mRestaurantList.size();
    }

    public void updatePlace(final ArrayList<Result> restaurantList, LatLng latLng) {
        this.mRestaurantList = restaurantList;
        this.mCurrentPosition = latLng;
        notifyDataSetChanged();
        Log.d("updatePlace", "currentPosition = " + mCurrentPosition + "Latlng =" + latLng);
    }
}
