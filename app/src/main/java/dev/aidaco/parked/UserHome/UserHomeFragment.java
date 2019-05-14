package dev.aidaco.parked.UserHome;


import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import dev.aidaco.parked.Model.Entities.SpotData;
import dev.aidaco.parked.R;
import dev.aidaco.parked.Utils.BaseFragment;
import dev.aidaco.parked.Utils.ClickListener;

// TODO: 5/14/19 javadoc
public class UserHomeFragment extends BaseFragment<UserHomeViewModel> {
    private static final String TAG = "UserHomeFragment";

    private SpotAdapter spotAdapter;
    private FloatingActionButton fabAddVehicle;
    private RecyclerView recyclerViewSpots;

    /**
     * Initializes the View objects needed to implement requisite behavior.
     *
     * @param view Root view of the inflated layout resource
     */
    @Override
    public void initViews(View view) {
        fabAddVehicle = view.findViewById(R.id.fabAddVehicle);
        recyclerViewSpots = view.findViewById(R.id.recyclerViewSpots);

        spotAdapter = new SpotAdapter();

        recyclerViewSpots.setAdapter(spotAdapter);

        Log.d(TAG, "initViews: views initialized");
    }

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
    }

    @Override
    public Class<UserHomeViewModel> getViewModelClass() {
        return UserHomeViewModel.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_user_home;
    }

    private void navigateToSpotDetail(int spotId) {
        Log.d(TAG, "navigateToSpotDetail: navigating to spot detail with spotId:" + Integer.toString(spotId));
        Bundle argsBundle = new Bundle();
        argsBundle.putInt("spotId", spotId);
        navigateActionWithArgs(R.id.action_userHomeFragment_to_spotDetailFragment, argsBundle);
    }

    private void navigateToAddNewVehicle() {
        Log.d(TAG, "navigateToAddNewVehicle: navigate to addnewvehicle");
        navigateAction(R.id.action_userHomeFragment_to_addNewVehicleFragment);
    }
}
