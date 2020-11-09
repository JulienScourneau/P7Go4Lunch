package com.example.go4lunch.Models;

public class User {

    private String mUid;
    private String mUserName;
    private String mUserStatus;
    private int mUserAvatar;

    public User(String uid, String userName, String userStatus, int userAvatar) {
        this.mUid = uid;
        this.mUserName = userName;
        this.mUserAvatar = userAvatar;
        this.mUserStatus = userStatus;
    }

    public String getUid() {
        return mUid;
    }

    public String getUserName() {
        return mUserName;
    }

    public String getUserStatus() {
        return mUserStatus;
    }

    public int getUserAvatar() {
        return mUserAvatar;
    }

    public void setUid(String mUid) {
        this.mUid = mUid;
    }

    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public void setUserStatus(String mUserStatus) {
        this.mUserStatus = mUserStatus;
    }

    public void setUserAvatar(int mUserAvatar) {
        this.mUserAvatar = mUserAvatar;
    }
}
