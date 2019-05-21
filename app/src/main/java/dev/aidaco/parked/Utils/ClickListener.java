package dev.aidaco.parked.Utils;


/**
 * Declares an action to be taken when a view is clicked.
 *
 * @param <T> Return type expected by the caller.
 * @author Aidan Courtney
 */
public interface ClickListener<T> {
    void onClick(T t);

    void onLongClick(T t);
}
