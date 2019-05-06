package dev.aidaco.parked.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import dev.aidaco.parked.Interfaces.ResultListener;
import dev.aidaco.parked.Model.Entities.LicensePlate;
import dev.aidaco.parked.Model.Entities.SpotData;
import dev.aidaco.parked.Model.Enums;

public class AddNewVehicleViewModel extends BaseViewModel {
    private static final String TAG = "AddNewVehicleViewModel";

    AddNewVehicleViewModel(@NonNull Application application) {
        super(application);
    }

    public void onButtonParkVehicleClicked(LicensePlate licensePlate, Enums.VehicleType vehicletype, Enums.BillingType billingType, final ResultListener<SpotData> listener) {
        masterVM.parkNewVehicle(vehicletype, licensePlate, billingType, listener);
        // TODO handle park button clicked and navigate to parkvehicle
    }
}
