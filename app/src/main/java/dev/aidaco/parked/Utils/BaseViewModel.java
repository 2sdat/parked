package dev.aidaco.parked.Utils;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public abstract class BaseViewModel extends AndroidViewModel {
    protected MasterViewModel masterVM;

    public BaseViewModel(@NonNull Application application, MasterViewModel masterVM) {
        super(application);
        this.masterVM = masterVM;
    }

    public void refreshLiveData() {
    }
}
