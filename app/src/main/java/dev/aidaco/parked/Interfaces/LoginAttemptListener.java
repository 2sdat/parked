package dev.aidaco.parked.Interfaces;

public interface LoginAttemptListener {
    int SUCCESS = 0;
    int INC_USERNAME = 1;
    int INC_PASSWORD = 2;

    void onResult(int resultCode);
}