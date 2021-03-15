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

import java.util.Map;

import static com.example.go4lunch.Utils.Constants.ID_RESTAURANT_COLLECTION;
import static com.example.go4lunch.Utils.Constants.LIKE_COLLECTION;
import static com.example.go4lunch.Utils.Constants.LIKE_FIELD;
import static com.example.go4lunch.Utils.Constants.USERS_COLLECTION;
import static com.example.go4lunch.Utils.Constants.USER_RESTAURANT_COLLECTION;

public class UserHelper {

    public static FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public static Boolean isCurrentUserLogged() {
        return (getCurrentUser() != null);
    }

    public static CollectionReference getUsersCollection() {
        return FirebaseFirestore.getInstance().collection(USERS_COLLECTION);
    }

    public static CollectionReference getLikeCollection(String uid) {
        return getUsersCollection().document(uid).collection(LIKE_COLLECTION);
    }

    public static Task<Void> createUser(String uid, String userName, String userMail, String urlPicture) {
        User userToCreate = new User(uid, userName, userMail, urlPicture);
        return UserHelper.getUsersCollection().document(uid).set(userToCreate);
    }

    public static Task<Void> createLikeList(String uid, String restaurantId, Map<String, Object> like) {
        return UserHelper.getLikeCollection(uid).document(restaurantId).set(like);
    }

    public static Task<DocumentSnapshot> getUser(String uid) {
        return UserHelper.getUsersCollection().document(uid).get();
    }

    public static Task<DocumentSnapshot> getLike(String uid, String restaurantId) {
        return UserHelper.getLikeCollection(uid).document(restaurantId).get();
    }

    public static void updateRestaurantId(String restaurantId, String uid) {
        UserHelper.getUsersCollection().document(uid).update(ID_RESTAURANT_COLLECTION, restaurantId);
    }

    public static void updateRestaurantName(String restaurantName, String uid) {
        UserHelper.getUsersCollection().document(uid).update(USER_RESTAURANT_COLLECTION, restaurantName);
    }

    public static void updateRestaurantLike(String uid, String restaurantId, Boolean restaurantLike) {
        UserHelper.getLikeCollection(uid).document(restaurantId).update(LIKE_FIELD, restaurantLike);
    }

    public static void deleteUser(String uid) {
        UserHelper.getUsersCollection().document(uid).delete();
    }

    public static void getUserList(OnSuccessListener<QuerySnapshot> listener) {
        getUsersCollection().get().addOnSuccessListener(listener);
    }

    public static void getUserRestaurantList(OnSuccessListener<QuerySnapshot> listener, String restaurantId) {
        getUsersCollection().whereEqualTo(ID_RESTAURANT_COLLECTION, restaurantId).get().addOnSuccessListener(listener);
    }

}
