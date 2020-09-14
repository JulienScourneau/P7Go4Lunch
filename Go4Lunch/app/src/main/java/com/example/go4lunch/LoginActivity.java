package com.example.go4lunch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import gr.net.maroulis.library.EasySplashScreen;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginActivity();
    }

    private void loginActivity() {
        Thread thread = new Thread() {
            public void run() {
                try {
                    setUpSplashScreen();
                    sleep(2 * 1000);

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("LoginException", "Login exception catch");

                } finally {

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        thread.start();
    }

    private void setUpSplashScreen() {
        EasySplashScreen config = new EasySplashScreen(LoginActivity.this)
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


    protected FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    protected Boolean isCurrentUserLogged() {
        return (this.getCurrentUser() != null);
    }
}