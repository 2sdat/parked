package dev.aidaco.parked.AddNewUser;

import android.app.Application;

import androidx.annotation.NonNull;
import dev.aidaco.parked.Model.Enums;
import dev.aidaco.parked.Utils.BaseViewModel;
import dev.aidaco.parked.Utils.CreateUserResultListener;


/**
 * Implements the logic required by the AddNewUser Fragment.
 *
 * @author Aidan Courtney
 * @see AddNewUserFragment
 */
public class AddNewUserViewModel extends BaseViewModel {


    /**
     * Initializes the ViewModel.
     *
     * @param application The Application context
     */
    public AddNewUserViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * Calls the database to add a user with the specified information.
     *
     * @param firstName The user's first name.
     * @param lastName  The user's last name.
     * @param username  The desired username.
     * @param password  The desired password.
     * @param confirmPassword   Confirmation of the desired password.
     * @param userType  The type of user.
     * @param listener  Callback to recieve results of attempted user creation.
     */
    public void attemptCreateUser(String firstName, String lastName, String username, String password, String confirmPassword, Enums.UserType userType, CreateUserResultListener listener) {
        if (password.equals(confirmPassword)) {
            if (userType.getTypeCode() <= userRepo.getAccessPrivilege().getTypeCode()) {
                userRepo.attemptCreateUser(firstName, lastName, username, password, userType, listener);
            } else {
                listener.onAttemptReturn(CreateUserResultListener.INC_ACCESS_PRIVELIGE, 0);
            }
        } else {
            listener.onAttemptReturn(CreateUserResultListener.INC_PASSWORD, 0);
        }
    }
}
