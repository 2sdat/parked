package dev.aidaco.parked.ParkedTestUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import dev.aidaco.parked.Model.Entities.Spot;
import dev.aidaco.parked.Model.Enums;

public class SpotFactory {
    private static final int numSpots = 300;
    private static int curId = 0;
    public static HashMap<Integer, Spot> spots = new HashMap<>();
    public static HashSet<Integer> fullSpots = new HashSet<>();

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
