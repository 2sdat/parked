package dev.aidaco.parked.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import dev.aidaco.parked.Interfaces.DoubleResultListener;
import dev.aidaco.parked.Model.Entities.LicensePlate;
import dev.aidaco.parked.Model.Enums;
import dev.aidaco.parked.ViewModels.Util.BaseViewModel;

public class AddNewVehicleViewModel extends BaseViewModel {
    private static final String TAG = "AddNewVehicleViewModel";

    public AddNewVehicleViewModel(@NonNull Application application, MasterViewModel masterVM) {
        super(application, masterVM);
    }

    public void onButtonParkVehicleClicked(LicensePlate licensePlate, Enums.VehicleType vehicletype, Enums.BillingType billingType, final DoubleResultListener<Long, Integer> listener) {
        masterVM.parkNewVehicle(vehicletype, licensePlate, billingType, listener);
    }
}
