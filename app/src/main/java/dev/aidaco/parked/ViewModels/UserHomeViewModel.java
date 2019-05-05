package dev.aidaco.parked.ViewModels;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import dev.aidaco.parked.Model.Entities.ParkingTicket;
import dev.aidaco.parked.Model.Entities.ParkingTicketData;
import dev.aidaco.parked.Model.Entities.Spot;
import dev.aidaco.parked.Model.Entities.SpotData;
import dev.aidaco.parked.Model.Entities.User;
import dev.aidaco.parked.ParkedRepository;

public class UserHomeViewModel extends AndroidViewModel {
    private static final String TAG = "UserHomeViewModel";

    private ParkedRepository parkedRepo;

    private LiveData<List<SpotData>> occupiedSpots;
    private LiveData<List<SpotData>> allSpots;
    private LiveData<List<ParkingTicketData>> activeTickets;
    private User currentUser = null;

    public UserHomeViewModel(@NonNull Application application) {
        super(application);
        parkedRepo = new ParkedRepository(application);
        occupiedSpots = parkedRepo.getOccupiedSpots();
        allSpots = parkedRepo.getAllSpots();
        activeTickets = parkedRepo.getActiveTickets();
    }

    public LiveData<List<SpotData>> getOccupiedSpots() {
        return occupiedSpots;
    }

    public LiveData<List<SpotData>> getAllSpots() {
        return allSpots;
    }

    public LiveData<List<ParkingTicketData>> getActiveTickets() {
        return activeTickets;
    }

    public void addSpot(Spot spot) {
        parkedRepo.addSpot(spot);
    }

    public void addTicket(ParkingTicket ticket) {
        parkedRepo.addTicket(ticket);
    }

    public void updateSpot(Spot spot) {
        parkedRepo.updateSpot(spot);
    }

    public void updateTicket(ParkingTicket ticket) {
        parkedRepo.updateTicket(ticket);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }
}
