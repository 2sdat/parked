package dev.aidaco.parked.Model.Daos;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import dev.aidaco.parked.Model.Entities.Spot;

@Dao
public interface SpotDao {
    @Insert
    void addSpot(Spot spot);

    @Update
    void updateSpot(Spot spot);

    @Query("SELECT * FROM spots WHERE NOT is_empty")
    LiveData<List<Spot>> getOccupiedSpots();

    @Query("SELECT * FROM spots WHERE is_empty")
    LiveData<List<Spot>> getEmptySpots();

    @Query("SELECT * FROM spots")
    LiveData<List<Spot>> getAllSpots();

    @Query("SELECT * FROM spots WHERE id = :id")
    LiveData<Spot> getByID(int id);
}
