package dev.aidaco.parked.ParkVehicle;

import android.app.Application;

import androidx.annotation.NonNull;
import dev.aidaco.parked.Utils.AttemptListener;
import dev.aidaco.parked.Utils.BaseViewModel;

public class ParkVehicleViewModel extends BaseViewModel {
    private static final String TAG = "ParkVehicleViewModel";

    private long ticketId;
    private int spotId;
    private int timerLength = 60;
    private boolean isPaused = false;
    private boolean isDone = false;

    public ParkVehicleViewModel(@NonNull Application application) {
        super(application);
    }

    public void onDone(AttemptListener listener) {
        isDone = true;
        parkedRepo.finalizePark(ticketId, listener);
    }

    public void onPause() {
        isPaused = !isPaused;
    }

    public void onCancel(AttemptListener listener) {
        parkedRepo.cancelPark(ticketId, listener);
    }

    public int getSpotId() {
        return spotId;
    }

    public void setSpotId(int spotId) {
        this.spotId = spotId;
    }

    public long getTicketId() {
        return ticketId;
    }

    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }

    public int getTimerLength() {
        return timerLength;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public boolean isDone() {
        return isDone;
    }
}
