package dev.aidaco.parked;

import android.os.Bundle;

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
}
