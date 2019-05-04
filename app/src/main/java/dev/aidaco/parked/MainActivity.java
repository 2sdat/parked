package dev.aidaco.parked;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import dev.aidaco.parked.ViewModels.ParkedViewModel;

public class MainActivity extends AppCompatActivity {

    private ParkedViewModel parkedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parkedViewModel = ViewModelProviders.of(this).get(ParkedViewModel.class);
    }
}
