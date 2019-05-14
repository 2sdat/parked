package dev.aidaco.parked.AddNewUser;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import dev.aidaco.parked.Model.Enums;
import dev.aidaco.parked.R;
import dev.aidaco.parked.Utils.BaseFragment;
import dev.aidaco.parked.Utils.CreateUserResultListener;


/**
 * Fragment defining the behavior of the Add New User screen in the Manager work flow.
 *
 * @author Aidan Courtney
 * @see AddNewUserViewModel
 */
public class AddNewUserFragment extends BaseFragment<AddNewUserViewModel> {

    private ImageButton buttonBack;
    private EditText firstName;
    private EditText lastName;
    private EditText username;
    private EditText password;
    private EditText confirmPassword;
    private Spinner userType;
    private Button buttonDone;

    /**
     * Initializes the View objects needed to implement requisite behavior.
     *
     * @param view Root view of the inflated layout resource
     */
    @Override
    public void initViews(View view) {
        buttonBack = view.findViewById(R.id.addNewUser_ToolbarBack);
        firstName = view.findViewById(R.id.addNewUser_FirstName);
        lastName = view.findViewById(R.id.addNewUser_LastName);
        username = view.findViewById(R.id.addNewUser_UserName);
        password = view.findViewById(R.id.addNewUser_Password);
        confirmPassword = view.findViewById(R.id.addNewUser_ConfirmPassword);
        userType = view.findViewById(R.id.addNewUser_UserType);
        buttonDone = view.findViewById(R.id.addNewUser_Done);

        @SuppressWarnings("ConstantConditions")
        ArrayAdapter<Enums.UserType> userTypeAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, Enums.UserType.values());

        userTypeAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        userType.setAdapter(userTypeAdapter);
    }

    /**
     * Creates the callbacks and listeners for the Views and resources that require them.
     */
    @Override
    public void createCallbacks() {
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateUp();
            }
        });

        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _firstName = firstName.getText().toString();
                String _lastName = lastName.getText().toString();
                String _username = username.getText().toString();
                String _password = password.getText().toString();
                String _confirmPassword = confirmPassword.getText().toString();
                Enums.UserType _userType = (Enums.UserType) userType.getSelectedItem();

                CreateUserResultListener listener = new CreateUserResultListener() {
                    @Override
                    public void onAttemptReturn(int statusCode, int newUserId) {
                        switch (statusCode) {
                            case INC_USERNAME:
                                showSnackbar("That username is already taken.");
                                break;
                            case INC_PASSWORD:
                                showSnackbar("Confirm password doesn't match.");
                                break;
                            case INC_ACCESS_PRIVELIGE:
                                showSnackbar("Cannot create user of that type.");
                                break;
                            case FAIL:
                                showSnackbar("Something went wrong, please try again.");
                                break;
                            case SUCCESS:
                                showSnackbar("User successfully created.");
                                navigateToUserDetail(newUserId);
                                break;
                        }
                    }
                };

                viewModel.attemptCreateUser(_firstName, _lastName, _username, _password, _confirmPassword, _userType, listener);
            }
        });
    }

    /**
     * Navigates to the manager home screen.
     * <p>
     * Called when back button pressed.
     */
    private void navigateUp() {
        navigateActionAndPopUpTo(R.id.action_addNewUserFragment_to_managerHomeFragment, R.id.addNewUserFragment);
    }

    /**
     * Navigates to the detail view for the newly created user.
     *
     * @param userId The ID of the user account to navigate to.
     */
    private void navigateToUserDetail(int userId) {
        Bundle argsBundle = new Bundle();
        argsBundle.putInt("userId", userId);
        navigateActionWithArgs(R.id.action_addNewUserFragment_to_userDetailFragment, argsBundle);
    }

    /**
     * Returns the Class object of AddNewUserViewModel
     *
     * Called as part of the BaseFragment's viewmodel abstraction.
     *
     * @return The Class object of the AddNewUserViewModel
     */
    @Override
    public Class<AddNewUserViewModel> getViewModelClass() {
        return AddNewUserViewModel.class;
    }

    /**
     * Called as part of the BaseFragment's initialization abstraction
     *
     * @return The resource ID of the layout resource
     */
    @Override
    public int getLayoutId() {
        return R.layout.fragment_add_new_user;
    }
}
