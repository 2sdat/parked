package dev.aidaco.parked.Model.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import dev.aidaco.parked.Model.Enums;

@Entity(tableName = "users",
        indices = {
                @Index(value = {"id"}, unique = true),
                @Index(value = {"username"}, unique = true)
        })
public class User {
    @Ignore
    public static User DEF_USER = new User(0, "admin", "password", "Aidan", "Courtney", Enums.UserType.ADMIN, true);

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String username;

    private String password;

    @ColumnInfo(name = "first_name")
    private String firstName;

    @ColumnInfo(name = "last_name")
    private String lastName;

    @ColumnInfo(name = "user_type")
    private Enums.UserType userType;

    @ColumnInfo(name = "is_active")
    private boolean isActive;

    public User(int id, String username, String password, String firstName, String lastName, Enums.UserType userType, boolean isActive) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userType = userType;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    public Enums.UserType getUserType() {
        return userType;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean compare(User user) {
        if (user == this) {
            return true;
        }

        boolean res = this.id == user.getId();
        res = res && (this.username.equals(user.getUsername()));
        res = res && (this.password.equals(user.getPassword()));
        res = res && (this.firstName.equals(user.getFirstName()));
        res = res && (this.lastName.equals(user.getLastName()));
        res = res && (this.userType == user.getUserType());
        res = res && (this.isActive == user.getIsActive());
        return res;
    }
}
