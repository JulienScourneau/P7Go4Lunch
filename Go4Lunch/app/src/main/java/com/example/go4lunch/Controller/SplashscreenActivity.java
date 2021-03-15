package com.example.go4lunch.Controller;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.go4lunch.Network.UserHelper;
import com.example.go4lunch.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;

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
                                new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                                new AuthUI.IdpConfig.Builder(AuthUI.TWITTER_PROVIDER).build()))
                        .setIsSmartLockEnabled(false, true)
                        .build(),
                RC_SIGN_IN);

    }

    private void createUserInFireStore() {
        if (UserHelper.getCurrentUser() != null) {

            String urlPicture = (UserHelper.getCurrentUser().getPhotoUrl() != null) ?
                    UserHelper.getCurrentUser().getPhotoUrl().toString() : null;
            String userName = UserHelper.getCurrentUser().getDisplayName();
            String uid = UserHelper.getCurrentUser().getUid();
            String userMail = UserHelper.getCurrentUser().getEmail();

            UserHelper.createUser(uid, userName, userMail, urlPicture).addOnFailureListener(e -> {
                Toast.makeText(getApplicationContext(), getString(R.string.error_unknown), Toast.LENGTH_LONG).show();
            });
        }
        Log.d("createUser", "Create user in FireStore");
    }

    private void handleResponseAfterSignIn(int requestCode, int resultCode, Intent data) {
        IdpResponse response = IdpResponse.fromResultIntent(data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                this.createUserInFireStore();
                startMainActivity();

                Log.d("handleResponse", "CreateUser");

            } else {
                signInActivity();
                Toast.makeText(this, "Please login", Toast.LENGTH_SHORT).show();
                Log.d("handleResponse", "SignIn");

                if (response == null) {
                    Toast.makeText(getApplicationContext(), R.string.error_authentication_canceled, Toast.LENGTH_SHORT).show();
                } else if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Toast.makeText(getApplicationContext(), R.string.error_no_internet, Toast.LENGTH_SHORT).show();
                } else if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    Toast.makeText(getApplicationContext(), R.string.error_unknown, Toast.LENGTH_SHORT).show();
                }
            }
            Log.d("handleResponse", "RequestCode " + requestCode);
            Log.d("handleResponse", "ResultCode " + resultCode);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        this.handleResponseAfterSignIn(requestCode, resultCode, data);
        Log.d("OnActivityResult", "Resultcode =" + requestCode);
    }
}