package com.aidancourtney.parked.Model.Entities;

import com.aidancourtney.parked.Model.Enums;

import androidx.room.ColumnInfo;

public class LicensePlate {

    @ColumnInfo(name = "license_plate_number")
    private String licensePlateNumber;

    @ColumnInfo(name = "license_plate_state")
    private Enums.State state;

    public LicensePlate(String licensePlateNumber, Enums.State state) {
        this.licensePlateNumber = licensePlateNumber;
        this.state = state;
    }

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public Enums.State getState() {
        return state;
    }
}
