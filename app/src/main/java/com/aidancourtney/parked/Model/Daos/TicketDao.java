package com.aidancourtney.parked.Model.Daos;

import com.aidancourtney.parked.Model.Entities.ParkingTicket;
import com.aidancourtney.parked.Model.Enums;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TicketDao {
    @Insert
    void addTicket(ParkingTicket ticket);

    @Update
    void updateTicket(ParkingTicket ticket);

    @Query("SELECT * FROM tickets")
    LiveData<List<ParkingTicket>> getAllTickets();

    @Query("SELECT * FROM tickets WHERE id LIKE :id")
    LiveData<ParkingTicket> getTicketByID(long id);

    @Query("SELECT * FROM tickets WHERE license_plate_number LIKE :licensePlateNumber")
    LiveData<List<ParkingTicket>> getByPlateNumber(String licensePlateNumber);

    @Query("SELECT * FROM tickets WHERE license_plate_state LIKE :state")
    LiveData<List<ParkingTicket>> getByPlateState(Enums.State state);

    @Query("SELECT * FROM tickets WHERE license_plate_state LIKE :licensePlateState AND license_plate_number LIKE :licensePlateNumber")
    LiveData<ParkingTicket> getByFullPlate(String licensePlateNumber, Enums.State licensePlateState);

    @Query("SELECT * FROM tickets WHERE attendent_id LIKE :attendentID")
    LiveData<List<ParkingTicket>> getByAttendentID(int attendentID);
}
