package net.chmielowski.shoppinglist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dagger.Lazy
import net.chmielowski.shoppinglist.data.shop.ShopDao
import net.chmielowski.shoppinglist.shop.ColorEntity
import net.chmielowski.shoppinglist.shop.ObserveShops
import net.chmielowski.shoppinglist.shop.ShopWithItemsCount
import net.chmielowski.shoppinglist.view.IconMapper.drawableFromId
import net.chmielowski.shoppinglist.view.ShopViewModel
import net.chmielowski.shoppinglist.view.shops.ShopListViewModel
import org.junit.Rule
import org.junit.Test


class ShopListScreenTest {
    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Test
    fun `no shops visible`() {
        setupIoSchedulerForTests()
        val dao = ShopDao.Fake()
        val model = ShopListViewModel(ObserveShops(Lazy { dao }))

        model.noShops shouldHaveValue true

        dao.subject.onNext(
            listOf(
                ShopWithItemsCount(0, "Grocery", ColorEntity(1, 2), 3, 0),
                ShopWithItemsCount(1, "Hardware", ColorEntity(4, 1), 2, 0)
            )
        )
        model.noShops shouldHaveValue false
        model.shops shouldHaveValue listOf(
            ShopViewModel(
                0,
                ShopViewModel.Appearance(
                    "Grocery",
                    1 to 2,
                    drawableFromId(3)
                ),
                0
            ),
            ShopViewModel(
                1,
                ShopViewModel.Appearance(
                    "Hardware",
                    4 to 1,
                    drawableFromId(2)
                ),
                0
            )
        )
    }
}