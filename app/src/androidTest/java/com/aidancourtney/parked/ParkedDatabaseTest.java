package com.aidancourtney.parked;

import android.util.Log;

import com.aidancourtney.parked.Model.Daos.SpotDao;
import com.aidancourtney.parked.Model.Daos.TicketDao;
import com.aidancourtney.parked.Model.Daos.UserDao;
import com.aidancourtney.parked.Model.Entities.ParkingTicket;
import com.aidancourtney.parked.Model.Entities.Spot;
import com.aidancourtney.parked.Model.Entities.User;
import com.aidancourtney.parked.Model.Enums;
import com.aidancourtney.parked.Model.ParkedDatabase;
import com.aidancourtney.parked.ParkedTestUtils.LiveDataTestUtil;
import com.aidancourtney.parked.ParkedTestUtils.SpotFactory;
import com.aidancourtney.parked.ParkedTestUtils.TicketFactory;
import com.aidancourtney.parked.ParkedTestUtils.UserFactory;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

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
                            u1.getFirstName() + " " + user.getFirstName() + " " + Boolean.toString(u1.getFirstName() == user.getFirstName()) + "\n " +
                            u1.getLastName() + " " + user.getLastName() + " " + Boolean.toString(u1.getLastName() == user.getLastName()) + "\n" +
                            u1.getPassword() + " " + user.getPassword() +  " " + Boolean.toString(u1.getPassword() == user.getPassword()) +"\n" +
                            u1.getUsername() + " " + user.getUsername() + " " + Boolean.toString(u1.getUsername() == user.getUsername()) + "\n" +
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
            Assert.assertEquals(u1, u2);

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
            List<Spot> spots = LiveDataTestUtil.getValue(mSpotDao.getAllSpots());
            for (Spot spot : spots) {
                Assert.assertTrue(SpotFactory.spots.containsKey(spot.getId()));
                Assert.assertTrue(SpotFactory.spots.get(spot.getId()).getSpotType() == spot.getSpotType());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
        try {
            List<Spot> emptySpots = LiveDataTestUtil.getValue(mSpotDao.getEmptySpots());
            for (Spot spot : emptySpots) {
                Assert.assertTrue(spot.getIsEmpty());
                Assert.assertTrue(spot.getTicketId() == Spot.NULL_TICKET_ID);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }

        try {

            List<Spot> fullSpots = LiveDataTestUtil.getValue(mSpotDao.getOccupiedSpots());
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

            Spot spot = new Spot(301, Enums.VehicleType.CAR, true, Spot.NULL_TICKET_ID);
            mSpotDao.addSpot(spot);

            Spot spot1 = LiveDataTestUtil.getValue(mSpotDao.getByID(spot.getId()));
            Assert.assertTrue(spot1.equals(spot));
            spot.toggleIsEmpty();
            mSpotDao.updateSpot(spot);
            spot1 = LiveDataTestUtil.getValue(mSpotDao.getByID(spot.getId()));
            Assert.assertTrue(!spot1.getIsEmpty());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testTicketDao() {
        try {
            List<ParkingTicket> allTickets = LiveDataTestUtil.getValue(mTicketDao.getAllTickets());
            for (ParkingTicket pt : allTickets) {
                assert (UserFactory.userList.containsKey(pt.getAttendentId()));
                assert (SpotFactory.spots.containsKey(pt.getSpotId()));
                assert (SpotFactory.spots.get(pt.getSpotId()).getIsEmpty() == false);

                Spot spot = LiveDataTestUtil.getValue(mSpotDao.getByID(pt.getSpotId()));
                ParkingTicket t = LiveDataTestUtil.getValue(mTicketDao.getTicketByID(spot.getTicketId()));
                assert (t.equals(pt));

                t = LiveDataTestUtil.getValue(mTicketDao.getByFullPlate(pt.getLicensePlate().getLicensePlateNumber(), pt.getLicensePlate().getState()));
                assert (t.equals(pt));

                List<ParkingTicket> tlist = LiveDataTestUtil.getValue(mTicketDao.getByPlateNumber(pt.getLicensePlate().getLicensePlateNumber()));
                assert (tlist.contains(pt));

                tlist = LiveDataTestUtil.getValue(mTicketDao.getByPlateState(pt.getLicensePlate().getState()));
                assert (tlist.contains(pt));
            }

            for (User user : UserFactory.userList.values()) {
                List<ParkingTicket> parkByUser = LiveDataTestUtil.getValue(mTicketDao.getByAttendentID(user.getId()));
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
