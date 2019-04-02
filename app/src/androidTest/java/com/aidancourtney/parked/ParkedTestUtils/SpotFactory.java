package com.aidancourtney.parked.ParkedTestUtils;

import com.aidancourtney.parked.Model.Entities.Spot;
import com.aidancourtney.parked.Model.Enums;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class SpotFactory {
    private static final int numSpots = 300;
    private static int curId = 0;
    public static HashMap<Integer, Spot> spots = new HashMap<Integer, Spot>();
    public static HashSet<Integer> fullSpots = new HashSet<Integer>();

    public static void genSpots() {
        for (int i = 0; i < numSpots; i++) {
            int id = curId;
            Enums.VehicleType vehicleType = Enums.VehicleType.fromTypeCode(new Random().nextInt(3));
            Spot spot = new Spot(id, vehicleType, true, Spot.NULL_TICKET_ID);
            spots.put(id, spot);
            curId += 1;
        }
    }
}
