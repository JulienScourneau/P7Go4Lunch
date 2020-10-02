package com.example.go4lunch.Controler;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.go4lunch.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingsActivity extends AppCompatActivity {
    private SwitchMaterial mNotificationSwitch;
    private TextView mDistanceSettings;
    private SeekBar mSeekBar;
    private Button mDeleteBtn;
    private int mRadius;
    private final int MIN = 50, MAX = 200, STEP = 10;

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

            }
        });
        mDistanceSettings = findViewById(R.id.distance_settings_number);
        mDeleteBtn = findViewById(R.id.delete_account_btn);
        mDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // deleteUserFromFirebase();
            }
        });
    }

    private void setUpSeekBar() {
        mSeekBar.setMax(200);
        mSeekBar.setProgress(calculateProgress(50, 50, 200));
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                float value = Math.round((progress * (MAX - MIN)) / 100);
                mRadius = (((int) value + MIN) / STEP) * STEP;
                mDistanceSettings.setText(mRadius + "\""+ getResources().getString(R.string.distance_settings_activity_txt));
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
        SharedPreferences sharedPreferences = getSharedPreferences("SettingsSharedPrefs",MODE_PRIVATE);
        sharedPreferences.edit().putInt("RadiusSettings",mRadius).apply();
    }



    //private void deleteUserFromFirebase() {
    //    if (this.getCurrentUser() != null) {
    //        AuthUI.getInstance()
    //                .delete(this);
    //    }
    //}

    //@Nullable
    //protected FirebaseUser getCurrentUser() {
    //    return FirebaseAuth.getInstance().getCurrentUser();
    //}
}
