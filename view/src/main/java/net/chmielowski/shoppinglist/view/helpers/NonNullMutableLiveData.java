package net.chmielowski.shoppinglist.view.helpers;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

/**
 * Class is written in Java, because Kotlin does not allow overriding method returning T?, with a method returning T.
 * Java allows it by using @NonNull T return type, which is interpreted as T in Kotlin.
 */
public final class NonNullMutableLiveData<T> extends MutableLiveData<T> {

    public NonNullMutableLiveData(final T initial) {
        setValue(initial);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void setValue(@NonNull final T value) {
        if (value == null) {
            throw new IllegalArgumentException("Trying to set null value.");
        }
        super.setValue(value);
    }

    @SuppressWarnings("ConstantConditions")
    @NonNull
    @Override
    public T getValue() {
        return super.getValue();
    }
}
