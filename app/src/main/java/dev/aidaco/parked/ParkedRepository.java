package dev.aidaco.parked;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;
import dev.aidaco.parked.Model.Daos.ParkingTicketDataDao;
import dev.aidaco.parked.Model.Daos.SpotDao;
import dev.aidaco.parked.Model.Daos.SpotDataDao;
import dev.aidaco.parked.Model.Daos.TicketDao;
import dev.aidaco.parked.Model.Daos.UserDao;
import dev.aidaco.parked.Model.Entities.ParkingTicket;
import dev.aidaco.parked.Model.Entities.ParkingTicketData;
import dev.aidaco.parked.Model.Entities.Spot;
import dev.aidaco.parked.Model.Entities.SpotData;
import dev.aidaco.parked.Model.Entities.User;
import dev.aidaco.parked.Model.ParkedDatabase;

public class ParkedRepository {
    private static final String TAG = "ParkedRepository";
    private UserDao userDao;
    private SpotDao spotDao;
    private SpotDataDao spotDataDao;
    private TicketDao ticketDao;
    private ParkingTicketDataDao ticketDataDao;
    private ParkedDatabase parkedDb;

    private LiveData<List<SpotData>> allSpots;
    private LiveData<List<SpotData>> occupiedSpots;
    private LiveData<List<ParkingTicketData>> activeTickets;

    public ParkedRepository(Context context) {
        parkedDb = ParkedDatabase.getInstance(context);
        userDao = parkedDb.userDao();
        spotDao = parkedDb.spotDao();
        ticketDao = parkedDb.ticketDao();
        spotDataDao = parkedDb.spotDataDao();
        ticketDataDao = parkedDb.ticketDataDao();

        allSpots = spotDataDao.getAllSpotsWithData();
        occupiedSpots = spotDataDao.getOccupiedSpotswithData();
        activeTickets = ticketDataDao.getAllActiveTickets();
    }

    public LiveData<List<SpotData>> getAllSpots() {
        return allSpots;
    }

    public LiveData<List<SpotData>> getOccupiedSpots() {
        return occupiedSpots;
    }

    public LiveData<List<ParkingTicketData>> getActiveTickets() {
        return activeTickets;
    }

    public void addUser(User user) {
        new AddUserAsyncTask(userDao).execute(user);
    }

    public void addSpot(Spot spot) {
        new AddSpotAsyncTask(spotDao).execute(spot);
    }

    public void addTicket(ParkingTicket ticket) {
        new AddTicketAsyncTask(ticketDao).execute(ticket);
    }

    public void updateUser(User user) {
        new UpdateUserAsyncTask(userDao).execute(user);
    }

    public void updateSpot(Spot spot) {
        new UpdateSpotAsyncTask(spotDao).execute(spot);
    }

    public void updateTicket(ParkingTicket ticket) {
        new UpdateTicketAsyncTask(ticketDao).execute(ticket);
    }

    private static class AddUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        AddUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.addUser(users[0]);
            return null;
        }
    }

    private static class AddSpotAsyncTask extends AsyncTask<Spot, Void, Void> {
        private SpotDao spotDao;

        AddSpotAsyncTask(SpotDao spotDao) {
            this.spotDao = spotDao;
        }

        @Override
        protected Void doInBackground(Spot... spots) {
            spotDao.addSpot(spots[0]);
            return null;
        }
    }

    private static class AddTicketAsyncTask extends AsyncTask<ParkingTicket, Void, Void> {
        private TicketDao ticketDao;

        AddTicketAsyncTask(TicketDao ticketDao) {
            this.ticketDao = ticketDao;
        }


        @Override
        protected Void doInBackground(ParkingTicket... parkingTickets) {
            ticketDao.addTicket(parkingTickets[0]);
            return null;
        }
    }

    private static class UpdateSpotAsyncTask extends AsyncTask<Spot, Void, Void> {
        private SpotDao spotDao;

        UpdateSpotAsyncTask(SpotDao spotDao) {
            this.spotDao = spotDao;
        }

        @Override
        protected Void doInBackground(Spot... spots) {
            spotDao.updateSpot(spots[0]);
            return null;
        }
    }

    private static class UpdateUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        UpdateUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.updateUser(users[0]);
            return null;
        }
    }

    private static class UpdateTicketAsyncTask extends AsyncTask<ParkingTicket, Void, Void> {
        private TicketDao ticketDao;

        UpdateTicketAsyncTask(TicketDao ticketDao) {
            this.ticketDao = ticketDao;
        }

        @Override
        protected Void doInBackground(ParkingTicket... parkingTickets) {
            ticketDao.updateTicket(parkingTickets[0]);
            return null;
        }
    }
}
