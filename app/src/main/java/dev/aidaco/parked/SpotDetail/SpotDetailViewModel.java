package dev.aidaco.parked.SpotDetail;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.text.format.DateUtils;

import java.util.concurrent.TimeUnit;

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

    @SuppressLint("DefaultLocale")
    private String formatElapsedTime(long elapsed) {
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
