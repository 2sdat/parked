package dev.aidaco.parked.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import dev.aidaco.parked.Interfaces.LoginAttemptListener;
import dev.aidaco.parked.Interfaces.ResultListener;
import dev.aidaco.parked.Model.Entities.User;

public class LoginViewModel extends BaseViewModel {
    private static final String TAG = "LoginViewModel";

    private String enteredUsername;
    private String enteredPassword;

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public void attemptLogin(final LoginAttemptListener listener) {
        masterVM.getUserByUsername(enteredUsername, new ResultListener<User>() {
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
}
