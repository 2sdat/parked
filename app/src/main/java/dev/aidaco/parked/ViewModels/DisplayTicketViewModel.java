package dev.aidaco.parked.ViewModels;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.text.format.DateUtils;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import dev.aidaco.parked.Model.Entities.ParkingTicketData;
import dev.aidaco.parked.Model.Enums;
import dev.aidaco.parked.ViewModels.Util.BaseViewModel;

public class DisplayTicketViewModel extends BaseViewModel {
    private LiveData<ParkingTicketData> ticketData;

    public DisplayTicketViewModel(@NonNull Application application, MasterViewModel masterVM) {
        super(application, masterVM);
    }

    public float calculateTotal(long elapsedTime, Enums.BillingType billingType) {
        return 10.35f;
    }

    @SuppressLint("DefaultLocale")
    public String formatElapsedTime(long elapsed) {
        long hours = TimeUnit.MILLISECONDS.toHours(elapsed);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(elapsed) -
                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(elapsed));
        long seconds = TimeUnit.MILLISECONDS.toSeconds(elapsed) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(elapsed));
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public String formatTime(Context context, long time) {
        return DateUtils.formatDateTime(context, time,
                DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_TIME);
    }

    public void setTicket(long ticketId) {
        masterVM.finalizeTicket(ticketId);
        this.ticketData = masterVM.getTicketDataById(ticketId);
    }

    public LiveData<ParkingTicketData> getTicketData() {
        return ticketData;
    }
}
