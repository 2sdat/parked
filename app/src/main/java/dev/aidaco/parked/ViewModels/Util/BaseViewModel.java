package dev.aidaco.parked.ViewModels.Util;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import dev.aidaco.parked.ViewModels.MasterViewModel;

public abstract class BaseViewModel extends AndroidViewModel {
    protected MasterViewModel masterVM;

    public BaseViewModel(@NonNull Application application, MasterViewModel masterVM) {
        super(application);
        this.masterVM = masterVM;
    }

    public void refreshLiveData() {
    }
}
