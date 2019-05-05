package dev.aidaco.parked.ViewModels;

import android.annotation.SuppressLint;
import android.app.Application;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import dev.aidaco.parked.Model.Entities.SpotData;

public class SpotDetailViewModel extends AndroidViewModel {
    private SpotData spotData;
    private MutableLiveData<SpotData> spotDataLive;

    public SpotDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public String calculateElapsedTime() {
        long elapsed = spotData.ticket.get(0).parkingTicket.getStartTime() - System.currentTimeMillis();
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
        return spotDataLive;
    }

    public void setSpotData(SpotData spotData) {
        this.spotData = spotData;
        this.spotDataLive.postValue(this.spotData);
    }
}
