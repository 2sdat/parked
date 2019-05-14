package dev.aidaco.parked.AddNewVehicle;

import android.app.Application;

import androidx.annotation.NonNull;
import dev.aidaco.parked.Model.Entities.LicensePlate;
import dev.aidaco.parked.Model.Enums;
import dev.aidaco.parked.Utils.BaseViewModel;
import dev.aidaco.parked.Utils.DoubleResultListener;

/**
 * Implements the logic required by the AddNewVehicle Fragment.
 *
 * @author Aidan Courtney
 * @see AddNewVehicleFragment
 */
public class AddNewVehicleViewModel extends BaseViewModel {

    /**
     * Initializes the ViewModel.
     *
     * @param application The Application context
     */
    public AddNewVehicleViewModel(@NonNull Application application) {
        super(application);
    }


    /**
     * Calls on the database to reserve a spot and create a ticket for the new vehicle.
     *
     * @param licensePlate License plate of the vehicle to park.
     * @param vehicleType  Type of vehicle being parked.
     * @param billingType  Type of billing to apply.
     * @param listener     Callback listener to revieve result code.
     */
    public void onButtonParkVehicleClicked(LicensePlate licensePlate, Enums.VehicleType vehicleType, Enums.BillingType billingType, final DoubleResultListener<Long, Integer> listener) {
        parkedRepo.parkNewVehicle(vehicleType, licensePlate, userRepo.getCurrentUser(), billingType, listener);
    }
}
