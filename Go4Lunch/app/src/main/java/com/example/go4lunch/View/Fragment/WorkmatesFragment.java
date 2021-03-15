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
import com.example.go4lunch.View.Adapter.WorkmatesAdapter;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Objects;

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
        if (!mWorkMate.isEmpty()) {
            mWorkMate.clear();
        }
        getWorkmateList();
    }

    private void getWorkmateList() {
        String currentUserUid = UserHelper.getCurrentUser().getUid();
        UserHelper.getUserList(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {

                if (!Objects.equals(document.get("uid"), currentUserUid)) {

                    User user = document.toObject(User.class);
                    mWorkMate.add(user);

                } else {
                    Log.e("getWorkmateList", "Error getting document");
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
