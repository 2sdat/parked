package dev.aidaco.parked.Model.Daos;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Query;
import dev.aidaco.parked.Model.Entities.ParkingTicketData;
import dev.aidaco.parked.Model.Enums;

public interface ParkingTicketDataDao {
    @Query("SELECT * FROM tickets")
    LiveData<List<ParkingTicketData>> getAllTickets();

    @Query("SELECT * FROM tickets WHERE end_time = 0")
    LiveData<List<ParkingTicketData>> getAllActiveTickets();

    @Query("SELECT * FROM tickets WHERE id LIKE :id")
    LiveData<ParkingTicketData> getTicketByID(long id);

    @Query("SELECT * FROM tickets WHERE license_plate_number LIKE :licensePlateNumber")
    LiveData<List<ParkingTicketData>> getByPlateNumber(String licensePlateNumber);

    @Query("SELECT * FROM tickets WHERE license_plate_state LIKE :state")
    LiveData<List<ParkingTicketData>> getByPlateState(Enums.State state);

    @Query("SELECT * FROM tickets WHERE license_plate_state LIKE :licensePlateState AND license_plate_number LIKE :licensePlateNumber")
    LiveData<ParkingTicketData> getByFullPlate(String licensePlateNumber, Enums.State licensePlateState);

    @Query("SELECT * FROM tickets WHERE attendent_id LIKE :attendentID")
    LiveData<List<ParkingTicketData>> getByAttendentID(int attendentID);
}
