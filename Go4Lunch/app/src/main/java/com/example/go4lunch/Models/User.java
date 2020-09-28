package com.example.go4lunch.Models;

public class User {

    private int mUserAvatar;
    private String mUserStatus;

    public User(int userAvatar, String userStatus){
        this.mUserAvatar = userAvatar;
        this.mUserStatus = userStatus;
    }

    public int getUserAvatar() {
        return mUserAvatar;
    }

    public String getUserStatus() {
        return mUserStatus;
    }

    public void setUserAvatar(int mUserAvatar) {
        this.mUserAvatar = mUserAvatar;
    }

    public void setUserStatus(String mUserStatus) {
        this.mUserStatus = mUserStatus;
    }
}
