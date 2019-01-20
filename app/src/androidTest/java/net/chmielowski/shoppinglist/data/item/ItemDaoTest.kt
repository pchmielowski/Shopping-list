package net.chmielowski.shoppinglist.data.item

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import net.chmielowski.shoppinglist.AppDatabase
import net.chmielowski.shoppinglist.ShopId
import net.chmielowski.shoppinglist.item.ItemEntity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ItemDaoTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    private val shop = ShopId(1)
    private val anotherShop = ShopId(2)

    @Test
    fun canInsertItemWithTheSameName_butDifferentShops() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }

        val db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), AppDatabase::class.java
        ).build()

        val entity = ItemEntity(name = "Item", shop = shop, quantity = "")
        db.itemDao.insert(entity)
        db.itemDao.insert(entity)
        db.itemDao.insert(entity.copy(shop = anotherShop))

        db.itemDao.observeAllItems(shop).test()
            .assertValue { it.single().name == "Item" }
        db.itemDao.observeAllItems(anotherShop).test()
            .assertValue { it.single().name == "Item" }
    }
}
