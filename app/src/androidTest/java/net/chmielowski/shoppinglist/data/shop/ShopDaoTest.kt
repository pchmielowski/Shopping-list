package net.chmielowski.shoppinglist.data.shop

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.chmielowski.shoppinglist.AppDatabase
import net.chmielowski.shoppinglist.ItemEntity
import net.chmielowski.shoppinglist.shop.ShopEntity
import net.chmielowski.shoppinglist.shop.ShopWithItemNumber
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ShopDaoTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Test
    fun getShopsWithNumberOfItems() {
        val db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), AppDatabase::class.java
        ).build()

        val thisShop = db.shopDao.insert(ShopEntity("This", null, 0))
        val otherShop = db.shopDao.insert(ShopEntity("Other", null, 0))

        db.itemDao.insert(ItemEntity(name = "This shop item 1", shop = thisShop))
        db.itemDao.insert(ItemEntity(name = "This shop item 2", shop = thisShop))
        db.itemDao.insert(ItemEntity(name = "Other shop item", shop = otherShop))

        db.shopDao.getAllAlt().test().assertValue(
            listOf(
                ShopWithItemNumber(1, "This", 2),
                ShopWithItemNumber(2, "Other", 1)
            )
        )
    }
}
