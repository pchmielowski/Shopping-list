package net.chmielowski.shoppinglist.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/*
 * A syntactic sugar over ViewModelProvider.Factory class.
 */
abstract class BaseViewModelFactory<T>(val creator: () -> T) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    final override fun <T : ViewModel?> create(modelClass: Class<T>) = creator() as T
}
