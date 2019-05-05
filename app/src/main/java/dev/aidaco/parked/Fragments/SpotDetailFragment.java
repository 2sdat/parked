package dev.aidaco.parked.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import dev.aidaco.parked.R;
import dev.aidaco.parked.ViewModels.SpotDetailViewModel;

public class SpotDetailFragment extends Fragment {
    private SpotDetailViewModel spotDetailViewModel;
    private Toolbar toolbar;
    private ImageButton imageButtonNavigateUp;
    private TextView textViewSpotNumber;
    private TextView textViewLicensePlate;
    private TextView textViewVehicleType;
    private TextView textViewSpotType;
    private TextView textViewAttendant;
    private TextView textViewParkTime;
    private TextView textViewElapsedTime;
    private Button buttonReleaseVehicle;

    private boolean isStopped = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        spotDetailViewModel = ViewModelProviders.of(getActivity()).get(SpotDetailViewModel.class);
        toolbar = view.findViewById(R.id.toolbarSpotDetail);
        imageButtonNavigateUp = view.findViewById(R.id.imageButtonSpotDetailToolbarUp);
        textViewSpotNumber = view.findViewById(R.id.textViewSpotDetailSpotNumber);
        textViewLicensePlate = view.findViewById(R.id.textViewSpotDetailLicensePlate);
        textViewVehicleType = view.findViewById(R.id.textViewSpotDetailVehicleType);
        textViewSpotType = view.findViewById(R.id.textViewSpotDetailSpotType);
        textViewAttendant = view.findViewById(R.id.textViewSpotDetailAttendant);
        textViewParkTime = view.findViewById(R.id.textViewSpotDetailParkTime);
        textViewElapsedTime = view.findViewById(R.id.textViewSpotDetailElapsedTime);
        buttonReleaseVehicle = view.findViewById(R.id.buttonSpotDetailReleaseVehicle);

        imageButtonNavigateUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spotDetailViewModel.navigateUp();
            }
        });

        buttonReleaseVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spotDetailViewModel.releaseVehicle();
            }
        });

        Thread timeElapsedThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isStopped) {
                    try {
                        Thread.sleep(1000);
                        final String newElapsed = spotDetailViewModel.calculateElapsedTime();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateElapsedTime(newElapsed);
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        timeElapsedThread.start();

        return view;
    }

    @Override
    public void onStop() {
        isStopped = true;
        super.onStop();
    }

    private void updateElapsedTime(String newElapsed) {
        textViewElapsedTime.setText(newElapsed);
    }

}
