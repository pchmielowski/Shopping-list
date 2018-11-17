package net.chmielowski.shoppinglist

import dagger.Binds
import dagger.Module
import net.chmielowski.shoppinglist.shop.AddShop
import net.chmielowski.shoppinglist.shop.AddShopParams
import net.chmielowski.shoppinglist.shop.AddShopResult
import net.chmielowski.shoppinglist.shop.ObserveShops
import net.chmielowski.shoppinglist.view.items.AddItemParams

@Module
abstract class ActionModule {
    @Binds
    abstract fun bindAddItem(impl: AddItem): AddItemType

    @Binds
    abstract fun bindObserveItems(impl: ObserveItems): ObserveItemsType

    @Binds
    abstract fun bindSetCompleted(impl: SetCompleted): CompletableAction<SetCompletedParams>

    @Binds
    abstract fun bindAddShop(impl: AddShop): ActionWithResult<AddShopParams, AddShopResult>

    @Binds
    internal abstract fun bindObserveShops(impl: ObserveShops): ObserveShopsType
}