package dev.aidaco.parked.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import dev.aidaco.parked.Interfaces.DoubleResultListener;
import dev.aidaco.parked.Model.Entities.LicensePlate;
import dev.aidaco.parked.Model.Enums;
import dev.aidaco.parked.R;
import dev.aidaco.parked.ViewModels.AddNewVehicleViewModel;

public class AddNewVehicleFragment extends BaseFragment<AddNewVehicleViewModel> {
    private static final String TAG = "AddNewVehicleFragment";
    private ArrayAdapter<Enums.State> arrayAdapterState;
    private ArrayAdapter<Enums.VehicleType> arrayAdapterVehicleType;
    private ArrayAdapter<Enums.BillingType> arrayAdapterBillingType;

    private Toolbar toolbarAddNewVehicle;
    private ImageButton imageButtonToolbarUp;
    private EditText editTextLicensePlate;
    private Spinner spinnerLicensePlateState;
    private Spinner spinnerVehicleType;
    private Spinner spinnerBillingType;
    private Button buttonParkVehicle;
    private TextView textViewCancel;

    @Override
    public void initViews(View view) {

        toolbarAddNewVehicle = view.findViewById(R.id.toolbarAddNewVehicle);
        imageButtonToolbarUp = view.findViewById(R.id.imageButtonToolbarUp);

        editTextLicensePlate = view.findViewById(R.id.editTextLicensePlate);
        spinnerLicensePlateState = view.findViewById(R.id.spinnerLicensePlateState);
        spinnerVehicleType = view.findViewById(R.id.spinnerVehicleType);
        spinnerBillingType = view.findViewById(R.id.spinnerBillingType);
        buttonParkVehicle = view.findViewById(R.id.buttonParkVehicle);
        textViewCancel = view.findViewById(R.id.textViewAddNewVehicleCancel);

        arrayAdapterState = new ArrayAdapter<Enums.State>(getActivity(), R.layout.spinner_item, Enums.State.values());
        arrayAdapterState.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerLicensePlateState.setAdapter(arrayAdapterState);

        arrayAdapterVehicleType = new ArrayAdapter<Enums.VehicleType>(getActivity(), R.layout.spinner_item, Enums.VehicleType.values());
        arrayAdapterVehicleType.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerVehicleType.setAdapter(arrayAdapterVehicleType);

        arrayAdapterBillingType = new ArrayAdapter<Enums.BillingType>(getActivity(), R.layout.spinner_item, Enums.BillingType.values());
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
