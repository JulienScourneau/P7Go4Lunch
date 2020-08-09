package com.example.go4lunch;

import com.example.go4lunch.models.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class TestUserList {

    public static List<User> userList = Arrays.asList(
            new User(R.mipmap.ic_launcher,"User name is eating/not decided yet (Restaurant)"),
            new User(R.mipmap.ic_launcher,"User name is eating/not decided yet (Restaurant)"),
            new User(R.mipmap.ic_launcher,"User name is eating/not decided yet (Restaurant)"),
            new User(R.mipmap.ic_launcher,"User name is eating/not decided yet (Restaurant)")
    );

    public static ArrayList<User> getFakeUserList(){
        return new ArrayList<User>(userList);
    }
}
