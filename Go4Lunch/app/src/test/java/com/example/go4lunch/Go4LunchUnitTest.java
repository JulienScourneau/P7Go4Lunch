package com.example.go4lunch;

import android.telecom.Call;

import com.example.go4lunch.Models.Details.PlaceDetails;
import com.example.go4lunch.Utils.Utils;
import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(JUnit4.class)
public class Go4LunchUnitTest {
    @Test
    public void change_the_ranking() {
        float rating = Utils.getRating(2.00);
        assertEquals(1.2000000476837158, rating, 0);
    }

    @Test
    public void get_details_place_url() {
        String fakeId = "fakeId";
        String url = Utils.getPlaceDetailsUrl(fakeId);
        assertEquals( "details/json?place_id=fakeId&key=AIzaSyD6y_8l1WeKKDk0dOHxxgL_ybA4Lmjc1Cc",url);
    }

    @Test
    public void get_nearby_place_url() {
        LatLng latLng = new LatLng(0.00,0.00) ;
        int radius = 0;
        String url = Utils.getNearbyPlaceUrl(latLng, radius);
        assertEquals("nearbysearch/json?location=0.0,0.0&radius=0&types=restaurant&sensor=true&key=AIzaSyD6y_8l1WeKKDk0dOHxxgL_ybA4Lmjc1Cc",url);
    }

    @Test
    public void change_details_on_nearby_place() {
        PlaceDetails placeDetails = new PlaceDetails();
        Utils.changeDetailsResult(placeDetails);
    }
}