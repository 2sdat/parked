package dev.aidaco.parked.TicketDetail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import dev.aidaco.parked.Model.Entities.ParkingTicket;
import dev.aidaco.parked.Model.Entities.ParkingTicketData;
import dev.aidaco.parked.Model.PaymentCalculator;
import dev.aidaco.parked.Utils.BaseViewModel;

/**
 * Implements the logic required by the TicketDetail Fragment.
 *
 * @author Aidan Courtney
 * @see TicketDetailFragment
 */
public class TicketDetailViewModel extends BaseViewModel {
    private LiveData<ParkingTicketData> ticketData;
    private ParkingTicket ticket;

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
        this.ticketData = parkedRepo.getTicketDataByIdLive(ticketId);
    }


    /**
     * Set the cached copy of the current ticket
     * @param ticket current ticket
     */
    public void setTicket(ParkingTicket ticket) {
        this.ticket = ticket;
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
        return System.currentTimeMillis() - ticket.getStartTime();
    }

    /**
     * Returns the total price.
     * @return Total price.
     */
    public float getCurrentPrice() {
        return PaymentCalculator.calculateTotal(ticket);
    }

}
