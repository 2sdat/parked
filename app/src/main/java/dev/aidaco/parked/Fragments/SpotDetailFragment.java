package dev.aidaco.parked.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import dev.aidaco.parked.Model.Entities.SpotData;
import dev.aidaco.parked.R;
import dev.aidaco.parked.ViewModels.SpotDetailViewModel;

public class SpotDetailFragment extends BaseFragment<SpotDetailViewModel> {
    private static final String TAG = "SpotDetailFragment";

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
        viewModel.getSpotData().observe(this, new Observer<SpotData>() {
            @Override
            public void onChanged(SpotData spotData) {
                viewModel.setTicketId(spotData.ticket.get(0).parkingTicket.getId());
                viewModel.setParkTime(spotData.ticket.get(0).parkingTicket.getStartTime());
                textViewSpotNumber.setText(Integer.toString(spotData.spot.getId()));
                textViewLicensePlate.setText(spotData.ticket.get(0).parkingTicket.getLicensePlate().toString());
                textViewVehicleType.setText(spotData.ticket.get(0).parkingTicket.getVehicleType().getName());
                textViewSpotType.setText(spotData.spot.getSpotType().getName());
                textViewAttendant.setText(spotData.ticket.get(0).attendent.get(0).getFullName());
                textViewParkTime.setText(viewModel.formatTime(getContext(), viewModel.getParkTime()));
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
                viewModel.releaseVehicle();
                navigateToDisplayTicket(viewModel.getTicketId());
            }
        });

        Thread timeElapsedThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isStopped) {
                    try {
                        Thread.sleep(1000);
                        final String newElapsed = viewModel.calculateElapsedTime();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateElapsedTime(newElapsed);
                            }
                        });
                    } catch (InterruptedException e) {
                        Log.d(TAG, "run: timeElapsedThread interrupted during sleep" + e.getMessage());
                    } catch (NullPointerException e) {
                        Log.d(TAG, "run: timeElapsedThread threw NullPointerException" + e.getMessage());
                    }
                }
            }
        });

        timeElapsedThread.start();
    }

    @Override
    public void handleArguments(Bundle argBundle) {
        viewModel.setSpotData(argBundle.getInt("spotId"));
    }

    @Override
    public Class<SpotDetailViewModel> getViewModelClass() {
        return SpotDetailViewModel.class;
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

    private void navigateToDisplayTicket(long ticketId) {
        Bundle argsBundle = new Bundle();
        argsBundle.putLong("ticketId", ticketId);
        navigateActionWithArgs(R.id.action_spotDetailFragment_to_displayTicketFragment, argsBundle);
    }

}
