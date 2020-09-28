package com.example.go4lunch.Utils;

import com.example.go4lunch.Models.Restaurant;
import com.example.go4lunch.Models.User;
import com.example.go4lunch.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class TestList {

    private static List<User> userList = Arrays.asList(
            new User(R.mipmap.ic_launcher,"User 1"),
            new User(R.mipmap.ic_launcher,"User 2"),
            new User(R.mipmap.ic_launcher,"User 3"),
            new User(R.mipmap.ic_launcher,"User 4"),
            new User(R.mipmap.ic_launcher,"User 5"),
            new User(R.mipmap.ic_launcher,"User 6"),
            new User(R.mipmap.ic_launcher,"User 7"),
            new User(R.mipmap.ic_launcher,"User 8"),
            new User(R.mipmap.ic_launcher,"User 9"),
            new User(R.mipmap.ic_launcher,"User 10")
    );

    public static ArrayList<User> getFakeUserList(){
        return new ArrayList<>(userList);
    }

    private static List<Restaurant> restaurantList = Arrays.asList(
            new Restaurant("King of Kebab","00 rue du Faubourg Petites Écoles","ouvert jusqu\\'a",R.drawable.background),
            new Restaurant("Le Blablabla","00 rue du Faubourg Petites Écoles","ouvert jusqu\\'a",R.drawable.background),
            new Restaurant("Super Sushi","00 rue du Faubourg Petites Écoles","ouvert jusqu\\'a",R.drawable.background),
            new Restaurant("Bufa","00 rue du Faubourg Petites Écoles","ouvert jusqu\\'a",R.drawable.background),
            new Restaurant("Cozna","00 rue du Faubourg Petites Écoles","ouvert jusqu\\'a",R.drawable.background),
            new Restaurant("Le Balcon","00 rue du Faubourg Petites Écoles","ouvert jusqu\\'a",R.drawable.background),
            new Restaurant("Sarment","00 rue du Faubourg Petites Écoles","ouvert jusqu\\'a",R.drawable.background),
            new Restaurant("Pizza Express","00 rue du Faubourg Petites Écoles","ouvert jusqu\\'a",R.drawable.background)
    );

    public static ArrayList<Restaurant> getFakeRestaurantList(){
        return new ArrayList<>(restaurantList);
    }
}
