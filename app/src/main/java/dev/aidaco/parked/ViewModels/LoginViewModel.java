package dev.aidaco.parked.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import dev.aidaco.parked.Interfaces.LoginAttemptListener;
import dev.aidaco.parked.Interfaces.SingleResultListener;
import dev.aidaco.parked.Model.Entities.Spot;
import dev.aidaco.parked.Model.Entities.User;
import dev.aidaco.parked.Model.Enums;
import dev.aidaco.parked.ViewModels.Util.BaseViewModel;

public class LoginViewModel extends BaseViewModel {
    private static final String TAG = "LoginViewModel";

    private String enteredUsername;
    private String enteredPassword;

    public LoginViewModel(@NonNull Application application, MasterViewModel masterVM) {
        super(application, masterVM);
    }

    public void attemptLogin(final LoginAttemptListener listener) {
        masterVM.getUserByUsername(enteredUsername, new SingleResultListener<User>() {
            @Override
            public void onResult(User user) {
                if (user == null) {
                    listener.onResult(LoginAttemptListener.INC_USERNAME);
                    return;
                }

                if (user.getPassword().equals(enteredPassword)) {
                    masterVM.setCurrentUser(user);
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
        User testUser = new User(0, "username", "password", "Aidan", "Courtney", Enums.UserType.BASIC, true);
        masterVM.addUser(testUser);

        for (int i = 0; i < 100; i++) {
            Spot spot = new Spot(i, Enums.VehicleType.CAR, true, 0, false);
            masterVM.addSpot(spot);
        }
    }
}
