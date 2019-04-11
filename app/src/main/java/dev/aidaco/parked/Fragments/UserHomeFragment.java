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
import dev.aidaco.parked.ParkedViewModel;
import dev.aidaco.parked.R;

public class UserHomeFragment extends Fragment {
    private static final String TAG = "UserHomeFragment";
    private ParkedViewModel parkedViewModel;
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
        recyclerViewSpots.setAdapter(spotAdapter);

        parkedViewModel = ViewModelProviders.of(getActivity()).get(ParkedViewModel.class);
        parkedViewModel.getOccupiedSpots().observe(this, new Observer<List<SpotData>>() {
            @Override
            public void onChanged(List<SpotData> spots) {
                spotAdapter.updateSpotData(spots);
            }
        });
        return view;
    }
}
