package dev.aidaco.parked.Model.Entities;

import androidx.room.ColumnInfo;
import dev.aidaco.parked.Model.Enums;

// TODO: 5/14/19 javadoc
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

    @Override
    public String toString() {
        return getState().name() + " " + getLicensePlateNumber();
    }
}
