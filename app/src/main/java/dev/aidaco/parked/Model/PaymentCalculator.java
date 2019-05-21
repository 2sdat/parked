package dev.aidaco.parked.Model;

import java.util.Calendar;
import java.util.Date;

import dev.aidaco.parked.Model.Entities.ParkingTicket;

public class PaymentCalculator {
    private static final float GRACE_PERIOD_HOURS = 0.25f;
    private static final int EARLY_BIRD_START_TIME = 9;
    private static final int EARLY_BIRD_END_TIME = 5;
    private static final int OVERNIGHT_START_TIME = 20;
    private static final int OVERNIGHT_END_TIME = 8;

    private static final float EARLY_BIRD_TRUCK = 40f;
    private static final float EARLY_BIRD_CAR = 20f;
    private static final float EARLY_BIRD_MOTORCYCLE = 10f;

    private static final float HOURLY_TRUCK = 5f;
    private static final float HOURLY_CAR = 2.5f;
    private static final float HOURLY_MOTORCYCLE = 1f;

    private static final float OVERNIGHT_TRUCK = 40f;
    private static final float OVERNIGHT_CAR = 20f;
    private static final float OVERNIGHT_MOTOTRCYCLE = 10f;

    public static float calculateTotal(ParkingTicket ticket) {
        switch (ticket.getBillingType()) {
            case EARLY_BIRD:
                if (verifyEarlyBird(ticket)) {
                    return calculateEarlyBird(ticket);
                }
                break;

            case OVERNIGHT:
                if (verifyOvernight(ticket)) {
                    return calculateOvernight(ticket);
                }
                break;

            default:
                break;
        }

        return calculateHourly(ticket);

    }


    private static boolean verifyEarlyBird(ParkingTicket ticket) {
        Calendar cal = Calendar.getInstance();
        Date start = new Date();
        start.setTime(ticket.getStartTime());
        Date end = new Date();
        end.setTime(ticket.getEndTime());

        cal.setTime(start);
        float startHour = cal.get(Calendar.HOUR_OF_DAY);
        float startDiff = Math.abs(startHour - EARLY_BIRD_START_TIME);

        cal.setTime(end);
        float endHour = cal.get(Calendar.HOUR_OF_DAY);
        float endDiff = Math.abs(endHour - EARLY_BIRD_END_TIME);

        return !(startDiff > GRACE_PERIOD_HOURS) && !(endDiff > GRACE_PERIOD_HOURS);
    }

    private static boolean verifyOvernight(ParkingTicket ticket) {
        Calendar cal = Calendar.getInstance();
        Date start = new Date();
        start.setTime(ticket.getStartTime());
        Date end = new Date();
        end.setTime(ticket.getEndTime());

        cal.setTime(start);
        float startHour = cal.get(Calendar.HOUR_OF_DAY);
        float startDiff = Math.abs(startHour - OVERNIGHT_START_TIME);

        cal.setTime(end);
        float endHour = cal.get(Calendar.HOUR_OF_DAY);
        float endDiff = Math.abs(endHour - OVERNIGHT_END_TIME);

        return !(startDiff > GRACE_PERIOD_HOURS) && !(endDiff > GRACE_PERIOD_HOURS);
    }

    private static float calculateEarlyBird(ParkingTicket ticket) {
        switch (ticket.getVehicleType()) {
            case CAR:
                return EARLY_BIRD_CAR;
            case TRUCK:
                return EARLY_BIRD_TRUCK;
            case MOTORCYCLE:
                return EARLY_BIRD_MOTORCYCLE;
        }

        throw new IllegalArgumentException("Invalid vehicle type");
    }

    private static float calculateHourly(ParkingTicket ticket) {
        long endTime = ticket.getEndTime();
        if (endTime == ParkingTicket.NULL_END_TIME) {
            endTime = System.currentTimeMillis();
        }
        long elapsedTimeMillis = endTime - ticket.getStartTime();
        float elapsedTimeHours = ((elapsedTimeMillis / 1000f) / 60f) / 60f;
        switch (ticket.getVehicleType()) {
            case CAR:
                return Math.round(elapsedTimeHours * HOURLY_CAR * 100f) / 100f;
            case TRUCK:
                return Math.round(elapsedTimeHours * HOURLY_TRUCK * 100f) / 100f;
            case MOTORCYCLE:
                return Math.round(elapsedTimeHours * HOURLY_MOTORCYCLE * 100f) / 100f;
        }

        throw new IllegalArgumentException("Invalid vehicle type");
    }

    private static float calculateOvernight(ParkingTicket ticket) {
        switch (ticket.getVehicleType()) {
            case CAR:
                return OVERNIGHT_CAR;
            case TRUCK:
                return OVERNIGHT_TRUCK;
            case MOTORCYCLE:
                return OVERNIGHT_MOTOTRCYCLE;
        }

        throw new IllegalArgumentException("Invalid vehicle type");
    }
}
