package net.chmielowski.shoppinglist.view

import dagger.Subcomponent
import net.chmielowski.shoppinglist.view.addshop.AddShopFragment
import net.chmielowski.shoppinglist.view.items.ItemListFragment

@Subcomponent
interface ViewComponent {
    fun inject(fragment: ItemListFragment)

    fun inject(fragment: AddShopFragment)

    fun inject(fragment: ShopListFragment)

    companion object {
        lateinit var instance: ViewComponent
    }
}
