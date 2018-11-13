package net.chmielowski.shoppinglist.view

import dagger.Subcomponent

@Subcomponent
interface ViewComponent {
    fun inject(fragment: ItemListFragment)

    fun inject(fragment: AddShopFragment)

    companion object {
        lateinit var instance: ViewComponent
    }
}
