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

public class TicketAdapter extends RecyclerView.Adapter<TicketItemViewHolder> {
    private static final String TAG = "TicketAdapter";
    private Activity activity;
    private List<ParkingTicket> tickets;
    private ClickListener<Long> listener;

    TicketAdapter(Activity activity) {
        this.activity = activity;
    }

    void updateTicketData(List<ParkingTicket> tickets) {
        Log.d(TAG, "updateTicketData: recieved tickets size: " + Integer.toString(tickets.size()));
        this.tickets = tickets;
    }

    @NonNull
    @Override
    public TicketItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_list_item, parent, false);
        return new TicketItemViewHolder(view, activity);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketItemViewHolder holder, int position) {
        holder.setListener(listener);
        holder.setData(tickets.get(position));
    }

    @Override
    public int getItemCount() {
        return tickets == null ? 0 : tickets.size();
    }

    void setClickListener(ClickListener<Long> listener) {
        this.listener = listener;
    }

    void onStop() {
        this.activity = null;
    }
}
