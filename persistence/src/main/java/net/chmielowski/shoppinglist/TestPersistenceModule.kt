package net.chmielowski.shoppinglist

import dagger.Module
import dagger.Provides
import net.chmielowski.shoppinglist.data.item.ItemRepository
import net.chmielowski.shoppinglist.shop.ShopRepository
import javax.inject.Singleton

// TODO: move to test directory
@Module
abstract class TestPersistenceModule {
    @Module
    companion object {
        @Singleton
        @JvmStatic
        @Provides
        fun provideItemRepository(): ItemRepository =
            ItemRepository.Fake()

        @Singleton
        @JvmStatic
        @Provides
        fun provideShopRepository(): ShopRepository =
            ShopRepository.Fake()
    }
}