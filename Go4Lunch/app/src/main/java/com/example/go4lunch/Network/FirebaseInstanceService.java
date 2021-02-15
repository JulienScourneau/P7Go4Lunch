package com.example.go4lunch.Network;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.go4lunch.Models.User;
import com.example.go4lunch.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.Random;

public class FirebaseInstanceService extends FirebaseMessagingService {
    private User currentUser = null;
    private String userListNotifications;
    private ArrayList<User> userList = new ArrayList<>();

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getData().isEmpty())
            getWorkmateList();

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
                .setContentText(body)
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

        UserHelper.getUser(UserHelper.getCurrentUser().getUid()).addOnSuccessListener(documentSnapshot -> {
            currentUser = documentSnapshot.toObject(User.class);

            UserHelper.getUserRestaurantList(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    for (int i = 0; i < queryDocumentSnapshots.getDocuments().size(); i++) {
                        queryDocumentSnapshots.getDocuments().get(i).get("userName");
                    }


                    String body = getString(R.string.body_notifications) +
                            currentUser.getUserRestaurantName() +
                            getString(R.string.with_notifications) + "userListNotifications";

                    showNotification(body);
                }
            }, currentUser.getUserRestaurantId());
        });
    }

    //UserHelper.getUser(UserHelper.getCurrentUser().getUid()).addOnSuccessListener(documentSnapshot -> {
    //    User currentUser = documentSnapshot.toObject(User.class);

    //    UserHelper.getUserList(queryDocumentSnapshots -> {
    //        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
    //            if (currentUser != null)
    //                if (Objects.equals(document.get("userRestaurantId"), currentUser.getUserRestaurantId())) {

    //                    workmateList.append(document.get("userName"));
    //                    if (queryDocumentSnapshots.iterator().hasNext()) {
    //                        workmateList.append(", ");
    //                        workmateList.append(document.get("userName"));
    //                    }
    //                    Log.d("getUserList","userName: " + document.get("userName"));

    //if (currentUser != null) {
    //    bodyNotification.append(getString(R.string.body_notifications));
    //    bodyNotification.append("currentUser.getUserRestaurantName()");
    //    bodyNotification.append(getString(R.string.with_notifications));
    //    bodyNotification.append(workmateList.toString());
    //    Log.d("bodyNotifications", bodyNotification.toString());
    //}


    //UserHelper.getUserList(new OnSuccessListener<QuerySnapshot>() {
    //    @Override
    //    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
    //        for (int i = 0; i < queryDocumentSnapshots.getDocuments().size(); i++) {
    //            queryDocumentSnapshots.getDocuments().get(i).toObject()
    //            Log.d("document", "document: " + queryDocumentSnapshots.getDocuments().size() + " size of i: " + i);
    //        }

    //        String body = getString(R.string.body_notifications) +
    //                " currentUser.getUserRestaurantName() " +
    //                getString(R.string.with_notifications) + userList;
    //        showNotification(body);
    //    }

    //});
}
