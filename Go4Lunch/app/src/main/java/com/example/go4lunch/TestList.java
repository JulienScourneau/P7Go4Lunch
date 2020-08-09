package com.example.go4lunch;

import com.example.go4lunch.models.Restaurant;
import com.example.go4lunch.models.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class TestList {

    public static List<User> userList = Arrays.asList(
            new User(R.mipmap.ic_launcher,"User name is eating/not decided yet (Restaurant)"),
            new User(R.mipmap.ic_launcher,"User name is eating/not decided yet (Restaurant)"),
            new User(R.mipmap.ic_launcher,"User name is eating/not decided yet (Restaurant)"),
            new User(R.mipmap.ic_launcher,"User name is eating/not decided yet (Restaurant)")
    );

    public static ArrayList<User> getFakeUserList(){
        return new ArrayList<>(userList);
    }

    public static List<Restaurant> restaurantList = Arrays.asList(
            new Restaurant("King of Kebab","00 rue du Faubourg Petites Écoles","ouvert jusqu\\'a",R.drawable.background),
            new Restaurant("Le Blablabla","00 rue du Faubourg Petites Écoles","ouvert jusqu\\'a",R.mipmap.ic_launcher),
            new Restaurant("Super Sushi","00 rue du Faubourg Petites Écoles","ouvert jusqu\\'a",R.mipmap.ic_launcher),
            new Restaurant("Pizza Express","00 rue du Faubourg Petites Écoles","ouvert jusqu\\'a",R.mipmap.ic_launcher)
    );

    public static ArrayList<Restaurant> getFakeRestaurantList(){
        return new ArrayList<>(restaurantList);
    }
}
