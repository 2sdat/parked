package dev.aidaco.parked.ManagerHome;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import dev.aidaco.parked.Model.Entities.User;
import dev.aidaco.parked.R;
import dev.aidaco.parked.Utils.ClickListener;
import dev.aidaco.parked.Utils.ParkedRepository;

public class UserAdapter extends RecyclerView.Adapter<UserItemViewHolder> {
    private static final String TAG = "UserAdapter";
    private ParkedRepository parkedRepo;
    private List<User> users;
    private ClickListener<Integer> listener;

    UserAdapter(ParkedRepository parkedRepo) {
        this.parkedRepo = parkedRepo;
    }

    void updateUserData(List<User> users) {
        Log.d(TAG, "updateSpotData: recieved users size: " + Integer.toString(users.size()));
        this.users = users;
    }

    @NonNull
    @Override
    public UserItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
        return new UserItemViewHolder(view, parkedRepo);
    }

    @Override
    public void onBindViewHolder(@NonNull UserItemViewHolder holder, int position) {
        holder.setListener(listener);
        holder.setData(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users == null ? 0 : users.size();
    }

    void setClickListener(ClickListener<Integer> listener) {
        this.listener = listener;
    }
}
