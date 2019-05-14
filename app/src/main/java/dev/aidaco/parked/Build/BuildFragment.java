package dev.aidaco.parked.Build;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import dev.aidaco.parked.R;
import dev.aidaco.parked.Utils.BaseFragment;

/**
 * Fragment defining the behavior of the Build screen in the Manager work flow.
 *
 * @author Aidan Courtney
 * @see BuildViewModel
 */
public class BuildFragment extends BaseFragment<BuildViewModel> {
    private static final String TAG = "BuildFragment";

    private ImageButton buttonBack;
    private EditText carSpots;
    private EditText motoSpots;
    private EditText truckSpots;
    private Button buttonDone;

    /**
     * Initializes the View objects needed to implement requisite behavior.
     *
     * @param view Root view of the inflated layout resource
     */
    @Override
    public void initViews(View view) {
        buttonBack = view.findViewById(R.id.build_ToolbarBack);
        carSpots = view.findViewById(R.id.build_Car);
        motoSpots = view.findViewById(R.id.build_Motorcycle);
        truckSpots = view.findViewById(R.id.build_Truck);
        buttonDone = view.findViewById(R.id.build_Done);
    }

    /**
     * Creates the callbacks and listeners for the Views and resources that require them.
     */
    @Override
    public void createCallbacks() {
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateUp();
            }
        });

        viewModel.setSnackbarLauncher(new BuildViewModel.SnackbarLauncher() {
            @Override
            public void displaySnackbar(String message) {
                showSnackbar(message);
            }
        });

        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numCar = Integer.parseInt(carSpots.getText().toString());
                int numTruck = Integer.parseInt(truckSpots.getText().toString());
                int numMoto = Integer.parseInt(motoSpots.getText().toString());

                // TODO implement input verification

                viewModel.rebuild(numCar, numMoto, numTruck);
                viewModel.resetData();
                navigateToLogin();
            }
        });
    }


    /**
     * Navigate to the Login screen.
     * <p>
     * Called after the database has been rebuilt.
     */
    private void navigateToLogin() {
        navigateToDestAndPopUpTo(R.id.loginFragment, R.id.managerHomeFragment);
    }

    /**
     * Navigate to ManagerHome.
     * <p>
     * Called when the back button is pressed.
     */
    private void navigateUp() {
        Log.d(TAG, "navigateUp: build -> managerhome");
        navigateActionAndPopUpTo(R.id.action_buildFragment_to_managerHomeFragment, R.id.buildFragment);
    }

    /**
     * Called as part of the BaseFragment's initialization abstraction
     *
     * @return The resource ID of the layout resource
     */
    @Override
    public int getLayoutId() {
        return R.layout.fragment_build;
    }

    /**
     * Returns the Class object of AddNewUserViewModel
     *
     * Called as part of the BaseFragment's viewmodel abstraction.
     *
     * @return The Class object of the AddNewUserViewModel
     */
    @Override
    public Class<BuildViewModel> getViewModelClass() {
        return BuildViewModel.class;
    }
}
