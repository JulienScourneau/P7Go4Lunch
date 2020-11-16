package com.example.go4lunch.View.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.Models.User;
import com.example.go4lunch.Network.UserHelper;
import com.example.go4lunch.R;
import com.example.go4lunch.Utils.TestList;
import com.example.go4lunch.View.Adapter.RestaurantAdapter;
import com.example.go4lunch.View.Adapter.WorkmatesAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class WorkmatesFragment extends Fragment {
    private WorkmatesAdapter mWorkmatesAdapter;
    private RecyclerView mRecyclerView;
    private ArrayList<User> mWorkMate = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.workmates_fragment, container, false);

        mRecyclerView = view.findViewById(R.id.workmates_recyclerview);
        setUpRecyclerView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getWorkmateList();
    }

    private void getWorkmateList() {
        String currentUserUid = UserHelper.getCurrentUser().getUid();
        UserHelper.getUserList(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {

                    if (document.get("uid") != currentUserUid) {

                        Log.d("getworkmate", "uid :" + document.get("uid") + " currentUser :" + currentUserUid);

                        String uid = (String) document.get("uid");
                        String userName = (String) document.get("userName");
                        String userMail = (String) document.get("userMail");
                        String userPicture = (String) document.get("userPicture");

                        User user = new User(uid, userName, userMail, userPicture);
                        mWorkMate.add(user);

                    } else {
                        Log.e("getWorkmateList", "Error getting document", task.getException());
                    }
                }
            }
            mRecyclerView.setAdapter(new WorkmatesAdapter(mWorkMate));

        });
    }

    private void setUpRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mWorkmatesAdapter);

    }

}
