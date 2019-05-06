package dev.aidaco.parked.ViewModels;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import dev.aidaco.parked.Interfaces.ResultListener;
import dev.aidaco.parked.Model.Entities.LicensePlate;
import dev.aidaco.parked.Model.Entities.ParkingTicket;
import dev.aidaco.parked.Model.Entities.ParkingTicketData;
import dev.aidaco.parked.Model.Entities.Spot;
import dev.aidaco.parked.Model.Entities.SpotData;
import dev.aidaco.parked.Model.Entities.User;
import dev.aidaco.parked.Model.Enums;
import dev.aidaco.parked.ParkedRepository;
import dev.aidaco.parked.UserRepository;

public class MasterViewModel extends AndroidViewModel {
    private ParkedRepository parkedRepo;
    private UserRepository userRepo;
    private User currentUser = null;
    private Enums.UserType accessPrivilege = null;
    private boolean loggedIn = false;

    public MasterViewModel(@NonNull Application application) {
        super(application);
        parkedRepo = new ParkedRepository(application);
        userRepo = new UserRepository(application);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
        this.accessPrivilege = user.getUserType();
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public Enums.UserType getAccessPrivilege() {
        return accessPrivilege;
    }

    public void parkNewVehicle(Enums.VehicleType vehicleType, LicensePlate licensePlate, Enums.BillingType billingType, ResultListener<SpotData> listener) {
        parkedRepo.parkNewVehicle(vehicleType, licensePlate, currentUser, billingType, listener);
    }

    public LiveData<List<SpotData>> getAllSpots() {
        return parkedRepo.getAllSpots();
    }

    public LiveData<List<SpotData>> getOccupiedSpots() {
        return parkedRepo.getOccupiedSpots();
    }

    public LiveData<List<ParkingTicketData>> getActiveTickets() {
        return parkedRepo.getActiveTickets();
    }

    public LiveData<SpotData> getSpotDataById(int id) {
        return parkedRepo.getSpotDataById(id);
    }

    public List<Spot> getEmptySpots() {
        return parkedRepo.getEmptySpots();
    }

    public void addSpot(Spot spot) {
        parkedRepo.addSpot(spot);
    }

    public void updateSpot(Spot spot) {
        parkedRepo.updateSpot(spot);
    }

    public void updateTicket(ParkingTicket ticket) {
        parkedRepo.updateTicket(ticket);
    }

    public void addUser(User user) {
        userRepo.addUser(user);
    }

    public void updateUser(User user) {
        userRepo.updateUser(user);
    }

    public void getUserById(int id, ResultListener<User> listener) {
        userRepo.getUserById(id, listener);
    }

    public void getUserByUsername(String username, ResultListener<User> listener) {
        userRepo.getUserByUsername(username, listener);
    }
}
