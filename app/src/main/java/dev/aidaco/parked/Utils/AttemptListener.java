package dev.aidaco.parked.Utils;


/**
 * Interface used throughout for the callback from any AsyncTasks representing
 * a task which can fail.
 *
 * @author Aidan Courtney
 */
public interface AttemptListener {
    int POS_SUCCESS = 0;
    int NEG_SUCCESS = 1;
    int FAIL = 2;

    void onReturnCode(int statusCode);

}
