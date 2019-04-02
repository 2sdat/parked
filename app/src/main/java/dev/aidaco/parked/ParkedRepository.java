package dev.aidaco.parked;

import android.content.Context;

import dev.aidaco.parked.Model.Daos.SpotDao;
import dev.aidaco.parked.Model.Daos.TicketDao;
import dev.aidaco.parked.Model.Daos.UserDao;
import dev.aidaco.parked.Model.ParkedDatabase;

public class ParkedRepository {
    private UserDao mUserDao;
    private SpotDao mSpotDao;
    private TicketDao mTicketDao;
    private ParkedDatabase parkedDb;

    public ParkedRepository(Context context) {
        parkedDb = ParkedDatabase.getInstance(context);
        mUserDao = parkedDb.userDao();
        mSpotDao = parkedDb.spotDao();
        mTicketDao = parkedDb.ticketDao();
    }

}
