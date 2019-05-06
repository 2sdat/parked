package dev.aidaco.parked.Model.Daos;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import dev.aidaco.parked.Model.Entities.ParkingTicket;
import dev.aidaco.parked.Model.Enums;

@Dao
public interface TicketDao {
    @Insert
    void addTicket(ParkingTicket ticket);

    @Update
    void updateTicket(ParkingTicket ticket);

    @Delete
    void deleteTicket(ParkingTicket ticket);

    @Query("SELECT * FROM tickets")
    LiveData<List<ParkingTicket>> getAllTickets();

    @Query("SELECT * FROM tickets WHERE end_time = 0")
    LiveData<List<ParkingTicket>> getAllActiveTickets();

    @Query("SELECT * FROM tickets WHERE id LIKE :id")
    List<ParkingTicket> getTicketByID(long id);

    @Query("SELECT * FROM tickets WHERE license_plate_number LIKE :licensePlateNumber")
    LiveData<List<ParkingTicket>> getByPlateNumber(String licensePlateNumber);

    @Query("SELECT * FROM tickets WHERE license_plate_state LIKE :state")
    LiveData<List<ParkingTicket>> getByPlateState(Enums.State state);

    @Query("SELECT * FROM tickets WHERE license_plate_state LIKE :licensePlateState AND license_plate_number LIKE :licensePlateNumber LIMIT 1")
    List<ParkingTicket> getByFullPlate(String licensePlateNumber, Enums.State licensePlateState);

    @Query("SELECT * FROM tickets WHERE attendent_id LIKE :attendentID")
    LiveData<List<ParkingTicket>> getByAttendentID(int attendentID);
}
