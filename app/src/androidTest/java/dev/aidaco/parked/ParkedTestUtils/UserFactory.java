package dev.aidaco.parked.ParkedTestUtils;

import android.util.Log;

import java.util.HashMap;
import java.util.Random;

import dev.aidaco.parked.Model.Entities.User;
import dev.aidaco.parked.Model.Enums;

public class UserFactory {
    private static final String TAG = "UserFactory";

    private static final String[] firstNames = {
            "Aidan",
            "Theresa",
            "Alba",
            "Peter",
            "Rowan",
            "Albert",
            "John",
            "Keith",
            "Paul",
            "Jude",
            "Mike",
            "Dave",
            "David"
    };

    private static final String[] lastNames= {
            "Courtney",
            "Sweeney",
            "Mitchell",
            "McConnel",
            "Davidson",
            "Fitzpatrick",
            "White",
            "Black",
            "Gregory",
            "McKenzie"
    };

    public static HashMap<Integer, User> userList = new HashMap<>();
    public static int numUsers = 0;

    public static User genNewUser(){
        Random rand = new Random();
        String first = firstNames[rand.nextInt(firstNames.length)];
        String last = lastNames[rand.nextInt(lastNames.length)];
        int id = numUsers;
        User user = new User(id, first+last+Integer.toString(id), "password", first, last, Enums.UserType.ADMIN, true);
        numUsers += 1;
        userList.put(id, user);
        Log.d(TAG, "New User Added: " + first+last+Integer.toString(id));
        return user;
    }
}
