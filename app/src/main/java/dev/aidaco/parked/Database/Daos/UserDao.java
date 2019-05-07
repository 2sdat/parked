package dev.aidaco.parked.Database.Daos;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import dev.aidaco.parked.Model.Entities.User;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addUser(User user);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateUser(User user);

    @Query("SELECT * FROM users")
    LiveData<List<User>> getAllUsers_LiveData();

    @Query("SELECT * FROM users")
    List<User> getAllUsers();

    @Query("SELECT * FROM users WHERE id = :id")
    LiveData<List<User>> getUserById_LiveData(int id);

    @Query("SELECT * FROM users WHERE id = :id")
    List<User> getUserById(int id);

    @Query("SELECT * FROM users WHERE username = :username")
    LiveData<List<User>> getUserByUsername_LiveData(String username);

    @Query("SELECT * FROM users WHERE username = :username")
    List<User> getUserByUsername(String username);
}
