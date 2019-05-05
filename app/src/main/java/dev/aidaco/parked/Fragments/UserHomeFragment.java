package dev.aidaco.parked.Fragments;


import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import dev.aidaco.parked.Fragments.Accessories.SpotAdapter;
import dev.aidaco.parked.Interfaces.ClickListener;
import dev.aidaco.parked.Model.Entities.SpotData;
import dev.aidaco.parked.R;
import dev.aidaco.parked.ViewModels.AddNewVehicleViewModel;
import dev.aidaco.parked.ViewModels.SpotDetailViewModel;
import dev.aidaco.parked.ViewModels.UserHomeViewModel;

public class UserHomeFragment extends BaseFragment {
    private static final String TAG = "UserHomeFragment";
    private UserHomeViewModel userHomeViewModel;
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

        userHomeViewModel = ViewModelProviders.of(getActivity()).get(UserHomeViewModel.class);
    }

    @Override
    public void createCallbacks() {
        spotAdapter.setClickListener(new ClickListener<SpotData>() {
            @Override
            public void onClick(SpotData spotData) {
                navigateToSpotDetail(spotData);
            }

            @Override
            public void onLongClick(SpotData spotData) {
                onClick(spotData);
            }
        });

        userHomeViewModel.getOccupiedSpots().observe(this, new Observer<List<SpotData>>() {
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

    private void navigateToSpotDetail(SpotData spotData) {
        ViewModelProviders.of(getActivity()).get(SpotDetailViewModel.class).setSpotData(spotData);
        navigateAction(R.id.action_userHomeFragment_to_spotDetailFragment);
    }

    private void navigateToAddNewVehicle() {
        ViewModelProviders.of(getActivity()).get(AddNewVehicleViewModel.class).setCurrentUser(userHomeViewModel.getCurrentUser());
        navigateAction(R.id.action_userHomeFragment_to_addNewVehicleFragment);
    }
}
