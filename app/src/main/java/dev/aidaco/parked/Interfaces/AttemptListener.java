package dev.aidaco.parked.Interfaces;

public interface AttemptListener {
    int POS_SUCCESS = 0;
    int NEG_SUCCESS = 1;
    int FAIL = 2;

    void onReturnCode(int statusCode);

}
