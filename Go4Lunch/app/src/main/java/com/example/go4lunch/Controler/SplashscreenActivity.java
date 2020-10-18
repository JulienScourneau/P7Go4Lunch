package com.example.go4lunch.Controler;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.go4lunch.R;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashscreenActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_LOCATION = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        launchSplashScreen();
    }

    private void launchSplashScreen() {
        Thread thread = new Thread() {
            public void run() {
                try {
                    setUpSplashScreen();
                    sleep(2 * 1000);

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("LoginException", "Login exception catch");

                } finally {
                    Intent intent = new Intent(SplashscreenActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }
            }
        };
        thread.start();
    }

    private void setUpSplashScreen() {
        EasySplashScreen config = new EasySplashScreen(SplashscreenActivity.this)
                .withFullScreen()
                .withSplashTimeOut(2000)
                .withBackgroundColor(Color.parseColor("#353535"))
                .withAfterLogoText("Find a nice restaurant and invite your co-worker for lunch!")
                .withLogo(R.drawable.go4lunch_text_icon);

        config.getAfterLogoTextView().setTextColor(Color.WHITE);
        config.getAfterLogoTextView().setTextSize(12);

        View easySplashScreen = config.create();
        setContentView(easySplashScreen);
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, REQUEST_CODE_LOCATION);
        } else {
            Log.e("PermissionCheck", "PERMISSION GRANTED");
        }
    }

}