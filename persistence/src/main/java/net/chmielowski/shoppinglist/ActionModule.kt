package net.chmielowski.shoppinglist

import dagger.Binds
import dagger.Module
import net.chmielowski.shoppinglist.data.item.DeleteItem
import net.chmielowski.shoppinglist.data.shop.ReadShopName
import net.chmielowski.shoppinglist.shop.AddShop
import net.chmielowski.shoppinglist.shop.ObserveShops

@Suppress("unused")
@Module
abstract class ActionModule {
    @Binds
    internal abstract fun bindAddItem(impl: AddItem): AddItemType

    @Binds
    internal abstract fun bindObserveItems(impl: ObserveItems): ObserveItemsType

    @Binds
    internal abstract fun bindSetCompleted(impl: SetCompleted): CompletableAction<SetCompletedParams>

    @Binds
    internal abstract fun bindAddShop(impl: AddShop): AddShopType

    @Binds
    internal abstract fun bindObserveShops(impl: ObserveShops): ObserveShopsType

    @Binds
    internal abstract fun bindReadShopName(impl: ReadShopName): ReadShopNameType

    @Binds
    internal abstract fun bindDeleteItem(impl: DeleteItem): DeleteItemType
}