package dev.aidaco.parked.Model.Entities;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;

public class SpotData {

    @Embedded
    public Spot spot;

    @Relation(parentColumn = "ticket_id", entityColumn = "id", entity = ParkingTicket.class)
    public List<ParkingTicketData> ticket;
}
