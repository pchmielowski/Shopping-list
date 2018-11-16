package net.chmielowski.shoppinglist

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Lazy
import dagger.Module
import dagger.Provides
import net.chmielowski.shoppinglist.data.item.ItemRepository
import net.chmielowski.shoppinglist.data.item.RealItemRepository
import net.chmielowski.shoppinglist.data.shop.ShopDao
import net.chmielowski.shoppinglist.shop.ShopEntity
import net.chmielowski.shoppinglist.shop.ShopRepository

// TODO: Duplicated code with PersistenceModule
// TODO: move to test directory
@Module
abstract class TestPersistenceModule {
    @Binds
    abstract fun bindItemRepository(impl: RealItemRepository): ItemRepository

    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideDb(context: Context) = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()

        @JvmStatic
        @Provides
        fun provideItemDao(db: AppDatabase) = db.itemDao

        @JvmStatic
        @Provides
        fun provideShopDao(db: AppDatabase) = db.shopDao

        // TODO: extract class
        @JvmStatic
        @Provides
        fun provideShopRepository(dao: Lazy<ShopDao>) = object :
            ShopRepository {
            override fun observe() = dao.asSingle()
                .flatMapObservable { it.getAll().toObservable() }

            override fun add(entity: ShopEntity) = dao.asSingle()
                .map { it.insert(entity) }
        }
    }
}
