package dev.aidaco.parked.Database.Daos;

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
    LiveData<List<SpotData>> getOccupiedSpotswithData_LiveData();

    @Transaction
    @Query("SELECT * FROM spots WHERE NOT is_empty")
    List<SpotData> getOccupiedSpotswithData();

    @Transaction
    @Query("SELECT * FROM spots")
    LiveData<List<SpotData>> getAllSpotsWithData_LiveData();

    @Transaction
    @Query("SELECT * FROM spots")
    List<SpotData> getAllSpotsWithData();

    @Transaction
    @Query("SELECT * FROM spots WHERE id = :id")
    LiveData<SpotData> getSpotDataById_LiveData(int id);

    @Transaction
    @Query("SELECT * FROM spots WHERE id = :id")
    List<SpotData> getSpotDataById(int id);
}
