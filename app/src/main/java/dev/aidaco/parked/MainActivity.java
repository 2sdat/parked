package dev.aidaco.parked;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showSnackBar(String message, int snackBarLength) {
        Snackbar snackBar = Snackbar.make(findViewById(android.R.id.content), message, snackBarLength);
        snackBar.show();
    }
}
