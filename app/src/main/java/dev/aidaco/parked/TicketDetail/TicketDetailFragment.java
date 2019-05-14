package dev.aidaco.parked.TicketDetail;

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

// TODO: 5/14/19 javadoc
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

    @Override
    public void createCallbacks() {
        viewModel.getTicketData().observe(this, new Observer<ParkingTicketData>() {
            @Override
            public void onChanged(final ParkingTicketData parkingTicketData) {
                viewModel.setParkTime(parkingTicketData.parkingTicket.getStartTime());
                ticketId.setText(Long.toString(parkingTicketData.parkingTicket.getId()));
                spotNumber.setText(Integer.toString(parkingTicketData.parkingTicket.getSpotId()));
                licensePlate.setText(parkingTicketData.parkingTicket.getLicensePlate().toString());
                vehicleType.setText(parkingTicketData.parkingTicket.getVehicleType().getName());
                attendant.setText(parkingTicketData.attendent.get(0).getFullName());
                startTime.setText(viewModel.formatTime(getContext(), parkingTicketData.parkingTicket.getStartTime()));

                if (parkingTicketData.parkingTicket.getEndTime() == ParkingTicket.NULL_END_TIME) {
                    endTime.setText("--");
                    Thread timeElapsedThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (!isStopped) {
                                try {
                                    Thread.sleep(1000);
                                    final String newElapsed = viewModel.formatElapsedTime(viewModel.calculateElapsedTime());
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            elapsedTime.setText(newElapsed);
                                            priceTotal.setText(String.format("$%.2f", viewModel.calculateTotal(viewModel.calculateElapsedTime(), parkingTicketData.parkingTicket.getBillingType())));

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
                    priceTotal.setText(String.format("$%.2f", viewModel.calculateTotal(parkingTicketData.parkingTicket.getEndTime() -
                            parkingTicketData.parkingTicket.getStartTime(), parkingTicketData.parkingTicket.getBillingType())));
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

    private void navigateUp() {
        Log.d(TAG, "navigateUp: ticketdetail -> managerhome");
        navigateActionAndPopUpTo(R.id.action_ticketDetailFragment_to_managerHomeFragment, R.id.ticketDetailFragment);
    }

    @Override
    public void handleArguments(Bundle argBundle) {
        viewModel.setTicketId(argBundle.getLong("ticketId"));
    }

    @Override
    public Class<TicketDetailViewModel> getViewModelClass() {
        return TicketDetailViewModel.class;
    }

    @Override
    public void onStop() {
        isStopped = true;
        super.onStop();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ticket_detail;
    }
}
