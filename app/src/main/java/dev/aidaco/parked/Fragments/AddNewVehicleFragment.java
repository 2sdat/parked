package dev.aidaco.parked.Fragments;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import dev.aidaco.parked.Interfaces.ResultListener;
import dev.aidaco.parked.Model.Entities.LicensePlate;
import dev.aidaco.parked.Model.Entities.SpotData;
import dev.aidaco.parked.Model.Enums;
import dev.aidaco.parked.R;
import dev.aidaco.parked.ViewModels.AddNewVehicleViewModel;

public class AddNewVehicleFragment extends BaseFragment {
    private AddNewVehicleViewModel addNewVehicleViewModel;

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
        addNewVehicleViewModel = ViewModelProviders.of(getActivity()).get(AddNewVehicleViewModel.class);

        toolbarAddNewVehicle = view.findViewById(R.id.toolbarAddNewVehicle);
        imageButtonToolbarUp = view.findViewById(R.id.imageButtonToolbarUp);

        editTextLicensePlate = view.findViewById(R.id.editTextLicensePlate);
        spinnerLicensePlateState = view.findViewById(R.id.spinnerLicensePlateState);
        spinnerVehicleType = view.findViewById(R.id.spinnerVehicleType);
        spinnerBillingType = view.findViewById(R.id.spinnerBillingType);
        buttonParkVehicle = view.findViewById(R.id.buttonParkVehicle);
        textViewCancel = view.findViewById(R.id.textViewAddNewVehicleCancel);

        arrayAdapterState = new ArrayAdapter<Enums.State>(getActivity(), android.R.layout.simple_list_item_1, Enums.State.values());
        arrayAdapterState.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLicensePlateState.setAdapter(arrayAdapterState);

        arrayAdapterVehicleType = new ArrayAdapter<Enums.VehicleType>(getActivity(), android.R.layout.simple_list_item_1, Enums.VehicleType.values());
        arrayAdapterVehicleType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVehicleType.setAdapter(arrayAdapterVehicleType);

        arrayAdapterBillingType = new ArrayAdapter<Enums.BillingType>(getActivity(), android.R.layout.simple_list_item_1, Enums.BillingType.values());
        arrayAdapterBillingType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBillingType.setAdapter(arrayAdapterBillingType);
    }

    @Override
    public void createCallbacks() {
        imageButtonToolbarUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateUp();
            }
        });

        buttonParkVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String licensePlateNum = editTextLicensePlate.getText().toString();
                Enums.State licensePlateState = (Enums.State) spinnerLicensePlateState.getSelectedItem();
                Enums.VehicleType vehicletype = (Enums.VehicleType) spinnerVehicleType.getSelectedItem();
                Enums.BillingType billingType = (Enums.BillingType) spinnerBillingType.getSelectedItem();
                LicensePlate licensePlate = new LicensePlate(licensePlateNum, licensePlateState);
                ResultListener<SpotData> resListener = new ResultListener<SpotData>() {
                    @Override
                    public void onResult(SpotData spotData) {
                        if (spotData == null) {
                            showSnackbar("There are no available spots.");
                            return;
                        }

                        navigateToParkVehicle();
                    }
                };

                addNewVehicleViewModel.onButtonParkVehicleClicked(licensePlate, vehicletype, billingType);
            }
        });

        textViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateUp();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_add_new_vehicle;
    }

    private void navigateToParkVehicle() {

    }

    private void navigateUp() {
        navigateActionAndPopUpTo(R.id.action_addNewVehicleFragment_to_userHomeFragment, R.id.addNewVehicleFragment);
    }
}
