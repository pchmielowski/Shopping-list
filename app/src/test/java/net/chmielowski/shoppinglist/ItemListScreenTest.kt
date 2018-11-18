package net.chmielowski.shoppinglist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dagger.Lazy
import net.chmielowski.shoppinglist.data.item.ItemDao
import net.chmielowski.shoppinglist.data.shop.ReadShopName
import net.chmielowski.shoppinglist.data.shop.ShopDao
import net.chmielowski.shoppinglist.shop.ShopWithItemsCount
import net.chmielowski.shoppinglist.view.items.ItemViewModel
import net.chmielowski.shoppinglist.view.items.ItemsViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class ItemListScreenTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    private lateinit var model: ItemsViewModel

    private val shop = 0L
    private lateinit var shopDao: ShopDao.Fake
    private lateinit var itemDao: ItemDao.Fake

    @Before
    fun setUp() {
        setupIoSchedulerForTests()
        shopDao = ShopDao.Fake(listOf(ShopWithItemsCount(shop, "Fake name", null, 0, 0)))
        itemDao = ItemDao.Fake()
        model = ItemsViewModel(
            ReadShopName(Lazy { shopDao }),
            ObserveItems(Lazy { itemDao }),
            SetCompleted(Lazy { itemDao }),
            shop
        )
    }

    @Test
    fun `marking item as completed`() {
        itemDao.subject.onNext(
            listOf(
                ItemEntity(0, "Bread", shop = shop, quantity = ""),
                ItemEntity(1, "Butter", shop = shop, quantity = "")
            )
        )

        model.onToggled(0, true)
        model.items shouldHaveValue listOf(ItemViewModel(1, "Butter", false, quantity = ""))
    }
}
