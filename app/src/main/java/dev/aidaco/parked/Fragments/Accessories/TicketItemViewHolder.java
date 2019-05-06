package dev.aidaco.parked.Fragments.Accessories;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import dev.aidaco.parked.Interfaces.ClickListener;
import dev.aidaco.parked.Model.Entities.ParkingTicket;
import dev.aidaco.parked.R;
import dev.aidaco.parked.ViewModels.MasterViewModel;

class TicketItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    private MasterViewModel masterVM;
    private TextView ticketId;
    private TextView spotNumber;
    private TextView elapsedTime;
    private TextView attendant;
    private TextView licensePlate;
    private ClickListener<Long> listener;
    private long ticketIdNum;
    private long startTime;
    private long endTime;
    private Thread timerThread;

    public TicketItemViewHolder(View itemView, MasterViewModel masterVM) {
        super(itemView);
        itemView.setOnClickListener(this);
        ticketId = itemView.findViewById(R.id.ticketListItem_TicketId);
        spotNumber = itemView.findViewById(R.id.ticketListItem_SpotNumber);
        elapsedTime = itemView.findViewById(R.id.ticketListItem_ElapsedTime);
        attendant = itemView.findViewById(R.id.ticketListItem_Attendent);
        licensePlate = itemView.findViewById(R.id.ticketListItem_LicensePlate);
    }

    @Override
    public void onClick(View v) {
        listener.onClick(ticketIdNum);
    }

    @Override
    public boolean onLongClick(View v) {
        listener.onLongClick(ticketIdNum);
        return false;
    }

    public void setData(ParkingTicket parkingTicket) {
        this.startTime = parkingTicket.getStartTime();
        this.endTime = parkingTicket.getEndTime();
        this.ticketIdNum = parkingTicket.getId();
        ticketId.setText(Long.toString(ticketIdNum));
        spotNumber.setText(parkingTicket.getSpotId());
        licensePlate.setText(parkingTicket.getLicensePlate().toString());
        attendant.setText("--");
        elapsedTime.setText("--:--:--");
    }

    public void setListener(ClickListener<Long> listener) {
        this.listener = listener;
    }
}
