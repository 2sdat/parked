package dev.aidaco.parked.UserHome;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import dev.aidaco.parked.Model.Entities.SpotData;
import dev.aidaco.parked.Utils.BaseViewModel;

// TODO: 5/14/19 javadoc
public class UserHomeViewModel extends BaseViewModel {
    private static final String TAG = "UserHomeViewModel";

    private LiveData<List<SpotData>> occupiedSpots;

    public UserHomeViewModel(@NonNull Application application) {
        super(application);
        occupiedSpots = parkedRepo.getOccupiedSpots();
    }

    public LiveData<List<SpotData>> getOccupiedSpots() {
        return occupiedSpots;
    }
}
