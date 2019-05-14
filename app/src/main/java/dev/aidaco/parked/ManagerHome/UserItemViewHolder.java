package dev.aidaco.parked.ManagerHome;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import dev.aidaco.parked.Model.Entities.ParkingTicket;
import dev.aidaco.parked.Model.Entities.User;
import dev.aidaco.parked.R;
import dev.aidaco.parked.Utils.ClickListener;
import dev.aidaco.parked.Utils.ParkedRepository;
import dev.aidaco.parked.Utils.SingleResultListener;

/**
 * Implements the logic for populating the data in an item displaying user data.
 *
 * @author Aidan Courtney
 * @see UserAdapter
 */
class UserItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    private ParkedRepository parkedRepo;
    private TextView fullName;
    private TextView username;
    private TextView activeTickets;
    private TextView totalTickets;
    private ClickListener<Integer> listener;
    private int userId;

    /**
     * Instantiates a UserItemViewHolder.
     *
     * @param itemView   The inflated layout.
     * @param parkedRepo An instance of ParkedRepository
     */
    UserItemViewHolder(View itemView, ParkedRepository parkedRepo) {
        super(itemView);
        this.parkedRepo = parkedRepo;
        itemView.setOnClickListener(this);
        fullName = itemView.findViewById(R.id.userListItem_FullName);
        username = itemView.findViewById(R.id.userListItem_Username);
        activeTickets = itemView.findViewById(R.id.userListItem_ActiveTickets);
        totalTickets = itemView.findViewById(R.id.userListItem_TotalTickets);
    }

    /**
     * Called when this item is clicked.
     * @param v Specific view that was clicked, unused.
     */
    @Override
    public void onClick(View v) {
        listener.onClick(userId);
    }

    /**
     * Called when this item is long clicked.
     * Functionally identical to onClick.
     * @param v Specific view that was clicked, unused.
     * @return Unused.
     */
    @Override
    public boolean onLongClick(View v) {
        listener.onLongClick(userId);
        return false;
    }

    /**
     * Set the data source for this item.
     *
     * @param user The data to use.
     */
    @SuppressLint("SetTextI18n")
    void setData(User user) {
        this.userId = user.getId();
        fullName.setText(user.getFullName());
        username.setText(user.getUsername());
        activeTickets.setText("Active: --");
        totalTickets.setText("Total: --");

        parkedRepo.getTicketsByUserId(userId, new SingleResultListener<List<ParkingTicket>>() {
            @Override
            public void onResult(List<ParkingTicket> parkingTickets) {
                resultCallback(parkingTickets);
            }
        });
    }


    /**
     * Handles the list of parking tickets returned by the repository.
     *
     * @param parkingTickets List of tickets parked by this attendant.
     */
    private void resultCallback(List<ParkingTicket> parkingTickets) {
        new TicketCounterAsyncTask(this, parkingTickets).execute();
    }

    /**
     * Callback to display the number of active tickets.
     *
     * @param numActiveTickets Number of active tickets this user has.
     */
    private void activeTicketsCallback(int numActiveTickets) {
        String text = "Active: " + Integer.toString(numActiveTickets);
        activeTickets.setText(text);
    }

    /**
     * Callback to display the number of total tickets.
     *
     * @param numTotalTickets Number of tickets this user has.
     */
    private void totalTicketsCallback(int numTotalTickets) {
        String text = "Total: " + Integer.toString(numTotalTickets);
        totalTickets.setText(text);
    }

    /**
     * Set the listener to be called when this item is clicked.
     * @param listener Click listener.
     */
    public void setListener(ClickListener<Integer> listener) {
        this.listener = listener;
    }

    /**
     * AsyncTask to count the number of tickets so as to not slow down the UI thread.
     * @author Aidan Courtney
     */
    private static class TicketCounterAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserItemViewHolder owner;
        private List<ParkingTicket> tickets;
        private int numActive = 0;
        private int numTotal = 0;

        TicketCounterAsyncTask(UserItemViewHolder owner, List<ParkingTicket> tickets) {
            this.tickets = tickets;
            this.owner = owner;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            owner.activeTicketsCallback(numActive);
            owner.totalTicketsCallback(numTotal);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            numTotal = tickets.size();
            for (ParkingTicket ticket : tickets) {
                if (ticket.getEndTime() == ParkingTicket.NULL_END_TIME) {
                    numActive++;
                }
            }
            return null;
        }
    }

}
