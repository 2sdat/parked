package dev.aidaco.parked;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;
import dev.aidaco.parked.Interfaces.ResultListener;
import dev.aidaco.parked.Model.Daos.ParkingTicketDataDao;
import dev.aidaco.parked.Model.Daos.SpotDao;
import dev.aidaco.parked.Model.Daos.SpotDataDao;
import dev.aidaco.parked.Model.Daos.TicketDao;
import dev.aidaco.parked.Model.Entities.LicensePlate;
import dev.aidaco.parked.Model.Entities.ParkingTicket;
import dev.aidaco.parked.Model.Entities.ParkingTicketData;
import dev.aidaco.parked.Model.Entities.Spot;
import dev.aidaco.parked.Model.Entities.SpotData;
import dev.aidaco.parked.Model.Entities.User;
import dev.aidaco.parked.Model.Enums;
import dev.aidaco.parked.Model.ParkedDatabase;

public class ParkedRepository {
    private static final String TAG = "ParkedRepository";
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
        spotDao = parkedDb.spotDao();
        ticketDao = parkedDb.ticketDao();
        spotDataDao = parkedDb.spotDataDao();
        ticketDataDao = parkedDb.ticketDataDao();

        allSpots = spotDataDao.getAllSpotsWithData();
        occupiedSpots = spotDataDao.getOccupiedSpotswithData();
        activeTickets = ticketDataDao.getAllActiveTickets();
    }

    public void parkNewVehicle(final Enums.VehicleType vehicleType, LicensePlate licensePlate, User attendant, Enums.BillingType billingType, ResultListener<SpotData> listener) {
        ParkNewVehicleAsyncTask parkAsync = new ParkNewVehicleAsyncTask(spotDao, spotDataDao, ticketDao, listener);
        parkAsync.setVehicleType(vehicleType);
        parkAsync.setLicensePlate(licensePlate);
        parkAsync.setAttendant(attendant);
        parkAsync.setBillingType(billingType);
        parkAsync.execute();
    }

    public LiveData<List<SpotData>> getAllSpots() {
        return allSpots;
    }

    public LiveData<List<SpotData>> getOccupiedSpots() {
        return occupiedSpots;
    }

    public LiveData<SpotData> getSpotDataById(int id) {
        return spotDataDao.getLiveDataById(id);
    }

    public LiveData<List<ParkingTicketData>> getActiveTickets() {
        return activeTickets;
    }

    public List<Spot> getEmptySpots() {
        return spotDao.getEmptySpots();
    }

    public void addSpot(Spot spot) {
        new AddSpotAsyncTask(spotDao).execute(spot);
    }

    public void addTicket(ParkingTicket ticket) {
        new AddTicketAsyncTask(ticketDao).execute(ticket);
    }

    public void updateSpot(Spot spot) {
        new UpdateSpotAsyncTask(spotDao).execute(spot);
    }

    public void updateTicket(ParkingTicket ticket) {
        new UpdateTicketAsyncTask(ticketDao).execute(ticket);
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

    private static class ParkNewVehicleAsyncTask extends AsyncTask<Void, Void, Void> {
        private ResultListener<SpotData> listener;
        private SpotDao spotDao;
        private SpotDataDao spotDataDao;
        private TicketDao ticketDao;
        private LicensePlate licensePlate;
        private Enums.VehicleType vehicleType;
        private Enums.BillingType billingType;
        private User attendant;
        private SpotData spotData;

        ParkNewVehicleAsyncTask(SpotDao spotDao, SpotDataDao spotDataDao, TicketDao ticketDao, ResultListener<SpotData> listener) {
            this.spotDao = spotDao;
            this.spotDataDao = spotDataDao;
            this.ticketDao = ticketDao;
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            List<Spot> emptySpot = spotDao.getEmptySpotOfType(vehicleType.getTypeCode());
            if (emptySpot.size() == 0) {
                emptySpot = spotDao.getEmptySpots();
                if (emptySpot.size() == 0) {
                    spotData = null;
                    return null;
                }
            }

            Spot reservedSpot = emptySpot.get(0);

            ParkingTicket ticket = new ParkingTicket(vehicleType, reservedSpot.getId(), licensePlate, attendant.getId(), billingType, System.currentTimeMillis());
            ticketDao.addTicket(ticket);
            ticket = ticketDao.getByFullPlate(licensePlate.getLicensePlateNumber(), licensePlate.getState()).get(0);
            reservedSpot.setTicketId(ticket.getId());
            reservedSpot.toggleIsEmpty();
            spotDao.updateSpot(reservedSpot);
            spotData = spotDataDao.getById(reservedSpot.getId()).get(0);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            listener.onResult(spotData);
        }


        public void setLicensePlate(LicensePlate licensePlate) {
            this.licensePlate = licensePlate;
        }

        public void setVehicleType(Enums.VehicleType vehicleType) {
            this.vehicleType = vehicleType;
        }

        public void setBillingType(Enums.BillingType billingType) {
            this.billingType = billingType;
        }

        public void setAttendant(User attendant) {
            this.attendant = attendant;
        }
    }
}
