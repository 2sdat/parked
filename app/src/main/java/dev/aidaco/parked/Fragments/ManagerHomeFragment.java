package dev.aidaco.parked.Fragments;

import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import dev.aidaco.parked.Fragments.Accessories.UserAdapter;
import dev.aidaco.parked.Interfaces.ClickListener;
import dev.aidaco.parked.Model.Entities.User;
import dev.aidaco.parked.R;
import dev.aidaco.parked.ViewModels.ManagerHomeViewModel;

public class ManagerHomeFragment extends BaseFragment<ManagerHomeViewModel> {
    private static final String TAG = "ManagerHomeFragment";

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private FloatingActionButton fabAdd;

    @Override
    public void initViews(View view) {
        recyclerView = view.findViewById(R.id.managerHome_recyclerView);
        fabAdd = view.findViewById(R.id.managerHome_fabAdd);

        userAdapter = new UserAdapter(masterVM);

        recyclerView.setAdapter(userAdapter);

        Log.d(TAG, "initViews: views init'd");
    }

    @Override
    public void createCallbacks() {
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

        viewModel.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                userAdapter.updateUserData(users);
                userAdapter.notifyDataSetChanged();
            }
        });

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabCallHandler();
            }
        });
    }

    public void fabCallHandler() {
        // TODO handle fab call
    }

    @Override
    public Class<ManagerHomeViewModel> getViewModelClass() {
        return ManagerHomeViewModel.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_manager_home;
    }

    public void navigateToUserDetailView(int userId) {
        // TODO implement UserDetailView and navigate
    }
}
