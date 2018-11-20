package net.chmielowski.shoppinglist.view

import androidx.lifecycle.ViewModel
import dagger.Lazy
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.chmielowski.shoppinglist.DeleteType
import net.chmielowski.shoppinglist.Id
import net.chmielowski.shoppinglist.ItemQualifier
import net.chmielowski.shoppinglist.ShopQualifier
import javax.inject.Inject

class RemoveViewModel(
    private val delete: DeleteType,
    private val dispatcher: CoroutineDispatcher = IO
) : ViewModel() {

    class ForItemFactory @Inject constructor(@ItemQualifier delete: Lazy<DeleteType>) :
        BaseViewModelFactory<RemoveViewModel>({
            RemoveViewModel(delete.get())
        })

    class ForShopFactory @Inject constructor(@ShopQualifier delete: Lazy<DeleteType>) :
        BaseViewModelFactory<RemoveViewModel>({
            RemoveViewModel(delete.get())
        })

    fun onRemoveItem(item: Id) {
        GlobalScope.launch(dispatcher) {
            delete(item)
        }
    }
}
