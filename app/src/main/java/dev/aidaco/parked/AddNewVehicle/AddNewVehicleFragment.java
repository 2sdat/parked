package dev.aidaco.parked.AddNewVehicle;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import dev.aidaco.parked.Model.Entities.LicensePlate;
import dev.aidaco.parked.Model.Enums;
import dev.aidaco.parked.R;
import dev.aidaco.parked.Utils.BaseFragment;
import dev.aidaco.parked.Utils.DoubleResultListener;

public class AddNewVehicleFragment extends BaseFragment<AddNewVehicleViewModel> {
    private static final String TAG = "AddNewVehicleFragment";

    private ImageButton imageButtonToolbarUp;
    private EditText editTextLicensePlate;
    private Spinner spinnerLicensePlateState;
    private Spinner spinnerVehicleType;
    private Spinner spinnerBillingType;
    private Button buttonParkVehicle;
    private TextView textViewCancel;

    @Override
    public void initViews(View view) {

        imageButtonToolbarUp = view.findViewById(R.id.addNewVehicle_ToolbarUp);

        editTextLicensePlate = view.findViewById(R.id.addNewVehicle_EditLicensePlate);
        spinnerLicensePlateState = view.findViewById(R.id.addNewVehicle_State);
        spinnerVehicleType = view.findViewById(R.id.addNewVehicle_Type);
        spinnerBillingType = view.findViewById(R.id.addNewVehicle_Billing);
        buttonParkVehicle = view.findViewById(R.id.addNewVehicle_Park);
        textViewCancel = view.findViewById(R.id.addNewVehicle_Cancel);

        ArrayAdapter<Enums.State> arrayAdapterState = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, Enums.State.values());
        arrayAdapterState.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerLicensePlateState.setAdapter(arrayAdapterState);

        ArrayAdapter<Enums.VehicleType> arrayAdapterVehicleType = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, Enums.VehicleType.values());
        arrayAdapterVehicleType.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerVehicleType.setAdapter(arrayAdapterVehicleType);

        ArrayAdapter<Enums.BillingType> arrayAdapterBillingType = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, Enums.BillingType.values());
        arrayAdapterBillingType.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerBillingType.setAdapter(arrayAdapterBillingType);

        Log.d(TAG, "initViews: views init'd");
    }

    @Override
    public void createCallbacks() {
        imageButtonToolbarUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: back button clicked");
                navigateUp();
            }
        });

        buttonParkVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: park vehicle clicked");
                String licensePlateNum = editTextLicensePlate.getText().toString();
                Enums.State licensePlateState = (Enums.State) spinnerLicensePlateState.getSelectedItem();
                Enums.VehicleType vehicletype = (Enums.VehicleType) spinnerVehicleType.getSelectedItem();
                Enums.BillingType billingType = (Enums.BillingType) spinnerBillingType.getSelectedItem();
                LicensePlate licensePlate = new LicensePlate(licensePlateNum, licensePlateState);
                Log.d(TAG, "onClick: vehicle info: " + licensePlate.toString() + " " + billingType.name() + " " + vehicletype.getName());
                DoubleResultListener<Long, Integer> resListener = new DoubleResultListener<Long, Integer>() {
                    @Override
                    public void onResult(Long ticketId, Integer spotId) {
                        Log.d(TAG, "onResult: parkvehicle result recieved");
                        if (ticketId == null) {
                            Log.d(TAG, "onResult: ticketId null");
                            showSnackbar("There are no available spots.");
                            return;
                        }

                        Log.d(TAG, "onResult: ticketId: " + ticketId.toString() + " spotId: " + spotId.toString());

                        navigateToParkVehicle(ticketId, spotId);
                    }
                };

                viewModel.onButtonParkVehicleClicked(licensePlate, vehicletype, billingType, resListener);
            }
        });

        textViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: cancel clicked");
                navigateUp();
            }
        });
    }

    @Override
    public Class<AddNewVehicleViewModel> getViewModelClass() {
        return AddNewVehicleViewModel.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_add_new_vehicle;
    }

    private void navigateToParkVehicle(long ticketId, int spotId) {
        Bundle argsBundle = new Bundle();
        argsBundle.putLong("ticketId", ticketId);
        argsBundle.putInt("spotId", spotId);
        Log.d(TAG, "navigateToParkVehicle: navigate to parkvehicle with ticketId: " + Long.toString(ticketId) + " spotId: " + Integer.toString(spotId));
        navigateActionWithArgs(R.id.action_addNewVehicleFragment_to_parkVehicleFragment, argsBundle);
    }

    private void navigateUp() {
        Log.d(TAG, "navigateUp: navigate to userhome");
        navigateActionAndPopUpTo(R.id.action_addNewVehicleFragment_to_userHomeFragment, R.id.addNewVehicleFragment);
    }
}
