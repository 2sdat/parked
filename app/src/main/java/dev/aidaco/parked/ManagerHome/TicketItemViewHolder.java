package dev.aidaco.parked.ManagerHome;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import androidx.recyclerview.widget.RecyclerView;
import dev.aidaco.parked.Model.Entities.ParkingTicket;
import dev.aidaco.parked.Model.Entities.User;
import dev.aidaco.parked.R;
import dev.aidaco.parked.Utils.ClickListener;
import dev.aidaco.parked.Utils.SingleResultListener;
import dev.aidaco.parked.Utils.UserRepository;

class TicketItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    private static final String TAG = "TicketItemViewHolder";
    private boolean RUNNING = false;

    private UserRepository userRepo;
    private Activity activity;
    private TextView ticketId;
    private TextView spotNumber;
    private TextView elapsedTime;
    private TextView attendant;
    private TextView licensePlate;
    private ClickListener<Long> listener;
    private long ticketIdNum;
    private long startTime;

    TicketItemViewHolder(View itemView, Activity activity) {
        super(itemView);
        itemView.setOnClickListener(this);
        this.userRepo = UserRepository.getInstance(activity);
        this.activity = activity;
        ticketId = itemView.findViewById(R.id.ticketListItem_TicketId);
        spotNumber = itemView.findViewById(R.id.ticketListItem_SpotNumber);
        elapsedTime = itemView.findViewById(R.id.ticketListItem_ElapsedTime);
        attendant = itemView.findViewById(R.id.ticketListItem_Attendant);
        licensePlate = itemView.findViewById(R.id.ticketListItem_LicensePlate);
    }

    @Override
    public void onClick(View v) {
        listener.onClick(ticketIdNum);
    }

    @Override
    public boolean onLongClick(View v) {
        listener.onLongClick(ticketIdNum);
        return false;
    }

    @SuppressLint("SetTextI18n")
    void setData(final ParkingTicket parkingTicket) {
        RUNNING = false;
        this.startTime = parkingTicket.getStartTime();
        long endTime = parkingTicket.getEndTime();
        this.ticketIdNum = parkingTicket.getId();
        ticketId.setText(Long.toString(ticketIdNum));
        spotNumber.setText(Integer.toString(parkingTicket.getSpotId()));
        licensePlate.setText(parkingTicket.getLicensePlate().toString());
        attendant.setText("--");
        elapsedTime.setText("--:--:--");

        Thread getAttendantThread = new Thread(new Runnable() {
            @Override
            public void run() {
                userRepo.getUserById(parkingTicket.getAttendentId(), new SingleResultListener<User>() {
                    @Override
                    public void onResult(User user) {
                        final String text = user.getFullName();
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                attendant.setText(text);
                            }
                        });
                    }
                });
            }
        });

        getAttendantThread.start();

        if (endTime == ParkingTicket.NULL_END_TIME) {
            RUNNING = true;
            Thread timerThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (RUNNING) {
                        try {
                            final long elapsed = System.currentTimeMillis() - startTime;
                            activity.runOnUiThread(new Runnable() {
                                final String text = formatElapsedTime(elapsed);

                                public void run() {
                                    elapsedTime.setText(text);
                                }
                            });
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            Log.d(TAG, "run: timerThread threw interrupted exception:" + e.getMessage());
                        } catch (NullPointerException e) {
                            Log.d(TAG, "run: timerThread threw nullpointerexception:" + e.getMessage());
                        }
                    }
                }
            });
            timerThread.start();
        } else {
            elapsedTime.setText(formatElapsedTime(endTime - startTime));
        }
    }

    public void setListener(ClickListener<Long> listener) {
        this.listener = listener;
    }

    @SuppressLint("DefaultLocale")
    private String formatElapsedTime(long elapsed) {
        long hours = TimeUnit.MILLISECONDS.toHours(elapsed);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(elapsed) -
                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(elapsed));
        long seconds = TimeUnit.MILLISECONDS.toSeconds(elapsed) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(elapsed));
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
