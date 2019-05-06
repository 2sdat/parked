package dev.aidaco.parked.Model.Daos;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import dev.aidaco.parked.Model.Entities.Spot;

@Dao
public interface SpotDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addSpot(Spot spot);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateSpot(Spot spot);

    @Query("SELECT * FROM spots WHERE NOT is_empty ORDER BY id ASC")
    LiveData<List<Spot>> getOccupiedSpots();

    @Query("SELECT * FROM spots WHERE is_empty ORDER BY id ASC")
    List<Spot> getEmptySpots();

    @Query("SELECT * FROM spots WHERE is_empty AND spot_type == :vehicleTypeCode ORDER BY id ASC LIMIT 1")
    List<Spot> getEmptySpotOfType(int vehicleTypeCode);

    @Query("SELECT * FROM spots ORDER BY id ASC")
    LiveData<List<Spot>> getAllSpots();

    @Query("SELECT * FROM spots WHERE id = :id")
    List<Spot> getSpotById(int id);
}
