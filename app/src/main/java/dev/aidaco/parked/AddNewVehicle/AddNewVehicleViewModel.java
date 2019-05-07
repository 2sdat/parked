package dev.aidaco.parked.AddNewVehicle;

import android.app.Application;

import androidx.annotation.NonNull;
import dev.aidaco.parked.Model.Entities.LicensePlate;
import dev.aidaco.parked.Model.Enums;
import dev.aidaco.parked.Utils.BaseViewModel;
import dev.aidaco.parked.Utils.DoubleResultListener;

public class AddNewVehicleViewModel extends BaseViewModel {
    private static final String TAG = "AddNewVehicleViewModel";

    public AddNewVehicleViewModel(@NonNull Application application) {
        super(application);
    }

    public void onButtonParkVehicleClicked(LicensePlate licensePlate, Enums.VehicleType vehicletype, Enums.BillingType billingType, final DoubleResultListener<Long, Integer> listener) {
        parkedRepo.parkNewVehicle(vehicletype, licensePlate, userRepo.getCurrentUser(), billingType, listener);
    }
}
