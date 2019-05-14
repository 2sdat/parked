package dev.aidaco.parked.ManagerHome;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import dev.aidaco.parked.Model.Entities.ParkingTicket;
import dev.aidaco.parked.R;
import dev.aidaco.parked.Utils.ClickListener;

/**
 * Stores the data for and handles the binding of ticket views for the recyclerview in the ManagerHome Fragment.
 *
 * @author Aidan Courtney
 * @see ManagerHomeFragment
 * @see TicketItemViewHolder
 */
public class TicketAdapter extends RecyclerView.Adapter<TicketItemViewHolder> {
    private static final String TAG = "TicketAdapter";
    private Activity activity;
    private List<ParkingTicket> tickets;
    private ClickListener<Long> listener;


    /**
     * Instantiates the adapter.
     * @param activity Owner.
     */
    TicketAdapter(Activity activity) {
        this.activity = activity;
    }


    /**
     * Set the list of tickets to display
     * @param tickets List of tickets.
     */
    void updateTicketData(List<ParkingTicket> tickets) {
        Log.d(TAG, "updateTicketData: recieved tickets size: " + Integer.toString(tickets.size()));
        this.tickets = tickets;
    }

    /**
     * Implemented as part of Android API.
     * Creates a new TicketItemViewHolder instance.
     * @param parent    Parent viewgroup.
     * @param viewType  Type of view.
     * @return Instance of TicektItemViewHolder
     */
    @NonNull
    @Override
    public TicketItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_list_item, parent, false);
        return new TicketItemViewHolder(view, activity);
    }

    /**
     * Binds the TicketItemViewHolder to the data specified by position.
     * @param holder    TicketItemViewHolder to bind.
     * @param position  Position of the data in the list.
     */
    @Override
    public void onBindViewHolder(@NonNull TicketItemViewHolder holder, int position) {
        holder.setListener(listener);
        holder.setData(tickets.get(position));
    }

    /**
     * Get the total number of items in the lsit.
     * @return Total number of items in the list.
     */
    @Override
    public int getItemCount() {
        return tickets == null ? 0 : tickets.size();
    }

    /**
     * Sets the listener that will handle click events.
     * @param listener Listener to be called on click events.
     */
    void setClickListener(ClickListener<Long> listener) {
        this.listener = listener;
    }

    /**
     * Releases the reference to the activity on destruction of the adapter.
     */
    void onStop() {
        this.activity = null;
    }
}
