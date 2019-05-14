package dev.aidaco.parked.Utils;

// TODO: 5/14/19 javadoc
public interface CreateUserResultListener {
    int SUCCESS = 0;
    int INC_USERNAME = 1;
    int INC_PASSWORD = 2;
    int INC_ACCESS_PRIVELIGE = 3;
    int FAIL = 4;

    void onAttemptReturn(int statusCode, int newUserId);
}
