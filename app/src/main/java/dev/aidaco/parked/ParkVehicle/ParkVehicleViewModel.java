package dev.aidaco.parked.ParkVehicle;

import android.app.Application;

import androidx.annotation.NonNull;
import dev.aidaco.parked.Utils.AttemptListener;
import dev.aidaco.parked.Utils.BaseViewModel;

/**
 * Implements the logic required by the ParkVehicle Fragment.
 *
 * @author Aidan Courtney
 * @see ParkVehicleFragment
 */
public class ParkVehicleViewModel extends BaseViewModel {

    private long ticketId;
    private int spotId;

    @SuppressWarnings("FieldCanBeLocal")
    private int timerLength = 60;

    private boolean isPaused = false;
    private boolean isDone = false;

    /**
     * Initializes the ViewModel.
     *
     * @param application The Application context
     */
    public ParkVehicleViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * Handles committing the ticket.
     * Either when the timer runs out or when the user clicks done.
     *
     * @param listener Listener to call when there is a result.
     */
    public void onDone(AttemptListener listener) {
        isDone = true;
        parkedRepo.finalizePark(ticketId, listener);
    }

    /**
     * Handles pausing the timer.
     */
    public void onPause() {
        isPaused = !isPaused;
    }

    /**
     * Handle cancelling the parking attempt.
     * @param listener Listener to call hwne there is a result.
     */
    public void onCancel(AttemptListener listener) {
        parkedRepo.cancelPark(ticketId, listener);
    }


    /**
     * Get the current spot ID.
     * @return The ID of the spot.
     */
    public int getSpotId() {
        return spotId;
    }


    /**
     * Set the current spot ID.
     * @param spotId ID of the spot.
     */
    public void setSpotId(int spotId) {
        this.spotId = spotId;
    }

    /**
     * Get the current ticket ID.
     * @return Ticket ID.
     */
    public long getTicketId() {
        return ticketId;
    }

    /**
     * Set the current ticket ID.
     * @param ticketId  Ticket ID.
     */
    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }

    /**
     * Get the length the timer should be.
     * @return Timer length.
     */
    public int getTimerLength() {
        return timerLength;
    }

    /**
     * Return whether the timer is paused.
     * @return Is paused.
     */
    public boolean isPaused() {
        return isPaused;
    }

    /**
     * Return whether parking is done.
     * @return Is done.
     */
    public boolean isDone() {
        return isDone;
    }
}
