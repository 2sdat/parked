package dev.aidaco.parked.ManagerHome;

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

class UserItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    private ParkedRepository parkedRepo;
    private TextView fullName;
    private TextView username;
    private TextView activeTickets;
    private TextView totalTickets;
    private ClickListener<Integer> listener;
    private int userId;

    public UserItemViewHolder(View itemView, ParkedRepository parkedRepo) {
        super(itemView);
        this.parkedRepo = parkedRepo;
        itemView.setOnClickListener(this);
        fullName = itemView.findViewById(R.id.userListItem_FullName);
        username = itemView.findViewById(R.id.userListItem_FullName);
        activeTickets = itemView.findViewById(R.id.userListItem_ActiveTickets);
        totalTickets = itemView.findViewById(R.id.userListItem_TotalTickets);
    }

    @Override
    public void onClick(View v) {
        listener.onClick(userId);
    }

    @Override
    public boolean onLongClick(View v) {
        listener.onLongClick(userId);
        return false;
    }

    public void setData(User user) {
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

    public void resultCallback(List<ParkingTicket> parkingTickets) {
        new TicketCounterAsyncTask(this, parkingTickets).execute();
    }

    public void activeTicketsCallback(int numActiveTickets) {
        String text = "Active: " + Integer.toString(numActiveTickets);
        activeTickets.setText(text);
    }

    public void totalTicketsCallback(int numTotalTickets) {
        String text = "Total: " + Integer.toString(numTotalTickets);
        totalTickets.setText(text);
    }

    public void setListener(ClickListener<Integer> listener) {
        this.listener = listener;
    }

    private static class TicketCounterAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserItemViewHolder owner;
        private List<ParkingTicket> tickets;
        private int numActive = 0;
        private int numTotal = 0;

        public TicketCounterAsyncTask(UserItemViewHolder owner, List<ParkingTicket> tickets) {
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
