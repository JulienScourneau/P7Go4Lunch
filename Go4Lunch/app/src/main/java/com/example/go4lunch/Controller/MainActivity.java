package com.example.go4lunch.Controller;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.Models.User;
import com.example.go4lunch.Network.UserHelper;
import com.example.go4lunch.R;
import com.example.go4lunch.View.Fragment.ListViewFragment;
import com.example.go4lunch.View.Fragment.MapViewFragment;
import com.example.go4lunch.View.Fragment.WorkmatesFragment;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import static com.example.go4lunch.Utils.Constants.ERROR_DIALOG_REQUEST;
import static com.example.go4lunch.Utils.Constants.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static com.example.go4lunch.Utils.Constants.PERMISSIONS_REQUEST_ENABLE_GPS;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawer;
    private boolean mLocationPermissionGranted = false;
    private ImageView mUserPicture;
    private TextView mUserName;
    private TextView mUserMail;
    private MenuItem searchItem;
    private String search;
    private BaseFragment fragment;
    private FragmentManager fm = getSupportFragmentManager();
    private final Fragment mapFragment = new MapViewFragment();
    private final Fragment viewFragment = new ListViewFragment();
    private final Fragment workmatesFragment = new WorkmatesFragment();
    private Fragment selectedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        updateUI();
    }

    private void initView() {
        BottomNavigationView mBottomNav = findViewById(R.id.bottom_navigation_view);
        mBottomNav.setOnNavigationItemSelectedListener(navListener);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mDrawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        final View headerLayout = mNavigationView.getHeaderView(0);
        mUserName = headerLayout.findViewById(R.id.user_name_drawer);
        mUserMail = headerLayout.findViewById(R.id.user_mail_drawer);
        mUserPicture = headerLayout.findViewById(R.id.user_picture_drawer);

        selectedFragment = mapFragment;
        fm.beginTransaction().add(R.id.fragment_container, workmatesFragment, "3").hide(workmatesFragment).commit();
        fm.beginTransaction().add(R.id.fragment_container, viewFragment, "2").hide(viewFragment).commit();
        fm.beginTransaction().add(R.id.fragment_container, mapFragment, "1").commit();

    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);

        searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setQueryHint(getString(R.string.search_view_hint));
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search = newText;
                fragment = (BaseFragment) selectedFragment;
                if (selectedFragment != null && newText.length() >= 3) {

                    fragment.getSearchPlaceWithAutoComplete(newText);
                    Log.d("searchViewIfCondition", "Text: " + newText);
                } else {
                    fragment.clearSearchList();
                    Log.d("searchViewElseCondition", "Text: " + newText + "");
                }
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.drawer_lunch_icon:
                UserHelper.getUser(UserHelper.getCurrentUser().getUid()).addOnSuccessListener(documentSnapshot -> {
                    User currentUser = documentSnapshot.toObject(User.class);

                    if (currentUser != null) {
                        if (currentUser.getUserRestaurantId() == null) {
                            Toast.makeText(getApplicationContext(), R.string.user_lunch_select, Toast.LENGTH_SHORT).show();
                        } else {
                            Intent restaurantIntent = new Intent(getApplicationContext(), RestaurantActivity.class);
                            restaurantIntent.putExtra("PLACE_ID", currentUser.getUserRestaurantId());
                            startActivity(restaurantIntent);
                        }
                    }
                });
                break;
            case R.id.drawer_settings_icon:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                break;
            case R.id.drawer_logout_icon:
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnSuccessListener(this, aVoid -> {
                            Intent intent = new Intent(this, SplashscreenActivity.class);
                            startActivity(intent);
                            finish();
                        });
                break;
        }
        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {

                switch (item.getItemId()) {
                    case R.id.nav_map:

                        fm.beginTransaction().hide(selectedFragment).show(mapFragment).commit();
                        selectedFragment = mapFragment;

                        if (search != null && search.length() >= 3) {
                            fragment.clearSearchList();
                            fragment = (BaseFragment) selectedFragment;
                            fragment.getSearchPlaceWithAutoComplete(search);
                            Log.d("mapFragment", "get IF conditions");
                        }
                        searchItem.setVisible(true);
                        break;
                    case R.id.nav_restaurant_list:

                        fm.beginTransaction().hide(selectedFragment).show(viewFragment).commit();
                        selectedFragment = viewFragment;

                        if (search != null && search.length() >= 3) {
                            fragment.clearSearchList();
                            fragment = (BaseFragment) selectedFragment;
                            fragment.getSearchPlaceWithAutoComplete(search);
                            Log.d("mapFragment", "get IF conditions");
                        }
                        searchItem.setVisible(true);
                        break;

                    case R.id.nav_workmates_list:
                        fm.beginTransaction().hide(selectedFragment).show(workmatesFragment).commit();
                        searchItem.collapseActionView();
                        searchItem.setVisible(false);
                        selectedFragment = workmatesFragment;
                        break;
                }

                return true;
            };

    private void updateUI() {
        if (UserHelper.getCurrentUser() != null) {

            if (UserHelper.getCurrentUser().getPhotoUrl() != null) {
                Glide.with(this)
                        .load(UserHelper.getCurrentUser().getPhotoUrl())
                        .apply(RequestOptions.circleCropTransform())
                        .into(mUserPicture);
            }

            mUserName.setText(UserHelper.getCurrentUser().getDisplayName());
            mUserMail.setText(UserHelper.getCurrentUser().getEmail());

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (checkMapServices()) {
            getLocationPermission();
        }
    }

    private boolean checkMapServices() {
        if (isServicesOK()) {
            return isMapsEnabled();
        }
        return false;
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("This application requires GPS to work properly, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public boolean isMapsEnabled() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }

    private void getLocationPermission() {

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    public boolean isServicesOK() {
        Log.d("TAG", "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if (available == ConnectionResult.SUCCESS) {

            Log.d("TAG", "isServicesOK: Google Play Services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {

            Log.d("TAG", "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG", "onActivityResult: called.");
        if (requestCode == PERMISSIONS_REQUEST_ENABLE_GPS) {
            if (mLocationPermissionGranted) {

            } else {
                getLocationPermission();
            }
        }
    }

}
