package net.chmielowski.shoppinglist.view

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import dagger.Lazy
import net.chmielowski.shoppinglist.DeleteType
import net.chmielowski.shoppinglist.Id
import net.chmielowski.shoppinglist.ItemQualifier
import net.chmielowski.shoppinglist.ShopQualifier
import javax.inject.Inject

class RemoveViewModel(private val delete: DeleteType) : ViewModel() {

    class ForItemFactory @Inject constructor(@ItemQualifier delete: Lazy<DeleteType>) :
        BaseViewModelFactory<RemoveViewModel>({
            RemoveViewModel(delete.get())
        })

    class ForShopFactory @Inject constructor(@ShopQualifier delete: Lazy<DeleteType>) :
        BaseViewModelFactory<RemoveViewModel>({
            RemoveViewModel(delete.get())
        })

    @SuppressLint("CheckResult")
    fun onRemoveItem(item: Id) {
        delete(item).subscribe()
    }
}
