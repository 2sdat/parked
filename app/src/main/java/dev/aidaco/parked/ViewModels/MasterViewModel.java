package dev.aidaco.parked.ViewModels;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import dev.aidaco.parked.Interfaces.AttemptListener;
import dev.aidaco.parked.Interfaces.DoubleResultListener;
import dev.aidaco.parked.Interfaces.SingleResultListener;
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

    public void parkNewVehicle(Enums.VehicleType vehicleType, LicensePlate licensePlate, Enums.BillingType billingType, DoubleResultListener<Long, Integer> listener) {
        parkedRepo.parkNewVehicle(vehicleType, licensePlate, currentUser, billingType, listener);
    }

    public void finalizePark(long ticketId, AttemptListener listener) {
        parkedRepo.finalizePark(ticketId, listener);
    }

    public void cancelPark(long ticketId, AttemptListener listener) {
        parkedRepo.cancelPark(ticketId, listener);
    }

    public void finalizeTicket(long ticketId) {
        parkedRepo.finalizeTicket(ticketId);
    }

    public void getTicketsByUserId(int userId, SingleResultListener<List<ParkingTicket>> listener) {
        parkedRepo.getTicketsByUserId(userId, listener);
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

    public LiveData<ParkingTicketData> getTicketDataById(long ticketId) {
        return parkedRepo.getTicketDataByIdLive(ticketId);
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

    public LiveData<List<User>> getAllUsers() {
        return userRepo.getAllUsers();
    }

    public void getUserById(int id, SingleResultListener<User> listener) {
        userRepo.getUserById(id, listener);
    }

    public void getUserByUsername(String username, SingleResultListener<User> listener) {
        userRepo.getUserByUsername(username, listener);
    }
}
