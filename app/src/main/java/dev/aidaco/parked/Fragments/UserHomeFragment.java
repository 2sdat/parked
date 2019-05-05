package dev.aidaco.parked.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import dev.aidaco.parked.Fragments.Accessories.SpotAdapter;
import dev.aidaco.parked.Model.Entities.SpotData;
import dev.aidaco.parked.R;
import dev.aidaco.parked.ViewModels.AddNewVehicleViewModel;
import dev.aidaco.parked.ViewModels.SpotDetailViewModel;
import dev.aidaco.parked.ViewModels.SpotListClickListener;
import dev.aidaco.parked.ViewModels.UserHomeViewModel;

public class UserHomeFragment extends Fragment {
    private static final String TAG = "UserHomeFragment";
    private UserHomeViewModel userHomeViewModel;
    private SpotAdapter spotAdapter;
    private FloatingActionButton fabAddVehicle;
    private RecyclerView recyclerViewSpots;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_home, container, false);
        fabAddVehicle = view.findViewById(R.id.fabAddVehicle);
        recyclerViewSpots = view.findViewById(R.id.recyclerViewSpots);

        spotAdapter = new SpotAdapter();
        spotAdapter.setClickListener(new SpotListClickListener() {
            @Override
            public void onSpotClick(SpotData spotData) {
                ViewModelProviders.of(getActivity()).get(SpotDetailViewModel.class).setSpotData(spotData);
                userHomeViewModel.navigateToSpotDetail();
            }

            @Override
            public void onSpotLongClick(SpotData spotData) {
                onSpotClick(spotData);
            }
        });

        recyclerViewSpots.setAdapter(spotAdapter);

        userHomeViewModel = ViewModelProviders.of(getActivity()).get(UserHomeViewModel.class);
        userHomeViewModel.getOccupiedSpots().observe(this, new Observer<List<SpotData>>() {
            @Override
            public void onChanged(List<SpotData> spots) {
                spotAdapter.updateSpotData(spots);
            }
        });

        fabAddVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewModelProviders.of(getActivity()).get(AddNewVehicleViewModel.class).setCurrentUser(userHomeViewModel.getCurrentUser());
                userHomeViewModel.navigateToAddNewVehicle();
            }
        });

        return view;
    }
}
