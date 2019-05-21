package dev.aidaco.parked.Model.Entities;

import androidx.room.ColumnInfo;
import dev.aidaco.parked.Model.Enums;


/**
 * Represents the data associated with a license plate
 * and the operations that may need to be done to that data.
 *
 * @author Aidan Courtney
 */
public class LicensePlate {

    @ColumnInfo(name = "license_plate_number")
    private String licensePlateNumber;

    @ColumnInfo(name = "license_plate_state")
    private Enums.State state;

    public LicensePlate(String licensePlateNumber, Enums.State state) {
        this.licensePlateNumber = licensePlateNumber;
        this.state = state;
    }

    /**
     * Returns the license plate number <em>only</em>.
     *
     * @return String containting the license plate number
     */
    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    /**
     * Returns the state associated with this license plate.
     *
     * @return Enums.State object
     */
    public Enums.State getState() {
        return state;
    }

    /**
     * Returns a string representation of the license plate.
     *
     * Of the format: two letter state abbreviation followed by plate number.
     * Ie. NY JEN5575
     * @return
     */
    @Override
    public String toString() {
        return getState().name() + " " + getLicensePlateNumber();
    }
}
