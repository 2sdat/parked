package dev.aidaco.parked.Utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.text.format.DateUtils;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import dev.aidaco.parked.Model.Entities.User;
import dev.aidaco.parked.Model.Enums;


/**
 * Defines base logic and utilities needed by viewmodels.
 * <p>
 * It is recommended that ViewModels inherit from this class.
 *
 * @author Aidan Courtney
 * @see BaseFragment
 */
public abstract class BaseViewModel extends AndroidViewModel {
    protected UserRepository userRepo;
    protected ParkedRepository parkedRepo;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        this.userRepo = UserRepository.getInstance(application);
        this.parkedRepo = ParkedRepository.getInstance(application);
    }


    /**
     * Returns the user that is currently logged in.
     *
     * @return User
     */
    public User getCurrentUser() {
        return userRepo.getCurrentUser();
    }

    /**
     * Sets the logged in User
     * @param user User to set as current
     */
    public void setCurrentUser(User user) {
        userRepo.setCurrentUser(user);
    }


    /**
     * Returns the UserType of the current user
     * @return UserType
     */
    public Enums.UserType getAccessPrivilege() {
        return userRepo.getAccessPrivilege();
    }

    /**
     * Formats an elapsed time in millis to the format hh:mm:ss
     * @param elapsed Elapsed Millis
     * @return String formatted time
     */
    @SuppressLint("DefaultLocale")
    public String formatElapsedTime(long elapsed) {
        long hours = TimeUnit.MILLISECONDS.toHours(elapsed);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(elapsed) -
                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(elapsed));
        long seconds = TimeUnit.MILLISECONDS.toSeconds(elapsed) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(elapsed));
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    /**
     * Formats the date and time of a given timestamp.
     * @param context Application context needed for locale info
     * @param time Time to be formatted
     * @return String formatted time
     */
    public String formatTime(Context context, long time) {
        return DateUtils.formatDateTime(context, time,
                DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_TIME);
    }

    /**
     * Resets the current user information.
     */
    public void onLogout() {
        userRepo.resetData();
    }
}
