package dev.aidaco.parked.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public abstract class BaseViewModel extends AndroidViewModel {
    protected MasterViewModel masterVM;

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    public void setMasterViewModel(MasterViewModel masterVM) {
        this.masterVM = masterVM;
    }
}
