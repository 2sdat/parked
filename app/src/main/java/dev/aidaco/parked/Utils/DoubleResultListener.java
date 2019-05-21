package dev.aidaco.parked.Utils;


/**
 * Interface defining an interaction wherein the listener expects two
 * return values.
 *
 * @param <T> Return type one
 * @param <U> Return type two
 */
public interface DoubleResultListener<T, U> {
    void onResult(T t, U u);
}
