package com.example.go4lunch.Controller;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.BuildConfig;
import com.example.go4lunch.Models.Details.PlaceDetails;
import com.example.go4lunch.Models.User;
import com.example.go4lunch.Network.UserHelper;
import com.example.go4lunch.R;
import com.example.go4lunch.View.Adapter.WorkmatesAdapter;
import com.example.go4lunch.ViewModel.PlacesViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
    private ArrayList<User> mWorkMate = new ArrayList<>();
    private Boolean mRestaurantLike = false;
    private Drawable mLikeDrawable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_activity);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mPlaceId = bundle.getString("PLACE_ID");
            Log.d("placeId", "Place id: " + mPlaceId);
        }
        configureViewModel();
        getPlaceDetails();
        setUpRecyclerView();
        initView();
    }

    private void initView() {

        mRestaurantName = findViewById(R.id.restaurant_activity_name);
        mRestaurantLocation = findViewById(R.id.restaurant_activity_location);
        mRestaurantPicture = findViewById(R.id.restaurant_activity_picture);
        mCallButton = findViewById(R.id.restaurant_call);
        mLikeButton = findViewById(R.id.restaurant_like);
        mWebsiteButton = findViewById(R.id.restaurant_website);
        mLunchButton = findViewById(R.id.add_lunch_button);
        setUpListener();
    }

    private void setUpRecyclerView() {
        mRecyclerView = findViewById(R.id.restaurant_activity_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWorkmateList();
        updateUI();
    }

    private void getWorkmateList() {
        UserHelper.getUserList(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {

                if (Objects.equals(document.get("userRestaurantId"), mPlaceId)) {
                    User user = document.toObject(User.class);
                    mWorkMate.add(user);

                    Log.d("getWorkmateList", "workmate: " + user.getUserName());
                } else {

                    Log.e("getWorkmateList", "Error getting document");
                }
            }
            mRecyclerView.setAdapter(new WorkmatesAdapter(mWorkMate));
        });
    }

    private void updateUI() {
        UserHelper.getUser(UserHelper.getCurrentUser().getUid()).addOnSuccessListener((OnSuccessListener<DocumentSnapshot>) documentSnapshot -> {
            User currentUser = documentSnapshot.toObject(User.class);

            if (currentUser != null) {
                if (currentUser.getUserRestaurantId() != null && currentUser.getUserRestaurantId().equals(mPlaceId)) {
                    mLunchButton.setImageResource(R.drawable.ic_baseline_remove_circle_24);
                } else {
                    mLunchButton.setImageResource(R.drawable.ic_add_icon_24dp);
                }
                updateLikeUi(currentUser);
            }
        });
    }

    private void setUpListener() {

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

        mLikeButton.setOnClickListener(v -> {
            if (mRestaurantLike) {
                updateLikeButton();
                UserHelper.updateRestaurantLike(UserHelper.getCurrentUser().getUid(), mPlaceId, false);

                Log.d("LikeBtn", "Remove like");
            } else {
                updateLikeButton();
                UserHelper.updateRestaurantLike(UserHelper.getCurrentUser().getUid(), mPlaceId, true);

                Log.d("LikeBtn", "Add like");
            }
        });

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

        mLunchButton.setOnClickListener(v -> UserHelper.getUser(UserHelper.getCurrentUser().getUid()).addOnSuccessListener(documentSnapshot -> {
            User currentUser = documentSnapshot.toObject(User.class);
            if (currentUser != null) {
                if (currentUser.getUserRestaurantId() == null) {
                    UserHelper.updateRestaurantId(mPlaceId, currentUser.getUid());
                    UserHelper.updateRestaurantName(placeDetails.getResult().getName(),currentUser.getUid());
                    mLunchButton.setImageResource(R.drawable.ic_baseline_remove_circle_24);
                    Toast.makeText(RestaurantActivity.this.getApplicationContext(), RestaurantActivity.this.getText(R.string.select_lunch_btn), Toast.LENGTH_SHORT).show();
                } else {
                    mLunchButton.setImageResource(R.drawable.ic_add_icon_24dp);
                    UserHelper.updateRestaurantId(null, currentUser.getUid());
                    UserHelper.updateRestaurantName(null,currentUser.getUid());
                    currentUser.setUserRestaurantId(null);
                    Toast.makeText(RestaurantActivity.this.getApplicationContext(), RestaurantActivity.this.getText(R.string.remove_lunch_btn), Toast.LENGTH_SHORT).show();
                }
            }
        }));
    }

    private void configureViewModel() {
        this.viewModel = new ViewModelProvider(this).get(PlacesViewModel.class);
    }

    public void getPlaceDetails() {
        this.viewModel.getDetailPlaces(getUrl()).observe(this, this::updatePlaceDetails);
    }

    private void updatePlaceDetails(PlaceDetails placeDetails) {
        this.placeDetails = placeDetails;
        mRestaurantName.setText(placeDetails.getResult().getName());
        mRestaurantLocation.setText(placeDetails.getResult().getVicinity());

        if (placeDetails.getResult().getPhotos() != null && placeDetails.getResult().getPhotos().size() > 0) {
            Glide.with(mRestaurantPicture.getContext())
                    .load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=2000&maxheight=2000&photoreference="
                            + placeDetails.getResult().getPhotos().get(0)
                            .getPhotoReference() + "&key=" + BuildConfig.PLACE_API_KEY)
                    .apply(RequestOptions.centerCropTransform())
                    .into(mRestaurantPicture);
        }
    }

    private void updateLikeButton() {
        if (mRestaurantLike) {
            mLikeDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_star_border_24, null);
            mRestaurantLike = false;
        } else {
            mLikeDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_icon_24dp, null);
            mRestaurantLike = true;
        }
        assert mLikeDrawable != null;
        mLikeDrawable = DrawableCompat.wrap(mLikeDrawable);
        DrawableCompat.setTint(mLikeDrawable, getResources().getColor(R.color.colorAccent));
        mLikeButton.setCompoundDrawablesWithIntrinsicBounds(null, mLikeDrawable, null, null);
    }

    private void updateLikeUi(User currentUser) {
        Map<String, Object> likeList = new HashMap<>();
        UserHelper.getLike(currentUser.getUid(), mPlaceId).addOnSuccessListener((OnSuccessListener<DocumentSnapshot>) documentSnapshot1 -> {
            if (documentSnapshot1.get("like") == null) {
                likeList.put("like", mRestaurantLike);
                UserHelper.createLikeList((String) currentUser.getUid(), mPlaceId, likeList).addOnFailureListener((OnFailureListener) e -> {
                    Toast.makeText(getApplicationContext(), R.string.error_unknown, Toast.LENGTH_SHORT).show();
                });

                Log.d("updateUiLike", "Create like doc");
            } else {
                Log.d("updateUiLike", "get like doc");
                mRestaurantLike = (Boolean) documentSnapshot1.get("like");
                mLikeDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_star_icon_24dp, null);
                if (mLikeDrawable != null) {
                    mLikeDrawable = DrawableCompat.wrap(mLikeDrawable);
                    DrawableCompat.setTint(mLikeDrawable, getResources().getColor(R.color.colorAccent));
                }
                mLikeButton.setCompoundDrawablesWithIntrinsicBounds(null, mLikeDrawable, null, null);
            }
        });
    }

    public String getUrl() {
        StringBuilder url = new StringBuilder();
        url.append("details/json?place_id=");
        url.append(mPlaceId);
        url.append("&key=");
        url.append(BuildConfig.PLACE_API_KEY);

        Log.d("getUrlDetails", url.toString());
        return url.toString();
    }
}
