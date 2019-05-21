package dev.aidaco.parked.Model.Entities;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;

/**
 * This class models database calls that shoudl return the associated
 * attendant data along with the parking ticket.
 *
 * @author Aidan Courtney
 */
public class ParkingTicketData {
    @Embedded
    public ParkingTicket parkingTicket;

    @Relation(parentColumn = "attendent_id", entityColumn = "id", entity = User.class)
    public List<User> attendent;
}
