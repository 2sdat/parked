package dev.aidaco.parked.ViewModels;

import android.app.Application;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import dev.aidaco.parked.Model.Entities.SpotData;

public class SpotDetailViewModel extends AndroidViewModel {
    private SpotData spotData;

    public SpotDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public void navigateUp() {
        // TODO implement navigate to user home
    }

    public void setSpotData(SpotData spotData) {
        this.spotData = spotData;
    }

    public void releaseVehicle() {
        // TODO implement finalise ticket and calculate payment total

        navigateUp();
    }

    public String calculateElapsedTime() {
        long elapsed = spotData.ticket.get(0).parkingTicket.getStartTime() - System.currentTimeMillis();
        return String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(elapsed),
                TimeUnit.MILLISECONDS.toMinutes(elapsed) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(elapsed)),
                TimeUnit.MILLISECONDS.toSeconds(elapsed) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(elapsed)));
    }
}
