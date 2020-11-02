package com.example.go4lunch.Controller;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.go4lunch.R;
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

    }

    private void initView() {
        mSeekBar = findViewById(R.id.settings_seekBar);
        mNotificationSwitch = findViewById(R.id.notification_switch);
        mNotificationSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mNotification) {
                    mNotification = true;
                    Toast.makeText(getApplicationContext(), "Notification on", Toast.LENGTH_SHORT).show();
                } else {
                    mNotification = false;
                    Toast.makeText(getApplicationContext(), "Notification off", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mDistanceSettings = findViewById(R.id.distance_settings_number);
        mDeleteBtn = findViewById(R.id.delete_account_btn);
        mDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setUpSeekBar() {

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                mRadius = (progress + 1) * 1000;
                String radiusSettings = progress + 1 + " " + getResources().getString(R.string.distance_settings_activity_txt);
                mDistanceSettings.setText(radiusSettings);
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

    private int calculateProgress(int value, int MIN, int MAX) {
        return (200 * (value - MIN)) / (MAX - MIN);
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("RadiusSetting", mRadius);
        editor.apply();
        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
    }

}
