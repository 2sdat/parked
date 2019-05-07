package dev.aidaco.parked.UserHome;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import dev.aidaco.parked.Model.Entities.LicensePlate;
import dev.aidaco.parked.Model.Entities.ParkingTicket;
import dev.aidaco.parked.Model.Entities.Spot;
import dev.aidaco.parked.Model.Entities.SpotData;
import dev.aidaco.parked.Model.Entities.User;
import dev.aidaco.parked.R;
import dev.aidaco.parked.Utils.ClickListener;

class SpotItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    private TextView textViewSpotNumber;
    private TextView textViewSpotType;
    private TextView textViewLicensePlate;
    private TextView textViewAttendent;
    private ClickListener<Integer> listener;
    private int spotId;

    SpotItemViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        textViewSpotNumber = itemView.findViewById(R.id.spotListItem_SpotNumber);
        textViewSpotType = itemView.findViewById(R.id.spotListItem_SpotType);
        textViewLicensePlate = itemView.findViewById(R.id.spotListItem_LicensePlate);
        textViewAttendent = itemView.findViewById(R.id.spotListItem_Attendant);
    }

    @Override
    public void onClick(View v) {
        listener.onClick(spotId);
    }

    @Override
    public boolean onLongClick(View v) {
        listener.onLongClick(spotId);
        return false;
    }

    void setData(SpotData spotData) {
        this.spotId = spotData.spot.getId();
        Spot spot = spotData.spot;
        ParkingTicket ticket = spotData.ticket.get(0).parkingTicket;
        User attendant = spotData.ticket.get(0).attendent.get(0);
        LicensePlate licensePlate = ticket.getLicensePlate();

        String textSpotNumber = Integer.toString(spot.getId());
        String textSpotType = spot.getSpotType().getName();
        String textLicensePlate = licensePlate.toString();
        String textAttendant = attendant.getFullName();

        textViewSpotNumber.setText(textSpotNumber);
        textViewSpotType.setText(textSpotType);
        textViewLicensePlate.setText(textLicensePlate);
        textViewAttendent.setText(textAttendant);
    }

    public void setListener(ClickListener<Integer> listener) {
        this.listener = listener;
    }
}
