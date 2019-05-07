package dev.aidaco.parked.AddNewUser;

import android.app.Application;

import androidx.annotation.NonNull;
import dev.aidaco.parked.Model.Enums;
import dev.aidaco.parked.Utils.BaseViewModel;
import dev.aidaco.parked.Utils.CreateUserResultListener;

public class AddNewUserViewModel extends BaseViewModel {
    public AddNewUserViewModel(@NonNull Application application) {
        super(application);
    }

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
