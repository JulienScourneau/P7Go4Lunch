package com.example.go4lunch.Network;

import com.example.go4lunch.Models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import static com.example.go4lunch.Utils.Constants.COLLECTION_NAME;

public class UserHelper {

    public static FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public static Boolean isCurrentUserLogged() {
        return (getCurrentUser() != null);
    }

    public static CollectionReference getUsersCollection() {
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    public static Task<Void> createUser(String uid, String userName, String userMail, String urlPicture) {
        User userToCreate = new User(uid, userName, userMail, urlPicture);
        return UserHelper.getUsersCollection().document(uid).set(userToCreate);
    }

    public static Task<DocumentSnapshot> getUser(String uid) {
        return UserHelper.getUsersCollection().document(uid).get();
    }

    public static Task<Void> updateRestaurantId(String restaurantId, String uid) {
        return UserHelper.getUsersCollection().document(uid).update("userRestaurantId", restaurantId);
    }

    public static void deleteUser(String uid) {
        UserHelper.getUsersCollection().document(uid).delete();
    }

    public static void getUserList(OnSuccessListener<QuerySnapshot> listener) {
        getUsersCollection().get().addOnSuccessListener(listener);
    }
}
