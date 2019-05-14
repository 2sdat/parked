package dev.aidaco.parked.ManagerHome;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import dev.aidaco.parked.Model.Entities.ParkingTicket;
import dev.aidaco.parked.Model.Entities.User;
import dev.aidaco.parked.Utils.BaseViewModel;

/**
 * Implements the logic required by the ManagerHome Fragment.
 *
 * @author Aidan Courtney
 * @see ManagerHomeFragment
 */
public class ManagerHomeViewModel extends BaseViewModel {
    static final boolean USER_VIEW = true;
    static final boolean TICKET_VIEW = false;
    private boolean CURRENT_VIEW = true;
    private LiveData<List<User>> users;
    private LiveData<List<ParkingTicket>> tickets;

    /**
     * Initializes the ViewModel.
     *
     * @param application The Application context
     */
    public ManagerHomeViewModel(@NonNull Application application) {
        super(application);
        users = userRepo.getAllUsers_LiveData();
        tickets = parkedRepo.getAllTickets();
    }


    /**
     * Get the list of all users.
     * @return LiveData containing list of all users.
     */
    public LiveData<List<User>> getUsers() {
        return users;
    }


    /**
     * Get the list of all tickets.
     * @return LiveData containing list of all tickets.
     */
    public LiveData<List<ParkingTicket>> getTickets() {
        return tickets;
    }

    /**
     * Toggle the current view between user and ticket
     */
    public void toggleCurrentView() {
        CURRENT_VIEW = !CURRENT_VIEW;
    }


    /**
     * Get the current view.
     * @return The current view.
     */
    public boolean getCurrentView() {
        return CURRENT_VIEW;
    }
}
