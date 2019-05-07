package dev.aidaco.parked.Database.Daos;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface ClearAllDao {
    @Query("DELETE FROM users")
    void wipeUsers();

    @Query("DELETE FROM spots")
    void wipeSpots();

    @Query("DELETE FROM tickets")
    void wipeTickets();
}
