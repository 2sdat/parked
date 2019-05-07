package dev.aidaco.parked.UserDetail;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.Observer;
import dev.aidaco.parked.Model.Entities.User;
import dev.aidaco.parked.R;
import dev.aidaco.parked.Utils.BaseFragment;

public class UserDetailFragment extends BaseFragment<UserDetailViewModel> {
    private static final String TAG = "UserDetailFragment";


    @Override
    public void initViews(View view) {
        // TODO init views
    }

    @Override
    public void createCallbacks() {
        viewModel.getUserData().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {

            }
        });

        imageButtonNavigateUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateUp();
            }
        });
    }

    @Override
    public void handleArguments(Bundle argBundle) {
        viewModel.setUser(argBundle.getInt("spotId"));
    }

    private void navigateUp() {
        // TODO implement navigate up
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
