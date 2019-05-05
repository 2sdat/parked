package dev.aidaco.parked;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import dev.aidaco.parked.ViewModels.UserHomeViewModel;

public class MainActivity extends AppCompatActivity {

    private UserHomeViewModel userHomeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userHomeViewModel = ViewModelProviders.of(this).get(UserHomeViewModel.class);
    }

    public void showSnackBar(String message, int snackBarLength) {
        Snackbar snackBar = Snackbar.make(findViewById(android.R.id.content), message, snackBarLength);
        snackBar.show();
    }
}
