package dev.aidaco.parked.TicketDetail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import dev.aidaco.parked.Model.Entities.ParkingTicketData;
import dev.aidaco.parked.Model.Enums;
import dev.aidaco.parked.Utils.BaseViewModel;

/**
 * Implements the logic required by the TicketDetail Fragment.
 *
 * @author Aidan Courtney
 * @see TicketDetailFragment
 */
public class TicketDetailViewModel extends BaseViewModel {
    private LiveData<ParkingTicketData> ticketData;
    @SuppressWarnings("FieldCanBeLocal")
    private long ticketId;
    private long parkTime;

    /**
     * Initializes the ViewModel.
     *
     * @param application The Application context
     */
    public TicketDetailViewModel(@NonNull Application application) {
        super(application);
    }


    /**
     * Sets the ticket to display information for.
     *
     * @param ticketId ID of the ticket to display.
     */
    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
        this.ticketData = parkedRepo.getTicketDataByIdLive(ticketId);
    }

    /**
     * Set the parking start time.
     * @param parkTime  Time parking started.
     */
    public void setParkTime(long parkTime) {
        this.parkTime = parkTime;
    }

    /**
     * Get the parking ticket data.
     * @return LiveData containing the ticket data.
     */
    public LiveData<ParkingTicketData> getTicketData() {
        return ticketData;
    }

    /**
     * Calculates the time elapsed since the vehicle was parked.
     * @return Time elapsed.
     */
    public long calculateElapsedTime() {
        return System.currentTimeMillis() - parkTime;
    }

    /**
     * Calculates the total price.
     * @param elapsed   Elapsed time.
     * @param billingType   Billing type to apply.
     * @return Total price.
     */
    public float calculateTotal(long elapsed, Enums.BillingType billingType) {
        // TODO implement calculate pricing;

        return 10.35f;
    }
}
