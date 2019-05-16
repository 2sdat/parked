package dev.aidaco.parked.Build;

import android.app.Application;

import androidx.annotation.NonNull;
import dev.aidaco.parked.Model.Enums;
import dev.aidaco.parked.Utils.BaseViewModel;

/**
 * Implements the logic required by the Nuild Fragment.
 *
 * @author Aidan Courtney
 * @see BuildFragment
 */
public class BuildViewModel extends BaseViewModel {
    private SnackbarLauncher launcher;

    /**
     * Initializes the ViewModel.
     *
     * @param application The Application context
     */
    public BuildViewModel(@NonNull Application application) {
        super(application);
    }


    /**
     * Set the callback for displaying a snackbar message.
     * @param launcher Interface to be called when a snc=ackbar needs to be created.
     */
    public void setSnackbarLauncher(SnackbarLauncher launcher) {
        this.launcher = launcher;
    }


    /**
     * Rebuilds the database to the specified parameters.
     *
     * <em>This completely wipes all stored data.</em>
     * @see dev.aidaco.parked.Database.ParkedDatabase
     *
     * @param numCar    Number of car spots in the garage.
     * @param numMoto   Number of motorcycle spots in the garage.
     * @param numTruck  Number of truck spots in the garage.
     */
    public void rebuild(int numCar, int numMoto, int numTruck) {
        if (getAccessPrivilege() == Enums.UserType.ADMIN) {
            parkedRepo.rebuildDatabase(numCar, numMoto, numTruck);
        } else {
            launcher.displaySnackbar("You do not have the necessary access.");
        }
    }

    /**
     * Interface that can be called to launch a snackbar message.
     */
    public interface SnackbarLauncher {

        /**
         * Launch a snackbar with the specified message.
         * @param message   Message to be displayed.
         */
        void displaySnackbar(String message);
    }
}
