package net.chmielowski.shoppinglist.view

import dagger.Subcomponent

@Subcomponent
interface ViewComponent {
    fun inject(fragment: ItemListFragment)

    companion object {
        lateinit var instance: ViewComponent
    }
}
