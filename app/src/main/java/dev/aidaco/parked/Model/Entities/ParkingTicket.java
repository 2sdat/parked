package dev.aidaco.parked.Model.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import dev.aidaco.parked.Model.Enums;


/**
 * Data storage class for Parking Tickets and their associated data.
 * <p>
 * This class declares the model for the "tickets" table of the Room database.
 *
 * @author Aidan Courtney
 */
@Entity(tableName = "tickets", indices = {@Index(value = {"id"}, unique = true)})
public class ParkingTicket {
    public static final long NULL_END_TIME = Long.MAX_VALUE;
    public static final long NULL_ID = Long.MAX_VALUE;

    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    private Enums.VehicleType vehicleType;

    @ColumnInfo(name = "spot_id")
    @ForeignKey(entity = Spot.class, parentColumns = "id", childColumns = "spot_id")
    private int spotId;

    @ColumnInfo(name = "attendent_id")
    @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "attendent_id")
    private int attendentId;

    @NonNull
    @ColumnInfo(name = "billing_type")
    private Enums.BillingType billingType;

    @NonNull
    @Embedded
    private LicensePlate licensePlate;

    @ColumnInfo(name = "start_time")
    private long startTime;

    @ColumnInfo(name = "end_time")
    private long endTime;

    @ColumnInfo(name = "total_price")
    private float totalPrice;

    public ParkingTicket(@NonNull Enums.VehicleType vehicleType, int spotId, @NonNull LicensePlate licensePlate, int attendentId, @NonNull Enums.BillingType billingType, long startTime) {
        this.id = 0;
        this.vehicleType = vehicleType;
        this.spotId = spotId;
        this.licensePlate = licensePlate;
        this.attendentId = attendentId;
        this.billingType = billingType;
        this.startTime = startTime;
        this.endTime = NULL_END_TIME;
        this.totalPrice = 0f;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @NonNull
    public Enums.VehicleType getVehicleType() {
        return vehicleType;
    }

    public int getSpotId() {
        return spotId;
    }

    @NonNull
    public LicensePlate getLicensePlate() {
        return licensePlate;
    }

    public int getAttendentId() {
        return attendentId;
    }

    public void setAttendentId(int attendentId) {
        this.attendentId = attendentId;
    }

    @NonNull
    public Enums.BillingType getBillingType() {
        return billingType;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
}
