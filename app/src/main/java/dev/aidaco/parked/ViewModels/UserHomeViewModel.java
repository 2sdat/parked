package dev.aidaco.parked.ViewModels;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import dev.aidaco.parked.Model.Entities.SpotData;

public class UserHomeViewModel extends BaseViewModel {
    private static final String TAG = "UserHomeViewModel";

    private LiveData<List<SpotData>> occupiedSpots;

    public UserHomeViewModel(@NonNull Application application) {
        super(application);
        occupiedSpots = masterVM.getOccupiedSpots();
    }

    public LiveData<List<SpotData>> getOccupiedSpots() {
        return occupiedSpots;
    }
}
