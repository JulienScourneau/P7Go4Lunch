package com.example.go4lunch.View;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.R;
import com.example.go4lunch.RestaurantActivity;
import com.example.go4lunch.models.Restaurant;

import java.util.ArrayList;


public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {
    private ArrayList<Restaurant> mRestaurantList;

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder{
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

    public RestaurantAdapter(ArrayList<Restaurant> restaurantList) {
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
        Restaurant currentRestaurant = mRestaurantList.get(position);

        holder.mRestaurantName.setText(currentRestaurant.getRestaurantName());
        holder.mRestaurantLocation.setText(currentRestaurant.getRestaurantLocation());
        holder.mRestaurantSchedule.setText(currentRestaurant.getRestaurantSchedule());
        holder.mRestaurantPictures.setImageResource(currentRestaurant.getRestaurantPictures());
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
}
