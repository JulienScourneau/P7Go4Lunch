package com.example.go4lunch.Utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserHelper {

    protected static FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public static Boolean isCurrentUserLogged() {
        return (getCurrentUser() !=null);
    }
}
