package dev.aidaco.parked.Model.Daos;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import dev.aidaco.parked.Model.Entities.SpotData;

@Dao
public interface SpotDataDao {
    @Query("SELECT * FROM spots WHERE NOT is_empty")
    LiveData<List<SpotData>> getOccupiedSpotswithData();

    @Query("SELECT * FROM spots")
    LiveData<List<SpotData>> getAllSpotsWithData();

    @Query("SELECT * FROM spots WHERE id = :id")
    LiveData<SpotData> getByID(int id);
}
