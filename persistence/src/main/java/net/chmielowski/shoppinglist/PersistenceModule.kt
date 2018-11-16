package net.chmielowski.shoppinglist

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Lazy
import dagger.Module
import dagger.Provides
import io.reactivex.Observable
import net.chmielowski.shoppinglist.data.item.ItemRepository
import net.chmielowski.shoppinglist.data.item.RealItemRepository
import net.chmielowski.shoppinglist.data.shop.ShopDao
import net.chmielowski.shoppinglist.shop.*
import net.chmielowski.shoppinglist.view.items.AddItemParams

@Module
abstract class PersistenceModule {
    @Binds
    abstract fun bindAddItem(impl: AddItem): ActionWithResult<AddItemParams, Item>

    @Binds
    abstract fun bindReadItems(impl: ReadItems): ActionWithResult<ReadItemsParams, List<@JvmSuppressWildcards Item>>

    @Binds
    abstract fun bindSetCompleted(impl: SetCompleted): CompletableAction<SetCompletedParams>

    @Binds
    abstract fun bindItemRepository(impl: RealItemRepository): ItemRepository

    @Binds
    abstract fun bindAddShop(impl: AddShop): ActionWithResult<AddShopParams, AddShopResult>

    @Binds
    internal abstract fun bindObserveShops(impl: ObserveShops): ObserveShopsType

    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideDb(context: Context) = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "room-db"
        )
            .fallbackToDestructiveMigration()
            .build()

        @JvmStatic
        @Provides
        fun provideItemDao(db: AppDatabase) = db.itemDao

        @JvmStatic
        @Provides
        fun provideShopDao(db: AppDatabase) = db.shopDao

        // TODO: extract class
        @JvmStatic
        @Provides
        fun provideShopRepository(dao: Lazy<ShopDao>) = object : ShopRepository {
            override fun observe(): Observable<List<ShopEntity>> {
                return dao.asSingle()
                    .flatMapObservable(ShopDao::getAll)
            }

            override fun add(entity: ShopEntity) = dao.asSingle()
                .map { it.insert(entity) }
        }
    }
}
