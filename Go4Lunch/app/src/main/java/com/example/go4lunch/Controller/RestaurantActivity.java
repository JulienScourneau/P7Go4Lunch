package com.example.go4lunch.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.go4lunch.Models.Details.PlaceDetails;
import com.example.go4lunch.R;
import com.example.go4lunch.Utils.TestList;
import com.example.go4lunch.View.Adapter.WorkmatesAdapter;
import com.example.go4lunch.ViewModel.PlacesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RestaurantActivity extends AppCompatActivity {
    private String mPlaceId;
    private RecyclerView mRecyclerView;
    private Button mCallButton, mLikeButton, mWebsiteButton;
    private FloatingActionButton mLunchButton;
    private PlacesViewModel viewModel;
    private PlaceDetails placeDetails;
    private TextView mRestaurantName;
    private TextView mRestaurantLocation;
    private ImageView mRestaurantPicture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_activity);
        Bundle bundle = getIntent().getExtras();
        mPlaceId = bundle.getString("PLACE_ID");

        configureViewModel();
        getPlaceDetails();
        setUpRecyclerView();
        initView();
        setUpListener();

    }

    private void initView() {

        mRestaurantName = findViewById(R.id.restaurant_activity_name);
        mRestaurantLocation = findViewById(R.id.restaurant_activity_location);
        mRestaurantPicture = findViewById(R.id.restaurant_activity_picture);
    }

    private void setUpRecyclerView() {
        mRecyclerView = findViewById(R.id.restaurant_activity_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new WorkmatesAdapter(TestList.getFakeUserList()));
    }

    private void setUpListener() {
        mCallButton = findViewById(R.id.restaurant_call);
        mLikeButton = findViewById(R.id.restaurant_like);
        mWebsiteButton = findViewById(R.id.restaurant_website);
        mLunchButton = findViewById(R.id.add_lunch_button);

        mCallButton.setOnClickListener(v -> {

            if (placeDetails != null) {
                if (placeDetails.getResult().getInternationalPhoneNumber() != null) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + placeDetails.getResult().getInternationalPhoneNumber()));
                    startActivity(intent);
                    Log.d("CallBtn", "InternationalPhoneNumber");

                } else if (placeDetails.getResult().getFormattedPhoneNumber() != null) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + placeDetails.getResult().getFormattedPhoneNumber()));
                    startActivity(intent);
                    Log.d("CallBtn", "FormattedPhoneNumber");

                }
            }
            Toast.makeText(getApplicationContext(), getResources().getText(R.string.call_btn_unavailable), Toast.LENGTH_SHORT).show();
            Log.d("CallBtn", "NoPhoneNumber");

        });

        mLikeButton.setOnClickListener(v -> Toast.makeText(getApplicationContext(), "Like", Toast.LENGTH_SHORT).show());

        mWebsiteButton.setOnClickListener(v -> {


            if (placeDetails.getResult().getWebsite() != null) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(placeDetails.getResult().getWebsite()));
                startActivity(intent);

            } else {
                Toast.makeText(getApplicationContext(), getResources().getText(R.string.website_btn_unavailable), Toast.LENGTH_SHORT).show();
            }
        });

        mLunchButton.setOnClickListener(v -> Toast.makeText(getApplicationContext(), "Lunch", Toast.LENGTH_SHORT).show());

    }

    private void configureViewModel() {
        this.viewModel = new ViewModelProvider(this).get(PlacesViewModel.class);
    }

    public void getPlaceDetails() {
        this.viewModel.getDetailPlaces(getUrl()).observe(this, this::upDatePlaceDetails);
    }

    private void upDatePlaceDetails(PlaceDetails placeDetails) {
        this.placeDetails = placeDetails;
        mRestaurantName.setText(placeDetails.getResult().getName());
        mRestaurantLocation.setText(placeDetails.getResult().getVicinity());

        if (placeDetails.getResult().getPhotos() != null && placeDetails.getResult().getPhotos().size() > 0) {
            Glide.with(mRestaurantPicture.getContext())
                    .load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=2000&maxheight=2000&photoreference="
                            + placeDetails.getResult().getPhotos().get(0)
                            .getPhotoReference() + "&key=AIzaSyD6y_8l1WeKKDk0dOHxxgL_ybA4Lmjc1Cc")
                    .into(mRestaurantPicture);
        }

    }

    public String getUrl() {
        StringBuilder url = new StringBuilder();
        url.append("details/json?place_id=");
        url.append(mPlaceId);
        url.append("&key=AIzaSyD6y_8l1WeKKDk0dOHxxgL_ybA4Lmjc1Cc");
        Log.d("getUrlDetails", url.toString());

        return url.toString();
    }
}
