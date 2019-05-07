package dev.aidaco.parked;

import android.util.Log;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashSet;
import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;
import dev.aidaco.parked.Database.Daos.SpotDao;
import dev.aidaco.parked.Database.Daos.TicketDao;
import dev.aidaco.parked.Database.Daos.UserDao;
import dev.aidaco.parked.Database.ParkedDatabase;
import dev.aidaco.parked.Model.Entities.ParkingTicket;
import dev.aidaco.parked.Model.Entities.Spot;
import dev.aidaco.parked.Model.Entities.User;
import dev.aidaco.parked.Model.Enums;
import dev.aidaco.parked.ParkedTestUtils.LiveDataTestUtil;
import dev.aidaco.parked.ParkedTestUtils.SpotFactory;
import dev.aidaco.parked.ParkedTestUtils.TicketFactory;
import dev.aidaco.parked.ParkedTestUtils.UserFactory;

@RunWith(AndroidJUnit4.class)
public class ParkedDatabaseTest {
    private static final String TAG = "ParkedDatabaseTest";

    private ParkedDatabase db;
    private UserDao mUserDao;
    private SpotDao mSpotDao;
    private TicketDao mTicketDao;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDb() {
        db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), ParkedDatabase.class).build();
        mUserDao = db.userDao();
        mSpotDao = db.spotDao();
        mTicketDao = db.ticketDao();
    }

    @Before
    public void populateDb(){
        for (int i = 0; i < 15; i++) {
            mUserDao.addUser(UserFactory.genNewUser());
        }

        SpotFactory.genSpots();
        for (Spot spot : SpotFactory.spots.values()){
            mSpotDao.addSpot(spot);
        }

        for (int i = 0; i < 300; i++) {
            ParkingTicket t = TicketFactory.genNewTicket();
            mTicketDao.addTicket(t);
            try {
                Spot s = LiveDataTestUtil.getValue(mSpotDao.getByID(t.getSpotId()));
                mSpotDao.updateSpot(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testUserDao() {
        for ( User user : UserFactory.userList.values()) {
            try {
                User u = LiveDataTestUtil.getValue(mUserDao.getUserById(user.getId()));
                User u1 = LiveDataTestUtil.getValue(mUserDao.getByUserName(user.getUsername()));

                if (!u1.compare(user)) {
                    Log.d(TAG, "testUserDao: Failed with users" +
                            Integer.toString(u1.getId()) + " " + Integer.toString(user.getId()) + " " + Boolean.toString(u1.getId() == user.getId()) + "\n" +
                            u1.getFirstName() + " " + user.getFirstName() + " " + Boolean.toString(u1.getFirstName().equals(user.getFirstName())) + "\n " +
                            u1.getLastName() + " " + user.getLastName() + " " + Boolean.toString(u1.getLastName().equals(user.getLastName())) + "\n" +
                            u1.getPassword() + " " + user.getPassword() + " " + Boolean.toString(u1.getPassword().equals(user.getPassword())) + "\n" +
                            u1.getUsername() + " " + user.getUsername() + " " + Boolean.toString(u1.getUsername().equals(user.getUsername())) + "\n" +
                            Boolean.toString(u1.getIsActive()) + " " + Boolean.toString(user.getIsActive()) + " " + Boolean.toString(u1.getIsActive() == user.getIsActive()) + "\n" +
                            u1.getUserType().toString() + " " + user.getUserType().toString() + " " + Boolean.toString(u1.getUserType() == user.getUserType()));
                }
                Assert.assertTrue(u1.compare(user));
                Assert.assertTrue(u.compare(u1));
            } catch (Exception e) {
                e.printStackTrace();
                Assert.fail();
            }
        }

        User user = UserFactory.genNewUser();
        mUserDao.addUser(user);
        try {
            User u1 = LiveDataTestUtil.getValue(mUserDao.getUserById(user.getId()));
            User u2 = LiveDataTestUtil.getValue(mUserDao.getByUserName(user.getUsername()));
            Assert.assertTrue(u1.compare(u2));

            User user1 = new User(user.getId(), user.getUsername(), user.getPassword(), "Johnny", user.getLastName(), user.getUserType(), user.getIsActive());
            mUserDao.updateUser(user1);
            User user2 = LiveDataTestUtil.getValue(mUserDao.getUserById(user.getId()));
            Assert.assertEquals(user2.getFirstName(), user1.getFirstName());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testSpotDao(){
        try {
            List<Spot> spots = LiveDataTestUtil.getValue(mSpotDao.getAllSpots_LiveData());
            for (Spot spot : spots) {
                Assert.assertTrue(SpotFactory.spots.containsKey(spot.getId()));
                Assert.assertSame(SpotFactory.spots.get(spot.getId()).getSpotType(), spot.getSpotType());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
        try {
            List<Spot> emptySpots = LiveDataTestUtil.getValue(mSpotDao.getEmptySpots());
            for (Spot spot : emptySpots) {
                Assert.assertTrue(spot.getIsEmpty());
                Assert.assertEquals(spot.getTicketId(), Spot.NULL_TICKET_ID);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }

        try {

            List<Spot> fullSpots = LiveDataTestUtil.getValue(mSpotDao.getOccupiedSpots_LiveData());
            for (Spot spot : fullSpots) {
                Assert.assertFalse(spot.getIsEmpty());
                Assert.assertNotEquals(spot.getTicketId(), Spot.NULL_TICKET_ID);
                Assert.assertTrue(TicketFactory.tickets.containsKey(spot.getTicketId()));
                Assert.assertEquals(spot.getId(), TicketFactory.tickets.get(spot.getTicketId()).getSpotId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }

        try {
            List<Spot> allSpots = LiveDataTestUtil.getValue(mSpotDao.getAllSpots_LiveData());
            HashSet<Integer> takenIds = new HashSet();
            for (Spot s : allSpots) {
                takenIds.add(s.getId());
            }
            int id = 0;
            while (takenIds.contains(id)) {
                id++;
            }

            Spot spot = new Spot(id, Enums.VehicleType.CAR, true, Spot.NULL_TICKET_ID);
            mSpotDao.addSpot(spot);

            Spot spot1 = LiveDataTestUtil.getValue(mSpotDao.getByID(spot.getId()));
            Assert.assertTrue(spot1.compare(spot));
            spot.toggleIsEmpty();
            mSpotDao.updateSpot(spot);
            spot1 = LiveDataTestUtil.getValue(mSpotDao.getByID(spot.getId()));
            Assert.assertTrue(!spot1.getIsEmpty());
        } catch (InterruptedException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testTicketDao() {
        try {
            List<ParkingTicket> allTickets = LiveDataTestUtil.getValue(mTicketDao.getAllTickets_LiveData());
            for (ParkingTicket pt : allTickets) {
                assert (UserFactory.userList.containsKey(pt.getAttendentId()));
                assert (SpotFactory.spots.containsKey(pt.getSpotId()));
                assert (!SpotFactory.spots.get(pt.getSpotId()).getIsEmpty());

                Spot spot = LiveDataTestUtil.getValue(mSpotDao.getByID(pt.getSpotId()));
                ParkingTicket t = LiveDataTestUtil.getValue(mTicketDao.getTicketById(spot.getTicketId()));
                assert (t.equals(pt));

                t = LiveDataTestUtil.getValue(mTicketDao.getByFullPlate(pt.getLicensePlate().getLicensePlateNumber(), pt.getLicensePlate().getState()));
                assert (t.equals(pt));

                List<ParkingTicket> tlist = LiveDataTestUtil.getValue(mTicketDao.getByPlateNumber_LiveData(pt.getLicensePlate().getLicensePlateNumber()));
                assert (tlist.contains(pt));

                tlist = LiveDataTestUtil.getValue(mTicketDao.getByPlateState_LiveData(pt.getLicensePlate().getState()));
                assert (tlist.contains(pt));
            }

            for (User user : UserFactory.userList.values()) {
                List<ParkingTicket> parkByUser = LiveDataTestUtil.getValue(mTicketDao.getByAttendantId(user.getId()));
                for (ParkingTicket pt : parkByUser) {
                    assert (pt.getAttendentId() == user.getId());
                    assert (!SpotFactory.spots.get(pt.getSpotId()).getIsEmpty());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }



    @After
    public void closeDb() {
        db.close();
    }

}
