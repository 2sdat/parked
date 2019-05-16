package dev.aidaco.parked.Utils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import dev.aidaco.parked.MainActivity;

// TODO: 5/14/19 javadoc
public abstract class BaseFragment<T extends BaseViewModel> extends Fragment {
    private static final String TAG = "BaseFragment";
    protected T viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        viewModel = ViewModelProviders.of(this).get(getViewModelClass());
        handleArguments(getArguments());
        initViews(view);
        handleBundle(savedInstanceState);
        createCallbacks();
        return view;
    }

    public abstract int getLayoutId();

    public abstract void initViews(View view);

    public abstract void createCallbacks();

    public abstract Class<T> getViewModelClass();

    public void handleBundle(Bundle savedInstanceState) {
    }

    public void handleArguments(Bundle argBundle) {
    }

    public void showSnackbar(String message) {
        ((MainActivity) getActivity()).showSnackBar(message, Snackbar.LENGTH_LONG);
    }

    public void navigateActionAndPopUpTo(int actionResId, int popUpToResId) {
        NavHostFragment.findNavController(this).navigate(actionResId, null, new NavOptions.Builder().setPopUpTo(popUpToResId, true).build());
    }

    public void navigateAction(int actionResId) {
        NavHostFragment.findNavController(this).navigate(actionResId);
    }

    public void navigateActionWithArgs(int actionResId, Bundle argsBundle) {
        NavHostFragment.findNavController(this).navigate(actionResId, argsBundle);
    }

    public void navigateToDestAndPopUpTo(int destResId, int targetResId) {
        NavHostFragment.findNavController(this).navigate(destResId, null, new NavOptions.Builder().setPopUpTo(targetResId, true).build());
    }

    public void hideKeyboard() {
        Activity activity = getActivity();
        InputMethodManager inputManager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        View currentFocusedView = activity.getCurrentFocus();
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
