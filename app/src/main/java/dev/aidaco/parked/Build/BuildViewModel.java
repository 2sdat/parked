package dev.aidaco.parked.Build;

import android.app.Application;

import androidx.annotation.NonNull;
import dev.aidaco.parked.Model.Enums;
import dev.aidaco.parked.Utils.BaseViewModel;

public class BuildViewModel extends BaseViewModel {
    public SnackbarLauncher launcher;

    public BuildViewModel(@NonNull Application application) {
        super(application);
    }

    public void setSnackbarLauncher(SnackbarLauncher launcher) {
        this.launcher = launcher;
    }

    public void rebuild(int numCar, int numMoto, int numTruck) {
        if (getAccessPrivilege() == Enums.UserType.ADMIN) {
            parkedRepo.rebuildDatabase(numCar, numMoto, numTruck);
        } else {
            launcher.displaySnackbar("You do not have the necessary access.");
        }
    }

    public void resetData() {
        userRepo.resetData();
    }

    public interface SnackbarLauncher {
        void displaySnackbar(String message);
    }
}
