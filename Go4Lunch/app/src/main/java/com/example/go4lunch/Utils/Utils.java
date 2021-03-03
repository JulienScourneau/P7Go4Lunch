package com.example.go4lunch.Utils;

import android.util.Log;

import com.example.go4lunch.BuildConfig;
import com.example.go4lunch.Models.Details.PlaceDetails;
import com.example.go4lunch.Models.NearbySearch.Result;
import com.google.android.gms.maps.model.LatLng;

public class Utils {

    public static Result changeDetailsResult(PlaceDetails searchPlace) {
        Result placeResult = new Result();
        placeResult.setPlaceId(searchPlace.getResult().getPlaceId());
        placeResult.setName(searchPlace.getResult().getName());
        placeResult.setPhotos(searchPlace.getResult().getPhotos());
        placeResult.setVicinity(searchPlace.getResult().getVicinity());
        placeResult.setGeometry(searchPlace.getResult().getGeometry());
        placeResult.setRating(searchPlace.getResult().getRating());
        placeResult.setOpeningHours(searchPlace.getResult().getOpeningHours());

        return placeResult;
    }

    public static String getPlaceDetailsUrl(String placeId) {
        StringBuilder url = new StringBuilder();
        url.append("details/json?place_id=");
        url.append(placeId);
        url.append("&key=");
        url.append(BuildConfig.PLACE_API_KEY);

        Log.d("getUrlPlaceDetails", url.toString());
        return url.toString();
    }

    public static String getNearbyPlaceUrl(LatLng userLocation, int mRadius) {

        StringBuilder url = new StringBuilder();
        url.append("nearbysearch/json?");
        url.append("location=");
        url.append(userLocation.latitude);
        url.append(",");
        url.append(userLocation.longitude);
        url.append("&radius=");
        url.append(mRadius);
        url.append("&types=restaurant&sensor=true&key=");
        url.append(BuildConfig.PLACE_API_KEY);

        Log.d("getUrlNearbyPlace", url.toString());
        return url.toString();
    }

    public static float getRating(Double rating) {

        return (float) (rating / 5) * 3;
    }
}
