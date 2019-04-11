package dev.aidaco.parked.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import dev.aidaco.parked.ParkedViewModel;
import dev.aidaco.parked.R;

public class LoginFragment extends Fragment {
    private static final String TAG = "LoginFragment";

    private ParkedViewModel parkedViewModel;

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;

    public LoginFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        editTextUsername = view.findViewById(R.id.editTextUsername);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        buttonLogin = view.findViewById(R.id.buttonLogin);

        parkedViewModel = ViewModelProviders.of(getActivity()).get(ParkedViewModel.class);
        return view;
    }
}
