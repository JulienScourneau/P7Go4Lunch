package com.example.go4lunch.Network;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.go4lunch.Models.User;
import com.example.go4lunch.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Objects;
import java.util.Random;

public class FirebaseInstanceService extends FirebaseMessagingService {
    private User currentUser = null;
    private Boolean notificationSettings = null;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        loadData();
        super.onMessageReceived(remoteMessage);
        if (notificationSettings){
            if (remoteMessage.getData().isEmpty())
                getWorkmateList();
        }


    }

    private void showNotification(String body) {

        Log.d("showNotifications", "Notifications Body: " + body);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "example.go4lunch.Network.test";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Notifications", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableLights(true);
            if (notificationManager != null)
                notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(getString(R.string.title_notifications))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setContentInfo("Info");

        if (notificationManager != null)
            notificationManager.notify(new Random().nextInt(), notificationBuilder.build());
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);

        Log.d("tokenFirebase", "get new FireBaseToken: " + s);
    }

    private void getWorkmateList() {
        Log.d("getWorkmateList", "getWorkmateList");
        StringBuilder userList = new StringBuilder();

        UserHelper.getUser(UserHelper.getCurrentUser().getUid()).addOnSuccessListener(documentSnapshot -> {
            currentUser = documentSnapshot.toObject(User.class);

            UserHelper.getUserRestaurantList(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    Log.d("getWorkmateList", "Enter onSuccess");
                    for (int i = 0; i < queryDocumentSnapshots.getDocuments().size(); i++) {
                        Log.d("getWorkmateList", "Enter For");

                        if (queryDocumentSnapshots.getDocuments().isEmpty()) {
                            userList.append(getString(R.string.nobody_notifications));
                        } else {
                            if (!Objects.equals(queryDocumentSnapshots.getDocuments().get(i).get("uid"), currentUser.getUid())) {
                                Log.d("getWorkmateList", "currentUser: " + currentUser.getUid());
                                if (i == queryDocumentSnapshots.size() - 1) {
                                    userList.append(queryDocumentSnapshots.getDocuments().get(i).get("userName"));
                                } else {
                                    userList.append(queryDocumentSnapshots.getDocuments().get(i).get("userName"));
                                    userList.append(", ");
                                }
                            }
                        }
                    }
                    Log.d("getWorkmateList", "userList: " + userList.toString());
                    String body = getString(R.string.body_notifications) +
                            currentUser.getUserRestaurantName() +
                            getString(R.string.with_notifications) + userList;

                    showNotification(body);
                }
            }, currentUser.getUserRestaurantId());
        });
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("SharedPrefs", Context.MODE_PRIVATE);
        notificationSettings = sharedPreferences.getBoolean("NotificationSetting",true);

    }

}
