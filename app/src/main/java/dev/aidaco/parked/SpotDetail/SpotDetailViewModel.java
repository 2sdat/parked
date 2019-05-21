package dev.aidaco.parked.SpotDetail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import dev.aidaco.parked.Model.Entities.ParkingTicket;
import dev.aidaco.parked.Model.Entities.SpotData;
import dev.aidaco.parked.Model.PaymentCalculator;
import dev.aidaco.parked.Utils.BaseViewModel;

/**
 * Implements the logic required by the SpotDetail Fragment.
 *
 * @author Aidan Courtney
 * @see SpotDetailFragment
 */
public class SpotDetailViewModel extends BaseViewModel {

    private LiveData<SpotData> spotData;
    private ParkingTicket ticket;

    /**
     * Initializes the ViewModel.
     *
     * @param application The Application context
     */
    public SpotDetailViewModel(@NonNull Application application) {
        super(application);
    }


    /**
     * Calculate the time elapsed since the vehicle was parked.
     *
     * @return Elapsed time.
     */
    public String calculateElapsedTime() {
        long elapsed = System.currentTimeMillis() - ticket.getStartTime();
        return formatElapsedTime(elapsed);
    }

    /**
     * Get the stored spot data.
     * @return LiveData containing the spot data.
     */
    public LiveData<SpotData> getSpotData() {
        return spotData;
    }

    /**.
     * Set the spot whose data shoudl be loaded
     * @param spotId ID of spot to display.
     */
    public void setSpotData(int spotId) {
        this.spotData = parkedRepo.getSpotDataById(spotId);
    }

    /**
     * Get the ID of the current ticket.
     * @return Current ticket ID.
     */
    public long getTicketId() {
        return this.ticket.getId();
    }

    /**
     * Get the parking start time.
     * @return Parking start time.
     */
    public long getParkTime() {
        return this.ticket.getStartTime();
    }

    public float getCurrentPrice() {
        return PaymentCalculator.calculateTotal(ticket);
    }

    public void setTicket(ParkingTicket parkingTicket) {
        this.ticket = parkingTicket;
    }
}
