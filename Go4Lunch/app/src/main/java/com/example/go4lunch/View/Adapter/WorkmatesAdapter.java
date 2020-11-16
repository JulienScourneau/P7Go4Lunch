package com.example.go4lunch.View.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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

        holder.mUserText.setText(currentUser.getUserName());

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
