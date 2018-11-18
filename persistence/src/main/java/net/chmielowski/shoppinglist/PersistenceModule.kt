package net.chmielowski.shoppinglist

import dagger.Binds
import dagger.Module
import dagger.Provides
import net.chmielowski.shoppinglist.data.item.ItemRepository
import net.chmielowski.shoppinglist.data.item.RealItemRepository

@Module
abstract class PersistenceModule {
    @Binds
    abstract fun bindItemRepository(impl: RealItemRepository): ItemRepository

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
