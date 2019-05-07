package dev.aidaco.parked.ManagerHome;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import dev.aidaco.parked.Model.Entities.ParkingTicket;
import dev.aidaco.parked.Model.Entities.User;
import dev.aidaco.parked.Utils.BaseViewModel;

public class ManagerHomeViewModel extends BaseViewModel {
    public static final boolean USER_VIEW = true;
    public static final boolean TICKET_VIEW = false;
    private boolean CURRENT_VIEW = true;
    private LiveData<List<User>> users;
    private LiveData<List<ParkingTicket>> tickets;

    public ManagerHomeViewModel(@NonNull Application application) {
        super(application);
        users = userRepo.getAllUsers();
        tickets = parkedRepo.getAllTickets();
    }

    public LiveData<List<User>> getUsers() {
        return users;
    }

    public LiveData<List<ParkingTicket>> getTickets() {
        return tickets;
    }

    public void toggleCurrentView() {
        CURRENT_VIEW = !CURRENT_VIEW;
    }

    public boolean getCurrentView() {
        return CURRENT_VIEW;
    }
}
