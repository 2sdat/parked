package dev.aidaco.parked.ParkedTestUtils;

import android.util.Log;

import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import dev.aidaco.parked.Model.Entities.LicensePlate;
import dev.aidaco.parked.Model.Entities.ParkingTicket;
import dev.aidaco.parked.Model.Enums;

public class TicketFactory {
    private static final String TAG = "TicketFactory";
    private static long curId = 0L;
    public static HashMap<Long, ParkingTicket> tickets = new HashMap<>();

    public static ParkingTicket genNewTicket(){
        long id = curId;
        curId += 1;
        LicensePlate lp = new LicensePlate(Integer.toString(new Random().nextInt(10000)), Enums.State.fromTypeCode(new Random().nextInt(49)));
        Enums.VehicleType vehicleType = Enums.VehicleType.fromTypeCode(new Random().nextInt(3));
        int spotId = 0;
        while (SpotFactory.fullSpots.contains(spotId)){
            spotId += 1;
        }
        SpotFactory.fullSpots.add(spotId);
        int attendentId = new Random().nextInt(UserFactory.numUsers);
        Enums.BillingType billingType = Enums.BillingType.fromTypeCode(new Random().nextInt(3));
        long parkTime = new Date().getTime();
        ParkingTicket ticket = new ParkingTicket(id, vehicleType, spotId, lp, attendentId, billingType, parkTime);
        tickets.put(id, ticket);
        Log.d(TAG, "New Ticket: " + Integer.toString(spotId) + " " + lp.getLicensePlateNumber() + " " + lp.getState().toString());
        return ticket;
    }
}
