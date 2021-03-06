package dev.aidaco.parked.SpotDetail;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import dev.aidaco.parked.Model.Entities.SpotData;
import dev.aidaco.parked.R;
import dev.aidaco.parked.Utils.BaseFragment;

/**
 * Fragment defining the behavior of the Add SpotDetail screen in the User work flow.
 *
 * @author Aidan Courtney
 * @see SpotDetailViewModel
 */
public class SpotDetailFragment extends BaseFragment<SpotDetailViewModel> {
    private static final String TAG = "SpotDetailFragment";

    private ImageButton imageButtonNavigateUp;
    private TextView textViewSpotNumber;
    private TextView textViewLicensePlate;
    private TextView textViewVehicleType;
    private TextView textViewSpotType;
    private TextView textViewAttendant;
    private TextView textViewParkTime;
    private TextView textViewElapsedTime;
    private TextView textViewTotalPrice;
    private Button buttonReleaseVehicle;

    private boolean isStopped = false;

    /**
     * Initializes the View objects needed to implement requisite behavior.
     *
     * @param view Root view of the inflated layout resource
     */
    @Override
    public void initViews(View view) {
        imageButtonNavigateUp = view.findViewById(R.id.spotDetail_ToolbarUp);
        textViewSpotNumber = view.findViewById(R.id.spotDetail_SpotNumber);
        textViewLicensePlate = view.findViewById(R.id.spotDetail_LicensePlate);
        textViewVehicleType = view.findViewById(R.id.spotDetail_VehicleType);
        textViewSpotType = view.findViewById(R.id.spotDetail_Spottype);
        textViewAttendant = view.findViewById(R.id.spotDetail_Attendant);
        textViewParkTime = view.findViewById(R.id.spotDetail_ParkTime);
        textViewElapsedTime = view.findViewById(R.id.spotDetail_ElapsedTime);
        textViewTotalPrice = view.findViewById(R.id.spotDetail_TotalPrice);
        buttonReleaseVehicle = view.findViewById(R.id.spotDetail_Release);
    }

    /**
     * Creates the callbacks and listeners for the Views and resources that require them.
     */
    @Override
    public void createCallbacks() {
        viewModel.getSpotData().observe(this, new Observer<SpotData>() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onChanged(SpotData spotData) {
                viewModel.setTicket(spotData.ticket.get(0).parkingTicket);
                textViewSpotNumber.setText(Integer.toString(spotData.spot.getId()));
                textViewLicensePlate.setText(spotData.ticket.get(0).parkingTicket.getLicensePlate().toString());
                textViewVehicleType.setText(spotData.ticket.get(0).parkingTicket.getVehicleType().getName());
                textViewSpotType.setText(spotData.spot.getSpotType().getName());
                textViewAttendant.setText(spotData.ticket.get(0).attendent.get(0).getFullName());
                textViewParkTime.setText(viewModel.formatTime(getContext(), viewModel.getParkTime()));
                textViewTotalPrice.setText(String.format("$%.2f", spotData.ticket.get(0).parkingTicket.getTotalPrice()));

                if (viewModel.getCurrentPrice() == 0f) {
                    textViewTotalPrice.setText("$--.--");
                }
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
                navigateToDisplayTicket(viewModel.getTicketId());
            }
        });

        Thread timeElapsedThread = new Thread(new Runnable() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void run() {
                while (!isStopped) {
                    try {
                        Thread.sleep(1000);
                        final String newPrice = String.format("$%.2f", viewModel.getCurrentPrice());
                        final String newElapsed = viewModel.calculateElapsedTime();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateElapsedTime(newElapsed);
                                updateCurrentPrice(newPrice);
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


    /**
     * Handles arguments passed in.
     * Requires:
     * "spotId":Int ID of the spotto display.
     *
     * @param argBundle Argument bundle.
     */
    @Override
    public void handleArguments(Bundle argBundle) {
        viewModel.setSpotData(argBundle.getInt("spotId"));
    }

    /**
     * Returns the Class object of AddNewUserViewModel
     *
     * Called as part of the BaseFragment's viewmodel abstraction.
     *
     * @return The Class object of the AddNewUserViewModel
     */
    @Override
    public Class<SpotDetailViewModel> getViewModelClass() {
        return SpotDetailViewModel.class;
    }

    /**
     * Called as part of the BaseFragment's initialization abstraction
     *
     * @return The resource ID of the layout resource
     */
    @Override
    public int getLayoutId() {
        return R.layout.fragment_spot_detail;
    }


    /**
     * Handles cleaning up resources when the fragment is stopped.
     */
    @Override
    public void onStop() {
        isStopped = true;
        super.onStop();
    }


    /**
     * Outputs the updated elapsed time to the elapsedTime TextView.
     * @param newElapsed String containing formatted elapsed time to be displayed.
     */
    private void updateElapsedTime(String newElapsed) {
        textViewElapsedTime.setText(newElapsed);
    }

    /**
     * Outputs the updated current price to the totalPrice TextView.
     *
     * @param currentPrice String containing formatted current price to be displayed.
     */
    private void updateCurrentPrice(String currentPrice) {
        textViewTotalPrice.setText(currentPrice);
    }

    /**
     * Navigate to UserHome
     */
    private void navigateUp() {
        navigateActionAndPopUpTo(R.id.action_spotDetailFragment_to_userHomeFragment, R.id.spotDetailFragment);
    }

    /**
     * Navigates to the DisplayTicketScreen when the vehicle is released.
     * @param ticketId ID of the ticket being released.
     */
    private void navigateToDisplayTicket(long ticketId) {
        Bundle argsBundle = new Bundle();
        argsBundle.putLong("ticketId", ticketId);
        navigateActionWithArgs(R.id.action_spotDetailFragment_to_displayTicketFragment, argsBundle);
    }
}
