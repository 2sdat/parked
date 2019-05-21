package dev.aidaco.parked.Utils;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;
import dev.aidaco.parked.Database.Daos.ClearAllDao;
import dev.aidaco.parked.Database.Daos.ParkingTicketDataDao;
import dev.aidaco.parked.Database.Daos.SpotDao;
import dev.aidaco.parked.Database.Daos.SpotDataDao;
import dev.aidaco.parked.Database.Daos.TicketDao;
import dev.aidaco.parked.Database.Daos.UserDao;
import dev.aidaco.parked.Database.ParkedDatabase;
import dev.aidaco.parked.Model.Entities.LicensePlate;
import dev.aidaco.parked.Model.Entities.ParkingTicket;
import dev.aidaco.parked.Model.Entities.ParkingTicketData;
import dev.aidaco.parked.Model.Entities.Spot;
import dev.aidaco.parked.Model.Entities.SpotData;
import dev.aidaco.parked.Model.Entities.User;
import dev.aidaco.parked.Model.Enums;
import dev.aidaco.parked.Model.PaymentCalculator;

public class ParkedRepository {

    private SpotDao spotDao;
    private SpotDataDao spotDataDao;
    private TicketDao ticketDao;
    private ParkingTicketDataDao ticketDataDao;
    private ClearAllDao clearAllDao;
    private ParkedDatabase parkedDb;

    private static ParkedRepository INSTANCE;

    private ParkedRepository(Context context) {
        parkedDb = ParkedDatabase.getInstance(context);
        spotDao = parkedDb.spotDao();
        ticketDao = parkedDb.ticketDao();
        spotDataDao = parkedDb.spotDataDao();
        ticketDataDao = parkedDb.ticketDataDao();
        clearAllDao = parkedDb.clearAllDao();
    }

    public static synchronized ParkedRepository getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new ParkedRepository(context);
        }

        return INSTANCE;
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

    public void getUserTicketCounts(int userId, DoubleResultListener<Integer, Integer> listener) {
        new UserTicketCounterAsyncTask(ticketDao, userId, listener).execute();
    }

    public void getTicketsByUserId(int userId, SingleResultListener<List<ParkingTicket>> listener) {
        new GetTicketsByUserIdAsyncTask(ticketDao, userId, listener).execute();
    }

    public void rebuildDatabase(int numCar, int numMotorcycle, int numTruck) {
        new RebuildDatabaseAsyncTask(clearAllDao, spotDao, parkedDb.userDao(), numCar, numMotorcycle, numTruck).execute();
    }

    public LiveData<List<SpotData>> getOccupiedSpots() {
        return spotDataDao.getOccupiedSpotswithData_LiveData();
    }

    public LiveData<SpotData> getSpotDataById(int id) {
        return spotDataDao.getSpotDataById_LiveData(id);
    }

    public LiveData<List<ParkingTicket>> getAllTickets() {
        return ticketDao.getAllTickets_LiveData();
    }

    public LiveData<ParkingTicketData> getTicketDataByIdLive(long ticketId) {
        return ticketDataDao.getTicketById_LiveData(ticketId);
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
                    ticketId = ParkingTicket.NULL_ID;
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
            ParkingTicket ticket = ticketDao.getTicketById(ticketId).get(0);
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
            ParkingTicket ticket = ticketDao.getTicketById(ticketId).get(0);
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
            ParkingTicket ticket = ticketDao.getTicketById(ticketId).get(0);
            Spot spot = spotDao.getSpotById(ticket.getSpotId()).get(0);

            ticket.setEndTime(System.currentTimeMillis());
            ticket.setTotalPrice(PaymentCalculator.calculateTotal(ticket));
            spot.setReserved(false);
            spot.setEmpty(true);
            spot.setTicketId(ParkingTicket.NULL_ID);

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
            result = ticketDao.getByAttendantId(userId);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            listener.onResult(result);
        }
    }

    private static class UserTicketCounterAsyncTask extends AsyncTask<Void, Void, Void> {
        private TicketDao ticketDao;
        private DoubleResultListener<Integer, Integer> listener;
        private int active = 0;
        private int total = 0;
        private int userId;

        public UserTicketCounterAsyncTask(TicketDao ticketDao, int userId, DoubleResultListener<Integer, Integer> listener) {
            this.ticketDao = ticketDao;
            this.listener = listener;
            this.userId = userId;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            List<ParkingTicket> tickets = ticketDao.getByAttendantId(userId);
            total = tickets.size();
            for (ParkingTicket ticket : tickets) {
                if (ticket.getEndTime() == ParkingTicket.NULL_END_TIME) {
                    active++;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            listener.onResult(active, total);
        }
    }

    private static class RebuildDatabaseAsyncTask extends AsyncTask<Void, Void, Void> {
        private ClearAllDao clearAllDao;
        private SpotDao spotDao;
        private UserDao userDao;

        private int numCar;
        private int numMoto;
        private int numTruck;

        public RebuildDatabaseAsyncTask(ClearAllDao clearAllDao, SpotDao spotDao, UserDao userDao, int numCar, int numMoto, int numTruck) {
            this.clearAllDao = clearAllDao;
            this.spotDao = spotDao;
            this.userDao = userDao;
            this.numCar = numCar;
            this.numMoto = numMoto;
            this.numTruck = numTruck;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            clearAllDao.wipeSpots();
            clearAllDao.wipeTickets();
            clearAllDao.wipeUsers();

            int offset = 0;
            for (int i = 1; i <= numCar; i++) {
                Spot spot = new Spot(i + offset, Enums.VehicleType.CAR, true, ParkingTicket.NULL_ID, false);
                spotDao.addSpot(spot);
            }

            offset += numCar;

            for (int i = 1; i <= numMoto; i++) {
                Spot spot = new Spot(i + offset, Enums.VehicleType.MOTORCYCLE, true, ParkingTicket.NULL_ID, false);
                spotDao.addSpot(spot);
            }

            offset += numMoto;

            for (int i = 1; i <= numTruck; i++) {
                Spot spot = new Spot(i + offset, Enums.VehicleType.TRUCK, true, ParkingTicket.NULL_ID, false);
                spotDao.addSpot(spot);
            }

            userDao.addUser(User.DEF_USER);
            return null;
        }
    }
}
