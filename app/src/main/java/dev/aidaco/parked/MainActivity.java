package dev.aidaco.parked;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;


/**
 * Main entry point for the application.
 * Serves as a container that fragments are swapped into and out of.
 *
 * @author Aidan Courtney
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: mainactivity init'd");
    }

    /**
     * Display a snackbar with the given message and length.
     *
     * @param message        String message
     * @param snackBarLength Length
     */
    public void showSnackBar(String message, int snackBarLength) {
        Snackbar snackBar = Snackbar.make(findViewById(android.R.id.content), message, snackBarLength);
        snackBar.show();
    }
}
