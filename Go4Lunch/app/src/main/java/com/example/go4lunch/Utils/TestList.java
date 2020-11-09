package com.example.go4lunch.Utils;

import com.example.go4lunch.Models.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class TestList {

    private static List<User> userList = Arrays.asList(
            new User("FakeId", "User 1", "Status", "https://api.adorable.io/AVATARS/512/1.png"),
            new User("FakeId", "User 2", "Status", "https://api.adorable.io/AVATARS/512/1.png"),
            new User("FakeId", "User 3", "Status", "https://api.adorable.io/AVATARS/512/1.png"),
            new User("FakeId", "User 4", "Status", "https://api.adorable.io/AVATARS/512/1.png"),
            new User("FakeId", "User 5", "Status", "https://api.adorable.io/AVATARS/512/1.png"),
            new User("FakeId", "User 6", "Status", "https://api.adorable.io/AVATARS/512/1.png"),
            new User("FakeId", "User 7", "Status", "https://api.adorable.io/AVATARS/512/1.png"),
            new User("FakeId", "User 8", "Status", "https://api.adorable.io/AVATARS/512/1.png"),
            new User("FakeId", "User 9", "Status", "https://api.adorable.io/AVATARS/512/1.png"),
            new User("FakeId", "User 10", "Status", "https://api.adorable.io/AVATARS/512/1.png")
    );

    public static ArrayList<User> getFakeUserList() {
        return new ArrayList<>(userList);
    }

}
