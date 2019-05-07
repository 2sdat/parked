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

public class ManagerHomeFragment extends BaseFragment<ManagerHomeViewModel> {
    private static final String TAG = "ManagerHomeFragment";

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private TicketAdapter ticketAdapter;
    private FloatingActionButton fabAdd;
    private ImageButton toggleContent;
    private ImageButton buttonBuild;
    private Observer<List<User>> userObserver;
    private Observer<List<ParkingTicket>> ticketObserver;

    @Override
    public void initViews(View view) {
        recyclerView = view.findViewById(R.id.managerHome_Recycler);
        fabAdd = view.findViewById(R.id.managerHome_FAB);
        toggleContent = view.findViewById(R.id.managerHome_ToggleContent);
        buttonBuild = view.findViewById(R.id.managerHome_Create);

        initUserAdapter();
        recyclerView.setAdapter(userAdapter);

        Log.d(TAG, "initViews: views init'd");
    }

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
    }

    private void toggleContent() {
        destoryUserAdapter();
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

    private void destoryUserAdapter() {
        fabAdd.hide();

        viewModel.getUsers().removeObserver(userObserver);
        userObserver = null;
        this.userAdapter = null;
    }

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

    private void destroyTicketAdapter() {
        viewModel.getTickets().removeObserver(ticketObserver);
        ticketObserver = null;

        if (ticketAdapter != null) {
            this.ticketAdapter.onStop();
        }
        this.ticketAdapter = null;
    }

    @Override
    public Class<ManagerHomeViewModel> getViewModelClass() {
        return ManagerHomeViewModel.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_manager_home;
    }

    private void navigateToUserDetailView(int userId) {
        Log.d(TAG, "navigateToUserDetailView: userhome -> userdetail");
        Bundle argsBundle = new Bundle();
        argsBundle.putInt("userId", userId);
        navigateActionWithArgs(R.id.action_managerHomeFragment_to_userDetailFragment, argsBundle);
    }

    private void navigateToTicketDetailView(long ticketId) {
        Log.d(TAG, "navigateToTicketDetailView: userhome -> ticketdetail");
        Bundle argsBundle = new Bundle();
        argsBundle.putLong("ticketId", ticketId);
        navigateActionWithArgs(R.id.action_managerHomeFragment_to_ticketDetailFragment, argsBundle);
    }

    public void navigateToAddNewUser() {
        Log.d(TAG, "navigateToAddNewUser: managerhome -> addnewuser");
        navigateAction(R.id.action_managerHomeFragment_to_addNewUserFragment);
    }

    private void navigateToBuild() {
        Log.d(TAG, "navigateToBuild: managerhome -> build");
        navigateAction(R.id.action_managerHomeFragment_to_buildFragment);
    }
}
