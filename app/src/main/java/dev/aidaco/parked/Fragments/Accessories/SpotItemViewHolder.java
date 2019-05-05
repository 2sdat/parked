package dev.aidaco.parked.Fragments.Accessories;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import dev.aidaco.parked.Interfaces.ClickListener;
import dev.aidaco.parked.Model.Entities.LicensePlate;
import dev.aidaco.parked.Model.Entities.ParkingTicket;
import dev.aidaco.parked.Model.Entities.Spot;
import dev.aidaco.parked.Model.Entities.SpotData;
import dev.aidaco.parked.Model.Entities.User;
import dev.aidaco.parked.R;

class SpotItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    private TextView textViewSpotNumber;
    private TextView textViewSpotType;
    private TextView textViewLicensePlate;
    private TextView textViewAttendent;
    private ClickListener<SpotData> listener;
    private SpotData spotData;

    public SpotItemViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        textViewSpotNumber = itemView.findViewById(R.id.textViewSpotNumber);
        textViewSpotType = itemView.findViewById(R.id.textViewSpotType);
        textViewLicensePlate = itemView.findViewById(R.id.textViewLicensePlate);
        textViewAttendent = itemView.findViewById(R.id.textViewAttendent);
    }

    @Override
    public void onClick(View v) {
        listener.onClick(spotData);
    }

    @Override
    public boolean onLongClick(View v) {
        listener.onLongClick(spotData);
        return false;
    }

    public void setData(SpotData spotData) {
        this.spotData = spotData;
        Spot spot = spotData.spot;
        ParkingTicket ticket = spotData.ticket.get(0).parkingTicket;
        User attendant = spotData.ticket.get(0).attendent.get(0);
        LicensePlate licensePlate = ticket.getLicensePlate();

        String textSpotNumber = Integer.toString(spot.getId());
        String textSpotType = spot.getSpotType().getName();
        String textLicensePlate = licensePlate.getState().toString() + " " + licensePlate.getLicensePlateNumber();
        String textAttendant = attendant.getFirstName() + " " + attendant.getLastName();

        textViewSpotNumber.setText(textSpotNumber);
        textViewSpotType.setText(textSpotType);
        textViewLicensePlate.setText(textLicensePlate);
        textViewAttendent.setText(textAttendant);
    }

    public void setListener(ClickListener listener) {
        this.listener = listener;
    }
}
