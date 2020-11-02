package com.example.go4lunch.Controller;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.go4lunch.R;
import com.example.go4lunch.Utils.UserHelper;
import com.firebase.ui.auth.AuthUI;

import java.util.Arrays;

import gr.net.maroulis.library.EasySplashScreen;

import static com.example.go4lunch.Utils.Constants.RC_SIGN_IN;

public class SplashscreenActivity extends AppCompatActivity {

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
                    Log.d("SplashScreenException", "SplashScreen exception catch");

                } finally {
                    if (UserHelper.isCurrentUserLogged()) {
                        startMainActivity();
                    } else {
                        signInActivity();
                    }
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
        Log.d("SplashScreen", "SplashScreen Display");
    }

    private void startMainActivity() {
        Intent intent = new Intent(SplashscreenActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        startMainActivity();
    }
}