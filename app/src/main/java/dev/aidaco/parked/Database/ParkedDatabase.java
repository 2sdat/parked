package dev.aidaco.parked.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import dev.aidaco.parked.Database.Daos.ClearAllDao;
import dev.aidaco.parked.Database.Daos.ParkingTicketDataDao;
import dev.aidaco.parked.Database.Daos.SpotDao;
import dev.aidaco.parked.Database.Daos.SpotDataDao;
import dev.aidaco.parked.Database.Daos.TicketDao;
import dev.aidaco.parked.Database.Daos.UserDao;
import dev.aidaco.parked.Model.Entities.ParkingTicket;
import dev.aidaco.parked.Model.Entities.Spot;
import dev.aidaco.parked.Model.Entities.User;
import dev.aidaco.parked.Model.Enums;

@Database(entities = {User.class, Spot.class, ParkingTicket.class},
        version = 1, exportSchema = false)
@TypeConverters(Enums.class)
public abstract class ParkedDatabase extends RoomDatabase {
    private static volatile ParkedDatabase INSTANCE;

    public static synchronized ParkedDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ParkedDatabase.class, "parked_database").fallbackToDestructiveMigration().build();
        }

        return INSTANCE;
    }

    public abstract UserDao userDao();

    public abstract SpotDao spotDao();

    public abstract TicketDao ticketDao();

    public abstract SpotDataDao spotDataDao();

    public abstract ParkingTicketDataDao ticketDataDao();

    public abstract ClearAllDao clearAllDao();
}
