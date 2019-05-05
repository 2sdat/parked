package dev.aidaco.parked.Model.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import dev.aidaco.parked.Model.Enums;

@Entity(tableName = "spots", indices = {@Index(value = {"id"}, unique = true)})
public class Spot {
    @Ignore
    public static final long NULL_TICKET_ID = Long.MAX_VALUE;

    @PrimaryKey
    private int id;

    @NonNull
    @ColumnInfo(name = "spot_type")
    private Enums.VehicleType spotType;

    @ColumnInfo(name = "is_empty")
    private boolean isEmpty;

    @ColumnInfo(name = "ticket_id")
    @ForeignKey(entity = ParkingTicket.class, parentColumns = {"id"}, childColumns = {"ticket_id"})
    private long ticketId;

    public Spot(int id, @NonNull Enums.VehicleType spotType, boolean isEmpty, long ticketId) {
        this.id = id;
        this.spotType = spotType;
        this.isEmpty = isEmpty;
        this.ticketId = ticketId;
    }

    public int getId() {
        return id;
    }

    @NonNull
    public Enums.VehicleType getSpotType() {
        return spotType;
    }

    public long getTicketId() {
        return ticketId;
    }

    public boolean getIsEmpty() {
        return isEmpty;
    }

    public void toggleIsEmpty() {
        isEmpty = !isEmpty;
    }

    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }

    public boolean compare(Spot spot) {
        if (spot == this) {
            return true;
        }

        boolean res = this.id == spot.getId();
        res = res && (this.spotType == spot.getSpotType());
        res = res && (this.isEmpty == spot.getIsEmpty());
        res = res && (this.ticketId == spot.getTicketId());
        return res;
    }
}
