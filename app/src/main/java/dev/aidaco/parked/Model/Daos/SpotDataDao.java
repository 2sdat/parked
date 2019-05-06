package dev.aidaco.parked.Model.Daos;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;
import dev.aidaco.parked.Model.Entities.SpotData;

@Dao
public interface SpotDataDao {
    @Transaction
    @Query("SELECT * FROM spots WHERE NOT is_empty")
    LiveData<List<SpotData>> getOccupiedSpotswithData();

    @Transaction
    @Query("SELECT * FROM spots")
    LiveData<List<SpotData>> getAllSpotsWithData();

    @Transaction
    @Query("SELECT * FROM spots WHERE id = :id")
    LiveData<SpotData> getLiveDataById(int id);

    @Transaction
    @Query("SELECT * FROM spots WHERE id = :id")
    List<SpotData> getById(int id);
}
