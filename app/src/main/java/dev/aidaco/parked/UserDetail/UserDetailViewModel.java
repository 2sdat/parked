package dev.aidaco.parked.UserDetail;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import dev.aidaco.parked.Model.Entities.User;
import dev.aidaco.parked.Utils.AttemptListener;
import dev.aidaco.parked.Utils.BaseViewModel;
import dev.aidaco.parked.Utils.DoubleResultListener;

/**
 * Implements the logic required by the UserDetail Fragment.
 *
 * @author Aidan Courtney
 * @see UserDetailFragment
 */
public class UserDetailViewModel extends BaseViewModel {
    private int userId;
    private LiveData<List<User>> user;

    public UserDetailViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * Returns a liveData wrapped single element list containing the user.
     *
     * @return LiveData&ltList&ltUser&gt&gt
     */
    public LiveData<List<User>> getUser() {
        return user;
    }

    /**
     * Used on creation to set the user to be displayed.
     *
     * @param userId ID of the user to display
     */
    public void setUser(int userId) {
        this.userId = userId;
        user = userRepo.getUserById_LiveData(userId);
    }

    /**
     * Starts an AsyncTask to count the number of Active and Completed tickets
     *  for the specified user.
     * @param userId ID of user whose tickets should be counted
     * @param listener DoubleResultListener&ltInt, Int&gt that will be notified with results
     */
    public void getUserTicketCounts(int userId, DoubleResultListener<Integer, Integer> listener) {
        parkedRepo.getUserTicketCounts(userId, listener);
    }

    /**
     * Attempts to toggle the Active status of the user being displayed.
     * @param listener Listener to handle attempt
     */
    public void attemptToggleActive(AttemptListener listener) {
        userRepo.attemptToggleActive(userId, listener);
    }


}
