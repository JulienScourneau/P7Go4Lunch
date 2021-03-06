package com.example.go4lunch.Models;

public class User {

    private String mUid;
    private String mUserName;
    private String mUserMail;
    private String mUserPicture;
    private String mUserRestaurantId;
    private String mUserRestaurantName;

    public User(String uid, String userName, String userMail, String userPicture) {
        this.mUid = uid;
        this.mUserName = userName;
        this.mUserMail = userMail;
        this.mUserPicture = userPicture;
        this.mUserRestaurantId = null;
        this.mUserRestaurantName = null;
    }

    public User() {
    }

    public String getUid() {
        return mUid;
    }

    public String getUserName() {
        return mUserName;
    }

    public String getUserMail() {
        return mUserMail;
    }

    public String getUserPicture() {
        return mUserPicture;
    }

    public String getUserRestaurantId() {
        return mUserRestaurantId;
    }

    public String getUserRestaurantName() {
        return mUserRestaurantName;
    }

    public void setUid(String mUid) {
        this.mUid = mUid;
    }

    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public void setUserMail(String mUserMail) {
        this.mUserMail = mUserMail;
    }

    public void setUserPicture(String mUserPicture) {
        this.mUserPicture = mUserPicture;
    }

    public void setUserRestaurantId(String mUserRestaurantId) {
        this.mUserRestaurantId = mUserRestaurantId;
    }

    public void setUserRestaurantName(String mUserRestaurantName) {
        this.mUserRestaurantName = mUserRestaurantName;
    }
}
