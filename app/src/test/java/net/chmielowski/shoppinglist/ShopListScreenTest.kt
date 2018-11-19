package net.chmielowski.shoppinglist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dagger.Lazy
import net.chmielowski.shoppinglist.data.shop.ShopDao
import net.chmielowski.shoppinglist.data.shop.ColorEntity
import net.chmielowski.shoppinglist.data.shop.ObserveShops
import net.chmielowski.shoppinglist.data.shop.ShopWithItemsCount
import net.chmielowski.shoppinglist.view.IconMapper.drawableFromId
import net.chmielowski.shoppinglist.view.ShopViewModel
import net.chmielowski.shoppinglist.view.shops.*
import org.junit.Rule
import org.junit.Test


class ShopListScreenTest {
    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Test
    fun `no shops visible`() {
        setupIoSchedulerForTests()
        val dao = ShopDao.Fake()
        val model = ShopListViewModel(
            ObserveShops(Lazy { dao }),
            ShopViewModelMapper(
                Strings.Fake,
                ColorMapper.Fake,
                IconMapper2.Fake
            )
        )

        dao.subject.onNext(emptyList())
        model.noShops shouldHaveValue true

        dao.subject.onNext(
            listOf(
                ShopWithItemsCount(0, "Grocery", null, 0, 0),
                ShopWithItemsCount(1, "Hardware", null, 0, 0)
            )
        )
        model.noShops shouldHaveValue false
        model.shops shouldHaveValue listOf(
            ShopViewModel2.dummy(0, "Grocery"),
            ShopViewModel2.dummy(1, "Hardware")
        )
    }
}
