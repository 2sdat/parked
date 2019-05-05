package dev.aidaco.parked.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import dev.aidaco.parked.Model.Entities.LicensePlate;
import dev.aidaco.parked.Model.Entities.SpotData;
import dev.aidaco.parked.Model.Entities.User;
import dev.aidaco.parked.Model.Enums;
import dev.aidaco.parked.ParkedRepository;

public class AddNewVehicleViewModel extends AndroidViewModel {
    private static final String TAG = "AddNewVehicleViewModel";

    private ParkedRepository parkedRepo;
    private User currentUser;

    AddNewVehicleViewModel(@NonNull Application application) {
        super(application);
        parkedRepo = new ParkedRepository(application);
    }

    public void onButtonParkVehicleClicked(LicensePlate licensePlate, Enums.VehicleType vehicletype, Enums.BillingType billingType) {
        parkedRepo.parkNewVehicle(vehicletype, licensePlate, currentUser, billingType, new ParkedRepository.ParkResultListener() {
            @Override
            public void onResult(SpotData spotData) {

            }
        });
        // TODO handle park button clicked and navigate to parkvehicle
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

}
