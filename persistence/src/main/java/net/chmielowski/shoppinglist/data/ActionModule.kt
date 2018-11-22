package net.chmielowski.shoppinglist.data

import dagger.Binds
import dagger.Module
import net.chmielowski.shoppinglist.DeleteType
import net.chmielowski.shoppinglist.ItemQualifier
import net.chmielowski.shoppinglist.ShopQualifier
import net.chmielowski.shoppinglist.data.item.DeleteItem
import net.chmielowski.shoppinglist.data.shop.DeleteShop

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
