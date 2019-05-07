package dev.aidaco.parked.UserDetail;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import androidx.lifecycle.Observer;
import dev.aidaco.parked.Model.Entities.User;
import dev.aidaco.parked.R;
import dev.aidaco.parked.Utils.AttemptListener;
import dev.aidaco.parked.Utils.BaseFragment;
import dev.aidaco.parked.Utils.DoubleResultListener;

public class UserDetailFragment extends BaseFragment<UserDetailViewModel> {
    private static final String TAG = "UserDetailFragment";

    private ImageButton toolbarUp;
    private TextView fullName;
    private TextView username;
    private TextView userId;
    private TextView userType;
    private TextView isActive;
    private TextView activeTickets;
    private TextView totalTickets;
    private Button toggleActive;

    @Override
    public void initViews(View view) {
        toolbarUp = view.findViewById(R.id.userDetail_ToolbarUp);
        fullName = view.findViewById(R.id.userDetail_FullName);
        username = view.findViewById(R.id.userDetail_Username);
        userId = view.findViewById(R.id.userDetail_UserId);
        userType = view.findViewById(R.id.userDetail_UserType);
        isActive = view.findViewById(R.id.userDetail_Active);
        activeTickets = view.findViewById(R.id.userDetail_ActiveTickets);
        totalTickets = view.findViewById(R.id.userDetail_TotalTickets);
        toggleActive = view.findViewById(R.id.userDetail_ToggleActive);
        Log.d(TAG, "initViews: views init'd");
    }

    @Override
    public void createCallbacks() {
        viewModel.getUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> user) {
                User u = user.get(0);
                fullName.setText(u.getFullName());
                username.setText(u.getUsername());
                String text = Integer.toString(u.getId());
                userId.setText(text);
                userType.setText(u.getUserType().toString());
                String t = u.getIsActive() ? "Yes" : "No";
                isActive.setText(t);
                viewModel.getUserTicketCounts(u.getId(), new DoubleResultListener<Integer, Integer>() {
                    @Override
                    public void onResult(Integer active, Integer total) {
                        String a = active.toString();
                        String t = total.toString();
                        activeTickets.setText(a);
                        totalTickets.setText(t);
                    }
                });
            }
        });

        toolbarUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateUp();
            }
        });

        toggleActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.attemptToggleActive(new AttemptListener() {
                    @Override
                    public void onReturnCode(int statusCode) {
                        switch (statusCode) {
                            case AttemptListener.POS_SUCCESS:
                                showSnackbar("User is now active.");
                                break;
                            case AttemptListener.NEG_SUCCESS:
                                showSnackbar("User is now inactive.");
                                break;
                            case AttemptListener.FAIL:
                                showSnackbar("User active state could not be changed.");
                                break;
                            default:
                                break;
                        }
                    }
                });
            }
        });
    }

    @Override
    public void handleArguments(Bundle argBundle) {
        viewModel.setUser(argBundle.getInt("userId"));
    }

    private void navigateUp() {
        Log.d(TAG, "navigateUp: user detail -> managerhome");
        navigateActionAndPopUpTo(R.id.action_userDetailFragment_to_managerHomeFragment, R.id.userDetailFragment);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_user_detail;
    }

    @Override
    public Class<UserDetailViewModel> getViewModelClass() {
        return UserDetailViewModel.class;
    }
}
