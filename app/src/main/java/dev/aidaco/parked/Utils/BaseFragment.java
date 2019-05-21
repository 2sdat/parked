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


/**
 * It is recommended to inherit all fragments form this base.
 * Presents an abstraction of some of the system details regarding Fragment
 * management and provides useful tools used throughout.
 *
 * @param <T> The ViewModel extending BaseViewModel associated with the fragment
 * @author Aidan Courtney
 * @see BaseViewModel
 */
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

    /**
     * Return the ID for the layout associated with this fragment.
     * ie. R.id.layout_main
     * Must be overridden.
     *
     * @return
     */
    public abstract int getLayoutId();

    /**
     * Called during fragment creation.
     * This is where Views should be instantiated.
     * Must be overridden.
     *
     * @param view the root view of the layout
     */
    public abstract void initViews(View view);


    /**
     * Called during fragment creation.
     * This is where callbacks should be created.
     * Must be overridden.
     */
    public abstract void createCallbacks();

    /**
     * Called during fragment creation to auto-instantiate viewmodel.
     * Should return the Class object of this fragment's ViewModel.
     * ie. MyViewModel.class
     * Must be overridden.
     * @return Class of viewmodel
     */
    public abstract Class<T> getViewModelClass();

    /**
     * Implement logic to handle the bundle if one is passed in.
     * Should be overridden if an instance state bundle is expected.
     *
     * @param savedInstanceState InstanceBundle
     */
    public void handleBundle(Bundle savedInstanceState) {
    }

    /**
     * Handle the arguments bundle if one is passed in.
     * Should be overridden if one is expected.
     *
     * @param argBundle Argument Bundle
     */
    public void handleArguments(Bundle argBundle) {
    }

    /**
     * Shows a SnackBar containing the given message
     * @param message String to display
     */
    public void showSnackbar(String message) {
        ((MainActivity) getActivity()).showSnackBar(message, Snackbar.LENGTH_LONG);
    }

    /**
     * Perform navigation along specified action and clear the backstack
     * up to and including the specified destination.
     * @param actionResId ID of action to navigate
     * @param popUpToResId ID of last item to be removed from nav stack
     */
    public void navigateActionAndPopUpTo(int actionResId, int popUpToResId) {
        NavHostFragment.findNavController(this).navigate(actionResId, null, new NavOptions.Builder().setPopUpTo(popUpToResId, true).build());
    }

    /**
     * Navigate along the specified action.
     * @param actionResId ID of action to navigate
     */
    public void navigateAction(int actionResId) {
        NavHostFragment.findNavController(this).navigate(actionResId);
    }

    /**
     * Navigate the specified action and pass the specified argument bundle.
     * @param actionResId ID of action
     * @param argsBundle Bundle containing arguments
     */
    public void navigateActionWithArgs(int actionResId, Bundle argsBundle) {
        NavHostFragment.findNavController(this).navigate(actionResId, argsBundle);
    }

    /**
     * Navigate to specified destination and clear the backstack up to and including the
     * specified destination.
     * @param destResId ID of destination
     * @param targetResId ID of last destination to be removed from nav stack
     */
    public void navigateToDestAndPopUpTo(int destResId, int targetResId) {
        NavHostFragment.findNavController(this).navigate(destResId, null, new NavOptions.Builder().setPopUpTo(targetResId, true).build());
    }

    /**
     * If the soft keyboard is visible, hide it.
     */
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
