package dev.aidaco.parked.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import dev.aidaco.parked.Interfaces.LoginAttemptListener;
import dev.aidaco.parked.Interfaces.ResultListener;
import dev.aidaco.parked.Model.Entities.User;
import dev.aidaco.parked.UserRepository;

public class LoginViewModel extends AndroidViewModel {
    private static final String TAG = "LoginViewModel";

    private UserRepository userRepository;
    private String enteredUsername;
    private String enteredPassword;
    private User currentUser;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public void attemptLogin(final LoginAttemptListener listener) {
        userRepository.getUserByUsername(enteredUsername, new ResultListener<User>() {
            @Override
            public void onResult(User user) {
                if (user == null) {
                    listener.onResult(LoginAttemptListener.INC_USERNAME);
                    return;
                }

                if (user.getPassword().equals(enteredPassword)) {
                    currentUser = user;
                    listener.onResult(LoginAttemptListener.SUCCESS);
                } else {
                    currentUser = null;
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

    public User getCurrentUser() {
        return currentUser;
    }
}
