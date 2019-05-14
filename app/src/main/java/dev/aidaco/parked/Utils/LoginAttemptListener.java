package dev.aidaco.parked.Utils;

// TODO: 5/14/19 javadoc
public interface LoginAttemptListener {
    int SUCCESS = 0;
    int INC_USERNAME = 1;
    int INC_PASSWORD = 2;
    int INACTIVE = 3;

    void onResult(int resultCode);
}
