package net.chmielowski.shoppinglist.view.helpers;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

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
