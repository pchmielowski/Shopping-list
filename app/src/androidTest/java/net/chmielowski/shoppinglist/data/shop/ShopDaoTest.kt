package net.chmielowski.shoppinglist.data.shop

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.chmielowski.shoppinglist.AppDatabase
import net.chmielowski.shoppinglist.IconId
import net.chmielowski.shoppinglist.ShopId
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

    private val icon1 = IconId(100)
    private val icon2 = IconId(200)
    private val icon3 = IconId(300)

    @Test
    fun getShopsWithNumberOfItems() {
        val db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), AppDatabase::class.java
        ).build()

        val thisShop = db.insertShopAndGetId("This", icon1)
        val otherShop = db.insertShopAndGetId("Other", icon2)
        val shopWithoutItems = db.insertShopAndGetId("Shop without items", icon3)

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

        db.shopDao.getAllWithUncompletedItemsCount()
            .test()
            .assertValue(
                listOf(
                    ShopWithItemsCount(thisShop, "This", null, icon1, 2),
                    ShopWithItemsCount(otherShop, "Other", null, icon2, 1),
                    ShopWithItemsCount(shopWithoutItems, "Shop without items", null, icon3, 0)
                )
            )
    }

    private fun AppDatabase.insertShopAndGetId(name: String, icon: IconId) =
        shopDao.insert(
            ShopEntity(
                name = name,
                color = null,
                icon = icon
            )
        ).let { ShopId(it.toInt()) }
}
