package dev.aidaco.parked.Fragments;

import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import dev.aidaco.parked.Model.Entities.SpotData;
import dev.aidaco.parked.R;
import dev.aidaco.parked.ViewModels.SpotDetailViewModel;

public class SpotDetailFragment extends BaseFragment {
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
    public int getLayoutId() {
        return R.layout.fragment_spot_detail;
    }

    @Override
    public void initViews(View view) {
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
    }

    @Override
    public void createCallbacks() {
        spotDetailViewModel.getSpotData().observe(this, new Observer<SpotData>() {
            @Override
            public void onChanged(SpotData spotData) {
                textViewSpotNumber.setText(spotData.spot.getId());
                textViewLicensePlate.setText(spotData.ticket.get(0).parkingTicket.getLicensePlate().toString());
                textViewVehicleType.setText(spotData.ticket.get(0).parkingTicket.getVehicleType().getName());
                textViewSpotType.setText(spotData.spot.getSpotType().getName());
                textViewAttendant.setText(spotData.ticket.get(0).attendent.get(0).getFullName());
                textViewParkTime.setText(DateUtils.formatDateTime(getContext(), spotData.ticket.get(0).parkingTicket.getStartTime(),
                        DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_TIME));
            }
        });

        imageButtonNavigateUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateUp();
            }
        });

        buttonReleaseVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spotDetailViewModel.releaseVehicle();
                navigateToDisplayTicket();
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
    }

    @Override
    public void onStop() {
        isStopped = true;
        super.onStop();
    }

    private void updateElapsedTime(String newElapsed) {
        textViewElapsedTime.setText(newElapsed);
    }

    private void navigateUp() {
        navigateActionAndPopUpTo(R.id.action_spotDetailFragment_to_userHomeFragment, R.id.spotDetailFragment);
    }

    private void navigateToDisplayTicket() {
        // TODO implement displayticket and naviagte to it here
    }

}
