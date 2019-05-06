package dev.aidaco.parked.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import dev.aidaco.parked.Interfaces.AttemptListener;
import dev.aidaco.parked.R;
import dev.aidaco.parked.ViewModels.ParkVehicleViewModel;

public class ParkVehicleFragment extends BaseFragment<ParkVehicleViewModel> {
    private static final String TAG = "ParkVehicleFragment";

    private TextView textViewSpotNumber;
    private TextView textViewTimer;
    private TextView textViewDone;
    private TextView textViewCancel;

    @Override
    public void initViews(View view) {
        textViewSpotNumber = view.findViewById(R.id.parkVehicle_SpotNumber);
        textViewTimer = view.findViewById(R.id.parkVehicle_Timer);
        textViewDone = view.findViewById(R.id.parkVehicle_Done);
        textViewCancel = view.findViewById(R.id.parkVehicle_Cancel);

        textViewSpotNumber.setText(Integer.toString(viewModel.getSpotId()));
        Log.d(TAG, "initViews: views init'd");
    }

    @Override
    public void createCallbacks() {
        textViewDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: done clicked");
                viewModel.onDone(new AttemptListener() {
                    @Override
                    public void onReturnCode(int statusCode) {
                        Log.d(TAG, "onReturnCode: finalizepark callback recieved with code: " + Integer.toString(statusCode));
                        onFinished(statusCode);
                    }
                });
            }
        });

        textViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: cancel clicked");
                viewModel.onCancel(new AttemptListener() {
                    @Override
                    public void onReturnCode(int statusCode) {
                        Log.d(TAG, "onReturnCode: cancelpark callback recieved with code: " + Integer.toString(statusCode));
                        onFinished(statusCode);
                    }
                });
            }
        });

        textViewTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: timer clicked");
                viewModel.onPause();
                if (viewModel.isPaused()) {
                    Log.d(TAG, "onClick: timer is paused");
                    textViewTimer.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                } else {
                    Log.d(TAG, "onClick: timer is not paused");
                    textViewTimer.setTextColor(ContextCompat.getColor(getContext(), R.color.colorText));
                }
            }
        });

        Thread timerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d(TAG, "run: start timerThread loop");
                    int countDown = viewModel.getTimerLength();
                    while (countDown >= 0 && !viewModel.isDone()) {
                        Log.d(TAG, "run: timerThread countDown = " + Integer.toString(countDown));
                        if (!viewModel.isPaused()) {

                            final String text = Integer.toString(countDown);
                            final boolean isDone = countDown == 0;
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d(TAG, "run: updating timer on ui thread: " + text);
                                    textViewTimer.setText(text);
                                    if (isDone) {
                                        Log.d(TAG, "run: timer ran out");
                                        viewModel.onDone(new AttemptListener() {
                                            @Override
                                            public void onReturnCode(int statusCode) {
                                                Log.d(TAG, "onReturnCode: finalize park result recieved by timerThread listener");
                                                onFinished(statusCode);
                                            }
                                        });
                                    }
                                }
                            });
                            countDown--;
                        }
                        Thread.sleep(1000);
                    }
                } catch (NullPointerException e) {
                    Log.d(TAG, "run: timerThread threw NullPointerException" + e.getMessage());
                } catch (InterruptedException e) {
                    Log.d(TAG, "run: timerThread threw InterruptedException" + e.getMessage());
                }
            }
        });
        timerThread.start();
    }

    @Override
    public void handleArguments(Bundle argBundle) {
        viewModel.setTicketId(argBundle.getLong("ticketId"));
        viewModel.setSpotId(argBundle.getInt("spotId"));
        Log.d(TAG, "handleArguments: handled bundle argument: ticketId = " + Long.toString(viewModel.getTicketId()) + " spotId = " + Integer.toString(viewModel.getSpotId()));
    }

    @Override
    public Class<ParkVehicleViewModel> getViewModelClass() {
        return ParkVehicleViewModel.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_park_vehicle;
    }

    public void onFinished(int resultCode) {
        Log.d(TAG, "onFinished: result recieved: " + Integer.toString(resultCode));
        if (resultCode == AttemptListener.POS_SUCCESS) {
            Log.d(TAG, "onFinished: resultCode pos_success");
            showSnackbar("Vehicle parked.");
        } else if (resultCode == AttemptListener.NEG_SUCCESS) {
            Log.d(TAG, "onFinished: resultCode neg_success");
            showSnackbar("Cancelled.");
        } else {
            Log.d(TAG, "onFinished: resultCode fail");
            showSnackbar("Something went wrong, please try again.");
        }

        navigateToUserHome();
    }

    public void navigateToUserHome() {
        Log.d(TAG, "navigateToUserHome: navigate to user home");
        navigateActionAndPopUpTo(R.id.action_parkVehicleFragment_to_userHomeFragment, R.id.userHomeFragment);
    }
}
