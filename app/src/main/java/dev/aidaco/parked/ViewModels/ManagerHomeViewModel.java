package dev.aidaco.parked.ViewModels;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import dev.aidaco.parked.Model.Entities.ParkingTicket;
import dev.aidaco.parked.Model.Entities.User;
import dev.aidaco.parked.ViewModels.Util.BaseViewModel;

public class ManagerHomeViewModel extends BaseViewModel {
    public final boolean USER_VIEW = true;
    public final boolean TICKET_VIEW = false;
    public boolean CURRENT_VIEW = true;
    private LiveData<List<User>> users;
    private LiveData<List<ParkingTicket>> tickets;

    public ManagerHomeViewModel(@NonNull Application application, MasterViewModel masterVM) {
        super(application, masterVM);
        users = masterVM.getAllUsers();
        tickets = masterVM.getAllTickets();
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
