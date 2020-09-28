package com.example.go4lunch.Controler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.go4lunch.R;
import com.example.go4lunch.Utils.TestList;
import com.example.go4lunch.View.Adapter.WorkmatesAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RestaurantActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private Button mCallButton, mLikeButton, mWebsiteButton;
    private FloatingActionButton mLunchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_activity);
        initView();

        mRecyclerView = findViewById(R.id.restaurant_activity_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new WorkmatesAdapter(TestList.getFakeUserList()));
    }

    private void initView() {
        mCallButton = findViewById(R.id.restaurant_call);
        mCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Call",Toast.LENGTH_SHORT).show();
            }
        });
        mLikeButton = findViewById(R.id.restaurant_like);
        mLikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Like",Toast.LENGTH_SHORT).show();
            }
        });
        mWebsiteButton = findViewById(R.id.restaurant_website);
        mWebsiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Website",Toast.LENGTH_SHORT).show();
            }
        });

        mLunchButton = findViewById(R.id.add_lunch_button);
        mLunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Lunch",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
