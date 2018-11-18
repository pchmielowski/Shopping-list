package net.chmielowski.shoppinglist.data

import dagger.Module
import dagger.Provides

@Module
abstract class PersistenceModule {
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
