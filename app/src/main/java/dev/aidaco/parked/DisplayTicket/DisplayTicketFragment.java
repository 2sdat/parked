package dev.aidaco.parked.DisplayTicket;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import dev.aidaco.parked.Model.Entities.ParkingTicketData;
import dev.aidaco.parked.R;
import dev.aidaco.parked.Utils.BaseFragment;

/**
 * Fragment defining the behavior of the Display Ticket screen in the User work flow.
 *
 * @author Aidan Courtney
 * @see DisplayTicketViewModel
 */
public class DisplayTicketFragment extends BaseFragment<DisplayTicketViewModel> {
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

    /**
     * Initializes the View objects needed to implement requisite behavior.
     *
     * @param view Root view of the inflated layout resource
     */
    @Override
    public void initViews(View view) {
        buttonBack = view.findViewById(R.id.displayTicket_ToolbarBack);
        ticketId = view.findViewById(R.id.displayTicket_TicketId);
        spotNumber = view.findViewById(R.id.displayTicket_SpotNumber);
        licensePlate = view.findViewById(R.id.displayTicket_LicensePlate);
        vehicleType = view.findViewById(R.id.displayTicket_VehicleType);
        attendant = view.findViewById(R.id.displayTicket_Attendant);
        startTime = view.findViewById(R.id.displayTicket_ParkTime);
        endTime = view.findViewById(R.id.displayTicket_EndTime);
        elapsedTime = view.findViewById(R.id.displayTicket_ElapsedTime);
        billingType = view.findViewById(R.id.displayTicket_BillingType);
        priceTotal = view.findViewById(R.id.displayTicket_PriceTotal);
        buttonDone = view.findViewById(R.id.displayTicket_Done);
    }

    /**
     * Creates the callbacks and listeners for the Views and resources that require them.
     */
    @Override
    public void createCallbacks() {
        viewModel.getTicketData().observe(this, new Observer<ParkingTicketData>() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onChanged(ParkingTicketData parkingTicketData) {
                ticketId.setText(Long.toString(parkingTicketData.parkingTicket.getId()));
                spotNumber.setText(Integer.toString(parkingTicketData.parkingTicket.getSpotId()));
                licensePlate.setText(parkingTicketData.parkingTicket.getLicensePlate().toString());
                vehicleType.setText(parkingTicketData.parkingTicket.getVehicleType().getName());
                attendant.setText(parkingTicketData.attendent.get(0).getFullName());
                startTime.setText(viewModel.formatTime(getContext(), parkingTicketData.parkingTicket.getStartTime()));
                endTime.setText(viewModel.formatTime(getContext(), parkingTicketData.parkingTicket.getEndTime()));
                elapsedTime.setText(viewModel.formatElapsedTime(parkingTicketData.parkingTicket.getEndTime()
                        - parkingTicketData.parkingTicket.getStartTime()));
                billingType.setText(parkingTicketData.parkingTicket.getBillingType().toString());
                priceTotal.setText(String.format("$%.2f", viewModel.calculateTotal(parkingTicketData.parkingTicket.getEndTime() -
                        -parkingTicketData.parkingTicket.getStartTime(), parkingTicketData.parkingTicket.getBillingType())));
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToUserHome();
            }
        });

        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToUserHome();
            }
        });
    }

    /**
     * Handles arguments passed to this fragment upon navigation.
     * <p>
     * Argument bundle should include:
     * "ticketId":long The ID of the ticket to be displayed.
     *
     * @param argBundle Argument bundle.
     */
    @Override
    public void handleArguments(Bundle argBundle) {
        viewModel.setTicket(argBundle.getLong("ticketId"));
    }

    /**
     * Returns the Class object of AddNewUserViewModel
     *
     * Called as part of the BaseFragment's viewmodel abstraction.
     *
     * @return The Class object of the AddNewUserViewModel
     */
    @Override
    public Class<DisplayTicketViewModel> getViewModelClass() {
        return DisplayTicketViewModel.class;
    }

    /**
     * Called as part of the BaseFragment's initialization abstraction
     *
     * @return The resource ID of the layout resource
     */
    @Override
    public int getLayoutId() {
        return R.layout.fragment_display_ticket;
    }


    /**
     * Navigate to UserHome.
     */
    private void navigateToUserHome() {
        navigateActionAndPopUpTo(R.id.action_displayTicketFragment_to_userHomeFragment, R.id.userHomeFragment);
    }
}
