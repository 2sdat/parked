package dev.aidaco.parked.Fragments.Accessories;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import dev.aidaco.parked.Model.Entities.LicensePlate;
import dev.aidaco.parked.Model.Entities.ParkingTicket;
import dev.aidaco.parked.Model.Entities.Spot;
import dev.aidaco.parked.Model.Entities.User;
import dev.aidaco.parked.R;

class SpotItemViewHolder extends RecyclerView.ViewHolder {
    private TextView textViewSpotNumber;
    private TextView textViewSpotType;
    private TextView textViewLicensePlate;
    private TextView textViewAttendent;

    public SpotItemViewHolder(View itemView) {
        super(itemView);
        textViewSpotNumber = itemView.findViewById(R.id.textViewSpotNumber);
        textViewSpotType = itemView.findViewById(R.id.textViewSpotType);
        textViewLicensePlate = itemView.findViewById(R.id.textViewLicensePlate);
        textViewAttendent = itemView.findViewById(R.id.textViewAttendent);
    }

    public void setSpot(Spot spot) {
        textViewSpotNumber.setText(Integer.toString(spot.getId()));
        textViewSpotType.setText(spot.getSpotType().getName());
    }

    public void setTicket(ParkingTicket ticket) {
        LicensePlate licensePlate = ticket.getLicensePlate();
        textViewLicensePlate.setText(licensePlate.getState().toString() + " " + licensePlate.getLicensePlateNumber());
    }

    public void setAttendent(User user) {
        textViewAttendent.setText(user.getFirstName() + " " + user.getLastName());
    }
}
