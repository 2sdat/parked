package dev.aidaco.parked.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import dev.aidaco.parked.R;
import dev.aidaco.parked.ViewModels.LoginAttemptListener;
import dev.aidaco.parked.ViewModels.LoginViewModel;

public class LoginFragment extends Fragment {
    private static final String TAG = "LoginFragment";

    private LoginViewModel loginViewModel;

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;
    private TextView textViewLoginMessage;
    private ProgressBar loadingSpinner;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        loginViewModel = ViewModelProviders.of(getActivity()).get(LoginViewModel.class);
        editTextUsername = view.findViewById(R.id.editTextUsername);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        buttonLogin = view.findViewById(R.id.buttonLogin);
        textViewLoginMessage = view.findViewById(R.id.textViewLoginMessage);
        loadingSpinner = view.findViewById(R.id.progressBarLoginLoading);

        loadingSpinner.setVisibility(View.GONE);
        textViewLoginMessage.setVisibility(View.GONE);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingSpinner.setVisibility(View.VISIBLE);
                loginViewModel.provideUsername(editTextUsername.getText().toString());
                loginViewModel.providePassword(editTextPassword.getText().toString());
                loginViewModel.attemptLogin(new LoginAttemptListener() {
                    @Override
                    public void onResult(int resultcode) {
                        onLoginAttemptReturn(resultcode);
                    }
                });
            }
        });

        return view;
    }

    private void onLoginAttemptReturn(int resultcode) {
        loadingSpinner.setVisibility(View.GONE);
        switch (resultcode) {
            case LoginAttemptListener.INC_USERNAME:
                textViewLoginMessage.setText(getString(R.string.login_fail_username));
                break;

            case LoginAttemptListener.INC_PASSWORD:
                textViewLoginMessage.setText(getString(R.string.login_fail_password));
                break;
            case LoginAttemptListener.SUCCESS:
                textViewLoginMessage.setText("");
                loadingSpinner.setVisibility(View.VISIBLE);
                break;
        }

        textViewLoginMessage.setVisibility(View.VISIBLE);
    }
}
