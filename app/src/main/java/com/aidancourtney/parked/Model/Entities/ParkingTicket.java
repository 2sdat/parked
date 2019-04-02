package com.aidancourtney.parked.Model.Entities;

import com.aidancourtney.parked.Model.Enums;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "tickets", indices = {@Index(value = {"id"}, unique = true)})
public class ParkingTicket {
    @PrimaryKey
    private long id;

    @NonNull
    private Enums.VehicleType vehicleType;

    @NonNull
    @ColumnInfo(name = "spot_id")
    @ForeignKey(entity = Spot.class, parentColumns = "id", childColumns = "spot_id")
    private int spotId;

    @NonNull
    @ColumnInfo(name = "attendent_id")
    @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "attendent_id")
    private int attendentId;

    @NonNull
    @ColumnInfo(name = "billing_type")
    private Enums.BillingType billingType;

    @NonNull
    @Embedded
    private LicensePlate licensePlate;

    @NonNull
    @ColumnInfo(name = "park_time")
    private long parkTime;

    public ParkingTicket(long id, Enums.VehicleType vehicleType, int spotId, LicensePlate licensePlate, int attendentId, Enums.BillingType billingType, long parkTime) {
        this.id = id;
        this.vehicleType = vehicleType;
        this.spotId = spotId;
        this.licensePlate = licensePlate;
        this.attendentId = attendentId;
        this.billingType = billingType;
        this.parkTime = parkTime;
    }

    public long getId() {
        return id;
    }

    public Enums.VehicleType getVehicleType() {
        return vehicleType;
    }

    public int getSpotId() {
        return spotId;
    }

    public LicensePlate getLicensePlate() {
        return licensePlate;
    }

    public int getAttendentId() {
        return attendentId;
    }

    public Enums.BillingType getBillingType() {
        return billingType;
    }

    public long getParkTime() {
        return parkTime;
    }

    public boolean compare(ParkingTicket ticket) {
        if (ticket == this) {
            return true;
        }

        boolean res = this.id == ticket.getId();
        res = res && (this.vehicleType == ticket.getVehicleType());
        res = res && (this.spotId == ticket.getSpotId());
        res = res && (this.licensePlate.getLicensePlateNumber() == ticket.getLicensePlate().getLicensePlateNumber());
        res = res && (this.licensePlate.getState() == ticket.getLicensePlate().getState());
        res = res && (this.attendentId == ticket.getAttendentId());
        res = res && (this.billingType == ticket.getBillingType());
        res = res && (this.parkTime == ticket.getParkTime());
        return res;
    }
}
