package dev.aidaco.parked.Model.Daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import dev.aidaco.parked.Model.Entities.User;

@Dao
public interface UserDao {
    @Insert
    void addUser(User user);

    @Update
    void updateUser(User user);

    @Query("SELECT * FROM users WHERE id = :id")
    User getUserById(int id);

    @Query("SELECT * FROM users WHERE username = :username")
    User getUserByUsername(String username);
}
