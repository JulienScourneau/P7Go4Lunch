package com.example.go4lunch.View.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.R;
import com.example.go4lunch.Models.User;

import java.util.ArrayList;

public class WorkmatesAdapter extends RecyclerView.Adapter<WorkmatesAdapter.WorkmatesViewHolder> {
    private ArrayList<User> mUserList;

    public static class WorkmatesViewHolder extends RecyclerView.ViewHolder{

        private ImageView mUserAvatar;
        private TextView mUserStatus;

        public WorkmatesViewHolder(@NonNull View itemView) {
            super(itemView);
            mUserAvatar = itemView.findViewById(R.id.user_fragment_avatar);
            mUserStatus = itemView.findViewById(R.id.user_fragment_status);
        }
    }

    public WorkmatesAdapter(ArrayList<User> userList){
        this.mUserList = userList;
    }

    @NonNull
    @Override
    public WorkmatesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workmates_item_fragment,parent, false);
        return new WorkmatesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkmatesViewHolder holder, int position) {
        User currentUser = mUserList.get(position);

        holder.mUserAvatar.setImageResource(R.mipmap.ic_launcher_round);
        holder.mUserStatus.setText(currentUser.getUserStatus());
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }
}
