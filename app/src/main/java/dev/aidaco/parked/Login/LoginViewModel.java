package dev.aidaco.parked.Login;

import android.app.Application;

import androidx.annotation.NonNull;
import dev.aidaco.parked.Model.Entities.Spot;
import dev.aidaco.parked.Model.Entities.User;
import dev.aidaco.parked.Model.Enums;
import dev.aidaco.parked.Utils.BaseViewModel;
import dev.aidaco.parked.Utils.LoginAttemptListener;
import dev.aidaco.parked.Utils.SingleResultListener;

public class LoginViewModel extends BaseViewModel {
    private static final String TAG = "LoginViewModel";

    private String enteredUsername;
    private String enteredPassword;

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public void attemptLogin(final LoginAttemptListener listener) {
        userRepo.getUserByUsername(enteredUsername, new SingleResultListener<User>() {
            @Override
            public void onResult(User user) {
                if (user == null) {
                    listener.onResult(LoginAttemptListener.INC_USERNAME);
                    return;
                }

                if (user.getPassword().equals(enteredPassword)) {
                    userRepo.setCurrentUser(user);
                    listener.onResult(LoginAttemptListener.SUCCESS);
                } else {
                    listener.onResult(LoginAttemptListener.INC_PASSWORD);
                }
            }
        });
    }

    public void provideUsername(String username) {
        enteredUsername = username;
    }

    public void providePassword(String password) {
        enteredPassword = password;
    }

    public void populateDbWithTestData() {
        User testUser = new User(0, "username", "password", "Basic", "Test", Enums.UserType.BASIC, true);
        User testAdmin = new User(0, "admin", "password", "Admin", "Test", Enums.UserType.ADMIN, true);
        User testManager = new User(0, "manager", "password", "Manager", "Test", Enums.UserType.MANAGER, true);
        userRepo.addUser(testUser);
        userRepo.addUser(testManager);
        userRepo.addUser(testAdmin);

        for (int i = 0; i < 100; i++) {
            Spot spot = new Spot(i, Enums.VehicleType.CAR, true, 0, false);
            parkedRepo.addSpot(spot);
        }
    }
}
