package dev.aidaco.parked.Database.Daos;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import dev.aidaco.parked.Model.Entities.ParkingTicket;
import dev.aidaco.parked.Model.Enums;

@Dao
public interface TicketDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addTicket(ParkingTicket ticket);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTicket(ParkingTicket ticket);

    @Delete
    void deleteTicket(ParkingTicket ticket);

    @Query("SELECT * FROM tickets")
    LiveData<List<ParkingTicket>> getAllTickets_LiveData();

    @Query("SELECT * FROM tickets")
    List<ParkingTicket> getAllTickets();

    @Query("SELECT * FROM tickets WHERE end_time = 0")
    LiveData<List<ParkingTicket>> getAllActiveTickets_LiveData();

    @Query("SELECT * FROM tickets WHERE end_time = 0")
    List<ParkingTicket> getAllActiveTickets();

    @Query("SELECT * FROM tickets WHERE id LIKE :id LIMIT 1")
    LiveData<List<ParkingTicket>> getTicketById_LiveData(long id);

    @Query("SELECT * FROM tickets WHERE id LIKE :id LIMIT 1")
    List<ParkingTicket> getTicketById(long id);

    @Query("SELECT * FROM tickets WHERE license_plate_number LIKE :licensePlateNumber")
    LiveData<List<ParkingTicket>> getByPlateNumber_LiveData(String licensePlateNumber);

    @Query("SELECT * FROM tickets WHERE license_plate_number LIKE :licensePlateNumber")
    List<ParkingTicket> getByPlateNumber(String licensePlateNumber);

    @Query("SELECT * FROM tickets WHERE license_plate_state LIKE :state")
    LiveData<List<ParkingTicket>> getByPlateState_LiveData(Enums.State state);

    @Query("SELECT * FROM tickets WHERE license_plate_state LIKE :state")
    List<ParkingTicket> getByPlateState(Enums.State state);

    @Query("SELECT * FROM tickets WHERE license_plate_state LIKE :licensePlateState AND license_plate_number LIKE :licensePlateNumber LIMIT 1")
    LiveData<List<ParkingTicket>> getByFullPlate_LiveData(String licensePlateNumber, Enums.State licensePlateState);

    @Query("SELECT * FROM tickets WHERE license_plate_state LIKE :licensePlateState AND license_plate_number LIKE :licensePlateNumber LIMIT 1")
    List<ParkingTicket> getByFullPlate(String licensePlateNumber, Enums.State licensePlateState);

    @Query("SELECT * FROM tickets WHERE attendent_id LIKE :attendentID")
    LiveData<List<ParkingTicket>> getByAttendantId_LiveData(int attendentID);

    @Query("SELECT * FROM tickets WHERE attendent_id LIKE :attendentID")
    List<ParkingTicket> getByAttendantId(int attendentID);
}
