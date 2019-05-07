package dev.aidaco.parked.Login;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import dev.aidaco.parked.Model.Entities.User;
import dev.aidaco.parked.Utils.BaseViewModel;
import dev.aidaco.parked.Utils.LoginAttemptListener;
import dev.aidaco.parked.Utils.SingleResultListener;

public class LoginViewModel extends BaseViewModel {
    private static final String TAG = "LoginViewModel";

    private String enteredUsername;
    private String enteredPassword;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        Log.d(TAG, "LoginViewModel: loginviewmodel init'd");
    }

    public void attemptLogin(final LoginAttemptListener listener) {
        userRepo.getUserByUsername(enteredUsername, new SingleResultListener<User>() {
            @Override
            public void onResult(User user) {
                if (user == null) {
                    listener.onResult(LoginAttemptListener.INC_USERNAME);
                    return;
                }

                if (!user.getIsActive()) {
                    listener.onResult(LoginAttemptListener.INACTIVE);
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

    public void ensureDefaultUser() {
        userRepo.ensureDefaultUser();
    }
}
