package net.chmielowski.shoppinglist.data.item

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import net.chmielowski.shoppinglist.data.AppDatabase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ItemDaoTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Test
    fun canInsertItemWithTheSameName_butDifferentShops() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }

        val db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), AppDatabase::class.java
        ).build()

        val entity = ItemEntity(name = "Item", shop = 1, quantity = "")
        db.itemDao.insert(entity)
        db.itemDao.insert(entity)
        db.itemDao.insert(entity.copy(shop = 2))

        db.itemDao.observeAllItems(1).test()
            .assertValue { it.single().name == "Item" }
        db.itemDao.observeAllItems(2).test()
            .assertValue { it.single().name == "Item" }
    }
}
