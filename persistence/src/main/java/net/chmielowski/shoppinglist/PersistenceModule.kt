package net.chmielowski.shoppinglist

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
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
        ).build()

        @JvmStatic
        @Provides
        fun provideDao(db: AppDatabase) = db.dao

        @JvmStatic
        @Provides
        fun provideShopRepository() = object : ShopRepository {
            override fun add(entity: ShopEntity) = TODO("not implemented")
        }
    }
}
