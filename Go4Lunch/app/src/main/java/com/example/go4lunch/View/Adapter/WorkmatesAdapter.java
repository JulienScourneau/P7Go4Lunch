package com.example.go4lunch.View.Adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.Controller.RestaurantActivity;
import com.example.go4lunch.R;
import com.example.go4lunch.Models.User;

import java.util.ArrayList;

public class WorkmatesAdapter extends RecyclerView.Adapter<WorkmatesAdapter.WorkmatesViewHolder> {
    private ArrayList<User> mUserList;

    public static class WorkmatesViewHolder extends RecyclerView.ViewHolder {

        private ImageView mUserPicture;
        private TextView mUserText;

        public WorkmatesViewHolder(@NonNull View itemView) {
            super(itemView);
            mUserPicture = itemView.findViewById(R.id.user_fragment_picture);
            mUserText = itemView.findViewById(R.id.user_fragment_status);
        }
    }

    public WorkmatesAdapter(ArrayList<User> userList) {
        this.mUserList = userList;
    }

    @NonNull
    @Override
    public WorkmatesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workmates_item_fragment, parent, false);
        return new WorkmatesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkmatesViewHolder holder, int position) {
        User currentUser = mUserList.get(position);

        StringBuilder userText = new StringBuilder();
        userText.append(currentUser.getUserName());
        userText.append(" ");

        if (currentUser.getUserRestaurantId() != null) {
            userText.append(holder.itemView.getContext().getString(R.string.workmate_select_lunch));
            holder.mUserText.setHint("");
            holder.mUserText.setText(userText.toString());
            holder.itemView.setClickable(true);
            Log.d("workmateAdapter", "UserName: " + currentUser.getUserName());
        } else {
            userText.append(holder.itemView.getContext().getString(R.string.workmate_not_select_lunch));
            holder.mUserText.setText("");
            holder.mUserText.setHint(userText.toString());
            holder.itemView.setClickable(false);
        }

        if (holder.itemView.isClickable()) {
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), RestaurantActivity.class);
                intent.putExtra("PLACE_ID", currentUser.getUserRestaurantId());
                v.getContext().startActivity(intent);
            });
        }

        if (currentUser.getUserPicture() != null) {
            Glide.with(holder.mUserPicture.getContext())
                    .load(currentUser.getUserPicture())
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.mUserPicture);
        }

    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }
}
