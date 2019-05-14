package dev.aidaco.parked;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

// TODO: 5/14/19 javadoc
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: mainactivity init'd");
    }

    public void showSnackBar(String message, int snackBarLength) {
        Snackbar snackBar = Snackbar.make(findViewById(android.R.id.content), message, snackBarLength);
        snackBar.show();
    }
}
