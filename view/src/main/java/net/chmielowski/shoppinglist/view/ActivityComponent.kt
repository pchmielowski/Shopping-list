package net.chmielowski.shoppinglist.view

import dagger.BindsInstance
import dagger.Subcomponent
import net.chmielowski.shoppinglist.view.addshop.AddShopFragment
import net.chmielowski.shoppinglist.view.items.ItemListFragment
import net.chmielowski.shoppinglist.view.items.NewItemListFragment
import net.chmielowski.shoppinglist.view.items.RemoveItemDialog
import net.chmielowski.shoppinglist.view.items.RemoveShopDialog
import net.chmielowski.shoppinglist.view.shops.ShopListFragment

@Subcomponent
interface ActivityComponent {
    fun inject(fragment: ItemListFragment)

    fun inject(fragment: AddShopFragment)

    fun inject(fragment: ShopListFragment)

    fun inject(dialog: RemoveItemDialog)

    fun inject(dialog: RemoveShopDialog)

    fun inject(fragment: NewItemListFragment)

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun bindActivity(activity: MainActivity): Builder

        fun build(): ActivityComponent
    }

    companion object {
        var instance: ActivityComponent? = null
            private set

        fun initializeWith(activity: MainActivity) {
            ActivityComponent.instance = ViewComponent.instance
                .plusActivityComponent()
                .bindActivity(activity)
                .build()
        }

        fun release() {
            instance = null
        }
    }
}