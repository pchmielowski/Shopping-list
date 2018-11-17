package net.chmielowski.shoppinglist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import net.chmielowski.shoppinglist.shop.*
import net.chmielowski.shoppinglist.view.addshop.IconViewModel
import net.chmielowski.shoppinglist.view.shops.ShopListViewModel
import net.chmielowski.shoppinglist.view.shops.ShopViewModel
import org.junit.Rule
import org.junit.Test


class ShopListScreenTest {
    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Test
    fun `no shops visible`() {
        val repo = ShopRepository.Fake()
        val model = ShopListViewModel(ObserveShops(repo))

        repo.observe.onNext(emptyList())
        model.noShops shouldHaveValue true

        repo.observe.onNext(
            listOf(
                ShopWithItemsCount(0, "Grocery", ColorEntity(1, 2), 3, 0),
                ShopWithItemsCount(1, "Hardware", ColorEntity(4, 1), 2, 0)
            )
        )
        model.noShops shouldHaveValue false
        model.shops shouldHaveValue listOf(
            ShopViewModel(0, "Grocery", 1 to 2, IconViewModel.drawable(3), 0),
            ShopViewModel(1, "Hardware", 4 to 1, IconViewModel.drawable(2), 0)
        )
    }
}