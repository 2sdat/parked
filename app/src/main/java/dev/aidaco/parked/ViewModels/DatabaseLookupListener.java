package dev.aidaco.parked.ViewModels;

public interface DatabaseLookupListener<T> {
    void onLookupReturned(T t);
}
