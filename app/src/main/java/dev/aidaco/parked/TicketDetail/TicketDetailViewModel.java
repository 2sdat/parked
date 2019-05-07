package dev.aidaco.parked.TicketDetail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import dev.aidaco.parked.Model.Entities.ParkingTicketData;
import dev.aidaco.parked.Model.Enums;
import dev.aidaco.parked.Utils.BaseViewModel;

public class TicketDetailViewModel extends BaseViewModel {
    private LiveData<ParkingTicketData> ticketData;
    private long ticketId;
    private long parkTime;

    public TicketDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
        this.ticketData = parkedRepo.getTicketDataByIdLive(ticketId);
    }

    public void setParkTime(long parkTime) {
        this.parkTime = parkTime;
    }

    public LiveData<ParkingTicketData> getTicketData() {
        return ticketData;
    }

    public long calculateElapsedTime() {
        return System.currentTimeMillis() - parkTime;
    }

    public float calculateTotal(long elapsed, Enums.BillingType billingType) {
        // TODO implement calculate pricing;

        return 10.35f;
    }
}
