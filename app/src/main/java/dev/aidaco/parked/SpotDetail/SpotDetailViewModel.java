package dev.aidaco.parked.SpotDetail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import dev.aidaco.parked.Model.Entities.SpotData;
import dev.aidaco.parked.Utils.BaseViewModel;

/**
 * Implements the logic required by the SpotDetail Fragment.
 *
 * @author Aidan Courtney
 * @see SpotDetailFragment
 */
public class SpotDetailViewModel extends BaseViewModel {

    private LiveData<SpotData> spotData;
    private long ticketId;
    private long parkTime = 0;

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
        long elapsed = System.currentTimeMillis() - parkTime;
        return formatElapsedTime(elapsed);
    }


    /**
     * Finalize the ticket and release vehicle to owner.
     */
    public void releaseVehicle() {
        // TODO implement calculate payment total
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
        return this.ticketId;
    }

    /**
     * Set the ID of the ticket to be loaded.
     * @param ticketId ID of ticket to display.
     */
    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }

    /**
     * Get the parking start time.
     * @return Parking start time.
     */
    public long getParkTime() {
        return parkTime;
    }

    /**
     * Set the parking start time to store.
     * @param parkTime  Parking start time.
     */
    public void setParkTime(long parkTime) {
        this.parkTime = parkTime;
    }
}
