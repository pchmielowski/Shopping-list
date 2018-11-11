package net.chmielowski.shoppinglist

import dagger.Subcomponent
import net.chmielowski.shoppinglist.view.ItemListFragment

@Subcomponent
interface ViewComponent {
    fun inject(fragment: ItemListFragment)

    companion object {
        lateinit var instance: ViewComponent
    }
}
