package dev.aidaco.parked.Login;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import dev.aidaco.parked.R;
import dev.aidaco.parked.Utils.BaseFragment;
import dev.aidaco.parked.Utils.LoginAttemptListener;

public class LoginFragment extends BaseFragment<LoginViewModel> {
    private static final String TAG = "LoginFragment";

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;


    @Override
    public void initViews(View view) {
        Log.d(TAG, "initViews: loginfragment start init views");
        editTextUsername = view.findViewById(R.id.login_Username);
        editTextPassword = view.findViewById(R.id.login_Password);
        buttonLogin = view.findViewById(R.id.login_Login);

        Log.d(TAG, "initViews: views init'd");

        viewModel.ensureDefaultUser();
    }

    @Override
    public void createCallbacks() {
        Log.d(TAG, "createCallbacks: login start create callbacks");

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.provideUsername(editTextUsername.getText().toString());
                viewModel.providePassword(editTextPassword.getText().toString());
                viewModel.attemptLogin(new LoginAttemptListener() {
                    @Override
                    public void onResult(int resultCode) {
                        Log.d(TAG, "onResult: recieved login attempt callback");
                        onLoginAttemptReturn(resultCode);
                    }
                });
            }
        });

        Log.d(TAG, "createCallbacks: login callback init'd");
    }

    private void onLoginAttemptReturn(int resultCode) {
        Log.d(TAG, "onLoginAttemptReturn: ");
        switch (resultCode) {
            case LoginAttemptListener.INC_USERNAME:
                Log.d(TAG, "onLoginAttemptReturn: incorrect username");
                showSnackbar(getString(R.string.login_fail_username));
                break;

            case LoginAttemptListener.INC_PASSWORD:
                Log.d(TAG, "onLoginAttemptReturn: incorrect password");
                showSnackbar(getString(R.string.login_fail_password));
                break;

            case LoginAttemptListener.INACTIVE:
                Log.d(TAG, "onLoginAttemptReturn: user is inactive");
                showSnackbar("Your account has been deactivated. Contact a manager for details.");
                break;

            case LoginAttemptListener.SUCCESS:
                Log.d(TAG, "onLoginAttemptReturn: login success");
                navigateToHome();
                break;
        }
    }

    private void navigateToHome() {
        switch (viewModel.getAccessPrivilege()) {
            case BASIC:
                Log.d(TAG, "navigateToHome: basic user -> userhome");
                navigateToUserHome();
                break;
            case MANAGER:
                Log.d(TAG, "navigateToHome: manager user -> managerhome");
                navigateToManagerHome();
                break;
            case ADMIN:
                Log.d(TAG, "navigateToHome: admin user -> managerhome");
                navigateToManagerHome();
                break;
        }
    }

    private void navigateToUserHome() {
        Log.d(TAG, "navigateToUserHome: navigate to userhome");
        navigateActionAndPopUpTo(R.id.action_loginFragment_to_user_nav_graph, R.id.loginFragment);
    }

    private void navigateToManagerHome() {
        Log.d(TAG, "navigateToManagerHome: navigate to managerhome");
        navigateActionAndPopUpTo(R.id.action_loginFragment_to_manager_nav_graph, R.id.loginFragment);
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
