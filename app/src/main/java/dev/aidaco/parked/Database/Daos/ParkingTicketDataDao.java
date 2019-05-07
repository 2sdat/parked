package dev.aidaco.parked.Database.Daos;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;
import dev.aidaco.parked.Model.Entities.ParkingTicketData;
import dev.aidaco.parked.Model.Enums;

@Dao
public interface ParkingTicketDataDao {

    @Transaction
    @Query("SELECT * FROM tickets")
    LiveData<List<ParkingTicketData>> getAllTickets_LiveData();

    @Transaction
    @Query("SELECT * FROM tickets")
    List<ParkingTicketData> getAllTickets();

    @Transaction
    @Query("SELECT * FROM tickets WHERE end_time = 0")
    LiveData<List<ParkingTicketData>> getAllActiveTickets_LiveData();

    @Transaction
    @Query("SELECT * FROM tickets WHERE end_time = 0")
    List<ParkingTicketData> getAllActiveTickets();

    @Transaction
    @Query("SELECT * FROM tickets WHERE id LIKE :id")
    LiveData<ParkingTicketData> getTicketById_LiveData(long id);

    @Transaction
    @Query("SELECT * FROM tickets WHERE id LIKE :id")
    List<ParkingTicketData> getTicketById(long id);

    @Transaction
    @Query("SELECT * FROM tickets WHERE license_plate_number LIKE :licensePlateNumber")
    LiveData<List<ParkingTicketData>> getByPlateNumber_LiveData(String licensePlateNumber);

    @Transaction
    @Query("SELECT * FROM tickets WHERE license_plate_number LIKE :licensePlateNumber")
    List<ParkingTicketData> getByPlateNumber(String licensePlateNumber);

    @Transaction
    @Query("SELECT * FROM tickets WHERE license_plate_state LIKE :state")
    LiveData<List<ParkingTicketData>> getByPlateState_LiveData(Enums.State state);

    @Transaction
    @Query("SELECT * FROM tickets WHERE license_plate_state LIKE :state")
    List<ParkingTicketData> getByPlateState(Enums.State state);

    @Transaction
    @Query("SELECT * FROM tickets WHERE license_plate_state LIKE :licensePlateState AND license_plate_number LIKE :licensePlateNumber")
    LiveData<ParkingTicketData> getByFullPlate_LiveData(String licensePlateNumber, Enums.State licensePlateState);

    @Transaction
    @Query("SELECT * FROM tickets WHERE license_plate_state LIKE :licensePlateState AND license_plate_number LIKE :licensePlateNumber")
    List<ParkingTicketData> getByFullPlate(String licensePlateNumber, Enums.State licensePlateState);


    @Transaction
    @Query("SELECT * FROM tickets WHERE attendent_id LIKE :attendentID")
    LiveData<List<ParkingTicketData>> getByAttendantId_LiveData(int attendentID);

    @Transaction
    @Query("SELECT * FROM tickets WHERE attendent_id LIKE :attendentID")
    List<ParkingTicketData> getByAttendantId(int attendentID);
}
