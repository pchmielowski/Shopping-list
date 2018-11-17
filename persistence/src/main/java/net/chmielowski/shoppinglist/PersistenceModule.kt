package net.chmielowski.shoppinglist

import dagger.Binds
import dagger.Module
import dagger.Provides
import net.chmielowski.shoppinglist.data.item.ItemRepository
import net.chmielowski.shoppinglist.data.item.RealItemRepository
import net.chmielowski.shoppinglist.data.shop.RealShopRepository
import net.chmielowski.shoppinglist.shop.ShopRepository

@Module
abstract class PersistenceModule {
    @Binds
    abstract fun bindItemRepository(impl: RealItemRepository): ItemRepository

    @Binds
    abstract fun bindShopRepository(impl: RealShopRepository): ShopRepository

    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideItemDao(db: AppDatabase) = db.itemDao

        @JvmStatic
        @Provides
        fun provideShopDao(db: AppDatabase) = db.shopDao
    }
}
