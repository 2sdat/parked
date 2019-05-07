package dev.aidaco.parked.Build;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import dev.aidaco.parked.R;
import dev.aidaco.parked.Utils.BaseFragment;

public class BuildFragment extends BaseFragment<BuildViewModel> {
    private static final String TAG = "BuildFragment";

    private ImageButton buttonBack;
    private EditText carSpots;
    private EditText motoSpots;
    private EditText truckSpots;
    private Button buttonDone;


    @Override
    public void initViews(View view) {
        buttonBack = view.findViewById(R.id.build_ToolbarBack);
        carSpots = view.findViewById(R.id.build_Car);
        motoSpots = view.findViewById(R.id.build_Motorcycle);
        truckSpots = view.findViewById(R.id.build_Truck);
        buttonDone = view.findViewById(R.id.build_Done);
    }

    @Override
    public void createCallbacks() {
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateUp();
            }
        });

        viewModel.setSnackbarLauncher(new BuildViewModel.SnackbarLauncher() {
            @Override
            public void displaySnackbar(String message) {
                showSnackbar(message);
            }
        });

        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numCar = Integer.parseInt(carSpots.getText().toString());
                int numTruck = Integer.parseInt(truckSpots.getText().toString());
                int numMoto = Integer.parseInt(motoSpots.getText().toString());

                // TODO implement input verification

                viewModel.rebuild(numCar, numMoto, numTruck);
                viewModel.resetData();
                navigateToLogin();
            }
        });
    }

    private void navigateToLogin() {
        navigateToDestAndPopUpTo(R.id.loginFragment, R.id.managerHomeFragment);
    }

    public void navigateUp() {
        Log.d(TAG, "navigateUp: build -> managerhome");
        navigateActionAndPopUpTo(R.id.action_buildFragment_to_managerHomeFragment, R.id.buildFragment);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_build;
    }

    @Override
    public Class<BuildViewModel> getViewModelClass() {
        return BuildViewModel.class;
    }
}
