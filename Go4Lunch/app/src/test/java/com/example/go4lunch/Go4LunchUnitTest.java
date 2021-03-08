package com.example.go4lunch;

import com.example.go4lunch.Models.Details.PlaceDetails;
import com.example.go4lunch.Models.Details.Result;
import com.example.go4lunch.Models.NearbySearch.Geometry;
import com.example.go4lunch.Models.NearbySearch.Locations;
import com.example.go4lunch.Models.NearbySearch.OpeningHours;
import com.example.go4lunch.Models.NearbySearch.Photo;
import com.example.go4lunch.Utils.Utils;
import com.google.android.gms.maps.model.LatLng;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(JUnit4.class)
public class Go4LunchUnitTest {

    private PlaceDetails placeDetails = new PlaceDetails();
    private Result detailsResult = new Result();
    private Double lat;
    private Double lng;
    private int radius;
    private String fakeId;

    @Before
    public void setup() {
        lat = 1.00;
        lng = 1.00;
        radius = 0;
        fakeId = "fakeId";
        detailsResult.setPlaceId("fakePlaceId");
        detailsResult.setName("fakeName");
        List<Photo> list = new ArrayList<>();
        Photo photo = new Photo();
        photo.setPhotoReference("https://pbs.twimg.com/profile_images/1363824536618229760/wr1Z_VKN_normal.jpg");
        list.add(photo);
        detailsResult.setPhotos(list);
        detailsResult.setVicinity("fakeVicinity");
        Geometry geometry = new Geometry();
        Locations locations = new Locations();
        locations.setLat(lat);
        locations.setLng(lng);
        geometry.setLocations(locations);
        detailsResult.setGeometry(geometry);
        detailsResult.setRating(0.00);
        OpeningHours openingHours = new OpeningHours();
        openingHours.setOpenNow(true);
        detailsResult.setOpeningHours(openingHours);
        placeDetails.setResult(detailsResult);
    }

    @Test
    public void change_the_ranking() {
        float rating = Utils.getRating(2.00);
        assertEquals(1.2000000476837158, rating, 0);
    }

    @Test
    public void get_details_place_url() {
        String url = Utils.getPlaceDetailsUrl(fakeId);
        assertEquals("details/json?place_id=fakeId&key=AIzaSyD6y_8l1WeKKDk0dOHxxgL_ybA4Lmjc1Cc", url);
    }

    @Test
    public void get_nearby_place_url() {
        LatLng latLng = new LatLng(lat, lng);
        String url = Utils.getNearbyPlaceUrl(latLng, radius);
        assertEquals("nearbysearch/json?location=1.0,1.0&radius=0&types=restaurant&sensor=true&key=AIzaSyD6y_8l1WeKKDk0dOHxxgL_ybA4Lmjc1Cc", url);
    }

    @Test
    public void change_details_on_nearby_place() {
        com.example.go4lunch.Models.NearbySearch.Result nearbyResult = Utils.changeDetailsResult(placeDetails);

        assertEquals(detailsResult.getPlaceId(), nearbyResult.getPlaceId());
        assertEquals(detailsResult.getName(), nearbyResult.getName());
        assertEquals(detailsResult.getPhotos().get(0), nearbyResult.getPhotos().get(0));
        assertEquals(detailsResult.getVicinity(), nearbyResult.getVicinity());
        assertEquals(detailsResult.getGeometry(), nearbyResult.getGeometry());
        assertEquals(detailsResult.getRating(), nearbyResult.getRating());
        assertTrue(nearbyResult.getOpeningHours().getOpenNow());
    }
}