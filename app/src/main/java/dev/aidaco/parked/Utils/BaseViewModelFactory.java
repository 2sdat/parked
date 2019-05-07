package dev.aidaco.parked.Utils;

import android.app.Application;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class BaseViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private static final String TAG = "BaseViewModelFactory";

    private Application application;
    private MasterViewModel masterVM;

    public BaseViewModelFactory(Application application, MasterViewModel masterVM) {
        this.application = application;
        this.masterVM = masterVM;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (BaseViewModel.class.isAssignableFrom(modelClass)) {
            try {
                return modelClass.getConstructor(Application.class, MasterViewModel.class).newInstance(application, masterVM);
            } catch (IllegalAccessException e) {
                Log.d(TAG, "create: IllegalArgumentException" + e.getMessage());
            } catch (InstantiationException e) {
                Log.d(TAG, "create: InstantiationException" + e.getMessage());
            } catch (InvocationTargetException e) {
                Log.d(TAG, "create: InvocationTargetException" + e.getMessage());
            } catch (NoSuchMethodException e) {
                Log.d(TAG, "create: NoSuchMethodException" + e.getMessage());
            }
        }

        return super.create(modelClass);
    }
}
