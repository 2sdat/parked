package dev.aidaco.parked.DisplayTicket;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import dev.aidaco.parked.Model.Entities.ParkingTicketData;
import dev.aidaco.parked.Model.Enums;
import dev.aidaco.parked.Utils.BaseViewModel;

public class DisplayTicketViewModel extends BaseViewModel {
    private LiveData<ParkingTicketData> ticketData;

    public DisplayTicketViewModel(@NonNull Application application) {
        super(application);
    }

    public float calculateTotal(long elapsedTime, Enums.BillingType billingType) {
        return 10.35f;
    }

    public void setTicket(long ticketId) {
        parkedRepo.finalizeTicket(ticketId);
        this.ticketData = parkedRepo.getTicketDataByIdLive(ticketId);
    }

    public LiveData<ParkingTicketData> getTicketData() {
        return ticketData;
    }
}
