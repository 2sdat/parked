package dev.aidaco.parked.Model.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import dev.aidaco.parked.Model.Enums;

// TODO: 5/14/19 javadoc
@Entity(tableName = "spots", indices = {@Index(value = {"id"}, unique = true)})
public class Spot {

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

    @ColumnInfo(name = "isreserved")
    private boolean isReserved;

    public Spot(int id, @NonNull Enums.VehicleType spotType, boolean isEmpty, long ticketId, boolean isReserved) {
        this.id = id;
        this.spotType = spotType;
        this.isEmpty = isEmpty;
        this.ticketId = ticketId;
        this.isReserved = isReserved;
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

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean isReserved) {
        this.isReserved = isReserved;
    }

    public boolean compare(Spot spot) {
        if (spot == this) {
            return true;
        }

        boolean res = this.id == spot.getId();
        res = res && (this.spotType == spot.getSpotType());
        res = res && (this.isEmpty == spot.isEmpty());
        res = res && (this.ticketId == spot.getTicketId());
        res = res && (this.isReserved == spot.isReserved());
        return res;
    }
}
