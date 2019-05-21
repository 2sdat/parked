package dev.aidaco.parked.Model.Entities;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;


/**
 * Class modelling the return type of database queries
 * which return data associated with the spot object being queried.
 *
 * @author Aidan Courtney
 */
public class SpotData {

    @Embedded
    public Spot spot;

    @Relation(parentColumn = "ticket_id", entityColumn = "id", entity = ParkingTicket.class)
    public List<ParkingTicketData> ticket;
}
