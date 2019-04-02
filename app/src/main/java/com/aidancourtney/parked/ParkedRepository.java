package com.aidancourtney.parked;

import android.content.Context;

import com.aidancourtney.parked.Model.Daos.SpotDao;
import com.aidancourtney.parked.Model.Daos.TicketDao;
import com.aidancourtney.parked.Model.Daos.UserDao;
import com.aidancourtney.parked.Model.ParkedDatabase;

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
