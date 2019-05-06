package dev.aidaco.parked.Fragments;


import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import dev.aidaco.parked.Fragments.Accessories.SpotAdapter;
import dev.aidaco.parked.Interfaces.ClickListener;
import dev.aidaco.parked.Model.Entities.SpotData;
import dev.aidaco.parked.R;
import dev.aidaco.parked.ViewModels.UserHomeViewModel;

public class UserHomeFragment extends BaseFragment<UserHomeViewModel> {
    private static final String TAG = "UserHomeFragment";
    private SpotAdapter spotAdapter;
    private FloatingActionButton fabAddVehicle;
    private RecyclerView recyclerViewSpots;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_user_home;
    }

    @Override
    public void initViews(View view) {
        fabAddVehicle = view.findViewById(R.id.fabAddVehicle);
        recyclerViewSpots = view.findViewById(R.id.recyclerViewSpots);

        spotAdapter = new SpotAdapter();

        recyclerViewSpots.setAdapter(spotAdapter);
    }

    @Override
    public void createCallbacks() {
        spotAdapter.setClickListener(new ClickListener<Integer>() {
            @Override
            public void onClick(Integer spotId) {
                navigateToSpotDetail(spotId);
            }

            @Override
            public void onLongClick(Integer spotId) {
                navigateToSpotDetail(spotId);
            }
        });

        viewModel.getOccupiedSpots().observe(this, new Observer<List<SpotData>>() {
            @Override
            public void onChanged(List<SpotData> spots) {
                spotAdapter.updateSpotData(spots);
            }
        });

        fabAddVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToAddNewVehicle();
            }
        });
    }

    @Override
    public Class<UserHomeViewModel> getViewModelClass() {
        return UserHomeViewModel.class;
    }

    private void navigateToSpotDetail(int spotId) {
        Bundle argsBundle = new Bundle();
        argsBundle.putInt("spotId", spotId);
        navigateActionWithArgs(R.id.action_userHomeFragment_to_spotDetailFragment, argsBundle);
    }

    private void navigateToAddNewVehicle() {
        navigateAction(R.id.action_userHomeFragment_to_addNewVehicleFragment);
    }
}
