package net.chmielowski.shoppinglist.data.shop

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.chmielowski.shoppinglist.AppDatabase
import net.chmielowski.shoppinglist.item.ItemEntity
import net.chmielowski.shoppinglist.shop.ShopEntity
import net.chmielowski.shoppinglist.shop.ShopWithItemsCount
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

        val thisShop = db.shopDao.insert(
            ShopEntity(
                name = "This",
                color = null,
                icon = 100
            )
        )
        val otherShop = db.shopDao.insert(
            ShopEntity(
                name = "Other",
                color = null,
                icon = 200
            )
        )
        db.shopDao.insert(
            ShopEntity(
                name = "Shop without items",
                color = null,
                icon = 300
            )
        )

        db.itemDao.insert(
            ItemEntity(
                name = "This shop item 1",
                shop = thisShop,
                quantity = ""
            )
        )
        db.itemDao.insert(
            ItemEntity(
                name = "This shop item 2",
                shop = thisShop,
                quantity = ""
            )
        )
        db.itemDao.insert(
            ItemEntity(
                name = "This shop item 3",
                shop = thisShop,
                completed = true,
                quantity = ""
            )
        )
        db.itemDao.insert(
            ItemEntity(
                name = "Other shop item",
                shop = otherShop,
                quantity = ""
            )
        )

        db.shopDao.getAllWithUncompletedItemsCount().test().assertValue(
            listOf(
                ShopWithItemsCount(1, "This", null, 100, 2),
                ShopWithItemsCount(2, "Other", null, 200, 1),
                ShopWithItemsCount(3, "Shop without items", null, 300, 0)
            )
        )
    }
}
