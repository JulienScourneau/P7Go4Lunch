package com.example.go4lunch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.go4lunch.View.ListViewFragment;
import com.example.go4lunch.View.MapViewFragment;
import com.example.go4lunch.View.WorkmatesFragment;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private DrawerLayout drawer;
    private Toolbar toolbar;
    private static final int RC_SIGN_IN = 123;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initView();
        signInActivity();
    }

    private void initView(){
        mapFragment = SupportMapFragment.newInstance();
        mapFragment.getMapAsync(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MapViewFragment()).commit();
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_view);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        drawer = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    FragmentManager fm = getSupportFragmentManager();
                    switch (item.getItemId()){
                        case R.id.nav_map:
                            selectedFragment = new MapViewFragment();
                            fm.beginTransaction().add(R.id.map,mapFragment).commit();
                            break;
                        case R.id.nav_restaurant_list:
                            selectedFragment = new ListViewFragment();
                            break;
                        case R.id.nav_workmates_list:
                            selectedFragment = new WorkmatesFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                    return true;
                }
            };

    private void signInActivity(){
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setTheme(R.style.LoginTheme)
                        .setLogo(R.drawable.go4lunch_icon)
                        .setAvailableProviders(Arrays.asList(
                                new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build(),
                                new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                        .setIsSmartLockEnabled(false, true)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
