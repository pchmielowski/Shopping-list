package net.chmielowski.shoppinglist.view.items

import androidx.annotation.LayoutRes
import net.chmielowski.shoppinglist.view.BaseFragment
import net.chmielowski.shoppinglist.view.R
import net.chmielowski.shoppinglist.view.getViewModel
import net.chmielowski.shoppinglist.view.onBackPressedListeners
import javax.inject.Inject

abstract class BaseItemListFragment(@LayoutRes layout: Int) : BaseFragment(layout) {
    @Inject
    lateinit var itemsAdapter: ItemsAdapter

    @Inject
    lateinit var addItemModelFactoryBuilder: AddItemViewModel.Factory.Builder

    protected val addItemModel by getViewModel { addItemModelFactoryBuilder.build(shopId) }

    @Inject
    lateinit var itemsModelFactoryBuilder: ItemsViewModel.Factory.Builder

    protected val shopId
        get() = arguments!!.getId(getString(R.string.argument_shop_id))

    protected val itemsModel by getViewModel { itemsModelFactoryBuilder.build(shopId) }

    protected lateinit var onBackPressedListener: () -> Boolean

    override fun onDetach() {
        onBackPressedListeners.remove(onBackPressedListener)
        super.onDetach()
    }
}