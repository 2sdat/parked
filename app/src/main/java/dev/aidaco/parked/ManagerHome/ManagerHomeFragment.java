package dev.aidaco.parked.ManagerHome;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import dev.aidaco.parked.Model.Entities.ParkingTicket;
import dev.aidaco.parked.Model.Entities.User;
import dev.aidaco.parked.R;
import dev.aidaco.parked.Utils.BaseFragment;
import dev.aidaco.parked.Utils.ClickListener;
import dev.aidaco.parked.Utils.ParkedRepository;

/**
 * Fragment defining the behavior of the Manager home screen in the Manager work flow.
 *
 * @author Aidan Courtney
 * @see ManagerHomeViewModel
 */
public class ManagerHomeFragment extends BaseFragment<ManagerHomeViewModel> {
    private static final String TAG = "ManagerHomeFragment";

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private TicketAdapter ticketAdapter;
    private FloatingActionButton fabAdd;
    private ImageButton logoutButton;
    private ImageButton toggleContent;
    private ImageButton buttonBuild;
    private Observer<List<User>> userObserver;
    private Observer<List<ParkingTicket>> ticketObserver;

    /**
     * Initializes the View objects needed to implement requisite behavior.
     *
     * @param view Root view of the inflated layout resource
     */
    @Override
    public void initViews(View view) {
        recyclerView = view.findViewById(R.id.managerHome_Recycler);
        fabAdd = view.findViewById(R.id.managerHome_FAB);
        toggleContent = view.findViewById(R.id.managerHome_ToggleContent);
        buttonBuild = view.findViewById(R.id.managerHome_Create);
        logoutButton = view.findViewById(R.id.managerHome_Logout);

        if (viewModel.getCurrentView() == ManagerHomeViewModel.USER_VIEW) {
            initUserAdapter();
            recyclerView.setAdapter(userAdapter);
        } else {
            initTicketAdapter();
            recyclerView.setAdapter(ticketAdapter);
        }

        Log.d(TAG, "initViews: views init'd");
    }

    /**
     * Creates the callbacks and listeners for the Views and resources that require them.
     */
    @Override
    public void createCallbacks() {
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToAddNewUser();
            }
        });

        buttonBuild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToBuild();
            }
        });

        toggleContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleContent();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.onLogout();
                navigateToLogin();
            }
        });
    }

    /**
     * Toggle between displaying users or tickets.
     */
    private void toggleContent() {
        destroyUserAdapter();
        destroyTicketAdapter();
        viewModel.toggleCurrentView();

        if (viewModel.getCurrentView() == ManagerHomeViewModel.TICKET_VIEW) {
            initTicketAdapter();
            toggleContent.setImageResource(R.drawable.ic_user);
        } else if (viewModel.getCurrentView() == ManagerHomeViewModel.USER_VIEW) {
            initUserAdapter();
            toggleContent.setImageResource(R.drawable.ic_ticket);
        } else {
            Log.d(TAG, "toggleContent: how did this happen");
            initUserAdapter();
        }
    }

    /**
     * Create a useradapter and apply it to the recyclerview
     */
    private void initUserAdapter() {
        userAdapter = new UserAdapter(ParkedRepository.getInstance(getContext()));

        userObserver = new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                userAdapter.updateUserData(users);
                userAdapter.notifyDataSetChanged();
            }
        };

        viewModel.getUsers().observe(this, userObserver);

        userAdapter.setClickListener(new ClickListener<Integer>() {
            @Override
            public void onClick(Integer userId) {
                navigateToUserDetailView(userId);
            }

            @Override
            public void onLongClick(Integer userId) {
                navigateToUserDetailView(userId);
            }
        });

        recyclerView.setAdapter(userAdapter);

        fabAdd.show();
    }


    /**
     * Handle destruction of the useradapter.
     */
    private void destroyUserAdapter() {
        fabAdd.hide();

        viewModel.getUsers().removeObserver(userObserver);
        userObserver = null;
        this.userAdapter = null;
    }

    /**
     * Create a ticketadapter and apply it to the recyclerview
     */
    private void initTicketAdapter() {
        fabAdd.hide();

        ticketAdapter = new TicketAdapter(getActivity());

        ticketObserver = new Observer<List<ParkingTicket>>() {
            @Override
            public void onChanged(List<ParkingTicket> tickets) {
                ticketAdapter.updateTicketData(tickets);
                ticketAdapter.notifyDataSetChanged();
            }
        };

        viewModel.getTickets().observe(this, ticketObserver);

        ticketAdapter.setClickListener(new ClickListener<Long>() {
            @Override
            public void onClick(Long ticketId) {
                navigateToTicketDetailView(ticketId);
            }

            @Override
            public void onLongClick(Long ticketId) {
                navigateToTicketDetailView(ticketId);
            }
        });

        recyclerView.setAdapter(ticketAdapter);
    }

    /**
     * Handle destruction of the ticketadapter.
     */
    private void destroyTicketAdapter() {
        viewModel.getTickets().removeObserver(ticketObserver);
        ticketObserver = null;

        if (ticketAdapter != null) {
            this.ticketAdapter.onStop();
        }
        this.ticketAdapter = null;
    }

    /**
     * Returns the Class object of AddNewUserViewModel
     * <p>
     * Called as part of the BaseFragment's viewmodel abstraction.
     *
     * @return The Class object of the AddNewUserViewModel
     */
    @Override
    public Class<ManagerHomeViewModel> getViewModelClass() {
        return ManagerHomeViewModel.class;
    }

    /**
     * Called as part of the BaseFragment's initialization abstraction
     *
     * @return The resource ID of the layout resource
     */
    @Override
    public int getLayoutId() {
        return R.layout.fragment_manager_home;
    }

    /**
     * Navigates to the detail view for the specified user.
     * @param userId User to display
     */
    private void navigateToUserDetailView(int userId) {
        Log.d(TAG, "navigateToUserDetailView: userhome -> userdetail");
        Bundle argsBundle = new Bundle();
        argsBundle.putInt("userId", userId);
        navigateActionWithArgs(R.id.action_managerHomeFragment_to_userDetailFragment, argsBundle);
    }


    /**
     * Navigates to the detail view for the specified ticket.
     * @param ticketId Ticket to display.
     */
    private void navigateToTicketDetailView(long ticketId) {
        Log.d(TAG, "navigateToTicketDetailView: userhome -> ticketdetail");
        Bundle argsBundle = new Bundle();
        argsBundle.putLong("ticketId", ticketId);
        navigateActionWithArgs(R.id.action_managerHomeFragment_to_ticketDetailFragment, argsBundle);
    }

    /**
     * Navigates to the AddNewUser screen.
     */
    private void navigateToAddNewUser() {
        Log.d(TAG, "navigateToAddNewUser: managerhome -> addnewuser");
        navigateAction(R.id.action_managerHomeFragment_to_addNewUserFragment);
    }

    /**
     * Navigates to the Build screen.
     */
    private void navigateToBuild() {
        Log.d(TAG, "navigateToBuild: managerhome -> build");
        navigateAction(R.id.action_managerHomeFragment_to_buildFragment);
    }

    /**
     * Navigate to Login screen and clear backstack
     */
    private void navigateToLogin() {
        navigateToDestAndPopUpTo(R.id.loginFragment, R.id.managerHomeFragment);
    }
}
