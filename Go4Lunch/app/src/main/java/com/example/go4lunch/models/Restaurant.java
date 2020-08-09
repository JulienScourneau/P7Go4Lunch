package com.example.go4lunch.models;

public class Restaurant {

    private String mRestaurantName;
    private String mRestaurantLocation;
    private String mRestaurantSchedule;
    private int mRestaurantPicture;

    public Restaurant(String RestaurantName, String RestaurantLocation, String RestaurantSchedule, int RestaurantPicture) {
        this.mRestaurantName = RestaurantName;
        this.mRestaurantLocation = RestaurantLocation;
        this.mRestaurantSchedule = RestaurantSchedule;
        this.mRestaurantPicture = RestaurantPicture;
    }

    public String getRestaurantName() {
        return mRestaurantName;
    }

    public String getRestaurantLocation() {
        return mRestaurantLocation;
    }

    public String getRestaurantSchedule() {
        return mRestaurantSchedule;
    }

    public int getRestaurantPictures() {
        return mRestaurantPicture;
    }

    public void setRestaurantName(String mRestaurantName) {
        this.mRestaurantName = mRestaurantName;
    }

    public void setRestaurantLocation(String mRestaurantLocation) {
        this.mRestaurantLocation = mRestaurantLocation;
    }

    public void setRestaurantSchedule(String mRestaurantSchedule) {
        this.mRestaurantSchedule = mRestaurantSchedule;
    }

    public void setRestaurantPictures(int mRestaurantPictures) {
        this.mRestaurantPicture = mRestaurantPictures;
    }
}
