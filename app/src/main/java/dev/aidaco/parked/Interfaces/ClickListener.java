package dev.aidaco.parked.Interfaces;

public interface ClickListener<T> {
    void onClick(T t);

    void onLongClick(T t);
}
