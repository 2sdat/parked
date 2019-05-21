package dev.aidaco.parked.TicketDetail;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import dev.aidaco.parked.Model.Entities.ParkingTicket;
import dev.aidaco.parked.Model.Entities.ParkingTicketData;
import dev.aidaco.parked.R;
import dev.aidaco.parked.Utils.BaseFragment;

/**
 * Fragment defining the behavior of the TicketDetail screen in the Manager work flow.
 *
 * @author Aidan Courtney
 * @see TicketDetailViewModel
 */
public class TicketDetailFragment extends BaseFragment<TicketDetailViewModel> {
    private static final String TAG = "TicketDetailFragment";

    private ImageButton buttonBack;
    private TextView ticketId;
    private TextView spotNumber;
    private TextView licensePlate;
    private TextView vehicleType;
    private TextView attendant;
    private TextView startTime;
    private TextView endTime;
    private TextView elapsedTime;
    private TextView billingType;
    private TextView priceTotal;
    private Button buttonDone;

    private boolean isStopped = false;

    /**
     * Initializes the View objects needed to implement requisite behavior.
     *
     * @param view Root view of the inflated layout resource
     */
    @Override
    public void initViews(View view) {
        buttonBack = view.findViewById(R.id.ticketDetail_ToolbarBack);
        ticketId = view.findViewById(R.id.ticketDetail_TicketId);
        spotNumber = view.findViewById(R.id.ticketDetail_SpotNumber);
        licensePlate = view.findViewById(R.id.ticketDetail_LicensePlate);
        vehicleType = view.findViewById(R.id.ticketDetail_VehicleType);
        attendant = view.findViewById(R.id.ticketDetail_Attendant);
        startTime = view.findViewById(R.id.ticketDetail_ParkTime);
        endTime = view.findViewById(R.id.ticketDetail_EndTime);
        elapsedTime = view.findViewById(R.id.ticketDetail_ElapsedTime);
        billingType = view.findViewById(R.id.ticketDetail_BillingType);
        priceTotal = view.findViewById(R.id.ticketDetail_PriceTotal);
        buttonDone = view.findViewById(R.id.ticketDetail_Done);
    }

    /**
     * Creates the callbacks and listeners for the Views and resources that require them.
     */
    @Override
    public void createCallbacks() {
        viewModel.getTicketData().observe(this, new Observer<ParkingTicketData>() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onChanged(final ParkingTicketData parkingTicketData) {
                viewModel.setTicket(parkingTicketData.parkingTicket);
                ticketId.setText(Long.toString(parkingTicketData.parkingTicket.getId()));
                spotNumber.setText(Integer.toString(parkingTicketData.parkingTicket.getSpotId()));
                licensePlate.setText(parkingTicketData.parkingTicket.getLicensePlate().toString());
                vehicleType.setText(parkingTicketData.parkingTicket.getVehicleType().getName());
                attendant.setText(parkingTicketData.attendent.get(0).getFullName());
                startTime.setText(viewModel.formatTime(getContext(), parkingTicketData.parkingTicket.getStartTime()));

                if (parkingTicketData.parkingTicket.getEndTime() == ParkingTicket.NULL_END_TIME) {
                    endTime.setText("--");
                    Thread timeElapsedThread = new Thread(new Runnable() {
                        @SuppressWarnings("ConstantConditions")
                        @Override
                        public void run() {
                            while (!isStopped) {
                                try {
                                    Thread.sleep(1000);
                                    final String newPrice = String.format("$%.2f", viewModel.getCurrentPrice());
                                    final String newElapsed = viewModel.formatElapsedTime(viewModel.calculateElapsedTime());
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
                } else {
                    endTime.setText(viewModel.formatTime(getContext(), parkingTicketData.parkingTicket.getEndTime()));
                    elapsedTime.setText(viewModel.formatElapsedTime(parkingTicketData.parkingTicket.getEndTime()
                            - parkingTicketData.parkingTicket.getStartTime()));
                    priceTotal.setText(String.format("$%.2f", parkingTicketData.parkingTicket.getTotalPrice()));
                }
                billingType.setText(parkingTicketData.parkingTicket.getBillingType().toString());
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateUp();
            }
        });
        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateUp();
            }
        });
    }

    /**
     * Navigate to ManagerHome.
     */
    private void navigateUp() {

        Log.d(TAG, "navigateUp: ticketdetail -> managerhome");
        navigateActionAndPopUpTo(R.id.action_ticketDetailFragment_to_managerHomeFragment, R.id.ticketDetailFragment);
    }

    /**
     * Parses arguments passed in a Bundle.
     * Requires:
     * "ticketId":Long ID of the ticket to display.
     *
     * @param argBundle Argument bundle.
     */
    @Override
    public void handleArguments(Bundle argBundle) {
        viewModel.setTicketId(argBundle.getLong("ticketId"));
    }

    /**
     * Returns the Class object of AddNewUserViewModel
     *
     * Called as part of the BaseFragment's viewmodel abstraction.
     *
     * @return The Class object of the AddNewUserViewModel
     */
    @Override
    public Class<TicketDetailViewModel> getViewModelClass() {
        return TicketDetailViewModel.class;
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
     * Outputs the updated current price to the totalPrice TextView.
     *
     * @param currentPrice String containing formatted current price to be displayed.
     */
    private void updateCurrentPrice(String currentPrice) {
        priceTotal.setText(currentPrice);
    }

    /**
     * Outputs the updated elapsed time to the elapsedTime TextView.
     *
     * @param newElapsed String containing formatted elapsed time to be displayed.
     */
    private void updateElapsedTime(String newElapsed) {
        elapsedTime.setText(newElapsed);
    }


    /**
     * Called as part of the BaseFragment's initialization abstraction
     *
     * @return The resource ID of the layout resource
     */
    @Override
    public int getLayoutId() {
        return R.layout.fragment_ticket_detail;
    }
}
