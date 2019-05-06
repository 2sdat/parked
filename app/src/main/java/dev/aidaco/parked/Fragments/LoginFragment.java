package dev.aidaco.parked.Fragments;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import dev.aidaco.parked.Interfaces.LoginAttemptListener;
import dev.aidaco.parked.R;
import dev.aidaco.parked.ViewModels.LoginViewModel;

public class LoginFragment extends BaseFragment<LoginViewModel> {
    private static final String TAG = "LoginFragment";

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;
    private ProgressBar loadingSpinner;

    @Override
    public void initViews(View view) {
        editTextUsername = view.findViewById(R.id.editTextUsername);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        buttonLogin = view.findViewById(R.id.buttonLogin);
        loadingSpinner = view.findViewById(R.id.progressBarLoginLoading);

        loadingSpinner.setVisibility(View.GONE);
    }

    @Override
    public void createCallbacks() {
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingSpinner.setVisibility(View.VISIBLE);
                viewModel.provideUsername(editTextUsername.getText().toString());
                viewModel.providePassword(editTextPassword.getText().toString());
                viewModel.attemptLogin(new LoginAttemptListener() {
                    @Override
                    public void onResult(int resultCode) {
                        onLoginAttemptReturn(resultCode);
                    }
                });
            }
        });
    }

    private void onLoginAttemptReturn(int resultCode) {
        loadingSpinner.setVisibility(View.GONE);
        switch (resultCode) {
            case LoginAttemptListener.INC_USERNAME:
                showSnackbar(getString(R.string.login_fail_username));
                break;

            case LoginAttemptListener.INC_PASSWORD:
                showSnackbar(getString(R.string.login_fail_password));
                break;
            case LoginAttemptListener.SUCCESS:
                loadingSpinner.setVisibility(View.VISIBLE);
                navigateToUserHome();
                break;
        }
    }

    private void navigateToUserHome() {
        navigateActionAndPopUpTo(R.id.action_loginFragment_to_user_nav_graph, R.id.loginFragment);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public Class<LoginViewModel> getViewModelClass() {
        return LoginViewModel.class;
    }
}
