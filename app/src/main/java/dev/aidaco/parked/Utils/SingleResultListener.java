package dev.aidaco.parked.Utils;


/**
 * Models an interaction wherein the listener expects a single return type.
 *
 * @param <T> Return type
 */
public interface SingleResultListener<T> {
    void onResult(T t);
}

