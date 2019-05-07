package dev.aidaco.parked.UserDetail;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import dev.aidaco.parked.Model.Entities.User;
import dev.aidaco.parked.Utils.AttemptListener;
import dev.aidaco.parked.Utils.BaseViewModel;
import dev.aidaco.parked.Utils.DoubleResultListener;

public class UserDetailViewModel extends BaseViewModel {
    private int userId;
    private LiveData<List<User>> user;

    public UserDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<User>> getUser() {
        return user;
    }

    public void setUser(int userId) {
        this.userId = userId;
        user = userRepo.getUserById_LiveData(userId);
    }

    public void getUserTicketCounts(int userId, DoubleResultListener<Integer, Integer> listener) {
        parkedRepo.getUserTicketCounts(userId, listener);
    }

    public void attemptToggleActive(AttemptListener listener) {
        userRepo.attemptToggleActive(userId, listener);
    }


}
