package dev.aidaco.parked.Model.Entities;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;

public class ParkingTicketData {
    @Embedded
    public ParkingTicket parkingTicket;

    @Relation(parentColumn = "attendent_id", entityColumn = "id", entity = User.class)
    public List<User> attendent;
}
