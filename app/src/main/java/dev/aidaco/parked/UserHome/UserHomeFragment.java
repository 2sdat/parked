package dev.aidaco.parked.UserHome;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import dev.aidaco.parked.Model.Entities.SpotData;
import dev.aidaco.parked.R;
import dev.aidaco.parked.Utils.BaseFragment;
import dev.aidaco.parked.Utils.ClickListener;

/**
 * Fragment defining the behavior of the user home screen in the Manager work flow.
 *
 * @author Aidan Courtney
 * @see UserHomeViewModel
 */
public class UserHomeFragment extends BaseFragment<UserHomeViewModel> {
    private static final String TAG = "UserHomeFragment";

    private SpotAdapter spotAdapter;
    private FloatingActionButton fabAddVehicle;
    private ImageButton logoutButton;

    /**
     * Initializes the View objects needed to implement requisite behavior.
     *
     * @param view Root view of the inflated layout resource
     */
    @Override
    public void initViews(View view) {
        logoutButton = view.findViewById(R.id.userHome_Logout);
        fabAddVehicle = view.findViewById(R.id.fabAddVehicle);
        RecyclerView recyclerViewSpots = view.findViewById(R.id.recyclerViewSpots);

        spotAdapter = new SpotAdapter();

        recyclerViewSpots.setAdapter(spotAdapter);

        Log.d(TAG, "initViews: views initialized");
    }

    /**
     * Creates the callbacks and listeners for the Views and resources that require them.
     */
    @Override
    public void createCallbacks() {
        spotAdapter.setClickListener(new ClickListener<Integer>() {
            @Override
            public void onClick(Integer spotId) {
                Log.d(TAG, "onClick: Spot card clicked, pos = " + spotId.toString());
                navigateToSpotDetail(spotId);
            }

            @Override
            public void onLongClick(Integer spotId) {
                navigateToSpotDetail(spotId);
                Log.d(TAG, "onClick: Spot card clicked, pos = " + spotId.toString());
            }
        });

        viewModel.getOccupiedSpots().observe(this, new Observer<List<SpotData>>() {
            @Override
            public void onChanged(List<SpotData> spots) {
                Log.d(TAG, "onChanged: recieved LiveData<SpotDat> changed, length = " + Integer.toString(spots.size()));
                spotAdapter.updateSpotData(spots);
                spotAdapter.notifyDataSetChanged();
            }
        });

        fabAddVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: FAB clicked");
                navigateToAddNewVehicle();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.onLogout();
                navigateToLogin();
            }
        });
    }

    /**
     * Returns the Class object of AddNewUserViewModel
     * <p>
     * Called as part of the BaseFragment's viewmodel abstraction.
     *
     * @return The Class object of the AddNewUserViewModel
     */
    @Override
    public Class<UserHomeViewModel> getViewModelClass() {
        return UserHomeViewModel.class;
    }

    /**
     * Called as part of the BaseFragment's initialization abstraction
     *
     * @return The resource ID of the layout resource
     */
    @Override
    public int getLayoutId() {
        return R.layout.fragment_user_home;
    }


    /**
     * Navigate to the detail view for the given spot.
     * @param spotId The ID of the spot to display info for.
     */
    private void navigateToSpotDetail(int spotId) {
        Log.d(TAG, "navigateToSpotDetail: navigating to spot detail with spotId:" + Integer.toString(spotId));
        Bundle argsBundle = new Bundle();
        argsBundle.putInt("spotId", spotId);
        navigateActionWithArgs(R.id.action_userHomeFragment_to_spotDetailFragment, argsBundle);
    }

    /**
     * Navigate to the Add New Vehicle Screen.
     */
    private void navigateToAddNewVehicle() {
        Log.d(TAG, "navigateToAddNewVehicle: navigate to addnewvehicle");
        navigateAction(R.id.action_userHomeFragment_to_addNewVehicleFragment);
    }


    /**
     * Navigate to Login screen and clear backstack
     */
    private void navigateToLogin() {
        navigateToDestAndPopUpTo(R.id.loginFragment, R.id.userHomeFragment);
    }
}
