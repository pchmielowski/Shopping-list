package net.chmielowski.shoppinglist.data

import dagger.Binds
import dagger.Module
import net.chmielowski.shoppinglist.*
import net.chmielowski.shoppinglist.data.item.AddItem
import net.chmielowski.shoppinglist.data.item.DeleteItem
import net.chmielowski.shoppinglist.data.item.ObserveItems
import net.chmielowski.shoppinglist.data.shop.AddShop
import net.chmielowski.shoppinglist.data.shop.DeleteShop
import net.chmielowski.shoppinglist.data.shop.GetShopAppearance
import net.chmielowski.shoppinglist.data.shop.ObserveShops

@Suppress("unused")
@Module
abstract class ActionModule {
    @ItemQualifier
    @Binds
    internal abstract fun bindDeleteItem(impl: DeleteItem): DeleteType

    @ShopQualifier
    @Binds
    internal abstract fun bindDeleteShop(impl: DeleteShop): DeleteType
}
