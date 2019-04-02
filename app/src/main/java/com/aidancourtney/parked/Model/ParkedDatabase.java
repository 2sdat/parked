package com.aidancourtney.parked.Model;

import android.content.Context;

import com.aidancourtney.parked.Model.Daos.SpotDao;
import com.aidancourtney.parked.Model.Daos.TicketDao;
import com.aidancourtney.parked.Model.Daos.UserDao;
import com.aidancourtney.parked.Model.Entities.ParkingTicket;
import com.aidancourtney.parked.Model.Entities.Spot;
import com.aidancourtney.parked.Model.Entities.User;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {User.class, Spot.class, ParkingTicket.class},
        version = 1, exportSchema = false)
@TypeConverters(Enums.class)
public abstract class ParkedDatabase extends RoomDatabase {
    private static volatile ParkedDatabase INSTANCE;

    public static synchronized ParkedDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ParkedDatabase.class, "parked_database").build();
        }

        return INSTANCE;
    }

    public abstract UserDao userDao();

    public abstract SpotDao spotDao();

    public abstract TicketDao ticketDao();
}
