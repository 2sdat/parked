package dev.aidaco.parked.UserDetail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import dev.aidaco.parked.Model.Entities.User;
import dev.aidaco.parked.Utils.BaseViewModel;

public class UserDetailViewModel extends BaseViewModel {
    private LiveData<User> user;

    public UserDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<User> getUser() {
        return user;
    }

    public void setUser(int userId) {

    }
}
