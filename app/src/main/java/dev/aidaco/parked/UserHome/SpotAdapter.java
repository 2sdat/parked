package dev.aidaco.parked.UserHome;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import dev.aidaco.parked.Model.Entities.SpotData;
import dev.aidaco.parked.R;
import dev.aidaco.parked.Utils.ClickListener;


/**
 * Custom adapter for displaying a list of SpotData objects
 * in a recyclerview.
 *
 * @author Aidan COurtney
 */
public class SpotAdapter extends RecyclerView.Adapter<SpotItemViewHolder> {
    private static final String TAG = "SpotAdapter";
    private List<SpotData> occupiedSpots;
    private ClickListener<Integer> listener;

    void updateSpotData(List<SpotData> spotData) {
        Log.d(TAG, "updateSpotData: recieved spotdata size: " + Integer.toString(spotData.size()));
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
        holder.setListener(listener);
        holder.setData(occupiedSpots.get(position));
    }

    @Override
    public int getItemCount() {
        return occupiedSpots == null ? 0 : occupiedSpots.size();
    }

    void setClickListener(ClickListener<Integer> listener) {
        this.listener = listener;
    }
}
