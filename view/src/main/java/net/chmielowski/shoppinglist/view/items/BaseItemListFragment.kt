package net.chmielowski.shoppinglist.view.items

import androidx.annotation.LayoutRes
import net.chmielowski.shoppinglist.ShopId
import net.chmielowski.shoppinglist.view.BaseFragment
import net.chmielowski.shoppinglist.view.R
import net.chmielowski.shoppinglist.view.onBackPressedListeners
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

abstract class BaseItemListFragment(@LayoutRes layout: Int) : BaseFragment(layout) {

    protected val itemsAdapter by inject<ItemsAdapter>()

    protected val addItemModel by viewModel<AddItemViewModel> { shopId }

    protected val itemsModel by viewModel<ItemsViewModel> { shopId }

    private val shopId get() = parametersOf(ShopId(arguments!!.getInt(getString(R.string.argument_shop_id))))

    protected lateinit var onBackPressedListener: () -> Boolean

    override fun onDetach() {
        onBackPressedListeners.remove(onBackPressedListener)
        super.onDetach()
    }
}