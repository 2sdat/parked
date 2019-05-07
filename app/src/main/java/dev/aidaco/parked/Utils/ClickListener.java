package dev.aidaco.parked.Utils;

public interface ClickListener<T> {
    void onClick(T t);

    void onLongClick(T t);
}
