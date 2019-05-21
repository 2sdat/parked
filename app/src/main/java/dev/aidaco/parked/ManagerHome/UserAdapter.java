package dev.aidaco.parked.ManagerHome;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import dev.aidaco.parked.Database.ParkedRepository;
import dev.aidaco.parked.Model.Entities.User;
import dev.aidaco.parked.R;
import dev.aidaco.parked.Utils.ClickListener;

/**
 * Stores the data for and handles the binding of user views for the recyclerview in the ManagerHome Fragment.
 *
 * @author Aidan Courtney
 * @see ManagerHomeFragment
 * @see UserItemViewHolder
 */
public class UserAdapter extends RecyclerView.Adapter<UserItemViewHolder> {
    private static final String TAG = "UserAdapter";
    private ParkedRepository parkedRepo;
    private List<User> users;
    private ClickListener<Integer> listener;

    /**
     * Instantiates the adapter.
     * @param parkedRepo Instance of parkedRepository.
     */
    UserAdapter(ParkedRepository parkedRepo) {
        this.parkedRepo = parkedRepo;
    }

    /**
     * Set the list of users to display.
     * @param users List of users.
     */
    void updateUserData(List<User> users) {
        Log.d(TAG, "updateSpotData: recieved users size: " + Integer.toString(users.size()));
        this.users = users;
    }

    /**
     * Implemented as part of Android API.
     * Creates a new UserItemViewHolder instance.
     * @param parent    Parent viewgroup.
     * @param viewType  Type of view.
     * @return Instance of UserItemViewHolder
     */
    @NonNull
    @Override
    public UserItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
        return new UserItemViewHolder(view, parkedRepo);
    }

    /**
     * Binds the UserItemViewHolder to the data specified by position.
     * @param holder    UserItemViewHolder to bind.
     * @param position  Position of the data in the list.
     */
    @Override
    public void onBindViewHolder(@NonNull UserItemViewHolder holder, int position) {
        holder.setListener(listener);
        holder.setData(users.get(position));
    }

    /**
     * Get the total number of items in the lsit.
     * @return Total number of items in the list.
     */
    @Override
    public int getItemCount() {
        return users == null ? 0 : users.size();
    }

    /**
     * Sets the listener that will handle click events.
     * @param listener Listener to be called on click events.
     */
    void setClickListener(ClickListener<Integer> listener) {
        this.listener = listener;
    }
}
