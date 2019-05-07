package dev.aidaco.parked.Utils;

public interface LoginAttemptListener {
    int SUCCESS = 0;
    int INC_USERNAME = 1;
    int INC_PASSWORD = 2;
    int INACTIVE = 3;

    void onResult(int resultCode);
}
