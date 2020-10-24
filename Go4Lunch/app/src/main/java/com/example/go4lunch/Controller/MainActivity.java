package com.example.go4lunch.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.go4lunch.R;
import com.example.go4lunch.View.Fragment.ListViewFragment;
import com.example.go4lunch.View.Fragment.MapViewFragment;
import com.example.go4lunch.View.Fragment.WorkmatesFragment;
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Arrays;

import static com.example.go4lunch.Utils.Constants.RC_SIGN_IN;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawer;
    private Toolbar mToolbar;
    private BottomNavigationView mBottomNav;
    private NavigationView mNavigationView;
    private Boolean mLunchSelected = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        signInActivity();
    }

    private void initView() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MapViewFragment()).commit();
        mBottomNav = findViewById(R.id.bottom_navigation_view);
        mBottomNav.setOnNavigationItemSelectedListener(navListener);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mDrawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();
        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.drawer_lunch_icon:
                if (mLunchSelected) {
                    Intent restaurantIntent = new Intent(this, RestaurantActivity.class);
                    restaurantIntent.putExtra("PLACE_ID", "Add_ID");
                    startActivity(restaurantIntent);
                }
                Toast.makeText(this, "Select your lunch", Toast.LENGTH_SHORT).show();
                break;
            case R.id.drawer_settings_icon:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                break;
            case R.id.drawer_logout_icon:
                finish();
                break;
        }
        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = new MapViewFragment();
                    switch (item.getItemId()) {
                        case R.id.nav_map:
                            selectedFragment = new MapViewFragment();
                            break;
                        case R.id.nav_restaurant_list:
                            selectedFragment = new ListViewFragment();
                            break;
                        case R.id.nav_workmates_list:
                            selectedFragment = new WorkmatesFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };


    private void signInActivity() {
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

}