package dev.aidaco.parked.ViewModels;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import dev.aidaco.parked.Model.Entities.User;
import dev.aidaco.parked.ViewModels.Util.BaseViewModel;

public class ManagerHomeViewModel extends BaseViewModel {
    private LiveData<List<User>> users;

    public ManagerHomeViewModel(@NonNull Application application, MasterViewModel masterVM) {
        super(application, masterVM);
        users = masterVM.getAllUsers();
    }

    public LiveData<List<User>> getUsers() {
        return users;
    }
}
