package net.chmielowski.shoppinglist.view.items

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import dagger.Lazy
import net.chmielowski.shoppinglist.DeleteItemType
import net.chmielowski.shoppinglist.Id
import net.chmielowski.shoppinglist.view.BaseViewModelFactory
import javax.inject.Inject

class RemoveItemViewModel(private val delete: DeleteItemType) : ViewModel() {

    class Factory @Inject constructor(delete: Lazy<DeleteItemType>) :
        BaseViewModelFactory<RemoveItemViewModel>({
            RemoveItemViewModel(delete.get())
        })

    @SuppressLint("CheckResult")
    fun onRemoveItem(item: Id) {
        delete(item).subscribe()
    }
}
