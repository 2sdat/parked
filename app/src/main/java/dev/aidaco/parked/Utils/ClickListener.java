package dev.aidaco.parked.Utils;

// TODO: 5/14/19 javadoc
public interface ClickListener<T> {
    void onClick(T t);

    void onLongClick(T t);
}
