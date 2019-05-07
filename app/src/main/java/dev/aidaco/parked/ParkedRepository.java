package dev.aidaco.parked;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;
import dev.aidaco.parked.Interfaces.AttemptListener;
import dev.aidaco.parked.Interfaces.DoubleResultListener;
import dev.aidaco.parked.Interfaces.SingleResultListener;
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

    public ParkedRepository(Context context) {
        parkedDb = ParkedDatabase.getInstance(context);
        spotDao = parkedDb.spotDao();
        ticketDao = parkedDb.ticketDao();
        spotDataDao = parkedDb.spotDataDao();
        ticketDataDao = parkedDb.ticketDataDao();
    }

    public void parkNewVehicle(final Enums.VehicleType vehicleType, LicensePlate licensePlate, User attendant, Enums.BillingType billingType, DoubleResultListener<Long, Integer> listener) {
        ParkNewVehicleAsyncTask parkAsync = new ParkNewVehicleAsyncTask(spotDao, ticketDao, listener);
        parkAsync.setVehicleType(vehicleType);
        parkAsync.setLicensePlate(licensePlate);
        parkAsync.setAttendant(attendant);
        parkAsync.setBillingType(billingType);
        parkAsync.execute();
    }

    public void finalizePark(long ticketId, AttemptListener listener) {
        new FinalizeParkAsyncTask(ticketDao, spotDao, ticketId, listener).execute();
    }

    public void cancelPark(long ticketId, AttemptListener listener) {
        new CancelParkAsyncTask(ticketDao, spotDao, ticketId, listener).execute();
    }

    public void finalizeTicket(long ticketId) {
        new FinalizeTicketAsyncTask(spotDao, ticketDao, ticketId).execute();
    }

    public void getTicketsByUserId(int userId, SingleResultListener<List<ParkingTicket>> listener) {
        new GetTicketsByUserIdAsyncTask(ticketDao, userId, listener).execute();
    }

    public LiveData<List<SpotData>> getAllSpots() {
        return spotDataDao.getAllSpotsWithData();
    }

    public LiveData<List<SpotData>> getOccupiedSpots() {
        return spotDataDao.getOccupiedSpotswithData();
    }

    public LiveData<SpotData> getSpotDataById(int id) {
        return spotDataDao.getLiveDataById(id);
    }

    public LiveData<List<ParkingTicketData>> getActiveTickets() {
        return ticketDataDao.getAllActiveTickets();
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

    public LiveData<List<ParkingTicket>> getAllTickets() {
        return ticketDao.getAllTickets();
    }

    public List<ParkingTicket> getTicketById(long ticketId, SingleResultListener<ParkingTicket> listener) {
        return ticketDao.getTicketByID(ticketId);
    }

    public LiveData<ParkingTicketData> getTicketDataByIdLive(long ticketId) {
        return ticketDataDao.getTicketById(ticketId);
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
        private DoubleResultListener<Long, Integer> listener;
        private SpotDao spotDao;
        private TicketDao ticketDao;
        private LicensePlate licensePlate;
        private Enums.VehicleType vehicleType;
        private Enums.BillingType billingType;
        private User attendant;
        private long ticketId;
        private int spotId;

        ParkNewVehicleAsyncTask(SpotDao spotDao, TicketDao ticketDao, DoubleResultListener<Long, Integer> listener) {
            this.spotDao = spotDao;
            this.ticketDao = ticketDao;
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            List<Spot> emptySpot = spotDao.getEmptySpotOfType(vehicleType.getTypeCode());
            if (emptySpot.size() == 0) {
                emptySpot = spotDao.getEmptySpots();
                if (emptySpot.size() == 0) {
                    return null;
                }
            }

            Spot reservedSpot = emptySpot.get(0);

            ParkingTicket ticket = new ParkingTicket(vehicleType, reservedSpot.getId(), licensePlate, attendant.getId(), billingType, System.currentTimeMillis());
            ticketDao.addTicket(ticket);
            spotId = reservedSpot.getId();
            ticketId = ticketDao.getByFullPlate(licensePlate.getLicensePlateNumber(), licensePlate.getState()).get(0).getId();
            reservedSpot.setEmpty(true);
            reservedSpot.setReserved(true);
            spotDao.updateSpot(reservedSpot);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            listener.onResult(ticketId, spotId);
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

    private static class FinalizeParkAsyncTask extends AsyncTask<Void, Void, Void> {
        private TicketDao ticketDao;
        private SpotDao spotDao;
        private AttemptListener listener;
        private long ticketId;

        public FinalizeParkAsyncTask(TicketDao ticketDao, SpotDao spotDao, long ticketId, AttemptListener listener) {
            this.ticketDao = ticketDao;
            this.spotDao = spotDao;
            this.ticketId = ticketId;
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ParkingTicket ticket = ticketDao.getTicketByID(ticketId).get(0);
            ticket.setStartTime(System.currentTimeMillis());
            Spot spot = spotDao.getSpotById(ticket.getSpotId()).get(0);
            spot.setTicketId(ticketId);
            spot.setEmpty(false);
            ticketDao.updateTicket(ticket);
            spotDao.updateSpot(spot);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            listener.onReturnCode(AttemptListener.POS_SUCCESS);
        }
    }

    private static class CancelParkAsyncTask extends AsyncTask<Void, Void, Void> {
        private TicketDao ticketDao;
        private SpotDao spotDao;
        private AttemptListener listener;
        private long ticketId;

        public CancelParkAsyncTask(TicketDao ticketDao, SpotDao spotDao, long ticketId, AttemptListener listener) {
            this.ticketDao = ticketDao;
            this.spotDao = spotDao;
            this.ticketId = ticketId;
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ParkingTicket ticket = ticketDao.getTicketByID(ticketId).get(0);
            Spot spot = spotDao.getSpotById(ticket.getSpotId()).get(0);
            spot.setEmpty(true);
            spot.setReserved(false);
            ticketDao.deleteTicket(ticket);
            spotDao.updateSpot(spot);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            listener.onReturnCode(AttemptListener.NEG_SUCCESS);
        }
    }

    private static class FinalizeTicketAsyncTask extends AsyncTask<Void, Void, Void> {
        private SpotDao spotDao;
        private TicketDao ticketDao;
        private long ticketId;


        public FinalizeTicketAsyncTask(SpotDao spotDao, TicketDao ticketDao, long ticketId) {
            this.spotDao = spotDao;
            this.ticketDao = ticketDao;
            this.ticketId = ticketId;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ParkingTicket ticket = ticketDao.getTicketByID(ticketId).get(0);
            Spot spot = spotDao.getSpotById(ticket.getSpotId()).get(0);

            ticket.setEndTime(System.currentTimeMillis());
            spot.setReserved(false);
            spot.setEmpty(true);
            spot.setTicketId(Spot.NULL_TICKET_ID);

            spotDao.updateSpot(spot);
            ticketDao.updateTicket(ticket);
            return null;
        }
    }

    private static class GetTicketsByUserIdAsyncTask extends AsyncTask<Void, Void, Void> {
        private TicketDao ticketDao;
        private SingleResultListener<List<ParkingTicket>> listener;
        private List<ParkingTicket> result;
        private int userId;

        public GetTicketsByUserIdAsyncTask(TicketDao ticketDao, int userId, SingleResultListener<List<ParkingTicket>> listener) {
            this.ticketDao = ticketDao;
            this.listener = listener;
            this.userId = userId;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            result = ticketDao.getByAttendentID(userId);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            listener.onResult(result);
        }
    }

}
