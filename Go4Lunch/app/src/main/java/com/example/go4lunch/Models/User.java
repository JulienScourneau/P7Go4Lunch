package com.example.go4lunch.Models;

public class User {

    private String mUid;
    private String mUserName;
    private String mUserMail;
    private String mUserPicture;
    private Boolean mUserStatus;

    public User(String uid, String userName, String userMail, String userPicture) {
        this.mUid = uid;
        this.mUserName = userName;
        this.mUserMail = userMail;
        this.mUserPicture = userPicture;
    }

    public String getUid() {
        return mUid;
    }

    public String getUserName() {
        return mUserName;
    }

    public Boolean getUserStatus() {
        return mUserStatus;
    }

    public String getUserMail() {
        return mUserMail;
    }

    public String getUserPicture() {
        return mUserPicture;
    }

    public void setUid(String mUid) {
        this.mUid = mUid;
    }

    public void setUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public void setUserStatus(Boolean mUserStatus) {
        this.mUserStatus = mUserStatus;
    }

    public void setUserMail(String mUserMail) {
        this.mUserMail = mUserMail;
    }

    public void setUserPicture(String mUserPicture) {
        this.mUserPicture = mUserPicture;
    }
}
