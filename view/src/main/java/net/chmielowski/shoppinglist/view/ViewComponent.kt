package net.chmielowski.shoppinglist.view

import dagger.Subcomponent
import net.chmielowski.shoppinglist.view.addshop.AddShopFragment
import net.chmielowski.shoppinglist.view.items.ItemListFragment
import net.chmielowski.shoppinglist.view.items.RemoveItemDialog
import net.chmielowski.shoppinglist.view.items.RemoveShopDialog
import net.chmielowski.shoppinglist.view.shops.ShopListFragment

@Subcomponent
interface ViewComponent {
    fun inject(fragment: ItemListFragment)

    fun inject(fragment: AddShopFragment)

    fun inject(fragment: ShopListFragment)

    fun inject(dialog: RemoveItemDialog)

    fun inject(dialog: RemoveShopDialog)

    companion object {
        lateinit var instance: ViewComponent
    }
}
