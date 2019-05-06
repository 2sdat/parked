package dev.aidaco.parked.ViewModels;

import android.annotation.SuppressLint;
import android.app.Application;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import dev.aidaco.parked.Model.Entities.SpotData;

public class SpotDetailViewModel extends BaseViewModel {
    private LiveData<SpotData> spotData;
    private long parkTime = 0;

    public SpotDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public String calculateElapsedTime() {
        long elapsed = parkTime - System.currentTimeMillis();
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

    public LiveData<SpotData> getSpotData() {
        return spotData;
    }

    public void setSpotData(int spotId) {
        this.spotData = masterVM.getSpotDataById(spotId);
    }

    public long getParkTime() {
        return parkTime;
    }

    public void setParkTime(long parkTime) {
        this.parkTime = parkTime;
    }
}
