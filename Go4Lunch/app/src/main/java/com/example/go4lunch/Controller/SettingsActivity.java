package com.example.go4lunch.Controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.go4lunch.Network.UserHelper;
import com.example.go4lunch.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingsActivity extends AppCompatActivity {
    private SwitchMaterial mNotificationSwitch;
    private TextView mDistanceSettings;
    private SeekBar mSeekBar;
    private Button mDeleteBtn;
    private int mRadius = 50;
    private final int MIN = 50, MAX = 200, STEP = 10;
    private static final String SHARED_PREF = "SharedPrefs";
    private boolean mNotification = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        initView();
        setUpSeekBar();
        setUpListener();

    }

    private void initView() {
        mSeekBar = findViewById(R.id.settings_seekBar);
        mNotificationSwitch = findViewById(R.id.notification_switch);
        mDistanceSettings = findViewById(R.id.distance_settings_number);
        mDeleteBtn = findViewById(R.id.delete_account_btn);

    }

    private void setUpListener() {
        mDeleteBtn.setOnClickListener(v -> {
            deleteUserAccountDialog();
            Log.d("deleteAccount", "Delete account listener");
        });

        mNotificationSwitch.setOnClickListener(v -> {
            if (!mNotification) {
                mNotification = true;
                Toast.makeText(getApplicationContext(), "Notification on", Toast.LENGTH_SHORT).show();
                saveData();
                Log.d("notificationSwitch", "Notif ON");
            } else {
                mNotification = false;
                Toast.makeText(getApplicationContext(), "Notification off", Toast.LENGTH_SHORT).show();
                saveData();
                Log.d("notificationSwitch", "Notif OFF");
            }
        });
    }

    private void setUpSeekBar() {



        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String radiusSettings;

                switch (progress) {

                    case 0:
                        mRadius = 100;
                        radiusSettings = "500" + " " + "m";
                        mDistanceSettings.setText(radiusSettings);
                        Log.d("SeekBar","case: 0");
                        break;
                    case 1:
                        mRadius = 1000;
                        radiusSettings = "1" + " " + "km";
                        mDistanceSettings.setText(radiusSettings);
                        Log.d("SeekBar","case: 1");
                        break;
                    case 2:
                        mRadius = 2000;
                        radiusSettings = "2" + " " + "km";
                        mDistanceSettings.setText(radiusSettings);
                        Log.d("SeekBar","case: 2");
                        break;
                    case 3:
                        mRadius = 3000;
                        radiusSettings = "3" + " " + "km";
                        mDistanceSettings.setText(radiusSettings);
                        Log.d("SeekBar","case: 3");
                        break;
                    case 4:
                        mRadius = 4000;
                        radiusSettings = "4" + " " + "km";
                        mDistanceSettings.setText(radiusSettings);
                        Log.d("SeekBar","case: 4");
                        break;
                    case 5:
                        mRadius = 5000;
                        radiusSettings = "5" + " " + "km";
                        mDistanceSettings.setText(radiusSettings);
                        Log.d("SeekBar","case: 5");
                        break;
                    case 6:
                        mRadius = 10000;
                        radiusSettings = "10" + " " + "km";
                        mDistanceSettings.setText(radiusSettings);
                        Log.d("SeekBar","case: 6");
                        break;
                    case 7:
                        mRadius = 15000;
                        radiusSettings = "15" + " " + "km";
                        mDistanceSettings.setText(radiusSettings);
                        Log.d("SeekBar","case: 7");
                        break;
                    case 8:
                        mRadius = 20000;
                        radiusSettings = "20" + " " + "km";
                        mDistanceSettings.setText(radiusSettings);
                        Log.d("SeekBar","case: 8");
                        break;
                    case 9:
                        mRadius = 25000;
                        radiusSettings = "25" + " " + "km";
                        mDistanceSettings.setText(radiusSettings);
                        Log.d("SeekBar","case: 9");
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                saveData();
            }
        });
    }

    private void deleteUserAccountDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.delete_account_dialog_warning)
                .setMessage(R.string.delete_account_dialog_message)
                .setPositiveButton(R.string.delete_account_dialog_yes_btn, (dialog, which) -> {

                    Log.d("deleteAccount", "positiveBtn delete account");
                    deleteUserAccount();

                })
                .setNegativeButton(R.string.delete_account_dialog_no_btn, (dialog, which) -> {
                })
                .show();
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("RadiusSetting", mRadius);
        editor.putBoolean("NotificationSetting", mNotification);
        editor.apply();
        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
        Log.d("saveData","Radius: " + mRadius + " Notifications: " + mNotification);
    }

    public void deleteUserAccount() {
        UserHelper.deleteUser(UserHelper.getCurrentUser().getUid());

        AuthUI.getInstance()
                .delete(this)
                .addOnSuccessListener(this, aVoid -> {
                    Log.d("deleteAccount", "onSuccess delete account");

                    Intent intent = new Intent(this, SplashscreenActivity.class);
                    startActivity(intent);
                    finish();
                });

        Log.d("deleteAccount", "Delete user account Method");
    }

}
