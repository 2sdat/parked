package dev.aidaco.parked.DisplayTicket;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import dev.aidaco.parked.Model.Entities.ParkingTicketData;
import dev.aidaco.parked.Utils.BaseViewModel;

/**
 * Implements the logic required by the DisplayTicket Fragment.
 *
 * @author Aidan Courtney
 * @see DisplayTicketFragment
 */
public class DisplayTicketViewModel extends BaseViewModel {
    private LiveData<ParkingTicketData> ticketData;

    /**
     * Initializes the ViewModel.
     *
     * @param application The Application context
     */
    public DisplayTicketViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * Sets the ticket to display.
     * @param ticketId  ID of ticket to display.
     */
    public void setTicket(long ticketId) {
        parkedRepo.finalizeTicket(ticketId);
        this.ticketData = parkedRepo.getTicketDataByIdLive(ticketId);
    }


    /**
     * Returns the data for the ticket to be displayed.
     * @return LiveData containing the ticket data
     */
    public LiveData<ParkingTicketData> getTicketData() {
        return ticketData;
    }
}
