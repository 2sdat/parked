package dev.aidaco.parked.Login;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import dev.aidaco.parked.Model.Entities.User;
import dev.aidaco.parked.Utils.BaseViewModel;
import dev.aidaco.parked.Utils.LoginAttemptListener;
import dev.aidaco.parked.Utils.SingleResultListener;

/**
 * Implements the logic required by the Login Fragment.
 *
 * @author Aidan Courtney
 * @see LoginFragment
 */
public class LoginViewModel extends BaseViewModel {
    private static final String TAG = "LoginViewModel";

    private String enteredUsername;
    private String enteredPassword;

    /**
     * Initializes the ViewModel.
     *
     * @param application The Application context
     */
    public LoginViewModel(@NonNull Application application) {
        super(application);
        Log.d(TAG, "LoginViewModel: loginviewmodel init'd");
    }


    /**
     * Attempts to log in user with the information provided.
     *
     * Must call provideUsername and providePassword first.
     *
     * @param listener Interface to be called upon result.
     */
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


    /**
     * Provide the username for a login attempt.
     * @param username Username.
     */
    public void provideUsername(String username) {
        enteredUsername = username;
    }

    /**
     * Provide the password for a login attempt.
     * @param password Password.
     */
    public void providePassword(String password) {
        enteredPassword = password;
    }

    /**
     * Ensures that the default user exists.
     */
    public void ensureDefaultUser() {
        userRepo.ensureDefaultUser();
    }
}
