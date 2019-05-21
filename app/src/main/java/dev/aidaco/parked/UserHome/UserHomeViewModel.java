package dev.aidaco.parked.UserHome;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import dev.aidaco.parked.Model.Entities.SpotData;
import dev.aidaco.parked.Utils.BaseViewModel;


/**
 * ViewModel implementing the logic needed to coordinate the UserHome fragment.
 *
 * @author Aidan Courtney
 * @see UserHomeFragment
 */
public class UserHomeViewModel extends BaseViewModel {
    private static final String TAG = "UserHomeViewModel";

    private LiveData<List<SpotData>> occupiedSpots;

    public UserHomeViewModel(@NonNull Application application) {
        super(application);
        occupiedSpots = parkedRepo.getOccupiedSpots();
    }

    /**
     * Returns a LiveData wrapped list of all occupied spots
     *
     * @return LiveData&ltList&ltSpotData&gt&gt
     */
    public LiveData<List<SpotData>> getOccupiedSpots() {
        return occupiedSpots;
    }
}
