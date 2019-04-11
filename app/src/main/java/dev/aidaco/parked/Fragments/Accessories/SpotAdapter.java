package dev.aidaco.parked.Fragments.Accessories;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import dev.aidaco.parked.Model.Entities.SpotData;
import dev.aidaco.parked.R;

public class SpotAdapter extends RecyclerView.Adapter<SpotItemViewHolder> {
    private List<SpotData> occupiedSpots;

    public void updateSpotData(List<SpotData> spotData) {
        this.occupiedSpots = spotData;
    }

    @NonNull
    @Override
    public SpotItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spot_list_item, parent, false);
        return new SpotItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpotItemViewHolder holder, int position) {
        SpotData spot = occupiedSpots.get(position);
        holder.setSpot(spot.spot);
        holder.setTicket(spot.ticket.get(0).parkingTicket);
        holder.setAttendent(spot.ticket.get(0).attendent.get(0));
    }

    @Override
    public int getItemCount() {
        return occupiedSpots == null ? 0 : occupiedSpots.size();
    }
}
