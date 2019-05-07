package dev.aidaco.parked.SpotDetail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import dev.aidaco.parked.Model.Entities.SpotData;
import dev.aidaco.parked.Utils.BaseViewModel;

public class SpotDetailViewModel extends BaseViewModel {
    private static final String TAG = "SpotDetailViewModel";

    private LiveData<SpotData> spotData;
    private long ticketId;
    private long parkTime = 0;

    public SpotDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public String calculateElapsedTime() {
        long elapsed = System.currentTimeMillis() - parkTime;
        return formatElapsedTime(elapsed);
    }

    public void releaseVehicle() {
        // TODO implement finalise ticket and calculate payment total
    }

    public LiveData<SpotData> getSpotData() {
        return spotData;
    }

    public void setSpotData(int spotId) {
        this.spotData = parkedRepo.getSpotDataById(spotId);
    }

    public long getTicketId() {
        return this.ticketId;
    }

    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }

    public long getParkTime() {
        return parkTime;
    }

    public void setParkTime(long parkTime) {
        this.parkTime = parkTime;
    }
}
