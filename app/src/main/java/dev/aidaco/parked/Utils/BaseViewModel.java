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

// TODO: 5/14/19 javadoc
public abstract class BaseViewModel extends AndroidViewModel {
    protected UserRepository userRepo;
    protected ParkedRepository parkedRepo;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        this.userRepo = UserRepository.getInstance(application);
        this.parkedRepo = ParkedRepository.getInstance(application);
    }

    public User getCurrentUser() {
        return userRepo.getCurrentUser();
    }

    public void setCurrentUser(User user) {
        userRepo.setCurrentUser(user);
    }

    public Enums.UserType getAccessPrivilege() {
        return userRepo.getAccessPrivilege();
    }

    @SuppressLint("DefaultLocale")
    public String formatElapsedTime(long elapsed) {
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

    public void onLogout() {
        userRepo.resetData();
    }
}
